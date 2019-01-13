package com.bojeg;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class PreferencesManager {

    private static final String USER_ID = "USER_ID";

    private static PreferencesManager sInstance;

    private Context context;

    public PreferencesManager(Context context) {
        this.context = context;
    }

    public static PreferencesManager with(Context context) {
        if (sInstance == null) {
            sInstance = new PreferencesManager(context);
        }

        return sInstance;
    }

    @Nullable
    public String getUserId() {
        return getSharedPreferences().getString(USER_ID, null);
    }

    public void setUserId(@NonNull String id) {
        Editor editor = getEditor();
        editor.putString(USER_ID, id);
        editor.apply();
    }

    private SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    private Editor getEditor() {
        return getSharedPreferences().edit();
    }
}
