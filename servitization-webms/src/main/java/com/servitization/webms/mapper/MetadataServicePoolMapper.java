package com.servitization.webms.mapper;

import com.servitization.commons.db.DataSource;
import com.servitization.webms.entity.MetadataServicePool;

import java.util.List;
import java.util.Map;

public interface MetadataServicePoolMapper {

    @DataSource(value = "slave")
    List<MetadataServicePool> selectPools(long metadataId);

    @DataSource(value = "master")
    int insertPool(MetadataServicePool pool);

    @DataSource(value = "master")
    int updatePoolByName(MetadataServicePool pool);

    @DataSource(value = "master")
    int deletePoolByName(Map<String, Object> param);

    @DataSource(value = "slave")
    List<MetadataServicePool> selectPoolByName(Map<String, Object> param);

    @DataSource(value = "master")
    void deleteServicePoolByMetadataid(List<String> metadataId);
}
