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

public class ForestScene extends PxScene {

    private List<TextureRegion> regions = new ArrayList<>();
    private ArrayList<Sprite> sprites = new ArrayList<>();

    public ForestScene(LiveWallpaperService liveWallpaperService) {
        super(liveWallpaperService);
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
        final ParallaxBackground background = new ParallaxBackground(176/255f, 112/255f, 48/255f);
        for (int i = 0; i < sprites.size(); i++) {
            background.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(i + 1, sprites.get(i)));
        }
        childScene.setBackground(background);
        childScene.registerUpdateHandler(new DefaultParallaxHandler(background));
        scene.setChildScene(childScene);
    }
}
