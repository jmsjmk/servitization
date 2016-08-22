package com.servitization.webms.service;

import com.servitization.webms.entity.MetadataServicePool;

import java.util.List;
import java.util.Map;

public interface IMetadataServicePoolService {

    List<MetadataServicePool> selectPools(long metadataId);

    int insertPool(MetadataServicePool pool);

    int updatePoolByName(MetadataServicePool pool);

    int deletePoolByName(Map<String, Object> param);

    List<MetadataServicePool> selectPoolByName(Map<String, Object> param);

    void deleteServicePoolByMetadataid(List<String> metadataId);
}
