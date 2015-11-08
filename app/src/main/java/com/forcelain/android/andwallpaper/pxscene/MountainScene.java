package com.forcelain.android.andwallpaper.pxscene;

import com.forcelain.android.andwallpaper.LiveWallpaperService;

import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;

public class MountainScene extends ParallaxScene {

    public MountainScene(LiveWallpaperService liveWallpaperService) {
        super(liveWallpaperService);
    }

    @Override
    protected ParallaxBackground createBackground() {
        return new ParallaxBackground(171/255f, 106/255f, 140/255f);
    }

    @Override
    public void createResources() {
        BitmapTextureAtlas textureAtlas = new BitmapTextureAtlas(getLiveWallpaperService().getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
        regions.add(createRegionMultiSampling(textureAtlas, "mountain/1.png", 550, 0));
        regions.add(createRegionMultiSampling(textureAtlas, "mountain/2.png", 550, 200));
        regions.add(createRegionMultiSampling(textureAtlas, "mountain/3.png", 0, 0));
        regions.add(createRegionMultiSampling(textureAtlas, "mountain/4.png", 0, 170));
        regions.add(createRegionMultiSampling(textureAtlas, "mountain/5.png", 0, 350));
        textureAtlas.load();
    }
}
