package com.servitization.request.service;

import com.alibaba.fastjson.JSON;
import com.servitization.commons.user.remote.common.ClientType;
import com.servitization.commons.user.remote.common.NewChannelEncryptKey;
import com.servitization.commons.user.remote.helper.MobileChannelHelper;
import com.servitization.commons.user.remote.request.QueryEncryptKeyListReq;
import com.servitization.commons.user.remote.result.MobileChannelResponse;
import com.servitization.commons.user.remote.result.QueryEncryptKeyListResp;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MobileChannelService {
    private static final Logger LOG = LoggerFactory
            .getLogger(RequestCheckService.class);
    private MobileChannelHelper helper;

    private static final long cacheTime = 1800 * 1000;

    private Set<String> channelIds = new HashSet<>();

    private ReadWriteLock lock = new ReentrantReadWriteLock();

    private Timer timer = new Timer();

    public void setHelper(MobileChannelHelper helper) {
        this.helper = helper;
    }

    public void init() {
        updateChannelIds();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                LOG.info("job updateChannelIds start");
                updateChannelIds();
            }
        };
        timer.schedule(task, cacheTime, cacheTime);
    }

    public MobileChannelResponse checkMobileChannel(String clientType,
                                                    String channelId) {
        MobileChannelResponse resp = new MobileChannelResponse();
        if (!StringUtils.equals(clientType,
                String.valueOf(ClientType.Wap.getClientType()))
                && !StringUtils.equals(clientType,
                String.valueOf(ClientType.Html5Wap.getClientType()))) {
            boolean isChannelValid = isChannelValid(channelId);
            resp.setIsValid(isChannelValid);
            if (!isChannelValid) {
                resp.setErrorMessage("此渠道号（ChannelId）未在 OpenApi 注册");
            }
        } else {
            resp.setIsValid(true);
        }
        return resp;
    }

    private boolean isChannelValid(String channelId) {
        boolean isChannelValid = false;
        try {
            lock.readLock().lock();
            isChannelValid = channelIds.size() > 0
                    && channelIds.contains(channelId);
        } catch (Exception e) {
            LOG.error("channelIds contains error", e);
        } finally {
            lock.readLock().unlock();
        }
        return isChannelValid;
    }

    private void updateChannelIds() {
        try {
            QueryEncryptKeyListReq req = new QueryEncryptKeyListReq();
            QueryEncryptKeyListResp resp = JSON.parseObject(
                    helper.queryEncryptKeyList(req),
                    QueryEncryptKeyListResp.class);
            lock.writeLock().lock();
            if ((resp == null || resp.getQueryEncryptKeyList().isEmpty())) {
                LOG.error("queryEncryptKeyList return null List");
            } else {
                channelIds.clear();
                for (NewChannelEncryptKey key : resp.getQueryEncryptKeyList()) {
                    channelIds.add(key.getChannelId());
                }
            }
        } catch (Exception e) {
            LOG.error("MobileChannelService.updateChannelIds error", e);
        } finally {
            lock.writeLock().unlock();
            LOG.info("init channelIds" + channelIds.size());
        }
    }

    public void destroy() {
        timer.cancel();
    }
}
