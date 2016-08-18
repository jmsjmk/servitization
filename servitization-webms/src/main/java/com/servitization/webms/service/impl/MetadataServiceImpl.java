package com.servitization.webms.service.impl;

import com.servitization.commons.util.DateUtil;
import com.servitization.metadata.define.embedder.ChainElementDefine;
import com.servitization.metadata.define.embedder.GroupPolicy;
import com.servitization.metadata.define.embedder.impl.ChainGroupDefineImpl;
import com.servitization.webms.entity.*;
import com.servitization.webms.mapper.*;
import com.servitization.webms.service.IMetadataService;
import com.servitization.webms.util.ConcreteSubject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class MetadataServiceImpl implements IMetadataService {

    @Resource
    private MetadataMapper metadataMapper;

    @Resource
    private MetadataModuleMapper metadataModuleMapper;

    @Resource
    private MetadataGroupMapper metadataGroupMapper;

    @Resource
    private MetadataProxyMapper metadataProxyMapper;

    @Resource
    private MetadataServicePoolMapper metadataServicePoolMapper;

    @Resource
    private MetadataAesMapper metadataAesMapper;

    @Resource
    private MetadataDefineMapper metadataDefineMapper;

    @Override
    public List<Metadata> getMetadataList(Map<String, Integer> params) {
        return metadataMapper.getMetadataList(params);
    }

    @Override
    public int getMetadataCount() {
        return metadataMapper.getMetadataCount();
    }

    @Override
    public int deleteMetadatas(List<String> ids) {
        return metadataMapper.deleteMetadatas(ids);
    }

    @Override
    public int addMetadata(Metadata metadata) {
        return metadataMapper.addMetadata(metadata);
    }

    @Override
    public int isExistMetadata(Metadata metadata) {
        return metadataMapper.isExistMetadata(metadata);
    }

    /**
     * @param metadata
     * @param beCopiedMetaId oldMetadataId
     * @return
     */
    @Override
    public int addMetadataByCopy(Metadata metadata, String beCopiedMetaId) {

        long oldMetaId = Long.parseLong(beCopiedMetaId);

        // 复制添加元数据标识
        // ----函数入参的metadata对象是为了从前端收集输入的id和描述等
        Metadata metadataBeCopied = metadataMapper.getMetadataById(oldMetaId);

        metadata.setCreateTime(new Date());
        metadata.setDeployModel(metadataBeCopied.getDeployModel());
        metadata.setDownChain(metadataBeCopied.getDownChain());
        metadata.setUpChain(metadataBeCopied.getUpChain());

        metadataMapper.addMetadata(metadata);

        long newMetaId = metadata.getId();
        ConcreteSubject concreteSubject = ConcreteSubject.instances();
        // 复制添加转发配置Proxy
        List<MetadataProxy> proxyList = metadataProxyMapper.getMetadataProxyList(oldMetaId);
        if (proxyList != null && proxyList.size() > 0) {
            for (MetadataProxy metadataProxy : proxyList) {
                long oldProxyId = metadataProxy.getId();
                metadataProxy.setMetadataId(newMetaId);
                metadataProxy.setCreateTime(new Date());
                metadataProxyMapper.addMetadataProxy(metadataProxy);
                long newProxyId = metadataProxy.getId();
                concreteSubject.copyMetadata(oldMetaId, newMetaId, newProxyId, oldProxyId);
            }
        }
        // 复制添加group配置
        List<MetadataGroup> groupList = metadataGroupMapper.getGroupsByMetadataId(oldMetaId);
        if (groupList != null && groupList.size() > 0) {
            for (MetadataGroup metadataGroup : groupList) {
                metadataGroup.setMetadataId(newMetaId);
                metadataGroup.setCreateTime(new Date());
                metadataGroupMapper.addGroup(metadataGroup);
            }
        }
        // 复制添加service_pool信息
        List<MetadataServicePool> servicePoolList = metadataServicePoolMapper.selectPools(oldMetaId);
        if (servicePoolList != null && servicePoolList.size() > 0) {
            for (MetadataServicePool servicePool : servicePoolList) {
                servicePool.setMetadataId(newMetaId);
                servicePool.setCreateTime(DateUtil.formatDate(new Date(), DateUtil.FORMAT_ALL));
                metadataServicePoolMapper.insertPool(servicePool);
            }
        }

        // 复制添加aes白名单
        Map<String, Object> params = new HashMap<>();
        params.put("metadata_id", oldMetaId);
        String ips = metadataAesMapper.getAesWhitelist(params);
        params = new HashMap<>();
        params.put("metadata_id", newMetaId);
        params.put("ips", ips);
        params.put("createTime", new Date());
        metadataAesMapper.addAesWhitelist(params);

        // 复制添加防刷白名单
        params = new HashMap<>();
        params.put("metadata_id", oldMetaId);
        ips = metadataDefineMapper.getDefineWhitelist(params);
        params = new HashMap<>();
        params.put("metadata_id", newMetaId);
        params.put("ips", ips);
        params.put("createTime", new Date());
        metadataDefineMapper.addDefineWhitelist(params);
        return 1;
    }

    /**
     * 项目一期没有组的概念<br/>
     * <p>
     * 根据字符串的值去获取数据数据库表
     */
    @Override
    public List<Chain> handleChain(String chainStr) {
        List<Chain> chains = new ArrayList<>();
        if (StringUtils.isBlank(chainStr)) {
            return chains;
        }
        String[] ids = StringUtils.split(chainStr, ',');
        List<MetadataModule> modules = null;
        List<MetadataGroup> groups = null;
        Chain chain = null;
        MetadataGroup group = null;

        for (String id : ids) {
            chain = new Chain();
            if (StringUtils.startsWith(id, "m")) {
                modules = metadataModuleMapper.getModulesByIds(Arrays.asList(id.substring(1)));
                if (modules != null && modules.size() == 1) {
                    chain.setMetadataModule(modules.get(0));
                    chain.setType(0);
                }
            } else if (StringUtils.isNotBlank(id)) {
                groups = metadataGroupMapper.getGroupsByIds(Arrays.asList(id));
                if (groups != null && groups.size() == 1) {
                    group = groups.get(0);
                    modules = metadataModuleMapper.getModulesByIds(
                            Arrays.asList(StringUtils.split(group.getModuleIds().replaceAll("m", ""), ',')));
                    group.setMetadataModules(modules);
                    chain.setMetadataGroup(group);
                    chain.setType(1);
                }
            }
            chains.add(chain);
        }
        return chains;
    }

    @Override
    public Metadata getMetadataById(Long metadataId) {
        return metadataMapper.getMetadataById(metadataId);
    }

    @Override
    public int updateMetadataChain(Metadata metadata) {
        return metadataMapper.updateMetadataChain(metadata);
    }

    /**
     * 判断链条内添加的模块是否有重复
     *
     * @param chainStr
     * @return
     */
    @Override
    public boolean isCurrentChain(String chainStr) {
        List<Chain> chains = handleChain(chainStr);
        if (chains == null || chains.size() == 0) {
            return false;
        }
        boolean flag = true;
        Set<Long> moduleIds = new HashSet<>();
        for (Chain chain : chains) {
            if (chain.getType() == 0) {
                if (moduleIds.contains(chain.getMetadataModule().getId())) {
                    flag = false;
                    break;
                } else {
                    moduleIds.add(chain.getMetadataModule().getId());
                }
            } else {
                for (MetadataModule module : chain.getMetadataGroup().getMetadataModules()) {
                    if (moduleIds.contains(module.getId())) {
                        flag = false;
                        break;
                    } else {
                        moduleIds.add(module.getId());
                    }
                }
            }
        }
        return flag;
    }

    /**
     * 构造xml数据对象 <br/>
     * <p>
     * 1.构造 List<Chain> chains 对象 (chain是系统本地的结构 )<br/>
     * chain就是将本地的数据库对象还原的一个系统内部对象<br/>
     * <p>
     * <p>
     * 2.创建并绑定xml对象 <br/>
     *
     * @param metadataId
     * @param chainStr
     * @return
     */
    @Override
    public List<ChainElementDefine> chainList(ConcreteSubject concreteSubject, long metadataId, String chainStr) {
        List<ChainElementDefine> chainList = new ArrayList<>();
        // 1.
        List<Chain> chains = handleChain(chainStr);

        List<ChainElementDefine> groupChainList = null;

        for (Chain chain : chains) {
            if (chain.getType() == 0) {
                // 2.
                MetadataModule module = chain.getMetadataModule();
                // 2.1
                concreteSubject.builderXml(metadataId, module.getHandlerName(), module, chainList);
            } else {
                MetadataGroup group = chain.getMetadataGroup();
                groupChainList = chainList(concreteSubject, metadataId, group.getModuleIds());
                ChainGroupDefineImpl chainGroupDefine = new ChainGroupDefineImpl();
                chainGroupDefine.setName(group.getName());
                chainGroupDefine.setGroupPolicy(GroupPolicy.valueOf(group.getPolicy()));
                chainGroupDefine.setGroupSize(group.getSize());
                chainGroupDefine.setGroupProcessTimeout(group.getProcessTimeOut());
                chainGroupDefine.setChainElementDefineList(groupChainList);
                chainList.add(chainGroupDefine);
            }
        }
        return chainList;
    }

}
