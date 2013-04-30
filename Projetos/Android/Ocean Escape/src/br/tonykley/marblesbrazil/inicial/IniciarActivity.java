package br.tonykley.marblesbrazil.inicial;

import java.security.spec.MGF1ParameterSpec;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.WakeLockOptions;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.SpriteBackground;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.vertex.RectangleVertexBuffer;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

public class IniciarActivity extends BaseGameActivity {

	private float larguraTela;
	private float alturaTela;
	private Camera camera;
	private Scene cena;

	// Imagens
	private BitmapTextureAtlas bitmapTextureAtlas;
	private TextureRegion inicioRegiao;
	private ImagemInicial imagemInicio;
	private BitmapTextureAtlas mBackgroundTexture;
	private TextureRegion mBgTexture;

	public void onLoadComplete() {
		// TODO Auto-generated method stub

	}

	public Engine onLoadEngine() {
		// PEGA TAMANHO DA TELA DO APARELHO
		Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE))
				.getDefaultDisplay();
		this.larguraTela = display.getWidth();
		this.alturaTela = display.getHeight();
		// define a camera
		this.camera = new Camera(0, 0, this.larguraTela, this.alturaTela);
		// Opçoes da Engine
		final EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(
						this.larguraTela, this.alturaTela), this.camera);
		final Engine engine = new Engine(engineOptions);
		engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		return engine;
	}

	public void onLoadResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.bitmapTextureAtlas = new BitmapTextureAtlas(512, 512,TextureOptions.BILINEAR);
		this.inicioRegiao = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.bitmapTextureAtlas, this, "play.png",0, 0);
		this.mEngine.getTextureManager().loadTexture(this.bitmapTextureAtlas);
		
		// Plano de fundo
		this.mBackgroundTexture = new BitmapTextureAtlas(1024, 1024, TextureOptions.DEFAULT);
		mBgTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBackgroundTexture, this, "backgroundInicio.png", 0, 0);
		this.mEngine.getTextureManager().loadTextures(this.mBackgroundTexture);
	}

	public Scene onLoadScene() {
		cena = new Scene();
		final int centroA = (int) ((larguraTela - mBgTexture.getWidth()) / 2); 
        final int centroB = (int) ((alturaTela - mBgTexture.getHeight()) / 2);
        SpriteBackground bg = new SpriteBackground(new Sprite(centroA, centroB,
        		this.mBgTexture));
        cena.setBackground(bg);
		
		int centroX = (int)((this.larguraTela/2) - 150);
		int centroY = (int)((this.alturaTela/2));
		this.imagemInicio = new ImagemInicial(centroX, centroY, this.inicioRegiao,this);
		cena.attachChild(this.imagemInicio);
		cena.registerTouchArea(this.imagemInicio);
		
		return cena;
	}
}
