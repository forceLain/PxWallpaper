package com.forcelain.android.andwallpaper.pxscene;

import com.forcelain.android.andwallpaper.LiveWallpaperService;

import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;

public class UrbanScene extends ParallaxScene {

    public UrbanScene(LiveWallpaperService liveWallpaperService) {
        super(liveWallpaperService);
    }

    @Override
    public void createResources() {
        BitmapTextureAtlas textureAtlas = new BitmapTextureAtlas(getLiveWallpaperService().getTextureManager(), 512, 256, TextureOptions.BILINEAR);
        regions.add(createRegionMultiSampling(textureAtlas, "urban/1.png", 0, 0));
        regions.add(createRegionMultiSampling(textureAtlas, "urban/2.png", 200, 0));
        textureAtlas.load();
    }

    @Override
    protected ParallaxBackground createBackground() {
        return new ParallaxBackground(182/255f, 211/255f, 225/255f);
    }
}
