package com.servitization.yoke.service;

import com.servitization.commons.util.AesEncryUtil;
import com.servitization.yoke.dao.DBAccess;
import com.servitization.yoke.entity.EMVersionKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public final class AesService {

    private DBAccess dbAccess;
    private Map<String, String> emversionKeyMap;
    private Timer timer = new Timer();
    private static final long PERIOD = 1800 * 1000;

    public static long getUpdatePERIOD() {
        return PERIOD;
    }

    public AesService(DBAccess db) {
        this.dbAccess = db;
        init();
    }

    public String decryptByClientKey(String clientKey, String text)
            throws Exception {
        String key = emversionKeyMap.get(clientKey);
        if (key == null) {
            throw new Exception(String.format(
                    "there does not exit keyMap with %s", clientKey));
        }
        return decryptByKey(key, text);
    }

    public String encryptByKey(String key, String text) {
        return AesEncryUtil.encryptByKey(text, key);
    }

    public String decryptByKey(String key, String text) throws Exception {
        return AesEncryUtil.decryptByKey(text, key);
    }

    public void destroy() {
        timer.cancel();
        this.emversionKeyMap.clear();
        this.emversionKeyMap = null;
    }

    private void init() {
        updateMapData();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                LOGGER.info("update em_version_key_info");
                updateMapData();
            }
        };
        timer.schedule(task, PERIOD, PERIOD);
    }

    public void updateMapData() {
        try {
            List<EMVersionKey> emVersionKeys = dbAccess.selectEmVersionKeys();
            Map<String, String> temp = new HashMap<String, String>();
            for (EMVersionKey e : emVersionKeys) {
                temp.put(e.getClientKey(), e.getParamKey());
            }
            this.emversionKeyMap = temp;
            LOGGER.info("load em_version_map#" + temp.size());
        } catch (Throwable e) {
            LOGGER.error("updateMapData error", e);
        }
    }

    private static final Logger LOGGER = LoggerFactory
            .getLogger(AesService.class);
}
