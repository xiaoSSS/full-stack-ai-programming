package com.xiaoss.starter.utils.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public final class XmlUtils {

    private static final XmlMapper XML_MAPPER = new XmlMapper();

    private XmlUtils() {
    }

    public static String toXml(Object value) {
        try {
            return XML_MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException ex) {
            throw new IllegalArgumentException("Serialize XML failed", ex);
        }
    }

    public static <T> T fromXml(String xml, Class<T> clazz) {
        try {
            return XML_MAPPER.readValue(xml, clazz);
        } catch (JsonProcessingException ex) {
            throw new IllegalArgumentException("Deserialize XML failed", ex);
        }
    }
}
