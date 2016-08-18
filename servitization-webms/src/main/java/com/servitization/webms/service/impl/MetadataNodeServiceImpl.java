package com.servitization.webms.service.impl;

import com.alibaba.fastjson.JSON;
import com.servitization.commons.business.agent.entity.CustomizeParameterEntity;
import com.servitization.webms.common.ConstantValue;
import com.servitization.webms.entity.MetadataNode;
import com.servitization.webms.http.AosAgent;
import com.servitization.webms.http.entity.AosNode;
import com.servitization.webms.http.entity.GetAosNodeByIdResp;
import com.servitization.webms.mapper.MetadataNodeMapper;
import com.servitization.webms.service.IConfigProvider;
import com.servitization.webms.service.IMetadataNodeService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class MetadataNodeServiceImpl implements IMetadataNodeService {

    private static final Logger LOGGER = Logger.getLogger(MetadataNodeServiceImpl.class);

    @Resource
    private MetadataNodeMapper metadataNodeMapper;

    @Resource
    private IConfigProvider configProvider;

    @Resource
    private AosAgent aosAgent;

    @Override
    public List<MetadataNode> getMetadataNodeList(Map<String, Object> params) {
        return metadataNodeMapper.getMetadataNodeList(params);
    }

    @Override
    public int getMetadataNodeCount(Map<String, Object> params) {
        return metadataNodeMapper.getMetadataNodeCount(params);
    }

    @Override
    public int delNode(long id) {
        return metadataNodeMapper.delNode(id);
    }

    @Override
    public int addNodes(List<MetadataNode> nodes) {
        return metadataNodeMapper.addNodes(nodes);
    }

    @Override
    public int addNode(MetadataNode node) {
        return metadataNodeMapper.addNode(node);
    }

    @Override
    public int addNodes(String[] nodeIds, long metadataId) {
        int num = 0;
        if (nodeIds == null || nodeIds.length == 0) {
            return num;
        }

        MetadataNode node = null;
        for (String nodeId : nodeIds) {
            if (!StringUtils.isNumeric(nodeId.trim())) {
                continue;
            }
            int id = Integer.parseInt(nodeId.trim());

            if (existNode(metadataId, id) > 0) {
                continue;
            }

            node = getAosNode(id);
            if (node != null) {
                node.setMetadataId(metadataId);
                num += addNode(node);
            }

        }
        return num;
    }

    private MetadataNode getAosNode(int nodeId) {
        MetadataNode node = null;

        String url = configProvider.Get("getAosNodeById.url") + nodeId;

        CustomizeParameterEntity entity = new CustomizeParameterEntity();
        entity.setUrl(url);

        String param = "authkey=" + ConstantValue.KEY + "&secret=" + ConstantValue.SECRET;

        GetAosNodeByIdResp resp = aosAgent.getAosNodeById(param, entity);

        LOGGER.info("获取aos节点 url:" + url + ", resp:" + JSON.toJSONString(resp));

        if (resp != null && resp.getCode() == 200 && resp.getData() != null && resp.getData().getIsServiceUnit() == 1) {
            AosNode aosNode = resp.getData();
            node = new MetadataNode();
            node.setNodeId(aosNode.getId());
            node.setNodeName(StringUtils.isBlank(aosNode.getFullpath()) ? "" : aosNode.getFullpath());
            node.setCreateTime(new Date());
        }
        return node;
    }

    @Override
    public int existNode(long metadataId, int nodeId) {
        return metadataNodeMapper.existNode(metadataId, nodeId);
    }

    @Override
    public void deleteNodeRelationByMetadataid(List<String> metadataId) {
        metadataNodeMapper.deleteNodeRelationByMetadataid(metadataId);
    }

}
