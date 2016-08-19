package com.servitization.compress;

import com.servitization.commons.util.compress.CompressUtil;
import com.servitization.embedder.context.GlobalContext;
import com.servitization.embedder.context.RequestContext;
import com.servitization.embedder.handler.ChainHandler;
import com.servitization.embedder.handler.HandleResult;
import com.servitization.embedder.immobile.ImmobileRequest;
import com.servitization.embedder.immobile.ImmobileResponse;
import com.servitization.metadata.common.CustomHeaderEnum;
import com.servitization.metadata.define.embedder.ChainElementDefine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 压缩服务
 */
public class CompressChainHandler implements ChainHandler {

    private static final Logger LOG = LoggerFactory.getLogger(CompressChainHandler.class);

    private final String headerName = CustomHeaderEnum.ACCEPTENCODING.headerName();

    private final String another_header = "iscompress";

    @Override
    public void init(ChainElementDefine eleDefine, GlobalContext context) {
        LOG.info("Initializing Compress Chain Handler...");
    }

    @Override
    public HandleResult handle(ImmobileRequest request, ImmobileResponse response, RequestContext context) {
        String cus_isCompress = request.getHeader(headerName);
        String another_isCompress = request.getHeader(another_header);
        CompressUtil.CompressType ct = null;
        if ((cus_isCompress != null && cus_isCompress.toLowerCase().indexOf(CompressUtil.CompressType.GZP.type()) != -1)
                || (another_isCompress != null && another_isCompress.toLowerCase().equals("true")))
            ct = CompressUtil.CompressType.GZP;
        if (cus_isCompress != null && cus_isCompress.toLowerCase().indexOf(CompressUtil.CompressType.LZSS.type()) != -1)
            ct = CompressUtil.CompressType.LZSS;
        if (ct != null) {
            byte[] data = response.getContent();
            if (data != null && data.length > 0) {
                try {
                    data = CompressUtil.compress(data, ct);
                    response.resetContent();
                    response.getOutputStream().write(data);
                } catch (Exception e) {
                    LOG.error(context.getGlobalContext().getServiceDefine().getName() + "#Error occurs when compress the response!", e);
                }
            }
        }
        return HandleResult.CONTINUE;
    }

    @Override
    public void destroy(GlobalContext context) {
        LOG.info("Compress Chain Handler has been destoryed.");
    }
}
