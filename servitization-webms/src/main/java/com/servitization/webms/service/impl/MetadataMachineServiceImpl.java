package com.servitization.webms.service.impl;

import com.servitization.webms.entity.MetadataMachine;
import com.servitization.webms.mapper.MetadataMachineMapper;
import com.servitization.webms.service.IMetadataMachineService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MetadataMachineServiceImpl implements IMetadataMachineService {

    @Resource
    private MetadataMachineMapper metadataMachineMapper;

    @Override
    public List<MetadataMachine> getMachineListByMtdtId(String metadataId) {
        return metadataMachineMapper.getMachineListByMtdtId(metadataId);
    }

    @Override
    public MetadataMachine getMetadataMachineById(long id) {
        return metadataMachineMapper.getMetadataMachineById(id);
    }

    @Override
    public int deleteMetadataMachine(long id) {
        return metadataMachineMapper.deleteMetadataMachine(id);
    }

    @Override
    public int addMetadataMachine(MetadataMachine metadataMachine) {
        return metadataMachineMapper.addMetadataMachine(metadataMachine);
    }

    @Override
    public int updateMetadataMachine(MetadataMachine metadataMachine) {
        return metadataMachineMapper.updateMetadataMachine(metadataMachine);
    }
}
