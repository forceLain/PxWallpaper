package com.forcelain.android.andwallpaper;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PxActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_px);
        findViewById(R.id.button_set_wallpaper).setOnClickListener(this);
        findViewById(R.id.radio_country).setOnClickListener(radioListener);
        findViewById(R.id.radio_forest).setOnClickListener(radioListener);
        findViewById(R.id.radio_industrial).setOnClickListener(radioListener);
        findViewById(R.id.radio_mountain).setOnClickListener(radioListener);
        findViewById(R.id.radio_urban).setOnClickListener(radioListener);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            intent.setAction(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
            String p = getPackageName();
            String c = LiveWallpaperService.class.getCanonicalName();
            intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, new ComponentName(p, c));
        } else {
            intent.setAction(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER);
        }
        startActivityForResult(intent, 0);
    }

    private View.OnClickListener radioListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.radio_country:
                    setScene(Constants.COUNTRY);
                    break;
                case R.id.radio_forest:
                    setScene(Constants.FOREST);
                    break;
                case R.id.radio_industrial:
                    setScene(Constants.INDUSTRIAL);
                    break;
                case R.id.radio_mountain:
                    setScene(Constants.MOUNTAIN);
                    break;
                case R.id.radio_urban:
                    setScene(Constants.URBAN);
                    break;
            }
        }
    };

    private void setScene(String scene) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit()
                .putString(Constants.PREF_SCENE, scene)
                .apply();
    }

}
