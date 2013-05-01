package br.com.pong;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

public class StartActivity extends SimpleBaseGameActivity {
	
	//Tela
	private float larguraTela;
	private float alturaTela;
	
	//Camera
	private Camera camera;

	@Override
	public EngineOptions onCreateEngineOptions() {
		
		//Pegando o tamanho da tela do aparelho
		Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		this.larguraTela = display.getWidth();
		this.alturaTela = display.getHeight();
		
		//Define a Camera
		this.camera = new Camera(0, 0, this.larguraTela, this.alturaTela);
		
		//Opções da Camera
		final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(this.larguraTela, this.alturaTela), this.camera);
		engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		
		return engineOptions;
		
	}

	@Override
	protected void onCreateResources() {
		ResourceManager.getInstance().loadTextures(this);
	}

	@Override
	protected Scene onCreateScene() {
		return SceneManager.getInstance().loadScene(this.getEngine());
	}

	public float getLarguraTela() {
		return larguraTela;
	}

	public void setLarguraTela(float larguraTela) {
		this.larguraTela = larguraTela;
	}

	public float getAlturaTela() {
		return alturaTela;
	}

	public void setAlturaTela(float alturaTela) {
		this.alturaTela = alturaTela;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

}
