package com.forcelain.android.andwallpaper.pxscene;

import com.forcelain.android.andwallpaper.LiveWallpaperService;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;

public class CountryScene extends ParallaxScene {


    public CountryScene(LiveWallpaperService liveWallpaperService) {
        super(liveWallpaperService);
    }

    @Override
    protected ParallaxBackground createBackground() {
        return new ParallaxBackground(130/255f, 182/255f, 255/255f);
    }

    @Override
    public void createResources() {
        BitmapTextureAtlas textureAtlas = new BitmapTextureAtlas(getLiveWallpaperService().getTextureManager(), 2048, 256, TextureOptions.BILINEAR);
        regions.add(createRegionMultiSampling(textureAtlas, "country/1.png", 0, 0));
        regions.add(createRegionMultiSampling(textureAtlas, "country/2.png", 400, 0));
        regions.add(createRegionMultiSampling(textureAtlas, "country/3.png", 800, 0));
        textureAtlas.load();
    }

    @Override
    public void onSurfaceChanged(int width, int height) {
        Camera camera = getLiveWallpaperService().getEngine().getCamera();
        for (Sprite sprite : sprites) {
            sprite.setY(camera.getHeight() - sprite.getHeight());
        }
    }
}
