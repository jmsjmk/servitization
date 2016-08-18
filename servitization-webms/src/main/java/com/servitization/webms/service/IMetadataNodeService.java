package com.servitization.webms.service;

import com.servitization.webms.entity.MetadataNode;

import java.util.List;
import java.util.Map;

public interface IMetadataNodeService {

    List<MetadataNode> getMetadataNodeList(Map<String, Object> params);

    int getMetadataNodeCount(Map<String, Object> params);

    int delNode(long id);

    int addNodes(List<MetadataNode> nodes);

    int addNode(MetadataNode node);

    int addNodes(String[] nodeIds, long metadataId);

    int existNode(long metadataId, int nodeId);

    void deleteNodeRelationByMetadataid(List<String> metadataId);

}
