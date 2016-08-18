package com.servitization.webms.service;

import com.servitization.webms.entity.MetadataSession;

import java.util.List;
import java.util.Map;

public interface IMetadataSessionService {

    List<MetadataSession> getMetadataSessionList(Map<String, Object> params);

    int getMetadataSessionCount(Map<String, Object> params);

    int deleteMetadataSessions(List<String> ids);

    int addMetadataSession(MetadataSession metadataSession);

    MetadataSession getSessionById(long id);

    int updateMetadataSession(MetadataSession metadataSession);

    int vertifySession(Map<String, Object> params);

    void deleteSessionByMetadataid(List<String> args);
}
