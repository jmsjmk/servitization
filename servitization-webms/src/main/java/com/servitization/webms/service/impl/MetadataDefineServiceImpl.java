package com.servitization.webms.service.impl;

import com.servitization.webms.entity.MetadataDefine;
import com.servitization.webms.mapper.MetadataDefineMapper;
import com.servitization.webms.service.IMetadataDefineService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class MetadataDefineServiceImpl implements IMetadataDefineService {

    @Resource
    private MetadataDefineMapper metadataDefineMapper;

    @Override
    public List<MetadataDefine> getMetadataDefineList(Map<String, Object> params) {
        return metadataDefineMapper.getMetadataDefineList(params);
    }

    @Override
    public int getMetadataDefineCount(Map<String, Object> params) {
        return metadataDefineMapper.getMetadataDefineCount(params);
    }

    @Override
    public int deleteMetadataDefines(List<String> ids) {
        return metadataDefineMapper.deleteMetadataDefines(ids);
    }

    @Override
    public int addMetadataDefine(MetadataDefine metadataDefine) {
        return metadataDefineMapper.addMetadataDefine(metadataDefine);
    }

    @Override
    public MetadataDefine getDefineById(long defineId) {
        return metadataDefineMapper.getDefineById(defineId);
    }

    @Override
    public int updateMetadataDefine(MetadataDefine metadataDefine) {
        return metadataDefineMapper.updateMetadataDefine(metadataDefine);
    }

    @Override
    public int vertifyDefine(Map<String, Object> params) {
        return metadataDefineMapper.vertifyDefine(params);
    }

    @Override
    public int deleteDefineByMetadaId(List<String> args) {
        return metadataDefineMapper.deleteDefineByMetadaId(args);
    }

    @Override
    public String getDefineWhitelist(Map<String, Object> params) {
        return metadataDefineMapper.getDefineWhitelist(params);
    }

    @Override
    public int addDefineWhitelist(Map<String, Object> params) {
        return metadataDefineMapper.addDefineWhitelist(params);
    }

    @Override
    public int updateDefineWhitelist(Map<String, Object> params) {
        return metadataDefineMapper.updateDefineWhitelist(params);
    }

    @Override
    public int deleteDefineWhitelist(List<String> ids) {
        return metadataDefineMapper.deleteDefineWhitelist(ids);
    }

    @Override
    public List<MetadataDefine> getMetadataDefineListByMetaIdAndProxyId(long metadataId, long proxyId) {
        return metadataDefineMapper.getMetadataDefineListByMetaIdAndProxyId(metadataId, proxyId);
    }
}
