package com.servitization.webms.task;

import com.alibaba.fastjson.JSON;
import com.servitization.metadata.zk.Constants;
import com.servitization.metadata.zk.PushState;
import com.servitization.metadata.zk.ZKConnection;
import com.servitization.webms.entity.MetadataPublish;
import com.servitization.webms.entity.MetadataPublishIp;
import com.servitization.webms.mapper.MetadataPublishIpMapper;
import com.servitization.webms.mapper.MetadataPublishMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.zookeeper.ZooKeeper;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateStatusTask implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(UpdateStatusTask.class);
    private int times = 0;
    private MetadataPublishMapper publishMapper;
    private MetadataPublishIpMapper publishIpMapper;
    private long metadataId;

    public UpdateStatusTask(MetadataPublishMapper publishMapper,
                            MetadataPublishIpMapper publishIpMapper) {
        this.publishIpMapper = publishIpMapper;
        this.publishMapper = publishMapper;
    }

    public long getMetadataId() {
        return metadataId;
    }

    public void setMetadataId(long metadataId) {
        this.metadataId = metadataId;
    }

    /**
     * metadata_publish:status[0:正在发布 ;1:发布成功; 2:发布失败]
     * metadata_publish_id:status[0:等待同步 1:正在同步 2:加载成功 3:加载失败 4:更新状态超时 5:ZK节点不存在]
     * <p>
     * node-ips:其中有一台机器出现了问题。认为本次发布失败。
     */
    @Override
    public void run() {
        while (times < 10) {
            times++;
            LOGGER.info("\n\n\n" + "********* time[" + times + "] ThreadName[" + Thread.currentThread().getName() + "]");
            MetadataPublish publishingJob = publishMapper.getPublishingJob();
            if (publishingJob != null) {
                //取得正在发布的机器列表
                List<MetadataPublishIp> publishIps = publishIpMapper.getPublishIpsByJobId(publishingJob.getId());
                //去zk上取状态信息
                ZooKeeper zk = ZKConnection.zk();
                int allCount = publishIps.size();
                int unCount = 0;
                int successCount = 0;
                int failCount = 0;
                int timeOutCount = 0;
                LOGGER.info("MachIne IpSize [" + publishIps.size() + "]");
                for (MetadataPublishIp publishIp : publishIps) {
                    String ip = publishIp.getIp();
                    String path = Constants.push + "/" + ip;
                    try {
                        byte[] bytes = zk.getData(path, false, null);
                        String dataString = new String(bytes);
                        LOGGER.info("Path[" + path + " ] dataString[" + dataString + "]");
                        Map map = JSON.parseObject(dataString, HashMap.class);
                        String ackString = (String) map.get(Constants.push_ack);
                        if (StringUtils.equals(ackString, PushState.UN_SYNCHRONOUS.toString())) {
                            if (times < 10) {
                                publishIp.setStatus(0);
                                unCount++;
                            } else {
                                publishIp.setStatus(4);
                                timeOutCount++;
                            }
                        } else if (StringUtils.equals(ackString, PushState.IN_SYNCHRONOUS.toString())) {
                            if (times < 10) {
                                publishIp.setStatus(1);
                            } else {
                                publishIp.setStatus(4);
                                timeOutCount++;
                            }
                        } else if (StringUtils.equals(ackString, PushState.SYNCHRONOUS_SUCCESS.toString())) {
                            publishIp.setStatus(2);
                            successCount++;
                        } else if (StringUtils.equals(ackString, PushState.SYNCHRONOUS_FAILED.toString())) {
                            publishIp.setStatus(3);
                            failCount++;
                        }
                    } catch (Exception e) {
                        LOGGER.error("获取状态异常IP:" + ip, e);
                    }
                    publishIp.setUpdateTime(new Date());
                }
                //批量更新ip表状态
                publishIpMapper.batchUpdateStatus(publishIps);
                //判断状态
                if (successCount == allCount) {
                    //全部成功
                    publishingJob.setStatus(1);
                    times = 11;
                } else if ((unCount == 0 && failCount > 0) || ((unCount == 0 && timeOutCount > 0))) {
                    //没有未同步 有失败机器
                    publishingJob.setStatus(2);
                    // 只要有一台机器发生了错误认为机器更新错误。直接推出。
                    times = 11;
                } else if (failCount > 0) {
                    publishingJob.setStatus(2);
                    times = 11;
                }
                //更新总体任务状态
                publishMapper.updatePublishStatus(publishingJob);
            } else {
                LOGGER.error("未获取到正在部署的任务当前对应MetadataId[" + metadataId + "]");
            }
            try {
                Thread.currentThread().sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
