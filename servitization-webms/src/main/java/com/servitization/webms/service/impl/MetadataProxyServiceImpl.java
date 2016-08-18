package com.servitization.webms.service.impl;

import com.servitization.webms.entity.MetadataProxy;
import com.servitization.webms.mapper.MetadataProxyMapper;
import com.servitization.webms.service.IMetadataProxyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class MetadataProxyServiceImpl implements IMetadataProxyService {

    @Resource
    private MetadataProxyMapper metadataProxyMapper;

    @Override
    public List<MetadataProxy> getMetadataProxyList(long metadataId) {
        return metadataProxyMapper.getMetadataProxyList(metadataId);
    }

    @Override
    public MetadataProxy getProxyById(long proxyId) {
        return metadataProxyMapper.getProxyById(proxyId);
    }

    @Override
    public List<MetadataProxy> getMetadataProxyListByParams(Map<String, Object> params) {
        return metadataProxyMapper.getMetadataProxyListByParams(params);
    }

    @Override
    public int getMetadataProxyCount(Map<String, Object> params) {
        return metadataProxyMapper.getMetadataProxyCount(params);
    }

    @Override
    public int deleteMetadataProxys(List<String> ids) {
        System.out.println(MetadataProxyServiceImpl.class.toString() + ":" + ids);
        return metadataProxyMapper.deleteMetadataProxys(ids);
    }

    @Override
    public int addMetadataProxy(MetadataProxy metadataProxy) {
        return metadataProxyMapper.addMetadataProxy(metadataProxy);
    }

    @Override
    public int updateMetadataProxy(MetadataProxy metadataProxy) {
        return metadataProxyMapper.updateMetadataProxy(metadataProxy);
    }

    @Override
    public List<String> selectServiceNameByServicePoolName(Map<String, Object> param) {
        return metadataProxyMapper.selectServiceNameByServicePoolName(param);
    }

    @Override
    public int updateProxyPoolName(Map<String, Object> param) {
        return metadataProxyMapper.updateProxyPoolName(param);
    }

    @Override
    public int sourcesUrlIsAgain(Map<String, Object> params) {
        return metadataProxyMapper.sourcesUrlIsAgain(params);
    }

    @Override
    public void deleteProxyByMetadataid(List<String> args) {
        metadataProxyMapper.deleteProxyByMetadataid(args);
    }


}
