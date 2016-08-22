package com.servitization.webms.service;

import com.servitization.webms.entity.MetadataMachine;

import java.util.List;

public interface IMetadataMachineService {

    List<MetadataMachine> getMachineListByMtdtId(String metadataId);

    MetadataMachine getMetadataMachineById(long id);

    int deleteMetadataMachine(long id);

    int addMetadataMachine(MetadataMachine metadataMachine);

    int updateMetadataMachine(MetadataMachine metadataMachine);
}
