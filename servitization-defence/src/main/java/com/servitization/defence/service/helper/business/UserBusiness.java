package com.servitization.defence.service.helper.business;

import com.servitization.commons.cache.RedisManager;
import com.servitization.defence.cacheKey.VerityCheckCodeCachekey;
import com.servitization.metadata.common.CommonsUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserBusiness {

    private static final Logger LOG = LoggerFactory
            .getLogger(UserBusiness.class);

    private static String serviceUrl = CommonsUtil.CONFIG_PROVIDAR
            .getProperty("SysConfig.CheckCodeUrlServiceUrl");

    public String getCheckCodeUrlInter(String deviceId) {
        return serviceUrl + "user/showCheckCode?deviceId=" + deviceId;
    }

    /**
     * 校验验证码
     *
     * @param deviceId
     * @param checkCode
     * @return boolean
     */
    public boolean verityCheckCode(String deviceId, String checkCode) {

        boolean result = false;
        try {
            RedisManager redisManager = RedisManager.getInstance();
            VerityCheckCodeCachekey cacheKey = new VerityCheckCodeCachekey();
            if (StringUtils.isNotBlank(deviceId)) {
                deviceId = deviceId.replaceAll("\\+", "");
            }
            cacheKey.setSubKey(deviceId);
            String cacheCode = redisManager.getStr(cacheKey); // 从缓存读取验证码
            if (StringUtils.equalsIgnoreCase(cacheCode, checkCode)) {
                result = true;
            }
        } catch (Exception ex) {
            LOG.error("VerifyCheckCode Exception:", ex);
        }
        return result;
    }

    public void destory() {

    }

}
