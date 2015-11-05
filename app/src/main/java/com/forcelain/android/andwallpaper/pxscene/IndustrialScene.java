package com.forcelain.android.andwallpaper.pxscene;

import com.forcelain.android.andwallpaper.LiveWallpaperService;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.TextureRegion;

import java.util.ArrayList;
import java.util.List;

public class IndustrialScene extends PxScene {

    private List<TextureRegion> regions = new ArrayList<>();
    private ArrayList<Sprite> sprites = new ArrayList<>();

    public IndustrialScene(LiveWallpaperService liveWallpaperService) {
        super(liveWallpaperService);
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

    @Override
    public void onSurfaceChanged(int width, int height) {
        Camera camera = getLiveWallpaperService().getEngine().getCamera();
        for (Sprite sprite : sprites) {
            sprite.setY(camera.getHeight() - sprite.getHeight());
        }
    }

    @Override
    public void populateScene(Scene scene) {
        Camera camera = getLiveWallpaperService().getEngine().getCamera();
        Scene childScene = new Scene();
        sprites.clear();
        for (TextureRegion region : regions) {
            sprites.add(new Sprite(0, camera.getHeight() - region.getHeight(), region, getLiveWallpaperService().getVertexBufferObjectManager()));
        }
        final ParallaxBackground background = new ParallaxBackground(25/255f, 40/255f, 31/255f);
        for (int i = 0; i < sprites.size(); i++) {
            background.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(i + 1, sprites.get(i)));
        }
        childScene.setBackground(background);
        childScene.registerUpdateHandler(new DefaultParallaxHandler(background));
        scene.setChildScene(childScene);
    }
}
