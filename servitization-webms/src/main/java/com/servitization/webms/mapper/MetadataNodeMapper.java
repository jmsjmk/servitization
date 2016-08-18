package com.servitization.webms.mapper;

import com.servitization.commons.db.DataSource;
import com.servitization.webms.entity.MetadataNode;

import java.util.List;
import java.util.Map;

public interface MetadataNodeMapper {

    @DataSource(value = "slave")
    List<MetadataNode> getMetadataNodeList(Map<String, Object> params);

    @DataSource(value = "slave")
    int getMetadataNodeCount(Map<String, Object> params);

    @DataSource(value = "master")
    int delNode(long id);

    @DataSource(value = "master")
    int addNodes(List<MetadataNode> nodes);

    @DataSource(value = "master")
    int addNode(MetadataNode node);

    @DataSource(value = "slave")
    int existNode(long metadataId, int nodeId);

    @DataSource(value = "slave")
    MetadataNode getMetadataIdByNodeId(int nodeId);

    @DataSource(value = "master")
    void deleteNodeRelationByMetadataid(List<String> metadataId);
}
