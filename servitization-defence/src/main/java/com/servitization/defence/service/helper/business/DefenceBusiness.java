package com.servitization.defence.service.helper.business;

import com.alibaba.fastjson.JSON;
import com.servitization.commons.cache.RedisManager;
import com.servitization.commons.user.remote.common.ErrorCodeEnum;
import com.servitization.commons.user.remote.request.DefenceRemoteReq;
import com.servitization.commons.user.remote.result.DefenceRemoteResp;
import com.servitization.defence.cacheKey.DefenceBlackIpCachekey;
import com.servitization.defence.cacheKey.DefenceCountCacheKey;
import com.servitization.defence.service.helper.vo.BlackIpInfo;
import com.servitization.defence.service.helper.vo.DefenceStrategy;
import com.servitization.embedder.context.GlobalContext;
import com.servitization.metadata.define.defence.DefenceDefine;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DefenceBusiness {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(DefenceBusiness.class);

    private UserBusiness userBusiness;

    private Map<String, DefenceStrategy> strategyMap = new ConcurrentHashMap<String, DefenceStrategy>();

    public void init(DefenceDefine dd, GlobalContext context) {
        updateStrategy(dd);
    }

    public DefenceRemoteResp validate(DefenceRemoteReq req) {
        DefenceRemoteResp resp = new DefenceRemoteResp();
        try {
            if (StringUtils.isBlank(req.getIp())
                    || StringUtils.isBlank(req.getPath())
                    || StringUtils.isBlank(req.getDeviceId())) {
                resp.setErrorCodeEnum(ErrorCodeEnum.Defence_Info_blanck);
                LOGGER.error("Defence_Info_blanck " + JSON.toJSONString(req));
                return resp;
            }

            DefenceStrategy strategy = getStrategy(req.getPath());
            if (strategy == null) {
                return resp;
            }

            DefenceBlackIpCachekey bk = new DefenceBlackIpCachekey(req.getIp(),
                    req.getPath());
            DefenceCountCacheKey dck = new DefenceCountCacheKey(req.getIp(),
                    req.getPath());
            BlackIpInfo blackIpInfo = RedisManager.getInstance().getMap(bk,
                    BlackIpInfo.class);

            if (blackIpInfo == null) {
                // 没在黑名单 +1操作
                long value = redis().incr(dck);
                // LOGGER.info(req.getPath()+"在"+req.getIp()+"请求达到"+value+"次");
                if (value == 1L) {
                    // 首次+1 设置超时时间
                    dck.setExpirationTime(strategy.getTimeInterval());
                    redis().expire(dck);
                }
                // 在timeInterval内超过 最大访问次数 记入黑名单
                if (value >= strategy.getMaxCount()) {
                    LOGGER.error(String.format("interval count full %s,%s",
                            req.getIp(), req.getPath()));
                    blackIpInfo = new BlackIpInfo();
                    redis().put(bk, blackIpInfo);
                    String url = userBusiness.getCheckCodeUrlInter(req
                            .getDeviceId());
                    resp.setCheckUrl(url);
                    resp.setErrorCodeEnum(ErrorCodeEnum.Defence_NEED_CODE);
                }
            } else {
                // 验证成功移除黑名单，并count清零重新计数
                if (StringUtils.isNotBlank(req.getCode())
                        && userBusiness.verityCheckCode(req.getDeviceId(),
                        req.getCode())) {
                    redis().del(bk);
                    redis().del(dck);
                } else {
                    // 在黑名单内，验证失败则继续生成验证码
                    String url = userBusiness.getCheckCodeUrlInter(req
                            .getDeviceId());
                    resp.setCheckUrl(url);
                    resp.setErrorCodeEnum(ErrorCodeEnum.Defence_NEED_CODE);
                }
            }
        } catch (Exception e) {
            LOGGER.error("defence core error", e);
        }

        return resp;
    }

    private RedisManager redis() {
        return RedisManager.getInstance();
    }

    private void updateStrategy(DefenceDefine dd) {
        if (dd.getStrategyMap() == null) return;
        for (Map.Entry<String, String> entry : dd.getStrategyMap().entrySet()) {
            String path = entry.getKey();
            String strategyString = entry.getValue();
            String[] strategy = strategyString.split(",");
            if (strategy.length == 2) {
                DefenceStrategy defenceStrategy = new DefenceStrategy();
                defenceStrategy.setPath(path);
                defenceStrategy.setTimeInterval(Integer.parseInt(strategy[0]));
                defenceStrategy.setMaxCount(Long.parseLong(strategy[1]));
                strategyMap.put(path, defenceStrategy);
            }
        }
        LOGGER.info("DefenceStrategy inited map:" + strategyMap);
    }

    public boolean isPathDefenceOpen(String path) {
        Set<String> pathSet = strategyMap.keySet();
        boolean result = (pathSet != null && pathSet.contains(path));
        return result;
    }

    private DefenceStrategy getStrategy(String path) {
        DefenceStrategy strategy = strategyMap.get(path);
        return strategy;
    }

    public void setUserBusiness(UserBusiness userBusiness) {
        this.userBusiness = userBusiness;
    }

    public void destory() {
        strategyMap.clear();
        strategyMap = null;
        userBusiness.destory();
        userBusiness = null;
    }

}
