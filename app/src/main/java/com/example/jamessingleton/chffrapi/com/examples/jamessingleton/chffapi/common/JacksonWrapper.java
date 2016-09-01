package com.example.jamessingleton.chffrapi.com.examples.jamessingleton.chffapi.common;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by frank on 8/31/16.
 */
public class JacksonWrapper {
    /**
     * Generates an {@link ObjectMapper} and configures it so that it does not fail on unknown properties.
     *
     * @return an object mapper.
     */
    public static ObjectMapper getObjectMapper() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY,true);
            return objectMapper;
        } catch (Exception e) {
            return null;
        }
    }
}