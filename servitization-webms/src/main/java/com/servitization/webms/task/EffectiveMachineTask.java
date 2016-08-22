package com.servitization.webms.task;

import com.servitization.commons.business.agent.entity.CustomizeParameterEntity;
import com.servitization.metadata.zk.Constants;
import com.servitization.metadata.zk.StatusInfo;
import com.servitization.metadata.zk.ZKConnection;
import com.servitization.webms.common.ConstantValue;
import com.servitization.webms.entity.Machine;
import com.servitization.webms.entity.Metadata;
import com.servitization.webms.http.AosAgent;
import com.servitization.webms.http.entity.AosNode;
import com.servitization.webms.http.entity.GetAosNodeByMachineNameResp;
import com.servitization.webms.service.IConfigProvider;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.concurrent.Callable;

public class EffectiveMachineTask implements Callable<Machine> {

    private static final Logger LOGGER = Logger.getLogger(EffectiveMachineTask.class);

    private Metadata metadata;
    private String versionId;
    private String ip;
    private AosAgent aosAgent;

    private IConfigProvider configProvider;

    public EffectiveMachineTask(Metadata metadata, String versionId, String ip, AosAgent aosAgent, IConfigProvider configProvider) {
        this.metadata = metadata;
        this.versionId = versionId;
        this.ip = ip;
        this.aosAgent = aosAgent;
        this.configProvider = configProvider;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Machine call() throws Exception {
        Machine machine = new Machine();
        machine.setMachineIp(ip);
        String path = Constants.status + "/" + ip;
        try {
            byte[] bytes = ZKConnection.zk().getData(path, false, null);
            StatusInfo statusInfo;
            try {
                statusInfo = new StatusInfo(new String(bytes));
            } catch (Exception e) {
                statusInfo = null;
            }
            if (statusInfo == null || !StringUtils.equals(metadata.getMetaKey(), statusInfo.getName()) || !StringUtils.equals(versionId, statusInfo.getVersion())) {
                return null;
            }
            machine.setMachineName(statusInfo.getHostname());
            String url = configProvider.Get("getAosNodeByMachineName.url") + statusInfo.getHostname() + "/node";
            String param = "authkey=" + ConstantValue.KEY + "&secret=" + ConstantValue.SECRET;
            CustomizeParameterEntity entity = new CustomizeParameterEntity();
            entity.setUrl(url);
            GetAosNodeByMachineNameResp resp = aosAgent.getAosNodeByMachineName(param, entity);
            if (resp != null && resp.getCode() == 200 && resp.getData() != null && resp.getData().size() > 0) {
                for (AosNode node : resp.getData()) {
                    if (node.getIsServiceUnit() == 1) {
                        machine.setNodeName(node.getFullpath());
                        break;
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.info("获取机器状态exception, path:" + path, e);
        }
        return machine;
    }
}
