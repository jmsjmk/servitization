package com.servitization.metadata.define.session;

import org.dom4j.Element;

import com.servitization.metadata.define.XmlSerializable;

public class StrategyEntry implements XmlSerializable {

	private static final long serialVersionUID = 1L;
	
	private String key;
	
	private String type;

	private String value;

	private String url;

	private String httpMethod;

	@Override
	public void deserialize(Element element) {
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public String getKey() {
		return key;
	}

	public String getType() {
		return type;
	}

	public String getUrl() {
		return url;
	}

	public String getValue() {
		return value;
	}

	@Override
	public void serialize(Element parent) {
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
