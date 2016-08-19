package com.servitization.metadata.define.proxy;

import com.servitization.metadata.define.XmlSerializable;
import org.dom4j.Element;

public class ServicePool implements XmlSerializable {

    private static final long serialVersionUID = 1L;

    // 目标服务类型: 0-http 1-emcf 2-asyn http
    private int serviceType;

    // URL（包含端口）
    private String url = "";

    private String servicePoolName = "";

    // ************************** 动态字段 *************************** //

    // emcf 连接池特有字段
    private double coefficient;

    private boolean forceCloseChannel;

    private long forceCloseTimeMillis;

    // http 连接池特有字段
    private int connectTimeout;

    public int getServiceType() {
        return serviceType;
    }

    public void setServiceType(int serviceType) {
        this.serviceType = serviceType;
    }

    public String getServicePoolName() {
        return servicePoolName;
    }

    public void setServicePoolName(String servicePoolName) {
        this.servicePoolName = servicePoolName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public boolean isForceCloseChannel() {
        return forceCloseChannel;
    }

    public void setForceCloseChannel(boolean forceCloseChannel) {
        this.forceCloseChannel = forceCloseChannel;
    }

    public long getForceCloseTimeMillis() {
        return forceCloseTimeMillis;
    }

    public void setForceCloseTimeMillis(long forceCloseTimeMillis) {
        this.forceCloseTimeMillis = forceCloseTimeMillis;
    }

    @Override
    public void serialize(Element parent) {
        Element me = parent.addElement(this.getClass().getSimpleName());
        me.addAttribute("servicePoolName", servicePoolName);
        me.addAttribute("url", url);
        me.addAttribute("serviceType", "" + serviceType);
        me.addAttribute("coefficient", "" + coefficient);
        me.addAttribute("forceCloseChannel", "" + forceCloseChannel);
        me.addAttribute("forceCloseTimeMillis", "" + forceCloseTimeMillis);
        me.addAttribute("connectTimeout", "" + connectTimeout);
    }

    @Override
    public void deserialize(Element self) {
        String serviceType_str = self.attributeValue("serviceType");
        if (serviceType_str != null) {
            this.serviceType = Integer.valueOf(serviceType_str);
        }
        this.servicePoolName = self.attributeValue("servicePoolName");
        this.url = self.attributeValue("url");
        String coefficient_str = self.attributeValue("coefficient");
        if (coefficient_str != null) {
            this.coefficient = Double.valueOf(coefficient_str);
        }
        String forceCloseChannel_str = self.attributeValue("forceCloseChannel");
        if (forceCloseChannel_str != null) {
            this.forceCloseChannel = Boolean.valueOf(forceCloseChannel_str);
        }
        String forceCloseTimeMillis_str = self
                .attributeValue("forceCloseTimeMillis");
        if (forceCloseTimeMillis_str != null) {
            this.forceCloseTimeMillis = Long.valueOf(forceCloseTimeMillis_str);
        }
        String connectTimeout_str = self.attributeValue("connectTimeout");
        if (connectTimeout_str != null) {
            this.connectTimeout = Integer.valueOf(connectTimeout_str);
        }
    }
}
