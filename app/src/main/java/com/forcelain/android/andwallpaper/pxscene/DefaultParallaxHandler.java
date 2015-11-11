package com.forcelain.android.andwallpaper.pxscene;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.background.ParallaxBackground;

public class DefaultParallaxHandler implements IUpdateHandler {
    private static final int STATE_GO_LEFT = 0;
    private static final int STATE_GO_RIGHT = 1;
    private static final int STATE_OFF = 2;
    private final ParallaxBackground background;
    private float value;
    private int state;
    private int speed;

    public DefaultParallaxHandler(ParallaxBackground background) {
        this.background = background;
    }

    @Override
    public void onUpdate(float pSecondsElapsed) {
        switch (state){
            case STATE_GO_LEFT:
                value += pSecondsElapsed * (speed + 1);
                break;
            case STATE_GO_RIGHT:
                value -= pSecondsElapsed * (speed + 1);
                break;
        }
        background.setParallaxValue(value);
    }

    @Override
    public void reset() {

    }

    public void goRight() {
        state = STATE_GO_RIGHT;
    }

    public void goLeft() {
        state = STATE_GO_LEFT;
    }

    public void off() {
        state = STATE_OFF;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
