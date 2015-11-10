package com.forcelain.android.andwallpaper;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.SeekBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PxActivity extends AppCompatActivity {

    public static final int SIZE_MAX = 5;
    @Bind(R.id.radio_country) RadioButton countryRadioButton;
    @Bind(R.id.radio_forest) RadioButton forestRadioButton;
    @Bind(R.id.radio_industrial) RadioButton industrialRadioButton;
    @Bind(R.id.radio_mountain) RadioButton mountainRadioButton;
    @Bind(R.id.radio_urban) RadioButton urbanRadioButton;

    @Bind(R.id.radio_direction_left) RadioButton directionLeftRadioButton;
    @Bind(R.id.radio_direction_right) RadioButton directionRightRadioButton;
    @Bind(R.id.radio_direction_touch) RadioButton directionTouchRadioButton;
    @Bind(R.id.radio_direction_off) RadioButton directionOffRadioButton;

    @Bind(R.id.checkbox_fullscreen) CheckBox fullScreenCheckBox;
    @Bind(R.id.seek_bar_size) SeekBar sizeSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_px);
        ButterKnife.bind(this);
        countryRadioButton.setOnClickListener(radioSceneListener);
        forestRadioButton.setOnClickListener(radioSceneListener);
        industrialRadioButton.setOnClickListener(radioSceneListener);
        mountainRadioButton.setOnClickListener(radioSceneListener);
        urbanRadioButton.setOnClickListener(radioSceneListener);

        directionLeftRadioButton.setOnClickListener(directionClickListener);
        directionRightRadioButton.setOnClickListener(directionClickListener);
        directionTouchRadioButton.setOnClickListener(directionClickListener);
        directionOffRadioButton.setOnClickListener(directionClickListener);

        fullScreenCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sizeSeekBar.setEnabled(!isChecked);
                saveFullscreen(isChecked);
            }
        });

        sizeSeekBar.setMax(SIZE_MAX);
        sizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                saveSize(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Scene scene = PrefUtils.getScene(this);
        switch (scene) {
            case COUNTRY:
                countryRadioButton.setChecked(true);
                break;
            case FOREST:
                forestRadioButton.setChecked(true);
                break;
            case INDUSTRIAL:
                industrialRadioButton.setChecked(true);
                break;
            case MOUNTAIN:
                mountainRadioButton.setChecked(true);
                break;
            case URBAN:
                urbanRadioButton.setChecked(true);
                break;
        }

        Direction direction = PrefUtils.getDirection(this);
        switch (direction) {
            case LEFT:
                directionLeftRadioButton.setChecked(true);
                break;
            case RIGHT:
                directionRightRadioButton.setChecked(true);
                break;
            case TOUCH:
                directionTouchRadioButton.setChecked(true);
                break;
            case OFF:
                directionOffRadioButton.setChecked(true);
                break;
        }

        int size = PrefUtils.getSize(this);
        sizeSeekBar.setProgress(size);

        boolean fullScreen = PrefUtils.isFullScreen(this);
        fullScreenCheckBox.setChecked(fullScreen);
    }

    private void saveFullscreen(boolean fullScreen) {
        PrefUtils.setFullScreen(this, fullScreen);
    }

    private void saveSize(int size) {
        PrefUtils.setSize(this, size);
    }

    @OnClick(R.id.button_set_wallpaper)
    void onWallpaperSetClicked() {
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

    private View.OnClickListener radioSceneListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.radio_country:
                    setScene(Scene.COUNTRY);
                    break;
                case R.id.radio_forest:
                    setScene(Scene.FOREST);
                    break;
                case R.id.radio_industrial:
                    setScene(Scene.INDUSTRIAL);
                    break;
                case R.id.radio_mountain:
                    setScene(Scene.MOUNTAIN);
                    break;
                case R.id.radio_urban:
                    setScene(Scene.URBAN);
                    break;
            }
        }
    };

    private View.OnClickListener directionClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.radio_direction_left:
                    PrefUtils.setDirection(PxActivity.this, Direction.LEFT);
                    break;
                case R.id.radio_direction_right:
                    PrefUtils.setDirection(PxActivity.this, Direction.RIGHT);
                    break;
                case R.id.radio_direction_touch:
                    PrefUtils.setDirection(PxActivity.this, Direction.TOUCH);
                    break;
                case R.id.radio_direction_off:
                    PrefUtils.setDirection(PxActivity.this, Direction.OFF);
                    break;
            }
        }
    };

    private void setScene(Scene scene) {
        PrefUtils.setScene(this, scene);
    }

}
