package com.servitization.metadata.define.defence.impl;

import com.servitization.metadata.define.defence.DefenceDefine;
import com.servitization.metadata.define.embedder.impl.ChainHandlerDefineImpl;
import org.dom4j.Element;

import java.util.*;
import java.util.Map.Entry;

public class DefenceDefineImpl extends ChainHandlerDefineImpl implements
        DefenceDefine {

    private static final long serialVersionUID = 1L;

    private Map<String, String> strategyMap;

    private Set<String> IPWhiteList;

    public Map<String, String> getStrategyMap() {
        return strategyMap;
    }

    public void setStrategyMap(Map<String, String> strategyMap) {
        this.strategyMap = strategyMap;
    }

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
        if (strategyMap != null && strategyMap.size() > 0) {
            Element e_strategyMap = me.addElement("strategyMap");
            for (Entry<String, String> entry : strategyMap.entrySet()) {
                Element e_entry = e_strategyMap.addElement("entry");
                e_entry.addAttribute("key", entry.getKey());
                e_entry.addAttribute("value", entry.getValue());
            }
        }
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
        Element e_strategyMap = self.element("strategyMap");
        if (e_strategyMap != null) {
            strategyMap = new HashMap<String, String>();
            @SuppressWarnings("unchecked")
            List<Element> entries = e_strategyMap.elements();
            if (entries != null && entries.size() > 0) {
                for (Element e_entry : entries) {
                    String key = e_entry.attributeValue("key");
                    String value = e_entry.attributeValue("value");
                    if (key != null)
                        strategyMap.put(key, value);
                }
            }
        }
        Element e_IPWhiteList = self.element("IPWhiteList");
        if (e_IPWhiteList != null) {
            IPWhiteList = new HashSet<String>();
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
