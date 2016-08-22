package com.servitization.metadata.define.pvUv.impl;

import com.servitization.metadata.define.embedder.impl.ChainHandlerDefineImpl;
import com.servitization.metadata.define.pvUv.PvUvDefine;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class PvUvDefineImpl extends ChainHandlerDefineImpl implements PvUvDefine {
    private static final long serialVersionUID = 1L;
    private List<String> pvUvList;

    @Override
    public void serialize(Element parent) {
        Element me = parent.addElement(this.getClass().getName());
        me.addAttribute("name", getName());
        me.addAttribute("handlerClazz", getHandlerClazz());
        int len;
        if (pvUvList != null && (len = pvUvList.size()) > 0) {
            Element e_pvUvList = me.addElement("pvUvList");
            for (int index = 0; index < len; index++) {
                Element e_entry = e_pvUvList.addElement("sourceUrl");
                e_entry.addAttribute("value", pvUvList.get(index));
            }
        }
    }

    @Override
    public void deserialize(Element self) {
        this.setName(self.attributeValue("name"));
        this.setHandlerClazz(self.attributeValue("handlerClazz"));
        Element e_pvUvList = self.element("pvUvList");
        pvUvList = new ArrayList<>();
        if (e_pvUvList != null) {
            List<Element> entries = e_pvUvList.elements();
            int len;
            if (entries != null && (len = entries.size()) > 0) {
                for (int index = 0; index < len; index++) {
                    pvUvList.add(entries.get(index).attributeValue("value"));
                }
            }
        }
    }

    public List<String> getPvUv() {
        return pvUvList;
    }

    public void setPvUvList(List<String> pvUvList) {
        this.pvUvList = pvUvList;
    }
}
