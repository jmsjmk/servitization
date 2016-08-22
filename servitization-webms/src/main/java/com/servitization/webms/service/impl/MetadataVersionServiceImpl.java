package com.servitization.webms.service.impl;

import com.servitization.webms.entity.MetadataVersion;
import com.servitization.webms.entity.MetadataXml;
import com.servitization.webms.mapper.MetadataVersionMapper;
import com.servitization.webms.service.IMetadataVersionService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class MetadataVersionServiceImpl implements IMetadataVersionService {

    private static final Logger LOGGER = Logger.getLogger(MetadataVersionServiceImpl.class);

    @Resource
    private MetadataVersionMapper metadataVersionMapper;

    @Override
    public List<MetadataVersion> getMetadataVersionList(Map<String, Object> params) {
        return metadataVersionMapper.getMetadataVersionList(params);
    }

    @Override
    public int getMetadataVersionCount(Map<String, Object> params) {
        return metadataVersionMapper.getMetadataVersionCount(params);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addVersion(MetadataVersion version) throws Exception {
        // 1.添加版本
        int versionInsertResult = metadataVersionMapper.addVersion(version);
        // 2.添加xml信息
        MetadataXml metadataXml = new MetadataXml();
        metadataXml.setVersionId(version.getId());
        metadataXml.setMetadataXml(version.getMetadataXml());
        int xmlInsertResult = metadataVersionMapper.addMetadataXml(metadataXml);

        if (versionInsertResult > 0 && xmlInsertResult > 0) {
            return 1;
        } else {
            LOGGER.error("Metadata version insert failure # MetadataId:" + version.getMetadataId()
                    + " version descreption:" + version.getDescription());
            throw (new Exception("MetadataVersion insert failure!"));
        }
    }

    @Override
    public MetadataVersion getMetadataVersionById(long id) {
        return metadataVersionMapper.getMetadataVersionById(id);
    }

    @Override
    public void deleteVersionByMetadataid(List<String> metadataId) {
        metadataVersionMapper.deleteVersionByMetadataid(metadataId);
    }

    @Override
    public void deleteXmlVersion() {
        metadataVersionMapper.deleteXmlVersion();
    }
}
