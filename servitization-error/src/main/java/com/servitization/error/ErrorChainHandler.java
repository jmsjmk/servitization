package com.servitization.error;

import com.servitization.embedder.context.ErrorEntity;
import com.servitization.embedder.context.GlobalContext;
import com.servitization.embedder.context.RequestContext;
import com.servitization.embedder.handler.ChainHandler;
import com.servitization.embedder.handler.HandleResult;
import com.servitization.embedder.immobile.ImmobileRequest;
import com.servitization.embedder.immobile.ImmobileResponse;
import com.servitization.metadata.common.CustomHeaderEnum;
import com.servitization.metadata.define.embedder.ChainElementDefine;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 异常处理
 */
public class ErrorChainHandler implements ChainHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ErrorChainHandler.class);

    public static final String ERROR_PRINT = "{\"IsError\":true,\"ErrorCode\":\"700\",\"ErrorMessage\":\"网络繁忙，请稍候再试\"}";

    @Override
    public void init(ChainElementDefine eleDefine, GlobalContext context) {
        LOG.info("Initializing Error Chain Handler...");
    }

    @Override
    public HandleResult handle(ImmobileRequest request, ImmobileResponse response, RequestContext context) {
        /**
         * 1、检查上下文，是否发生过error <br/>
         * 2、如果发生了error，有errorCode的，按最后一条errorCode<br/>
         * 3、没有errorCode的，记录所有exception <br/>
         * 4、没有errorCode的，按700，默认消息返回<br/>
         */
        if (context.getErrorList() != null && context.getErrorList().size() > 0) {
            ErrorEntity hasCodeEntity = null;
            for (ErrorEntity ee : context.getErrorList()) {
                if (ee.errorCode != null)
                    hasCodeEntity = ee;
                if (ee.exception != null) {
                    String traceId = request.getHeader(CustomHeaderEnum.TRACEID.headerName());
                    LOG.error(context.getGlobalContext().getServiceDefine().getName()
                            + "#"
                            + ee.handlerName
                            + "process request: traceId=" + traceId
                            + " occurs an exception: ", ee.exception);
                }
            }
            String rspStr;
            if (hasCodeEntity != null) {
                rspStr = build(hasCodeEntity.errorCode, hasCodeEntity.errorMessage, hasCodeEntity.customErrorEntity);
            } else {
                rspStr = ERROR_PRINT;
            }
            response.resetContent();
            try {
                String callback = request.getParameter("callback");
                if (StringUtils.isNotBlank(callback)) {
                    rspStr = callback + "(" + rspStr + ");";
                }
                response.getOutputStream().write(rspStr.getBytes());
            } catch (IOException e) {
                LOG.error(context.getGlobalContext().getServiceDefine().getName()
                        + "#Error occurs when write byte to content!", e);
            }
        }
        return HandleResult.CONTINUE;
    }

    private String build(String code, String msg, Map<String, String> customErrorEntity) {
        StringBuffer sb = new StringBuffer("{\"IsError\":true,\"ErrorCode\":\"");
        sb.append(code);
        sb.append("\",\"ErrorMessage\":\"");
        sb.append(msg);
        sb.append("\"");
        if (customErrorEntity != null) {
            for (Entry<String, String> entry : customErrorEntity.entrySet()) {
                String key = entry.getKey();
                if (key != null) {
                    sb.append(",\"" + key + "\":\"");
                    sb.append(entry.getValue());
                    sb.append("\"");
                }
            }
        }
        sb.append("}");
        return sb.toString();
    }

    @Override
    public void destroy(GlobalContext context) {
        LOG.info("Error Chain Handler has been destoryed.");
    }
}
