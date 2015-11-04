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

public class UrbanScene extends PxScene {

    private List<TextureRegion> regions = new ArrayList<>();
    private ArrayList<Sprite> sprites = new ArrayList<>();

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
    public void onSurfaceChanged(int width, int height) {
        Camera camera = getLiveWallpaperService().getEngine().getCamera();
        for (Sprite sprite : sprites) {
            sprite.setY(camera.getHeight() - sprite.getHeight());
        }
    }


    @Override
    public void populateScene(Scene scene) {

        scene.clearChildScene();

        Scene childrenScene = new Scene();

        Camera camera = getLiveWallpaperService().getEngine().getCamera();
        sprites.clear();
        for (TextureRegion region : regions) {
            sprites.add(new Sprite(0, camera.getHeight() - region.getHeight(), region, getLiveWallpaperService().getVertexBufferObjectManager()));
        }
        final ParallaxBackground background = new ParallaxBackground(182/255f, 211/255f, 225/255f);
        for (int i = 0; i < sprites.size(); i++) {
            background.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(i + 1, sprites.get(i)));
        }
        childrenScene.setBackground(background);
        childrenScene.registerUpdateHandler(new DefaultParallaxHandler(background));

        scene.setChildScene(childrenScene);
    }
}
