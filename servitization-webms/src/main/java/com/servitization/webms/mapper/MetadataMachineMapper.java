package com.servitization.webms.mapper;

import com.servitization.commons.db.DataSource;
import com.servitization.webms.entity.MetadataMachine;

import java.util.List;

public interface MetadataMachineMapper {

    @DataSource(value = "slave")
    List<MetadataMachine> getMachineListByMtdtId(String metadataId);

    @DataSource(value = "master")
    int addMetadataMachine(MetadataMachine metadataMachine);

    @DataSource(value = "master")
    int updateMetadataMachine(MetadataMachine metadataMachine);

    @DataSource(value = "master")
    int deleteMetadataMachine(long id);

    @DataSource(value = "slave")
    MetadataMachine getMetadataMachineById(long id);
}
