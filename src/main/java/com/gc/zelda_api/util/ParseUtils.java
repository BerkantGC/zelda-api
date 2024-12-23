package com.gc.zelda_api.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/*
    This util may be used when desired mongodb database is empty.
    Necessary data are placed in giving filepath below.
*/
@Service
public abstract class ParseUtils{
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Updated method to handle deserialization with TypeReference
    public <T> T parseJson(String filename, TypeReference<T> typeReference) {
        try (InputStream inputStream = PageUtils.class.getResourceAsStream("/constant/" + filename)) {
            return objectMapper.readValue(inputStream, typeReference);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }
}
