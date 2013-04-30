package br.tonykley.marblesbrazil.inicial;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.SpriteBackground;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;

public class GameOverActivity extends BaseGameActivity {

	private float larguraTela;
	private float alturaTela;
	private Camera camera;
	private Scene cena;
	
	// Imagens
	private BitmapTextureAtlas bitmapTextureAtlas;
	
    private BitmapTextureAtlas mBackgroundTexture;
    private TextureRegion mBgTexture;
    
	private TextureRegion sairRegiao;
	private TextureRegion continueRegiao;
	private ImagemInicial imagemContinue;
	private Exit sair;

	// PONTUACAO
	private Long pontos = 0L;
	private Long recorde = 0L;
	private BitmapTextureAtlas pontuacaoTextureAtlas;
	private Font pontuacaoFont;
	private ChangeableText textoPontuacao;
	
	@Override
    protected void onCreate(Bundle pSavedInstanceState) {
        if (this.getIntent() != null && this.getIntent().getExtras() != null) {
            Bundle bundle = this.getIntent().getExtras();
            pontos = bundle.getLong("pontos");
            recorde = bundle.getLong("recorde");
        }
        super.onCreate(pSavedInstanceState);
    }
	
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
		return engine;
	}

	public void onLoadResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.bitmapTextureAtlas = new BitmapTextureAtlas(512, 512,
				TextureOptions.BILINEAR);
		this.sairRegiao = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.bitmapTextureAtlas, this, "gameoverExit.png",	200, 0);
		this.continueRegiao = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.bitmapTextureAtlas, this, "gameoverContinue.png",	0, 0);
		this.mEngine.getTextureManager().loadTexture(this.bitmapTextureAtlas);
		// Plano de fundo
		this.mBackgroundTexture = new BitmapTextureAtlas(1024, 1024, TextureOptions.DEFAULT);
	    mBgTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBackgroundTexture, this, "backgroundGameOver.png", 0, 0);
        this.mEngine.getTextureManager().loadTextures(this.mBackgroundTexture);
		// PONTOS
		pontuacaoTextureAtlas = new BitmapTextureAtlas(256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.pontuacaoFont = new Font(pontuacaoTextureAtlas, Typeface.create(
				Typeface.DEFAULT, Typeface.BOLD), 20, true, Color.BLACK);
		this.mEngine.getTextureManager().loadTexture(pontuacaoTextureAtlas);
		this.mEngine.getFontManager().loadFont(pontuacaoFont);
	}

	public Scene onLoadScene() {
		cena = new Scene();

        final int centroA = (int) ((larguraTela - mBgTexture.getWidth()) / 2); 
        final int centroB = (int) ((alturaTela - mBgTexture.getHeight()) / 2);
        SpriteBackground bg = new SpriteBackground(new Sprite(centroA, centroB,
        		this.mBgTexture));
        cena.setBackground(bg);

		int centroX = (int) (this.larguraTela / 2) - 100;
		int centroY = (int) (this.alturaTela / 2) - 90;
		this.imagemContinue = new ImagemInicial(centroX, centroY,
				this.continueRegiao, this);
		cena.attachChild(this.imagemContinue);
		cena.registerTouchArea(this.imagemContinue);
		
		int centroZ = (int) (this.larguraTela / 2) - 100;
		int centrow = (int) (this.alturaTela / 2) + 30;
		this.sair = new Exit(centroZ, centrow,
				this.sairRegiao, this);
		cena.attachChild(this.sair);
		cena.registerTouchArea(this.sair);		

		// Pontos
		String textoPontos = "Pontuação: " + pontos + " / Recorde: " + recorde;
		int centroXpontos = (int) ((this.larguraTela / 2) - (this.pontuacaoFont
				.getStringWidth(textoPontos) / 2));
		int centroYpontos = (int) ((this.alturaTela / 2) - (this.pontuacaoFont
				.getLineHeight() / 2));
		textoPontuacao = new ChangeableText(centroXpontos, centroYpontos,
				this.pontuacaoFont, textoPontos);
		cena.attachChild(textoPontuacao);

		return cena;
	}

}
