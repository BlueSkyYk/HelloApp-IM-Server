package com.cuityk.helloappim.util;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Json工具类
 */
public class JsonUtil {

    private static final Gson gson = new Gson();

    public static <T> T toObject(String json, Type type) {
        return gson.fromJson(json, type);
    }

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }
}
