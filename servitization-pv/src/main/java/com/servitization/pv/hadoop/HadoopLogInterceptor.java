
package com.servitization.pv.hadoop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.servitization.commons.user.remote.helper.UserHelper;
import com.servitization.embedder.immobile.ImmobileRequest;
import com.servitization.metadata.common.Constants;
import com.servitization.metadata.common.CustomHeaderEnum;
import com.servitization.pv.entity.HadoopHeaderDemand;
import com.servitization.pv.entity.HadoopHeaderInfo;
import com.servitization.pv.enums.ClientEnum;
import com.servitization.pv.enums.DeviceType;
import com.servitization.pv.enums.OrderFromTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HadoopLogInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger("servitization_hadoop_header_log");
    private HadoopDemand hadoopDemand;
    private UserHelper userHelp;

    public void writeHadoopLog(ImmobileRequest request, String controller, String methodName, String realClientIp,
                               String businessLine) {
        ClientEnum clientType = ClientEnum.clientTypeOfEnum(NumberUtils.toInt(request.getHeader("clienttype"), -1));
        DeviceType deviceType = DeviceType.getDeviceType(NumberUtils.toInt(request.getHeader("devicetype"), -1));
        if (DeviceType.Robot == deviceType) {
            return;
        }
        // 通过chennelid获取需要的信息
        HadoopHeaderInfo info = null;
        if (this.hadoopDemand != null) {
            HadoopHeaderDemand demand = new HadoopHeaderDemand();
            demand.setChannelId(request.getHeader("channelid"));
            demand.setClientType(clientType.clientType());
            try {
                setBusinessLine(demand, businessLine);
                info = this.hadoopDemand.getHadoopInfoByDemand(demand, userHelp);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        StringBuilder hhl = new StringBuilder();

        hhl.append("\t");
        hhl.append(StringUtils.trimToEmpty(request.getHeader("channelid")));
        hhl.append("\t");
        hhl.append(StringUtils.trimToEmpty(request.getHeader("deviceid")));
        hhl.append("\t");
        hhl.append(StringUtils.trimToEmpty(request.getHeader("deviceid")));
        hhl.append("\t");

        if (StringUtils.equalsIgnoreCase(controller, "tuan")) {
            controller = "groupon";
        }
        hhl.append(controller);
        hhl.append("\t");
        hhl.append(methodName);
        hhl.append("\t");
        if (info != null) {
            hhl.append(StringUtils.trimToEmpty(info.getOrderForm()));// orderfrom
            // Orderfrom
            // 必填
        }
        hhl.append("\t");
        hhl.append("1.0\t");
        if (info != null && StringUtils.equals(info.getVisitType(), "3")) {
            hhl.append("6");
        } else {
            boolean isWap = false;
            switch (clientType) {
                case Android:
                    hhl.append("3");
                    break;

                case AndroidPad:
                    hhl.append("8");
                    break;
                case Ipad:
                    hhl.append("2");
                    break;
                case Iphone:
                    hhl.append("1");
                    break;
                case WindowsPcPad:
                    hhl.append("9");
                    break;
                case WindowsPhone:
                    hhl.append("5");
                    break;
                default:
                    isWap = true;
                    break;
            }
            if (isWap) {
                switch (deviceType) {
                    case Android:
                        hhl.append("3");
                        break;
                    case AndroidPad:
                        hhl.append("8");
                        break;
                    case Ipad:
                        hhl.append("2");
                        break;

                    case IPhone:
                        hhl.append("1");
                        break;
                    case WindowsPad:
                        hhl.append("9");
                        break;
                    case WindowsPhone:
                        hhl.append("5");
                        break;
                    case Robot:
                        return;
                    default:
                        hhl.append("99");
                        break;
                }
            }
        }

        hhl.append("\t");
        if (this.hadoopDemand != null && isFirstActivating(String.format("/%s/%s", controller, methodName))) {
            String osVersion = request.getHeader(CustomHeaderEnum.OSVERSION.headerName());
            String udid = null;

            if (clientType.clientType() == ClientEnum.Wap.clientType()
                    || clientType.clientType() == ClientEnum.Html5Wap.clientType()) {
                udid = request.getHeader(CustomHeaderEnum.GUID.headerName());
            } else {
                if (StringUtils.isNotBlank(osVersion)
                        && (osVersion.indexOf("iphone_7") != -1 || osVersion.indexOf("iphone_8") != -1)) {
                    udid = request.getHeader(CustomHeaderEnum.GUID.headerName());
                } else {
                    udid = request.getHeader(CustomHeaderEnum.DEVICEID.headerName());
                }
                if (StringUtils.isBlank(udid)) {
                    udid = request.getHeader(CustomHeaderEnum.DEVICEID.headerName());
                }
            }
            hhl.append(StringUtils.trimToEmpty(udid));
        }
        // at 激活 ID 标记(新接口写数据)
        // 必填（app为第一次激活时的标记、H5为第一次种cookieguid时的标记）
        hhl.append("\t");
        hhl.append(StringUtils.trimToEmpty(request.getHeader("referer")));
        hhl.append("\t");
        String browser = request.getHeader("browser");
        if (StringUtils.isBlank(browser)) {
            hhl.append(StringUtils.trimToEmpty(request.getHeader("user-agent")).replaceAll("\t|\n", ""));
        } else {
            hhl.append(browser);
        }

        hhl.append("\t");
        hhl.append(StringUtils.trimToEmpty(realClientIp));
        hhl.append("\t");
        hhl.append(StringUtils.trimToEmpty(request.getHeader("operators")));// tsp
        // 运营商
        // 默认空
        hhl.append("\t");
        hhl.append(StringUtils.trimToEmpty(request.getHeader("network")));// nt
        // 网络类型
        // 默认空
        hhl.append("\t");
        hhl.append(StringUtils.trimToEmpty(request.getHeader("osversion")));
        hhl.append("\t");
        hhl.append(StringUtils.trimToEmpty(request.getHeader("phonetype")));// ct
        // 机型
        // 默认空
        hhl.append("\t");
        hhl.append(StringUtils.trimToEmpty(request.getHeader("screensize")));// sc
        // 屏幕大小
        // 默认空
        hhl.append("\t");
        hhl.append(StringUtils.trimToEmpty(request.getHeader("version")));
        hhl.append("\t");
        hhl.append(StringUtils.trimToEmpty(request.getHeader("guid")));
        hhl.append("\t");
        // vt 访问类型（app、html5） 必填
        if (info != null) {
            hhl.append(StringUtils.isNotBlank(info.getVisitType()) ? info.getVisitType() : 99);
        } else {
            hhl.append(99);
        }
        hhl.append("\t");

        hhl.append(StringUtils.EMPTY);// appid app编号 H5默认空、app必填
        hhl.append("\t");
        String req = request.getParameter(Constants.REQ_PARAM_NAME);
        if (StringUtils.isNotBlank(req)) {
            JSONObject reqJson = JSON.parseObject(req);
            if (reqJson.containsKey("OpenStatus")) {
                if (reqJson.containsKey("IsShieldLog") && reqJson.getBooleanValue("IsShieldLog")) {
                    hhl.append("0\t0\t");
                } else {
                    Integer open = reqJson.getInteger("OpenStatus");
                    if (open != null && open == 1) {
                        hhl.append("1\t0\t");
                    } else {
                        hhl.append("0\t1\t");
                    }
                }
            } else {
                hhl.append("0\t0\t");
            }
        } else {
            hhl.append("0\t0\t");
        }
        LOG.info(hhl.toString());
    }

    /*--设置业务线--*/
    private void setBusinessLine(HadoopHeaderDemand demand, String businessLine) {
        if (StringUtils.isEmpty(businessLine)) {
            demand.setOrderFormType(0);
        }
        OrderFromTypeEnum orderFrom = OrderFromTypeEnum.OrderFromTypeOfEnum(businessLine);
        int orderType = orderFrom == null ? 0 : orderFrom.getTypeId();
        demand.setOrderFormType(orderType);
    }

    public HadoopDemand getHadoopDemand() {
        return hadoopDemand;
    }

    public void setHadoopDemand(HadoopDemand hadoopDemand) {
        this.hadoopDemand = hadoopDemand;
    }

    private boolean isFirstActivating(String path) {
        return "/market/appActive".equals(path);
    }

    public void setUserHelp(UserHelper userHelp) {
        this.userHelp = userHelp;
    }

}