package com.akashi.animelistdatabase.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class JsonUtils {

    private static final Gson gson = new Gson();

    /**
     * Converts a string JSON to an specific object.
     * @param jsonString String JSON.
     * @param clazz Object class.
     * @param <T> Object type.
     * @return An instance of the object.
     * @throws JsonSyntaxException In case of error.
     */
    public static <T> T fromJson(String jsonString, Class<T> clazz) throws JsonSyntaxException {
        return gson.fromJson(jsonString, clazz);
    }

    /**
     * Converts an object to string JSON.
     * @param object Object to convert.
     * @return String JSON.
     */
    public static String toJson(Object object) {
        return gson.toJson(object);
    }
}
