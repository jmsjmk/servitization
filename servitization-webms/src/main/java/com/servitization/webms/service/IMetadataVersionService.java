package com.servitization.webms.service;

import com.servitization.webms.entity.MetadataVersion;

import java.util.List;
import java.util.Map;

public interface IMetadataVersionService {

    List<MetadataVersion> getMetadataVersionList(Map<String, Object> params);

    int getMetadataVersionCount(Map<String, Object> params);

    int addVersion(MetadataVersion version) throws Exception;

    MetadataVersion getMetadataVersionById(long id);

    void deleteVersionByMetadataid(List<String> metadataId);

    void deleteXmlVersion();
}
