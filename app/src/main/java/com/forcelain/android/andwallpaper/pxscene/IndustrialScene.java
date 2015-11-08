package com.forcelain.android.andwallpaper.pxscene;

import com.forcelain.android.andwallpaper.LiveWallpaperService;

import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;

public class IndustrialScene extends ParallaxScene {

    public IndustrialScene(LiveWallpaperService liveWallpaperService) {
        super(liveWallpaperService);
    }

    @Override
    protected ParallaxBackground createBackground() {
        return new ParallaxBackground(25/255f, 40/255f, 31/255f);
    }

    @Override
    public void createResources() {
        BitmapTextureAtlas textureAtlas = new BitmapTextureAtlas(getLiveWallpaperService().getTextureManager(), 2048, 256, TextureOptions.BILINEAR);
        regions.add(createRegionMultiSampling(textureAtlas, "industrial/1.png", 0, 0));
        regions.add(createRegionMultiSampling(textureAtlas, "industrial/2.png", 274, 0));
        regions.add(createRegionMultiSampling(textureAtlas, "industrial/3.png", 488, 0));
        regions.add(createRegionMultiSampling(textureAtlas, "industrial/4.png", 762, 0));
        textureAtlas.load();
    }
}
