package com.forcelain.android.andwallpaper.pxscene;

import android.view.MotionEvent;

import com.forcelain.android.andwallpaper.Direction;
import com.forcelain.android.andwallpaper.LiveWallpaperService;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TextureRegion;

import java.util.ArrayList;
import java.util.List;

public abstract class ParallaxScene extends PxScene {

    private static final float THRESHOLD = 10;
    protected ArrayList<Sprite> sprites = new ArrayList<>();
    protected ParallaxBackground background;
    protected List<TextureRegion> regions = new ArrayList<>();
    private Scene childrenScene;
    private DefaultParallaxHandler updateHandler;
    private float startX = Float.MIN_VALUE;
    private Direction direction;

    public ParallaxScene(LiveWallpaperService liveWallpaperService) {
        super(liveWallpaperService);
    }

    protected abstract ParallaxBackground createBackground();

    @Override
    public void onSurfaceChanged(int width, int height) {
        Camera camera = getLiveWallpaperService().getEngine().getCamera();
        for (Sprite sprite : sprites) {
            sprite.setY(camera.getHeight() - sprite.getHeight());
        }
    }

    @Override
    public void populateScene(Scene scene) {
        childrenScene = new Scene();
        Camera camera = getLiveWallpaperService().getEngine().getCamera();
        sprites.clear();
        for (TextureRegion region : regions) {
            sprites.add(new Sprite(0, camera.getHeight() - region.getHeight(), region, getLiveWallpaperService().getVertexBufferObjectManager()));
        }
        background = createBackground();
        for (int i = 0; i < sprites.size(); i++) {
            background.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(i + 1, sprites.get(i)));
        }
        childrenScene.setBackground(background);

        updateHandler = new DefaultParallaxHandler(background);

        scene.setChildScene(childrenScene);
    }

    @Override
    public void onPauseGame() {
        childrenScene.clearUpdateHandlers();
    }

    @Override
    public void onResumeGame() {
        childrenScene.registerUpdateHandler(updateHandler);
    }

    @Override
    public void onTouchEvent(MotionEvent event) {
        if (direction != Direction.TOUCH){
            return;
        }
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            startX = event.getX();
        } else if (event.getAction() == MotionEvent.ACTION_MOVE){
            float deltaX = event.getX() - startX;
            if (Math.abs(deltaX) > THRESHOLD){
                if (deltaX > 0){
                    updateHandler.goLeft();
                } else {
                    updateHandler.goRight();
                }
            }
        }
    }

    public void setDirection(Direction direction){
        this.direction = direction;
        if (direction != null){
            switch (direction) {
                case LEFT:
                    updateHandler.goLeft();
                    break;
                case RIGHT:
                    updateHandler.goRight();
                    break;
                case OFF:
                    updateHandler.off();
                    break;
            }
        }
    }

    @Override
    public float getHeight() {
        float maxHeight = 0;
        for (Sprite sprite : sprites) {
            if (sprite.getHeight() > maxHeight){
                maxHeight = sprite.getHeight();
            }
        }
        return maxHeight;
    }
}
