package com.kuba.skateagramclient.managers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.io.IOException;

/**
 * Created by kuba on 29.11.2015.
 */
@Singleton
public class JsonManager {
    private ObjectMapper objectMapper;

    @Inject
    public JsonManager() {
        objectMapper = new ObjectMapper();
    }

    public <T> T getObject(String jsonContent,Class<T> type) {
        try {
            return objectMapper.readValue(jsonContent,type);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
