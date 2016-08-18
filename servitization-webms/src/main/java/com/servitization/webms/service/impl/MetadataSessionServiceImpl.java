package com.servitization.webms.service.impl;

import com.servitization.webms.entity.MetadataSession;
import com.servitization.webms.mapper.MetadataSessionMapper;
import com.servitization.webms.service.IMetadataSessionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class MetadataSessionServiceImpl implements IMetadataSessionService {

    @Resource
    private MetadataSessionMapper metadataSessionMapper;


    @Override
    public List<MetadataSession> getMetadataSessionList(Map<String, Object> params) {
        return metadataSessionMapper.getMetadataSessionList(params);
    }

    @Override
    public int getMetadataSessionCount(Map<String, Object> params) {
        return metadataSessionMapper.getMetadataSessionCount(params);
    }

    @Override
    public int deleteMetadataSessions(List<String> ids) {
        return metadataSessionMapper.deleteMetadataSessions(ids);
    }

    @Override
    public int addMetadataSession(MetadataSession metadataSession) {
        return metadataSessionMapper.addMetadataSession(metadataSession);
    }

    @Override
    public MetadataSession getSessionById(long SessionId) {
        return metadataSessionMapper.getSessionById(SessionId);
    }

    @Override
    public int updateMetadataSession(MetadataSession metadataSession) {
        return metadataSessionMapper.updateMetadataSession(metadataSession);
    }

    @Override
    public int vertifySession(Map<String, Object> params) {
        return metadataSessionMapper.vertifySession(params);
    }

    @Override
    public void deleteSessionByMetadataid(List<String> args) {
        metadataSessionMapper.deleteSessionByMetadataid(args);
    }
}
