package com.servitization.metadata.define.proxy;

import com.servitization.metadata.define.XmlSerializable;
import org.dom4j.Element;

public class TargetService implements XmlSerializable {

    private static final long serialVersionUID = 1L;

    private String servicePoolName = ""; // 对应servicePoolName
    private ThresholdType thresholdType;
    private int threshold; 			// 流量控制百分比阀值

    private int socketTimeout; 		// 请求超时时间
    private String serviceName = "";     // 服务方法

    /**
     * -----------------------类型为0时，http的转发字段--------------------------
     */
    private String method = "";

    /**
     * -----------------------类型为1时，emcf的转发字段--------------------------
     */
    private String serviceVersion = "";

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public ThresholdType getThresholdType() {
        return thresholdType;
    }

    public void setThresholdType(ThresholdType thresholdType) {
        this.thresholdType = thresholdType;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceVersion() {
        return serviceVersion;
    }

    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }

    public String getServicePoolName() {
        return servicePoolName;
    }

    public void setServicePoolName(String servicePoolName) {
        this.servicePoolName = servicePoolName;
    }

    @Override
    public void serialize(Element parent) {
        Element me = parent.addElement(this.getClass().getSimpleName());
        if (servicePoolName != null)
            me.addAttribute("servicePoolName", servicePoolName);
        if (method != null)
            me.addAttribute("method", method);
        if (serviceName != null)
            me.addAttribute("serviceName", serviceName);
        if (serviceVersion != null)
            me.addAttribute("serviceVersion", serviceVersion);

        me.addAttribute("socketTimeout", "" + socketTimeout);
        me.addAttribute("thresholdType", thresholdType.name());
        me.addAttribute("threshold", "" + threshold);
    }

    @Override
    public void deserialize(Element parent) {
        Element self = parent.element(this.getClass().getSimpleName());
        serviceName = self.attributeValue("serviceName");
        serviceVersion = self.attributeValue("serviceVersion");
        servicePoolName = self.attributeValue("servicePoolName");
        method = self.attributeValue("method");

        String socketTimeout_str = self.attributeValue("socketTimeout");
        if (socketTimeout_str != null)
            socketTimeout = Integer.valueOf(socketTimeout_str);
        String thresholdType_str = self.attributeValue("thresholdType");
        if (thresholdType_str != null)
            thresholdType = ThresholdType.valueOf(thresholdType_str);
        String threshold_str = self.attributeValue("threshold");
        if (threshold_str != null)
            threshold = Integer.valueOf(threshold_str);
    }
}
