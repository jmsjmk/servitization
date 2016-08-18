package com.servitization.request.service;

import com.servitization.commons.user.remote.common.ErrorCodeEnum;
import com.servitization.commons.user.remote.result.MobileChannelResponse;
import com.servitization.embedder.immobile.ImmobileRequest;
import com.servitization.metadata.common.CustomHeaderEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestCheckService {

    private static final Logger LOG = LoggerFactory.getLogger(RequestCheckService.class);

    private MobileChannelService channelService;

    public void setChannelService(MobileChannelService channelService) {
        this.channelService = channelService;
    }

    public MobileChannelResponse checkRequest(ImmobileRequest request) {
        MobileChannelResponse resp = new MobileChannelResponse();
        try {
            String channelId = request.getHeader(CustomHeaderEnum.CHANNELID
                    .headerName());
            String deviceId = request.getHeader(CustomHeaderEnum.DEVICEID
                    .headerName());
            String clientType = request.getHeader(CustomHeaderEnum.CLIENTTYPE
                    .headerName());
            if (StringUtils.isBlank(channelId)) {
                resp.setErrorMessage("请求头部文件渠道号（ChannelId）不能为空");
                resp.setIsValid(false);
                return resp;
            }
            if (StringUtils.isBlank(deviceId)) {
                resp.setErrorMessage("请求头部文件设备 Id（DeviceId）不能为空");
                resp.setIsValid(false);
                return resp;
            }
            resp = channelService.checkMobileChannel(clientType, channelId);
        } catch (Exception e) {
            LOG.error("RequestCheckService request check error", e);
            resp.setErrorCodeEnum(ErrorCodeEnum.ServerException);
        }
        return resp;
    }

    public void destory() {
        channelService.destroy();
    }
}
