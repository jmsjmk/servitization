package com.servitization.metadata.define;

import com.servitization.metadata.define.embedder.ServiceDefine;
import com.servitization.metadata.define.embedder.impl.ServiceDefineImpl;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class XmlSerializer {

    public static final String ROOT_NAME = "ROOT";

    public static String serialize(ServiceDefine sd) {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement(ROOT_NAME);
        sd.serialize(root);
        return document.asXML();
    }

    public static ServiceDefine deserialize(String xml)
            throws DocumentException {
        ServiceDefine sd = new ServiceDefineImpl();
        Document document = DocumentHelper.parseText(xml);
        Element root = document.getRootElement();
        sd.deserialize(root);
        return sd;
    }
}
