package com.servitization.metadata.define.proxy;

import com.servitization.metadata.define.XmlSerializable;
import org.dom4j.Element;

public class SourceService implements XmlSerializable {

    private static final long serialVersionUID = 1L;

    private String originalUrl;
    private String path;
    private String httpMethod; //使用逗号分割复选的http-method; e.g: get,post

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String toString() {
        return String.format("%s_%s", path, httpMethod);
    }

    @Override
    public void serialize(Element parent) {
        Element me = parent.addElement(this.getClass().getSimpleName());
        if (originalUrl != null)
            me.addAttribute("originalUrl", originalUrl);
        if (path != null)
            me.addAttribute("path", path);
        if (httpMethod != null)
            me.addAttribute("httpMethod", httpMethod);
    }

    @Override
    public void deserialize(Element parent) {
        Element self = parent.element(this.getClass().getSimpleName());
        this.originalUrl = self.attributeValue("originalUrl");
        this.path = self.attributeValue("path");
        this.httpMethod = self.attributeValue("httpMethod");
    }
}
