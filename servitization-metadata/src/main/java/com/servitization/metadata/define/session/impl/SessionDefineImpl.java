package com.servitization.metadata.define.session.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Element;

import com.servitization.metadata.define.embedder.impl.ChainHandlerDefineImpl;
import com.servitization.metadata.define.session.SessionDefine;
import com.servitization.metadata.define.session.StrategyEntry;

public class SessionDefineImpl extends ChainHandlerDefineImpl implements SessionDefine {

	private static final long serialVersionUID = 1L;
	
	// add by jiamingku 原始数据保留
	private Map<String, StrategyEntry> strategEntryMap;

	// addd by jiamingku 策略修改
	private Map<String, String> strategyMap;

	@Override
	public void deserialize(Element self) {
		this.setName(self.attributeValue("name"));
		this.setHandlerClazz(self.attributeValue("handlerClazz"));
		Element e_strategyMap = self.element("strategyMap");
		if (e_strategyMap != null) {
			strategyMap = new HashMap<String, String>();
			strategEntryMap = new HashMap<String, StrategyEntry>();
			@SuppressWarnings("unchecked")
			List<Element> entries = e_strategyMap.elements();
			if (entries != null && entries.size() > 0) {
				for (Element e_entry : entries) {
					String key = e_entry.attributeValue("key");
					String value = e_entry.attributeValue("value");
					String type = e_entry.attributeValue("type");
					String url = e_entry.attributeValue("url");
					String method = e_entry.attributeValue("method");
					if (key != null) {
						strategyMap.put(key, value);
						StrategyEntry entry = new StrategyEntry();
						entry.setHttpMethod(method);
						entry.setKey(key);
						entry.setType(type);
						entry.setUrl(url);
						entry.setValue(value);
						strategyMap.put(key, value);
						strategEntryMap.put(key, entry);
					}
				}
			}
		}
	}

	@Override
	public Map<String, StrategyEntry> getStrategEntryMap() {
		return strategEntryMap;
	}

	@Override
	public Map<String, String> getStrategyMap() {
		return strategyMap;
	}
	/**
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
	}
	*/
	@Override
	public void serialize(Element parent) {
		Element me = parent.addElement(this.getClass().getName());
		me.addAttribute("name", getName());
		me.addAttribute("handlerClazz", getHandlerClazz());
		if (strategyMap != null && strategyMap.size() > 0) {
			Element e_strategyMap = me.addElement("strategyMap");
			for (Entry<String, StrategyEntry> entry : strategEntryMap.entrySet()) {
				Element e_entry = e_strategyMap.addElement("entry");
				e_entry.addAttribute("key", entry.getKey());
				e_entry.addAttribute("value", entry.getValue().getValue());
				e_entry.addAttribute("type", entry.getValue().getType());
				e_entry.addAttribute("url", entry.getValue().getUrl());
				e_entry.addAttribute("method", entry.getValue().getHttpMethod());
			}
		}
	}

	public void setStrategEntryMap(Map<String, StrategyEntry> strategEntryMap) {
		this.strategEntryMap = strategEntryMap;
	}

	public void setStrategyMap(Map<String, String> strategyMap) {
		this.strategyMap = strategyMap;
	}

}
