package com.forcelain.android.andwallpaper.pxscene;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.background.ParallaxBackground;

public class DefaultParallaxHandler implements IUpdateHandler {
    private final ParallaxBackground background;
    private float value;

    public DefaultParallaxHandler(ParallaxBackground background) {
        this.background = background;
    }

    @Override
    public void onUpdate(float pSecondsElapsed) {
        value -= 0.1f;
        background.setParallaxValue(value);
    }

    @Override
    public void reset() {

    }
}
