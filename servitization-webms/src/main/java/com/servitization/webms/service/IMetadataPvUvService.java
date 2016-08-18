package com.servitization.webms.service;

import com.servitization.webms.entity.MetadataPvUv;

import java.util.List;
import java.util.Map;

public interface IMetadataPvUvService {

    List<MetadataPvUv> getMetadataPvUvList(Map<String, Object> params);

    int getMetadataPvUvCount(Map<String, Object> params);

    int vertifyPvUv(Map<String, Object> params);

    String addPvUv(Map<String, Object> params);

    int delete(Map<String, Object> params);

    int deleteMany(List<String> list);

    void deletePvUvByMetadataid(List<String> args);

}
