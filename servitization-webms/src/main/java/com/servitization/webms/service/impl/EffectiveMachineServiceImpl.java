package com.servitization.webms.service.impl;

import com.servitization.metadata.zk.Constants;
import com.servitization.metadata.zk.StatusInfo;
import com.servitization.metadata.zk.ZKConnection;
import com.servitization.webms.entity.Machine;
import com.servitization.webms.entity.Metadata;
import com.servitization.webms.entity.MetadataVersion;
import com.servitization.webms.http.AosAgent;
import com.servitization.webms.service.IConfigProvider;
import com.servitization.webms.service.IEffectiveMachineService;
import com.servitization.webms.task.EffectiveMachineTask;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.zookeeper.KeeperException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

@Service
public class EffectiveMachineServiceImpl implements IEffectiveMachineService {

    private static final Logger LOGGER = Logger.getLogger(EffectiveMachineServiceImpl.class);

    private ExecutorService executorService = Executors.newCachedThreadPool();

    @Resource
    private AosAgent aosAgent;

    @Resource
    private IConfigProvider configProvider;

    /**
     * 获取生效机器列表
     *
     * @param metadata
     * @param versionId
     * @return
     */
    @Override
    public List<Machine> getEffectiveMachineList(Metadata metadata, String versionId) {
        List<Machine> machines = new ArrayList<>();
        if (metadata == null || StringUtils.isBlank(versionId)) {
            return machines;
        }
        try {
            List<String> ips = ZKConnection.zk().getChildren(Constants.status, false);

            if (ips == null || ips.size() == 0) {
                return machines;
            }
            EffectiveMachineTask task;
            List<EffectiveMachineTask> tasks = new ArrayList<>();
            for (String ip : ips) {
                task = new EffectiveMachineTask(metadata, versionId, ip, aosAgent, configProvider);
                tasks.add(task);
            }
            List<Future<Machine>> futures = executorService.invokeAll(tasks);
            Machine machine;
            for (Future<Machine> future : futures) {
                machine = future.get();
                if (machine == null) {
                    continue;
                }
                machines.add(machine);
            }
        } catch (KeeperException e) {
            LOGGER.info("获取生效机器列表", e);
        } catch (InterruptedException e) {
            LOGGER.info("获取生效机器列表", e);
        } catch (ExecutionException e) {
            LOGGER.info("获取生效机器列表", e);
        }
        return machines;
    }


    /**
     * @return
     */
    public List<StatusInfo> getEffectiveMachineExistInfo() {
        List<StatusInfo> list = new ArrayList<>();
        List<String> ips;
        try {
            ips = ZKConnection.zk().getChildren(Constants.status, false);
            for (String ip : ips) {
                String path = Constants.status + "/" + ip;
                byte[] bytes = ZKConnection.zk().getData(path, false, null);
                StatusInfo statusInfo = null;
                try {
                    statusInfo = new StatusInfo(new String(bytes));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                list.add(statusInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean[] getEffectiveMachineExistence(List<MetadataVersion> versions) {
        boolean[] result = new boolean[versions.size()];
        List<String> ips = null;
        try {
            ips = ZKConnection.zk().getChildren(Constants.status, false);
        } catch (Exception e) {
            LOGGER.error("getEffectiveMachineExistence zookeeper error#", e);
        }
        if (ips == null || ips.size() == 0) {
            for (int i = 0; i < result.length; i++) {
                result[i] = false;
            }
            return result;
        }
        Set<String> versionIdSet = new HashSet<>();
        List<GetEffectiveMachineExistenceTask> taskList = new ArrayList<>();
        for (String ip : ips) {
            taskList.add(new GetEffectiveMachineExistenceTask(ip));
        }
        List<Future<String>> futureList = null;
        try {
            futureList = executorService.invokeAll(taskList);
            /**
             for(Future<String> future : futureList) {
             try {
             System.out.println(future.get());
             }catch (Exception e) {
             e.printStackTrace();
             }
             }*/
        } catch (InterruptedException e) {
            LOGGER.error("getEffectiveMachineExistence get futureList error#", e);
        }
        if (futureList == null || futureList.size() == 0) {
            for (int i = 0; i < result.length; i++) {
                result[i] = false;
            }
            return result;
        }
        for (Future<String> future : futureList) {
            try {
                versionIdSet.add(future.get());
            } catch (Exception e) {
                LOGGER.error("getEffectiveMachineExistence future.get() error#", e);
            }
        }
        for (int i = 0; i < versions.size(); i++) {
            result[i] = versionIdSet.contains(String.valueOf(versions.get(i).getId()));
        }
        return result;
    }

    private static class GetEffectiveMachineExistenceTask implements Callable<String> {

        private String ip;

        public GetEffectiveMachineExistenceTask(String ip) {
            this.ip = ip;
        }

        @Override
        public String call() throws Exception {
            String path = Constants.status + "/" + ip;
            byte[] bytes = ZKConnection.zk().getData(path, false, null);
            StatusInfo statusInfo;
            try {
                statusInfo = new StatusInfo(new String(bytes));
            } catch (Exception e) {
                LOGGER.error("Path[" + path + "] Data[" + new String(bytes) + "]");
                e.printStackTrace();
                return null;
            }
            return statusInfo.getVersion();
        }
    }
}
