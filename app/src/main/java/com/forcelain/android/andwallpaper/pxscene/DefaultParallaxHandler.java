package com.forcelain.android.andwallpaper.pxscene;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.background.ParallaxBackground;

public class DefaultParallaxHandler implements IUpdateHandler {
    private final ParallaxBackground background;
    private float value;
    private boolean goRight = true;

    public DefaultParallaxHandler(ParallaxBackground background) {
        this.background = background;
    }

    @Override
    public void onUpdate(float pSecondsElapsed) {
        value = goRight ? value - 0.1f : value + 0.11f;
        background.setParallaxValue(value);
    }

    @Override
    public void reset() {

    }

    public void goRight() {
        goRight = true;
    }

    public void goLeft() {
        goRight = false;
    }
}
