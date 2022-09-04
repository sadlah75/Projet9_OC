package com.openclassrooms.realestatemanager.utils;

import android.content.Context;

public class SharedPreferencesHelper {

    private static final String SHARED_PREF_PROPERTY = "SHARED_PREF_PROPERTY";
    private static final String SHARED_PREF_PROPERTY_MODE = "SHARED_PREF_PROPERTY_MODE";

    public static final String MODE_CREATE = "MODE_CREATE";
    public static final String MODE_UPDATE = "MODE_UPDATE";
    public static final String MODE_DETAIL = "MODE_DETAIL";


    // --- variables checkbox's visibility ---

    private static final String SHARED_PREF_VISIBILITY = "SHARED_PREF_VISIBILITY";
    private static final String SHARED_PREF_STATUS = "SHARED_PREF_STATUS";

    private static final String SHARED_PREF_PROP = "SHARED_PREF_PROP";
    private static final String SHARED_PREF_PROP_POSITION = "SHARED_PREF_PROP_POSITION";

    // --- Action mode CREATE or UPDATE ---
    public static void setActionPropertyMode(Context context, String mode) {
        context.getSharedPreferences(SHARED_PREF_PROPERTY,Context.MODE_PRIVATE)
                .edit()
                .putString(SHARED_PREF_PROPERTY_MODE,mode)
                .apply();
    }

    public static String getActionPropertyMode(Context context) {
        return context.getSharedPreferences(SHARED_PREF_PROPERTY,Context.MODE_PRIVATE)
                .getString(SHARED_PREF_PROPERTY_MODE,null);
    }

    // --- Checkbox's visibility ---

    public static void setVisibility(Context context, boolean status) {
        context.getSharedPreferences(SHARED_PREF_VISIBILITY,Context.MODE_PRIVATE)
                .edit()
                .putBoolean(SHARED_PREF_STATUS,status)
                .apply();
    }

    public static boolean isVisible(Context context) {
        return context.getSharedPreferences(SHARED_PREF_VISIBILITY,Context.MODE_PRIVATE)
                .getBoolean(SHARED_PREF_STATUS,false);
    }

    // --- Property's position ---

    public static void setPosition(Context context,int position) {
        context.getSharedPreferences(SHARED_PREF_PROP,Context.MODE_PRIVATE)
                .edit()
                .putInt(SHARED_PREF_PROP_POSITION,position)
                .apply();
    }

    public static int getPosition(Context context) {
        return context.getSharedPreferences(SHARED_PREF_PROP,Context.MODE_PRIVATE)
                .getInt(SHARED_PREF_PROP_POSITION,0);
    }
}
