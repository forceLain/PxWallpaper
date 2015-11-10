package com.forcelain.android.andwallpaper.pxscene;

import android.view.MotionEvent;

import com.forcelain.android.andwallpaper.LiveWallpaperService;

import org.andengine.entity.scene.Scene;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;

public abstract class PxScene {

    private final LiveWallpaperService liveWallpaperService;

    public PxScene(LiveWallpaperService liveWallpaperService) {
        this.liveWallpaperService = liveWallpaperService;
    }

    public LiveWallpaperService getLiveWallpaperService() {
        return liveWallpaperService;
    }

    abstract public void createResources();

    public abstract void onSurfaceChanged(int width, int height);

    protected TextureRegion createRegionMultiSampling(BitmapTextureAtlas textureAtlas, String fileName, int x, int y) {
        BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureAtlas, liveWallpaperService, fileName, x, y);
        BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureAtlas, liveWallpaperService, fileName, x + 2, y);
        return BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureAtlas, liveWallpaperService, fileName, x + 1, y);
    }

    public abstract void populateScene(Scene scene);

    public abstract void onPauseGame();

    public abstract void onResumeGame();

    public abstract void onTouchEvent(MotionEvent event);

    public abstract float getHeight();
}
