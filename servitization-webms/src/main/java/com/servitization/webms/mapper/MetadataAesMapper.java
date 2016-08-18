package com.servitization.webms.mapper;

import com.servitization.commons.db.DataSource;

import java.util.List;
import java.util.Map;

public interface MetadataAesMapper {

    @DataSource(value = "slave")
    String getAesWhitelist(Map<String, Object> params);

    @DataSource(value = "master")
    int addAesWhitelist(Map<String, Object> params);

    @DataSource(value = "master")
    int updateAesWhitelist(Map<String, Object> params);

    @DataSource(value = "master")
    int deleteAesWhitelist(List<String> ids);
}
