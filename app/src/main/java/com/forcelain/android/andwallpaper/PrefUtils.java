package com.forcelain.android.andwallpaper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

public class PrefUtils {

    private static final String PREF_SCENE = "PREF_SCENE";
    private static final String PREF_DIRECTION = "PREF_DIRECTION";
    private static final String PREF_SIZE = "PREF_SIZE";
    private static final String PREF_FULL_SCREEN = "PREF_FULL_SCREEN";

    public static void setScene(Context context, Scene scene) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit()
                .putString(PREF_SCENE, scene.name())
                .apply();
    }

    @NonNull
    public static Scene getScene(Context context) {
        Scene scene = Scene.COUNTRY;
        String sceneString = PreferenceManager.getDefaultSharedPreferences(context).getString(PREF_SCENE, null);
        if (sceneString != null){
            try {
                scene = Scene.valueOf(sceneString);
            } catch (Exception ignore){}
        }
        return scene;
    }

    public static void setDirection(Context context, Direction direction) {
        PreferenceManager
                .getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_DIRECTION, direction.name())
                .apply();
    }

    @NonNull
    public static Direction getDirection(Context context) {
        Direction direction = Direction.RIGHT;
        String directionString = PreferenceManager.getDefaultSharedPreferences(context).getString(PREF_DIRECTION, null);
        if (directionString != null) {
            try {
                direction = Direction.valueOf(directionString);
            } catch (Exception ignore){}
        }
        return direction;
    }

    public static void setSize(Context context, int size) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(PREF_SIZE, size)
                .apply();
    }

    public static int getSize(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(PREF_SIZE, 0);
    }

    public static void setFullScreen(Context context, boolean fullScreen) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(PREF_FULL_SCREEN, fullScreen)
                .apply();
    }

    public static boolean isFullScreen(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(PREF_FULL_SCREEN, true);
    }
}
