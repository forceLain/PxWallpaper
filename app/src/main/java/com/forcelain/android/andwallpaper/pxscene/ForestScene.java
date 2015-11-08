package com.forcelain.android.andwallpaper.pxscene;

import com.forcelain.android.andwallpaper.LiveWallpaperService;

import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;

public class ForestScene extends ParallaxScene {


    public ForestScene(LiveWallpaperService liveWallpaperService) {
        super(liveWallpaperService);
    }

    @Override
    protected ParallaxBackground createBackground() {
        return new ParallaxBackground(176/255f, 112/255f, 48/255f);
    }

    @Override
    public void createResources() {
        BitmapTextureAtlas textureAtlas = new BitmapTextureAtlas(getLiveWallpaperService().getTextureManager(), 2048, 256, TextureOptions.BILINEAR);
        regions.add(createRegionMultiSampling(textureAtlas, "forest/1.png", 0, 0));
        regions.add(createRegionMultiSampling(textureAtlas, "forest/2.png", 548, 0));
        regions.add(createRegionMultiSampling(textureAtlas, "forest/3.png", 822, 0));
        regions.add(createRegionMultiSampling(textureAtlas, "forest/4.png", 1096, 0));
        textureAtlas.load();
    }
}
