package com.servitization.metadata.define.encrypt.impl;

import com.servitization.metadata.define.embedder.impl.ChainHandlerDefineImpl;
import com.servitization.metadata.define.encrypt.EncryptDefine;
import org.dom4j.Element;

import java.util.HashSet;
import java.util.Set;

public class EncryptDefineImpl extends ChainHandlerDefineImpl implements EncryptDefine {

    private static final long serialVersionUID = 1L;

    private Set<String> IPWhiteList;

    public Set<String> getIPWhiteList() {
        return IPWhiteList;
    }

    public void setIPWhiteList(Set<String> iPWhiteList) {
        IPWhiteList = iPWhiteList;
    }

    @Override
    public void serialize(Element parent) {
        Element me = parent.addElement(this.getClass().getName());
        me.addAttribute("name", getName());
        me.addAttribute("handlerClazz", getHandlerClazz());
        if (IPWhiteList != null && IPWhiteList.size() > 0) {
            StringBuffer sb = new StringBuffer();
            boolean isFirst = true;
            for (String ip : IPWhiteList) {
                if (isFirst) {
                    sb.append(ip);
                    isFirst = false;
                } else {
                    sb.append(",").append(ip);
                }
            }
            Element e_IPWhiteList = me.addElement("IPWhiteList");
            e_IPWhiteList.setText(sb.toString());
        }
    }

    @Override
    public void deserialize(Element self) {
        this.setName(self.attributeValue("name"));
        this.setHandlerClazz(self.attributeValue("handlerClazz"));
        Element e_IPWhiteList = self.element("IPWhiteList");
        if (e_IPWhiteList != null) {
            IPWhiteList = new HashSet<>();
            String text = e_IPWhiteList.getText();
            if (text != null && text.length() > 0) {
                String[] ips = text.split(",");
                if (ips != null && ips.length > 0) {
                    for (String ip : ips) {
                        IPWhiteList.add(ip);
                    }
                }
            }
        }
    }
}
