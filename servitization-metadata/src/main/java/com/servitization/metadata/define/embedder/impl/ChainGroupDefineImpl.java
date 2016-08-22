package com.servitization.metadata.define.embedder.impl;

import com.servitization.metadata.define.embedder.ChainElementDefine;
import com.servitization.metadata.define.embedder.ChainGroupDefine;
import com.servitization.metadata.define.embedder.GroupPolicy;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class ChainGroupDefineImpl implements ChainGroupDefine {

    private static final long serialVersionUID = 1L;

    private String name;

    private List<ChainElementDefine> chainElementDefineList;

    private int groupProcessTimeout;

    private int groupSize;

    private GroupPolicy groupPolicy;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChainElementDefine> getChainElementDefineList() {
        return chainElementDefineList;
    }

    public void setChainElementDefineList(
            List<ChainElementDefine> chainElementDefineList) {
        this.chainElementDefineList = chainElementDefineList;
    }

    public int getGroupProcessTimeout() {
        return groupProcessTimeout;
    }

    public void setGroupProcessTimeout(int groupProcessTimeout) {
        this.groupProcessTimeout = groupProcessTimeout;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }

    public GroupPolicy getGroupPolicy() {
        return groupPolicy;
    }

    public void setGroupPolicy(GroupPolicy groupPolicy) {
        this.groupPolicy = groupPolicy;
    }

    @Override
    public void serialize(Element parent) {
        Element me = parent.addElement(this.getClass().getName());
        if (name != null)
            me.addAttribute("name", name);
        me.addAttribute("groupProcessTimeout", "" + groupProcessTimeout);
        me.addAttribute("groupSize", "" + groupSize);
        if (groupPolicy != null)
            me.addAttribute("groupPolicy", "" + groupPolicy.name());
        if (chainElementDefineList != null && chainElementDefineList.size() > 0) {
            for (ChainElementDefine ced : chainElementDefineList) {
                ced.serialize(me);
            }
        }
    }

    @Override
    public void deserialize(Element self) {
        this.name = self.attributeValue("name");
        String gpt = self.attributeValue("groupProcessTimeout");
        if (gpt != null)
            groupProcessTimeout = Integer.valueOf(gpt);
        String gz = self.attributeValue("groupSize");
        if (gz != null)
            groupSize = Integer.valueOf(gz);
        String gp = self.attributeValue("groupPolicy");
        if (gp != null)
            groupPolicy = GroupPolicy.valueOf(gp);
        List<Element> e_chainElementDefineList = self.elements();
        if (e_chainElementDefineList != null
                && e_chainElementDefineList.size() > 0) {
            chainElementDefineList = new ArrayList<>();
            for (Element e : e_chainElementDefineList) {
                Class<?> clazz;
                ChainElementDefine ced;
                try {
                    clazz = Class.forName(e.getName());
                    ced = (ChainElementDefine) (clazz.newInstance());
                } catch (Exception e1) {
                    throw new RuntimeException("Deserialize failed!", e1);
                }
                ced.deserialize(e);
                chainElementDefineList.add(ced);
            }
        }
    }
}
