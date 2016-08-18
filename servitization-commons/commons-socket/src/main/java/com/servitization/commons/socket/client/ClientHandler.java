package com.servitization.commons.socket.client;

import com.google.protobuf.ByteString;
import com.servitization.commons.log.GlobalConstant;
import com.servitization.commons.log.Log;
import com.servitization.commons.log.LogFactory;
import com.servitization.commons.log.Logger;
import com.servitization.commons.log.trace.OSUtil;
import com.servitization.commons.log.trace.SpanTypeEnum;
import com.servitization.commons.socket.client.pool.IConnectPool;
import com.servitization.commons.socket.enums.CharsetEnum;
import com.servitization.commons.socket.enums.CompressEnum;
import com.servitization.commons.socket.enums.EncryptEnum;
import com.servitization.commons.socket.message.SocketResponse;
import com.servitization.commons.socket.utils.ZLibUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.handler.timeout.ReadTimeoutException;
import org.joda.time.DateTime;

import java.io.IOException;

public class ClientHandler extends SimpleChannelHandler {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ClientHandler.class);
    private static final Logger CYP_LOGGER = LogFactory.getLogger();
    private final IConnectPool connectPool;

    public ClientHandler(final IConnectPool connectPool) {
        this.connectPool = connectPool;
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
        SocketResponse.Response response = (SocketResponse.Response) e.getMessage();

        String marking = response.getMarking();
        Follower follower = FollowerFactory.getFollowerInfo(marking);
        SocketResponse.Response.Status status = response.getStatus();

        Log log = new Log();
        log.setLogTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss SSS"));
        log.setTraceId(follower == null ? marking : follower.getTraceId());
        log.setSpan(SpanTypeEnum.RPC_CLIENT_RECEIVED.type() + "|" + (follower == null ? "1.1" : follower.getSpanName()));
        log.setBusinessLine(GlobalConstant.getBusinessLine());
        log.setLogType("001");
        log.setServerName(OSUtil.linuxLocalName());
        log.setServerIp(OSUtil.linuxLocalIp());
        log.setUserLogType("emcf-received");
        log.setAppName(GlobalConstant.getAppName());
        log.setServiceName(response.getServiceName());
        log.setExtend1(e.getChannel().toString());
        log.setResponseCode(status.name());

        CYP_LOGGER.info(log);
        if (!StringUtils.equals(status.name(), SocketResponse.Response.Status.SUCCESS.name())) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("ClientHandler.messageReceived response status is " + status.name() + ",marking:" + marking);
            }
            FollowerFactory.finishProduction(marking, null);
            return;
        }

        ByteString reqString = response.getResult();

        if (reqString.isEmpty()) {
            FollowerFactory.finishProduction(marking, null);
            return;
        }
        byte[] result = reqString.toByteArray();

        CharsetEnum charset = CharsetEnum.valueOfEnum(response.getCharset());
        CompressEnum compress = CompressEnum.valueOfEnum(response.getCompress());
        EncryptEnum encrypt = EncryptEnum.valueOfEnum(response.getEncrypt());
        //解压缩
        switch (compress) {
            case NON:
                break;
            case ZLIB:
                try {
                    result = ZLibUtils.decompress(result);
                } catch (Exception e1) {
                    LOGGER.error("ClientHandler.messageReceived decompress Exception#marking:" + marking, e1);
                    FollowerFactory.finishProduction(marking, null);
                }

                break;
            default:
                LOGGER.error("ClientHandler.messageReceived exception#marking:" + marking,
                        new RuntimeException("Only support NON and ZLIB compress，Temporarily does not support other compress."));
                FollowerFactory.finishProduction(marking, null);
                return;
        }
        //解密
        switch (encrypt) {
            case NON:
                break;
            default:
                LOGGER.error("ClientHandler.messageReceived exception#marking:" + marking,
                        new RuntimeException("Temporarily does not support decrypt."));
                FollowerFactory.finishProduction(marking, null);
                return;
        }
        if (charset.code() != CharsetEnum.UTF8.code()) {
            LOGGER.error("ClientHandler.messageReceived exception#marking:" + marking,
                    new RuntimeException("Only support UTF-8 charset，Temporarily does not support other charset."));

            FollowerFactory.finishProduction(marking, null);
            return;
        }
        FollowerFactory.finishProduction(marking, result);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        Throwable throwed = e.getCause();
        if (throwed instanceof ReadTimeoutException || throwed instanceof IOException) {
            LOGGER.error(
                    "ClientHandler socket exception channel will close.channel#"
                            + ctx.getChannel().toString(), throwed);
            //释放客户请求
            this.connectPool.discardChannel(e.getChannel());
        } else {
            LOGGER.error(
                    "ClientHandler uncaught exception#channel:" + ctx.getChannel().toString(), throwed);
            super.exceptionCaught(ctx, e);
        }
    }
}
