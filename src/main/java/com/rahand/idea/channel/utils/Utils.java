package com.rahand.idea.channel.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rahand.idea.channel.enums.Bank;
import com.rahand.idea.channel.exception.ChannelRequestException;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.Base64;

import java.util.Iterator;
import java.util.Map;

/* Created by z.mohammadtabar on 28/08/2021.*/

public class Utils {

    public static Map jsonStringToMap(String json) {
        Map result = null;
        try {
            result = new ObjectMapper().readValue(json, Map.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String encodeStringToBase64(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }

    public static String decodeBase64ToString(String encodedString) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        return new String(decodedBytes);
    }

    public static boolean hasKey(JSONObject jsonObject, String key) {
        return jsonObject != null && jsonObject.has(key);
    }

    public static Object getJsonValueByIgnoredCaseKey(String json, String key) {

        try {
            JSONObject jsonObject = new JSONObject(json);
            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()) {
                String key1 = keys.next();
                if (key.equalsIgnoreCase(key1)) {
                    return jsonObject.get(key1);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, String> convertObjectToMap(Object obj) {
        // object -> Map
        ObjectMapper oMapper = new ObjectMapper();
        oMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        Map<String, String> map = oMapper.convertValue(obj, Map.class);
        return map;
    }

    public static Bank getBankEnumFromMapByKey(Map<String, String> map, String key) throws IllegalArgumentException {
        String bankStr = map.get(key);
        if (bankStr == null) {
            throw new ChannelRequestException("'" + key + "' is null.");
        }
        return Bank.valueOf(bankStr);
    }

}
