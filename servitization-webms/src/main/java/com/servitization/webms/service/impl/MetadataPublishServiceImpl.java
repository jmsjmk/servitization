package com.servitization.webms.service.impl;

import com.alibaba.fastjson.JSON;
import com.servitization.commons.business.agent.entity.CustomizeParameterEntity;
import com.servitization.metadata.zk.Constants;
import com.servitization.metadata.zk.PushState;
import com.servitization.metadata.zk.ZKBaseStructureBuilder;
import com.servitization.metadata.zk.ZKConnection;
import com.servitization.webms.common.ConstantValue;
import com.servitization.webms.entity.MetadataMachine;
import com.servitization.webms.entity.MetadataPublish;
import com.servitization.webms.entity.MetadataPublishIp;
import com.servitization.webms.entity.MetadataVersion;
import com.servitization.webms.http.AosAgent;
import com.servitization.webms.http.entity.AosMachine;
import com.servitization.webms.http.entity.GetAosMachineByNodeResp;
import com.servitization.webms.mapper.MetadataPublishIpMapper;
import com.servitization.webms.mapper.MetadataPublishMapper;
import com.servitization.webms.mapper.MetadataVersionMapper;
import com.servitization.webms.service.IConfigProvider;
import com.servitization.webms.service.IMetadataPublishService;
import com.servitization.webms.task.UpdateStatusTask;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class MetadataPublishServiceImpl implements IMetadataPublishService {

    private static final Logger LOGGER = Logger.getLogger(MetadataPublishServiceImpl.class);
    ExecutorService service = Executors.newCachedThreadPool();
    @Resource
    private AosAgent aosAgent;
    @Resource
    private IConfigProvider configProvider;
    @Resource
    private MetadataPublishMapper publishMapper;
    @Resource
    private MetadataVersionMapper versionMapper;
    @Resource
    private MetadataPublishIpMapper publishIpMapper;
    @Resource
    private MetadataMachineServiceImpl metadataMachineService;

    @Override
    public int publish(int nodeId, long nodeRelationId, long metadataId, long versionId) {
        String url = configProvider.Get("getAosMachineByNode.url") + nodeId + "/servers";

        CustomizeParameterEntity entity = new CustomizeParameterEntity();
        entity.setUrl(url);

        String param = "authkey=" + ConstantValue.KEY + "&secret=" + ConstantValue.SECRET;
        // 根据AOS节点获取机器IP列表
        GetAosMachineByNodeResp aosMachineByNodeResp = aosAgent.getAosMachineByNode(param, entity);
        if (aosMachineByNodeResp == null || aosMachineByNodeResp.getCode() != 200) {
            return 1;// 调用AOS为空
        }
        List<AosMachine> machines = aosMachineByNodeResp.getData();
        // 根据版本ID查出一条元数据
        MetadataVersion version = versionMapper.getMetadataVersionById(versionId);
        // 首先插入一条任务记录
        MetadataPublish publish = new MetadataPublish();
        publish.setMetadataVersionId(versionId);
        publish.setNodeRelationId(nodeRelationId);
        publish.setStatus(0);
        publish.setCreateTime(new Date());
        publishMapper.addPublish(publish);
        try {
            // 建立zk树结构（如果没有的话）
            ZKBaseStructureBuilder.buildBaseStructure();
            ZooKeeper zk = ZKConnection.zk();
            for (int i = 0; i < machines.size(); i++) {
                String ip = machines.get(i).getHostname();
                // //测试代码
                // ip = "EBJ1167.local";
                // //测试代码结束
                MetadataPublishIp metadataPublishIp = new MetadataPublishIp();
                metadataPublishIp.setIp(ip);
                metadataPublishIp.setCreateTime(new Date());
                metadataPublishIp.setUpdateTime(new Date());
                metadataPublishIp.setPublishId(publish.getId());
                // 在ZK上寻找节点 并设置推送内容及状态
                if (zk.exists(Constants.push + "/" + ip, false) != null) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put(Constants.push_meta, version.getMetadataXml());
                    map.put(Constants.push_ack, PushState.UN_SYNCHRONOUS.toString());
                    map.put(Constants.push_version, String.valueOf(version.getId()));
                    zk.setData(Constants.push + "/" + ip, JSON.toJSONString(map).getBytes(), -1);
                    // 下面在数据库中统一写入ip记录
                } else {
                    // 在数据库中设置IP失败记录
                    metadataPublishIp.setStatus(5);
                }
                publishIpMapper.addPublishIp(metadataPublishIp);
            }
        } catch (Exception e) {
            LOGGER.error("推送异常", e);
            return 2;
        }
        return 0;
    }


    public void initIpList(String ips, List<String> ipList) {
        if (StringUtils.isEmpty(ips)) {
            return;
        }
        String[] ipArray = ips.split(",");
        for (String s : ipArray) {
            ipList.add(s);
        }
    }


    public List<String> getMachineIps(List<MetadataMachine> machines) {
        List<String> ips = new ArrayList<>();
        if (machines == null || machines.size() == 0) {
            return ips;
        }
        for (MetadataMachine metadataMachine : machines) {
            ips.add(metadataMachine.getIp());
        }
        return ips;
    }


    /**
     * metadata_publish:status[0:正在发布 ;1:发布成功; 2:发布失败]
     * metadata_publish_id:status[0:等待同步 1:正在同步 2:加载成功 3:加载失败 4:更新状态超时 5:ZK节点不存在]
     * <p>
     * node-ips:其中有一台机器出现了问题。认为本次发布失败。
     *
     * @param versionIdStr
     * @return
     */
    public int publishNew(String versionIdStr, String metadataIdStr) {
        List<String> ipList;
        long versionId = Long.parseLong(versionIdStr);
        MetadataVersion version = versionMapper.getMetadataVersionById(versionId);
        long metadataId = Long.valueOf(metadataIdStr);

        List<MetadataMachine> machines = metadataMachineService.getMachineListByMtdtId(String.valueOf(metadataId));
        ipList = getMachineIps(machines);
        if (null == ipList || ipList.size() == 0) {
            LOGGER.error("MetadataId[" + metadataId + "] no hava machines!!!");
            return 3;
        }
        // 首先插入一条任务记录(插入数据表,这事发布状态表记录数据正在发布中)
        MetadataPublish publish = new MetadataPublish();
        publish.setMetadataVersionId(versionId);
        publish.setNodeRelationId(metadataId);
        publish.setStatus(0);
        publish.setCreateTime(new Date());
        publishMapper.addPublish(publish);
        try {
            // 建立zk树结构（如果没有的话）
            ZKBaseStructureBuilder.buildBaseStructure();
            ZooKeeper zk = ZKConnection.zk();
            for (int i = 0; i < ipList.size(); i++) {
                String ip = ipList.get(i);
                // 插入数据库表
                MetadataPublishIp metadataPublishIp = new MetadataPublishIp();
                metadataPublishIp.setIp(ip);
                metadataPublishIp.setCreateTime(new Date());
                metadataPublishIp.setUpdateTime(new Date());
                metadataPublishIp.setPublishId(publish.getId());
                // 在ZK上寻找节点 并设置推送内容及状态
                if (zk.exists(Constants.push + "/" + ip, false) != null) {
                    Map<String, String> map = new HashMap<>();
                    map.put(Constants.push_meta, version.getMetadataXml());
                    map.put(Constants.push_ack, PushState.UN_SYNCHRONOUS.toString());
                    map.put(Constants.push_version, String.valueOf(version.getId()));
                    zk.setData(Constants.push + "/" + ip, JSON.toJSONString(map).getBytes(), -1);
                } else {
                    metadataPublishIp.setStatus(5);
                }
                publishIpMapper.addPublishIp(metadataPublishIp);
            }
        } catch (Exception e) {
            LOGGER.error("推送异常", e);
            return 2;
        }
        return 0;
    }

    @Override
    public int getPublishedVersionIdByRelationId(long nodeRelationId) {
        int result = 0;
        Integer integer = publishMapper.getPublishedVersionIdByRelationId(nodeRelationId);
        if (integer != null) {
            result = integer.intValue();
        }
        return result;
    }

    @Override
    public void updataPublishState() {
        UpdateStatusTask task = new UpdateStatusTask(publishMapper, publishIpMapper);
        service.execute(task);
    }

    @Override
    public List<MetadataPublish> getPublishHistoryByNodeRelationId(Map params) {
        return publishMapper.getPublishHistoryByNodeRelationId(params);
    }

    @Override
    public int getPublishHistoryCount(Map<String, Object> params) {
        return publishMapper.getPublishHistoryCount(params);
    }

    @Override
    public List<MetadataPublishIp> getPublishDetail(long publishId) {
        return publishIpMapper.getPublishIpsByJobId(publishId);
    }

}
