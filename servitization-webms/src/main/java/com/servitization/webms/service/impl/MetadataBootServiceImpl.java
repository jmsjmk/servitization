package com.servitization.webms.service.impl;

import com.alibaba.fastjson.JSON;
import com.servitization.metadata.zk.Constants;
import com.servitization.metadata.zk.ZKBaseStructureBuilder;
import com.servitization.metadata.zk.ZKConnection;
import com.servitization.webms.entity.MetadataNode;
import com.servitization.webms.entity.MetadataVersion;
import com.servitization.webms.http.AosAgent;
import com.servitization.webms.mapper.MetadataNodeMapper;
import com.servitization.webms.mapper.MetadataPublishIpMapper;
import com.servitization.webms.mapper.MetadataPublishMapper;
import com.servitization.webms.mapper.MetadataVersionMapper;
import com.servitization.webms.service.IConfigProvider;
import com.servitization.webms.service.IMetadataBootService;
import com.servitization.webms.service.IMetadataPublishService;
import org.apache.log4j.Logger;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MetadataBootServiceImpl implements IMetadataBootService {

    private static final Logger LOGGER = Logger.getLogger(MetadataBootServiceImpl.class);
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
    private MetadataNodeMapper nodeMapper;
    @Resource
    private IMetadataPublishService publishService;

    /**
     * 1. 原始代码备份
     @Override public int bootScan() {
     // 首先遍历zk上的boot节点，找到IP信息
     // 建立zk树结构（如果没有的话）
     int result = 0;
     try {
     // add by jiamingku
     // System.out.println("............: "+aosAgent.getClass());
     ZKBaseStructureBuilder.buildBaseStructure();
     ZooKeeper zk = ZKConnection.zk();
     List<String> hostsList = zk.getChildren(Constants.boot, false);
     // 根据HOSTNAME查询aos所属节点
     if (hostsList != null && hostsList.size() > 0) {
     String url = configProvider.Get("batchGetAosNodeByMachineName.url");
     url += "?authkey=" + ConstantValue.KEY + "&secret=" + ConstantValue.SECRET;
     CustomizeParameterEntity entity = new CustomizeParameterEntity();
     entity.setUrl(url);
     String param = "servers=";
     for (String host : hostsList) {
     param += host + ",";
     }
     param = param.substring(0, param.length() - 1);

     BatchGetAosNodeByMachineNameResp aosNodes = aosAgent.batchGetAosNodeByMachineName(param, entity);
     LOGGER.info("AOS REQ:" + param + " AOS RESP:" + JSON.toJSONString(aosNodes));
     // 测试代码
     // BatchGetAosNodeByMachineNameResp aosNodes = new
     // BatchGetAosNodeByMachineNameResp();
     // aosNodes.setCode(200);
     if (aosNodes != null && aosNodes.getCode() == 200) {
     Map<String, List<AosNode>> nodeMap = aosNodes.getData();
     // //测试代码开始
     // nodeMap = new HashMap<String, List<AosNode>>();
     // List<AosNode> nodesListTemp = new ArrayList<AosNode>();
     // AosNode nodeTemp = new AosNode();
     // nodeTemp.setId(41127);
     // nodeTemp.setIsServiceUnit(1);
     // nodesListTemp.add(nodeTemp);
     // nodeMap.put("EBJ1167.local", nodesListTemp);
     // //测试代码结束
     for (String hostName : hostsList) {
     List<AosNode> nodes = nodeMap.get(hostName);
     for (AosNode node : nodes) {
     if (node.getIsServiceUnit() == 1) {
     // 找到此节点绑定的元数据 推送到机器
     try {
     pushBootData(hostName, node.getId(), zk);
     } catch (Exception e) {
     LOGGER.error(hostName + "推送元数据异常", e);
     result = 3;// 部分推送失败
     }
     break;
     }
     }
     }
     } else {
     LOGGER.error("查询AOS节点信息失败");
     result = 2; // 查aos节点信息失败
     }
     }
     } catch (Exception e) {
     LOGGER.error("启动异常", e);
     result = 1;
     }
     return result;
     }


     private void pushBootData(String hostName, int nodeId, ZooKeeper zk) throws Exception {
     // 查询节点绑定的元数据
     MetadataNode metadataNode = nodeMapper.getMetadataIdByNodeId(nodeId);
     // 根据元数据id去发布表里查询历史记录
     int versionId = publishService.getPublishedVersionIdByRelationId(metadataNode.getId());
     // /int versionId =
     // publishMapper.getPublishedVersionIdByRelationId(metadataNode.getId());
     // 根据历史记录情况查找指定版本的xml
     MetadataVersion metadataVersion = null;
     if (versionId > 0) {
     // 根据versionId查询元数据
     metadataVersion = versionMapper.getMetadataVersionById(versionId);
     } else {
     // 根据metadataId查询出最新的一版元数据
     metadataVersion = versionMapper.getLatestMetadataVersionByMetadataId(metadataNode.getMetadataId());
     }
     if (metadataVersion != null) {
     // 推送数据
     if (zk.exists(Constants.boot + "/" + hostName, false) != null) {
     Map<String, String> map = new HashMap<String, String>();
     map.put(Constants.boot_meta, metadataVersion.getMetadataXml());
     map.put(Constants.boot_version, String.valueOf(metadataVersion.getId()));

     LOGGER.info("path:" + Constants.boot + "/" + hostName);
     LOGGER.info("data:" + JSON.toJSONString(map));
     LOGGER.info("version:" + "-1");
     zk.setData(Constants.boot + "/" + hostName, JSON.toJSONString(map).getBytes(), -1);
     }
     }
     }
     *
     */

    /**
     * web程序的每台机器开始时候要想zk 的   === mobileapi/servitization/boot
     * 节点写入信息,节点信息是note 并且是临时节点。
     *
     * 就是个初始化的东西没啥太大意识，一期干掉。
     * webms定时扫描数据库信息发现有变动就开始推送数据 <br/>
     */
    /**
     * mobileapi(/mobileapi-根节点,内容: "NONE")
     * │
     * ├──1.servitization(/servitization-二级节点，内容:"NONE")
     * │    	│
     * │       ├──1.1.boot(/boot-启动节点,内容："NONE")
     * │		│
     * │		├──1.2.status(/status-状态节点,内容："NONE")
     * │		│
     * │		├──1.3.push(/push-推送节点,内容："NONE")
     */
    @Override
    public int bootScan() {
        // 首先遍历zk上的boot节点，找到IP信息
        // 建立zk树结构（如果没有的话）
        int result = 0;
        try {
            ZKBaseStructureBuilder.buildBaseStructure();
            ZooKeeper zk = ZKConnection.zk();
            List<String> bootList = zk.getChildren(Constants.boot, false);
            List<String> statusList = zk.getChildren(Constants.status, false);
            List<String> pushList = zk.getChildren(Constants.push, false);
            LOGGER.info(bootList);
            LOGGER.info(statusList);
            LOGGER.info(pushList);
            //zk.getData();

        } catch (Exception e) {
            LOGGER.error("启动异常", e);
            result = 1;
        }
        return result;
    }

    /**
     * add by jiamingku 2016-5-12
     * 原始逻辑：元数据与节点有关系(metadata,node) <br/>
     * 元数据与发布的关系(metadata_publish)<br/>
     * <p>
     * <p>
     * 1.nodeId(metadata_node_relation)------->metadataNode--------->nodeId(metadataNode.getId()) <br/>
     * <p>
     * 2.nodeId(metadata_publish)-------------> metadata_version_id <br/>
     * <p>
     * 3.metadata_version, metadata_xml 关系.
     * <p>
     * 将数据推送到zk里面去 <br/>
     *
     * @param hostName
     * @param nodeId
     * @param zk
     * @throws Exception
     */
    private void pushBootData(String hostName, int nodeId, ZooKeeper zk) throws Exception {
        // 查询节点绑定的元数据
        MetadataNode metadataNode = nodeMapper.getMetadataIdByNodeId(nodeId);
        // 根据元数据id去发布表里查询历史记录
        int versionId = publishService.getPublishedVersionIdByRelationId(metadataNode.getId());
        // /int versionId =
        // publishMapper.getPublishedVersionIdByRelationId(metadataNode.getId());
        // 根据历史记录情况查找指定版本的xml
        MetadataVersion metadataVersion = null;
        if (versionId > 0) {
            // 根据versionId查询元数据
            metadataVersion = versionMapper.getMetadataVersionById(versionId);
        } else {
            // 根据metadataId查询出最新的一版元数据
            metadataVersion = versionMapper.getLatestMetadataVersionByMetadataId(metadataNode.getMetadataId());
        }
        if (metadataVersion != null) {
            // 推送数据
            if (zk.exists(Constants.boot + "/" + hostName, false) != null) {
                Map<String, String> map = new HashMap<String, String>();
                map.put(Constants.boot_meta, metadataVersion.getMetadataXml());
                map.put(Constants.boot_version, String.valueOf(metadataVersion.getId()));

                LOGGER.info("path:" + Constants.boot + "/" + hostName);
                LOGGER.info("data:" + JSON.toJSONString(map));
                LOGGER.info("version:" + "-1");
                zk.setData(Constants.boot + "/" + hostName, JSON.toJSONString(map).getBytes(), -1);
            }
        }
    }
}
