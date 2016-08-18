package com.servitization.webms.service;

import com.servitization.webms.entity.MetadataModule;

import java.util.List;
import java.util.Map;

public interface IMetadataModuleService {

    List<MetadataModule> getModuleList(Map<String, Integer> params);

    List<MetadataModule> getAllModuleList();

    int getModuleCount();

    int deleteModule(long id);

    int addModule(MetadataModule metadataModule);

    int updateModule(MetadataModule metadataModule);

    List<MetadataModule> getModulesByIds(List<String> ids);

    int vertifyModule(Map<String, Object> map);
}
