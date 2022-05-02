package com.ptit.toeic.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Convert {

    public static <T> ArrayList<T> string2Array(String s){
        try {
            return new ObjectMapper().reader(List.class).readValue(s);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static Map<String, Object> string2Json(String s){
        try {
            return new ObjectMapper().readValue(s, new TypeReference<Map<String, Object>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return (Map<String, Object>) new Object();
    }
}
