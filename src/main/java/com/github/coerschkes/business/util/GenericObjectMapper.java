package com.github.coerschkes.business.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenericObjectMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(GenericObjectMapper.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> String toJson(final T t) {
        try {
            return OBJECT_MAPPER.writeValueAsString(t);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //todo: catch json processing exception and display error
    public static <T> T fromJson(final String json, Class<T> t) {
        try {
            return OBJECT_MAPPER.readValue(json, t);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
