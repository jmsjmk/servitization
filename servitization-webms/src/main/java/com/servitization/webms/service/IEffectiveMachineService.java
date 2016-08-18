package com.servitization.webms.service;

import com.servitization.metadata.zk.StatusInfo;
import com.servitization.webms.entity.Machine;
import com.servitization.webms.entity.Metadata;
import com.servitization.webms.entity.MetadataVersion;

import java.util.List;

public interface IEffectiveMachineService {

    /**
     * 获取生效机器列表
     *
     * @param metadata
     * @param versionId
     * @return
     */
    List<Machine> getEffectiveMachineList(Metadata metadata, String versionId);

    /**
     * 检查各version下是否有机器
     *
     * @author zhao.wang
     */
    boolean[] getEffectiveMachineExistence(List<MetadataVersion> versions);

    /**
     * 直接查询zk上面的配置信息
     */
    List<StatusInfo> getEffectiveMachineExistInfo();
}
