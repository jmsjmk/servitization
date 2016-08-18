package com.servitization.webms.mapper;

import com.servitization.commons.db.DataSource;
import com.servitization.webms.entity.MetadataPvUv;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MetadataPvUvMapper {
    @DataSource(value = "slave")
    List<MetadataPvUv> getMetadataPvUvList(Map<String, Object> params);

    @DataSource(value = "slave")
    int getMetadataPvUvCount(Map<String, Object> params);

    @DataSource(value = "slave")
    int vertifyPvUv(Map<String, Object> params);

    @DataSource(value = "master")
    int addPvUv(Map<String, Object> params);

    @DataSource(value = "master")
    int delete(Map<String, Object> params);

    @DataSource(value = "master")
    int deleteMany(List<String> list);

    @DataSource(value = "slave")
    List<MetadataPvUv> selectPvUvList(@Param(value = "oldMetaId") long oldMetaId,
                                      @Param(value = "oldProxyId") long oldProxyId);

    @DataSource(value = "master")
    void batchInsert(List<MetadataPvUv> pvUvList);

    @DataSource(value = "master")
    void deletePvUvByMetadataid(List<String> args);

}
