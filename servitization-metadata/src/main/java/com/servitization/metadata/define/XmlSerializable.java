package com.servitization.metadata.define;

import org.dom4j.Element;

import java.io.Serializable;

public interface XmlSerializable extends Serializable {

    void serialize(Element parent);

    void deserialize(Element element);

}
