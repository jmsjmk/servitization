package com.servitization.webms.mapper;

import com.servitization.commons.db.DataSource;
import com.servitization.webms.entity.MetadataModule;

import java.util.List;
import java.util.Map;

public interface MetadataModuleMapper {

    @DataSource(value = "slave")
    List<MetadataModule> getModuleList(Map<String, Integer> params);

    @DataSource(value = "slave")
    List<MetadataModule> getAllModuleList();

    @DataSource(value = "slave")
    int getModuleCount();

    @DataSource(value = "master")
    int deleteModule(long id);

    @DataSource(value = "master")
    int addModule(MetadataModule metadataModule);

    @DataSource(value = "master")
    int updateModule(MetadataModule metadataModule);

    @DataSource(value = "slave")
    List<MetadataModule> getModulesByIds(List<String> ids);

    @DataSource(value = "slave")
    int vertifyModule(Map<String, Object> map);

}
