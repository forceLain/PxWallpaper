package com.forcelain.android.andwallpaper;

import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.forcelain.android.andwallpaper.pxscene.CountryScene;
import com.forcelain.android.andwallpaper.pxscene.ForestScene;
import com.forcelain.android.andwallpaper.pxscene.IndustrialScene;
import com.forcelain.android.andwallpaper.pxscene.MountainScene;
import com.forcelain.android.andwallpaper.pxscene.ParallaxScene;
import com.forcelain.android.andwallpaper.pxscene.PxScene;
import com.forcelain.android.andwallpaper.pxscene.UrbanScene;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FixedResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.IResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.extension.ui.livewallpaper.BaseLiveWallpaperService;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.util.GLState;

public class LiveWallpaperService extends BaseLiveWallpaperService {

    private Camera camera;
    private float factor = 5;
    private PxScene currentScene;
    private Scene scene;
    private CountryScene countryScene;
    private UrbanScene urbanScene;
    private MountainScene mountainScene;
    private IndustrialScene industrialScene;
    private ForestScene forestScene;
    private com.forcelain.android.andwallpaper.Scene currentSceneEnum;

    @Override
    public EngineOptions onCreateEngineOptions() {
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        int cameraWidth = displayMetrics.widthPixels;
        int cameraHeight = displayMetrics.heightPixels;
        camera = new Camera(0, 0, cameraWidth / factor, cameraHeight / factor);
        IResolutionPolicy pResolutionPolicy = new FixedResolutionPolicy(cameraWidth, cameraHeight);
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, pResolutionPolicy, camera);
        //engineOptions.getRenderOptions().setMultiSampling(true);
        return engineOptions;
    }

    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws Exception {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

        countryScene = new CountryScene(this);
        countryScene.createResources();

        urbanScene = new UrbanScene(this);
        urbanScene.createResources();

        mountainScene = new MountainScene(this);
        mountainScene.createResources();

        industrialScene = new IndustrialScene(this);
        industrialScene.createResources();

        forestScene = new ForestScene(this);
        forestScene.createResources();

        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    @Override
    public void onSurfaceChanged(GLState pGLState, int pWidth, int pHeight) {
        super.onSurfaceChanged(pGLState, pWidth, pHeight);
        camera.set(0, 0, pWidth / factor, pHeight / factor);
        currentScene.onSurfaceChanged(pWidth, pHeight);
    }

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception {
        scene = new Scene();
        pOnCreateSceneCallback.onCreateSceneFinished(this.scene);
    }

    @Override
    public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }

    @Override
    public synchronized void onResumeGame() {
        super.onResumeGame();
        com.forcelain.android.andwallpaper.Scene newScene = PrefUtils.getScene(this);
        if (newScene != currentSceneEnum) {
            switch (newScene) {
                case COUNTRY:
                    currentScene = countryScene;
                    break;
                case FOREST:
                    currentScene = forestScene;
                    break;
                case INDUSTRIAL:
                    currentScene = industrialScene;
                    break;
                case MOUNTAIN:
                    currentScene = mountainScene;
                    break;
                case URBAN:
                    currentScene = urbanScene;
                    break;
            }
            this.scene.clearChildScene();
            currentScene.populateScene(this.scene);
        }
        currentSceneEnum = newScene;

        Direction direction = PrefUtils.getDirection(this);
        int speed = PrefUtils.getSpeed(this);
        if (currentScene instanceof ParallaxScene){
            ((ParallaxScene) currentScene).setDirection(direction);
            ((ParallaxScene) currentScene).setSpeed(speed);
        }

        boolean fullScreen = PrefUtils.isFullScreen(this);
        if (fullScreen || currentSceneEnum == com.forcelain.android.andwallpaper.Scene.FOREST){
            int screenHeight = getScreenHeight();
            float sceneHeight = currentScene.getHeight();
            factor = screenHeight / sceneHeight;
        } else {
            factor = PrefUtils.getSize(this) + 1;
        }

        if (currentScene != null) {
            currentScene.onResumeGame();
        }
    }

    private int getScreenHeight() {
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            Point point = new Point();
            display.getRealSize(point);
            return point.y;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    @Override
    public void onPauseGame() {
        super.onPauseGame();
        if (currentScene != null) {
            currentScene.onPauseGame();
        }
    }

    @Override
    protected void onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        currentScene.onTouchEvent(event);
    }
}
