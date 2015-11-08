package com.forcelain.android.andwallpaper;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.forcelain.android.andwallpaper.pxscene.CountryScene;
import com.forcelain.android.andwallpaper.pxscene.ForestScene;
import com.forcelain.android.andwallpaper.pxscene.IndustrialScene;
import com.forcelain.android.andwallpaper.pxscene.MountainScene;
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
    private int factor = 5;
    private PxScene currentScene;
    private Scene scene;
    private CountryScene countryScene;
    private UrbanScene urbanScene;
    private MountainScene mountainScene;
    private IndustrialScene industrialScene;
    private ForestScene forestScene;
    private String currentSceneName;

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
        engineOptions.getRenderOptions().setMultiSampling(true);
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
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String scene = preferences.getString(Constants.PREF_SCENE, "");
        if (!scene.equals(currentSceneName)){
            switch (scene) {
                case Constants.COUNTRY:
                    currentScene = countryScene;
                    break;
                case Constants.FOREST:
                    currentScene = forestScene;
                    break;
                case Constants.INDUSTRIAL:
                    currentScene = industrialScene;
                    break;
                case Constants.MOUNTAIN:
                    currentScene = mountainScene;
                    break;
                case Constants.URBAN:
                    currentScene = urbanScene;
                    break;
            }
            this.scene.clearChildScene();
            currentScene.populateScene(this.scene);
        }
        if (currentScene != null){
            currentScene.onResumeGame();
        }
        currentSceneName = scene;
    }

    @Override
    public void onPauseGame() {
        super.onPauseGame();
        if (currentScene != null){
            currentScene.onPauseGame();
        }
    }

    @Override
    protected void onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        currentScene.onTouchEvent(event);
    }
}
