package com.forcelain.android.andwallpaper;

import android.util.DisplayMetrics;
import android.view.WindowManager;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.IResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.ui.livewallpaper.BaseLiveWallpaperService;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.util.GLState;

public class LiveWallpaperService extends BaseLiveWallpaperService {

    private TextureRegion backRegion;
    private TextureRegion frontRegion;
    private ParallaxBackground background;
    private TextureRegion forestRegion;
    private float value = 0;
    private int cameraWidth;
    private int cameraHeight;
    private Camera camera;

    @Override
    public EngineOptions onCreateEngineOptions() {
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        adjustScreenSize(displayMetrics);
        IResolutionPolicy pResolutionPolicy = new RatioResolutionPolicy(cameraWidth, cameraHeight);
        return new EngineOptions(true, ScreenOrientation.PORTRAIT_SENSOR, pResolutionPolicy, camera);
    }

    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws Exception {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        BitmapTextureAtlas textureAtlas = new BitmapTextureAtlas(getTextureManager(), 2048, 2048, TextureOptions.BILINEAR);

        BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureAtlas, this, "back.png", 0, 0);
        BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureAtlas, this, "back.png", 2, 0);
        backRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureAtlas, this, "back.png", 1, 0);

        frontRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureAtlas, this, "front.png", 400, 0);

        forestRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureAtlas, this, "country_forest.png", 800, 0);

        pOnCreateResourcesCallback.onCreateResourcesFinished();
        textureAtlas.load();
    }

    @Override
    public void onSurfaceChanged(GLState pGLState, int pWidth, int pHeight) {
        super.onSurfaceChanged(pGLState, pWidth, pHeight);
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        adjustScreenSize(displayMetrics);
        adjustScene();
    }

    private void adjustScreenSize(DisplayMetrics displayMetrics) {
        WindowManager wm = (WindowManager)getSystemService(WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        wm.getDefaultDisplay().getRotation();
        cameraWidth = displayMetrics.widthPixels;
        cameraHeight = displayMetrics.heightPixels;
        if (camera == null){
            camera = new Camera(0, 0, cameraWidth, cameraHeight);
        } else {
            camera.set(0, 0, cameraWidth, cameraHeight);
        }
    }

    private void adjustScene() {
        float minSize = Math.min(cameraHeight, cameraWidth);
        float vaderSize = minSize / 2f;
        /*factor = 1024 / vaderSize;
        vader.setHeight(vaderSize);
        vader.setWidth(vaderSize);
        vader.setPosition(CAMERA_WIDTH / 2 - vaderSize / 2, CAMERA_HEIGHT / 2 - vaderSize / 2);
        float saberOffsetX = 234 / factor;
        float saberOffsetY = 60 / factor;

        float smokeOffsetX = (208) / factor - 16;
        float smokeOffsetY = (8) / factor - 16;

        particleSystem.reset();
        PointParticleEmitter particleEmitter = (PointParticleEmitter) particleSystem.getParticleEmitter();
        particleEmitter.setCenter(CAMERA_WIDTH/2 + smokeOffsetX, CAMERA_HEIGHT/2 + smokeOffsetY);

        float saberAnchor = CAMERA_WIDTH/2 + saberOffsetX;
        float saberAnchorY = CAMERA_HEIGHT/2 + saberOffsetY - saber.getHeight()/2;
        float angle = -65;
        saber.setPosition(saberAnchor, saberAnchorY);
        saber.setRotationCenter(0, saber.getHeight()/2);
        saber.setRotation(angle);
        saber.setScaleCenter(0, saber.getHeight()/2);*/
    }


    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception {
        Scene scene = new Scene();

        int offset = 0;


        Sprite back = new Sprite(0, cameraHeight - backRegion.getHeight() - offset, backRegion, getVertexBufferObjectManager());
        Sprite front = new Sprite(0, cameraHeight - frontRegion.getHeight() - offset, frontRegion, getVertexBufferObjectManager());
        Sprite forest = new Sprite(0, cameraHeight - forestRegion.getHeight() - offset, forestRegion, getVertexBufferObjectManager());

        background = new ParallaxBackground(130/255f, 182/255f, 255/255f);
        background.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(1, back));
        background.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(2, forest));
        background.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(3, front));
        scene.setBackground(background);


        scene.registerUpdateHandler(new IUpdateHandler() {
            @Override
            public void onUpdate(float pSecondsElapsed) {
                value -= 0.1f;
                background.setParallaxValue(value);
            }

            @Override
            public void reset() {

            }
        });

        //scene.attachChild(back);

        pOnCreateSceneCallback.onCreateSceneFinished(scene);

    }

    @Override
    public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }
}
