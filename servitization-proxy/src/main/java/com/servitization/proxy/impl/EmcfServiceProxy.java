package com.servitization.proxy.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.servitization.commons.socket.client.RemoteClient;
import com.servitization.commons.socket.quantize.QuantizeParameter;
import com.servitization.commons.socket.quantize.QuantizeRemoteAdapter;
import com.servitization.embedder.context.RequestContext;
import com.servitization.embedder.immobile.ImmobileRequest;
import com.servitization.embedder.immobile.ImmobileResponse;
import com.servitization.metadata.common.Constants;
import com.servitization.metadata.common.CustomHeaderEnum;
import com.servitization.metadata.define.proxy.TargetService;
import com.servitization.proxy.CommonLogger;
import com.servitization.proxy.IServiceProxy;
import org.apache.http.HttpStatus;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class EmcfServiceProxy implements IServiceProxy {

    private QuantizeRemoteAdapter adapter;

    public EmcfServiceProxy(RemoteClient rc) {
        adapter = new QuantizeRemoteAdapter();
        adapter.setRemoteClient(rc);
        CommonLogger.getLogger().info("EmcfServiceProxy inited");
    }

    @Override
    public Object doService(ImmobileRequest request,
                            ImmobileResponse response, TargetService targetService,
                            RequestContext context) throws NullPointerException, IOException, IllegalAccessException,
            URISyntaxException, InterruptedException, ExecutionException {
        QuantizeParameter qp = new QuantizeParameter();
        qp.setServiceName(targetService.getServiceName());
        qp.setServiceType(targetService.getServicePoolName());
        qp.setServiceVersion(targetService.getServiceVersion());
        qp.setTimeOut(targetService.getSocketTimeout());
        String json = request.getParameter(Constants.REQ_PARAM_NAME);
        JSONObject jo = JSON.parseObject(json);
        Map<String, String> headers = new HashMap<>();
        for (CustomHeaderEnum che : CustomHeaderEnum.values()) {
            if (che == CustomHeaderEnum.CLIENTIP) {
                headers.put(CustomHeaderEnum.CLIENTIP.headerName(),
                        request.getRemoteIP());
            } else {
                headers.put(che.headerName(),
                        request.getHeader(che.headerName()));
            }
        }
        // RemoteRequest类有global成员变量，反序列化后，所有header会在global中
        jo.put("global", headers);
        Object rst;
        try {
            rst = adapter.invoke(qp, new Object[]{jo.toJSONString()});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (rst == null || rst.toString().length() == 0)
            throw new RuntimeException("Null result exception! With json:" + jo.toJSONString());
        response.getOutputStream().write(rst.toString().getBytes());
        return HttpStatus.SC_OK;
    }

    @Override
    public void destory() {
        adapter.setRemoteClient(null);
        adapter = null;
    }
}
