package com.binance.connector.client.utils;

import com.alibaba.fastjson2.JSON;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class JSONParser {

    private JSONParser() {
    }

    public static String getJSONStringValue(String json, String key) {
        try {
            JSONObject obj = new JSONObject(json);
            return obj.getString(key);
        } catch (JSONException e) {
            throw new JSONException(String.format("[JSONParser] Failed to get \"%s\"  from JSON object", key));
        }
    }

    public static int getJSONIntValue(String json, String key) {
        try {
            JSONObject obj = new JSONObject(json);
            return obj.getInt(key);
        } catch (JSONException e) {
            throw new JSONException(String.format("[JSONParser] Failed to get \"%s\" from JSON object", key));
        }
    }

    public static String getJSONArray(ArrayList<?> symbols, String key) {
        try {
            JSONArray arr = new JSONArray(symbols);
            return arr.toString();
        } catch (JSONException e) {
            throw new JSONException(String.format("[JSONParser] Failed to convert \"%s\" to JSON array", key));
        }
    }

    public static String buildJSONString(String method, JSONObject parameters) {
        try {
            JSONObject json = new JSONObject();
            json.put("id", UUID.randomUUID().toString());
            json.put("method", method);
            json.put("params", parameters);
            return json.toString();
        } catch (JSONException e) {
            throw new JSONException(String.format("[JSONParser] Failed to convert to JSON string"));
        }
    }

    public static String buildJSONString(String method, Map<String, Object> parameters) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("id", UUID.randomUUID().toString());
        jsonMap.put("method", method);
        jsonMap.put("params", parameters);
        return JSON.toJSONString(jsonMap);
    }

    public static Map<String, Object> sortJSONObject(Map<String, Object> parameters) {
        return parameters.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    public static JSONObject addKeyValue(JSONObject parameters, String key, Object value) {
        if (parameters == null) {
            parameters = new JSONObject();
        }

        return parameters.put(key, value);
    }

    public static Object pullValue(JSONObject parameters, String key) {
        if (parameters == null) {
            return null;
        }
        Object value = parameters.opt(key);
        parameters.remove(key);
        return value;
    }

}
