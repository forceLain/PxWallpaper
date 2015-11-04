package com.forcelain.android.andwallpaper;

import android.util.DisplayMetrics;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LiveWallpaperService extends BaseLiveWallpaperService {

    private Camera camera;
    private int factor = 6;
    private PxScene currentScene;
    private ArrayList<PxScene> sceneList;
    private Scene scene;

    @Override
    public EngineOptions onCreateEngineOptions() {
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager)getSystemService(WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        int cameraWidth = displayMetrics.widthPixels;
        int cameraHeight = displayMetrics.heightPixels;
        camera = new Camera(0, 0, cameraWidth/factor, cameraHeight/factor);
        IResolutionPolicy pResolutionPolicy = new FixedResolutionPolicy(cameraWidth, cameraHeight);
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, pResolutionPolicy, camera);
        //engineOptions.getRenderOptions().setMultiSampling(true);
        return engineOptions;
    }

    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws Exception {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

        CountryScene countryScene = new CountryScene(this);
        countryScene.createResources();

        UrbanScene urbanScene = new UrbanScene(this);
        urbanScene.createResources();

        MountainScene mountainScene = new MountainScene(this);
        mountainScene.createResources();

        IndustrialScene industrialScene = new IndustrialScene(this);
        industrialScene.createResources();

        ForestScene forestScene = new ForestScene(this);
        forestScene.createResources();

        sceneList = new ArrayList<>();
        sceneList.add(countryScene);
        sceneList.add(urbanScene);
        sceneList.add(mountainScene);
        sceneList.add(industrialScene);
        sceneList.add(forestScene);

        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    @Override
    public void onSurfaceChanged(GLState pGLState, int pWidth, int pHeight) {
        super.onSurfaceChanged(pGLState, pWidth, pHeight);
        camera.set(0, 0, pWidth / factor, pHeight / factor);
        //currentScene.onSurfaceChanged(pWidth, pHeight);
    }

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception {
        scene = new Scene();
        pOnCreateSceneCallback.onCreateSceneFinished(scene);

    }

    @Override
    public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
        Log.d("&&&&", "onPopulateScene");
        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }

    @Override
    public synchronized void onResumeGame() {
        super.onResumeGame();
        //currentScene = sceneList.get(new Random().nextInt(sceneList.size()));
        currentScene = sceneList.get(1);
        currentScene.populateScene(scene);
    }
}
