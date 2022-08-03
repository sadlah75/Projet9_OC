package com.openclassrooms.realestatemanager.database.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class TypeConverter {
    private static Gson gson = new Gson();


    @android.arch.persistence.room.TypeConverter
    public static List<String> toList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<String>>() {
        }.getType();

        return gson.fromJson(data, listType);
    }

    @android.arch.persistence.room.TypeConverter
    public static String toString(List<String> someObjects) {
        return gson.toJson(someObjects);
    }
}
