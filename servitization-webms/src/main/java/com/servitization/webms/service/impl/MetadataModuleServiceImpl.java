package com.servitization.webms.service.impl;

import com.servitization.webms.entity.MetadataModule;
import com.servitization.webms.mapper.MetadataModuleMapper;
import com.servitization.webms.service.IMetadataModuleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class MetadataModuleServiceImpl implements IMetadataModuleService {

	@Resource
	private MetadataModuleMapper moduleMapper;

	@Override
	public List<MetadataModule> getModuleList(Map<String, Integer> params) {
		return moduleMapper.getModuleList(params);
	}

	@Override
	public List<MetadataModule> getAllModuleList() {
		return moduleMapper.getAllModuleList();
	}

	@Override
	public int getModuleCount() {
		return moduleMapper.getModuleCount();
	}

	@Override
	public int deleteModule(long id) {
		return moduleMapper.deleteModule(id);
	}

	@Override
	public int addModule(MetadataModule metadataModule) {
		return moduleMapper.addModule(metadataModule);
	}

	@Override
	public int updateModule(MetadataModule metadataModule) {
		return moduleMapper.updateModule(metadataModule);
	}

	@Override
	public List<MetadataModule> getModulesByIds(List<String> ids) {
		return moduleMapper.getModulesByIds(ids);
	}

	@Override
	public int vertifyModule(Map<String,Object> map) {
		return moduleMapper.vertifyModule(map);
	}
}
