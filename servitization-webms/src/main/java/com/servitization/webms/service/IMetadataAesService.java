package com.servitization.webms.service;

import java.util.List;
import java.util.Map;

public interface IMetadataAesService {

    String getAesWhitelist(Map<String, Object> params);

    int addAesWhitelist(Map<String, Object> params);

    int updateAesWhitelist(Map<String, Object> params);

    int deleteAesWhitelist(List<String> ids);
}
