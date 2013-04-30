package br.tonykley.marblesbrazil.jogo;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.AutoParallaxBackground;
import org.anddev.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import br.tonykley.marblesbrazil.db.RecordeSQLiteOpenHelper;
import br.tonykley.marblesbrazil.db.RecordeService;
import br.tonykley.marblesbrazil.inicial.GameOverActivity;

public class FulgaOceano extends BaseGameActivity {
	
	//Engine
	private Scene scene;
	private Camera camera;
	public int larguraTela;
	public int alturaTela;
	
	//Imagens
	private BitmapTextureAtlas bitmapTextureAtlas;
	
	private TiledTextureRegion mergulhadorRegiao;
	private TiledTextureRegion ouricoRegiao;
	private TiledTextureRegion tubaraoRegiao;
	
	private TextureRegion controleBaseRegiao;
	private TextureRegion controleKnobRegiao;
	
	private Mergulhador mergulhador;
	private Perigos ourico;
	private Perigos tubarao;
	
	//plano de fundo
	private BitmapTextureAtlas mAutoParallaxBackgroundTexture;
	
	private TextureRegion mParallaxLayerBack;
	private TextureRegion mParallaxLayerMid;
	private TextureRegion mParallaxLayerFront;
	
	//PONTUACAO
	private Long pontos = 0L;
	private Long recorde;
	private BitmapTextureAtlas pontuacaoTextureAtlas;
	private Font pontuacaoFont;
	private ChangeableText textoPontuacao;
	
	//BANCO
	private RecordeService recordService;

	public void onLoadComplete() {
		// TODO Auto-generated method stub
	}

	public Engine onLoadEngine() {
		RecordeSQLiteOpenHelper recordSqliteOpenHelper = new RecordeSQLiteOpenHelper(
				this);
		recordService = new RecordeService(recordSqliteOpenHelper);
		recordService.open();
		//PEGA TAMANHO DA TELA DO APARELHO
		Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		this.larguraTela = display.getWidth();
		this.alturaTela = display.getHeight();
		// define a camera
		this.camera = new Camera(0, 0, this.larguraTela, this.alturaTela);
		// Opçoes da Engine
		final EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE, new FillResolutionPolicy(
						), this.camera);
		final Engine engine = new Engine(engineOptions);
		return engine;
	}

	public void onLoadResources() {
		// define um caminho padrao dentro da pasta /assets para as imagens
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		// Inicializa o BitmapTextureAtlas, pense nela como uma gride que preencheremos com imagens
		this.bitmapTextureAtlas = new BitmapTextureAtlas(512, 512, TextureOptions.BILINEAR);
		// Carrega a imagem passando o BitmapTextureAtlas.
		this.mergulhadorRegiao = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.bitmapTextureAtlas, this, "Mergulhador.png", 0, 0, 4, 2);
		this.controleBaseRegiao = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.bitmapTextureAtlas, this, "controleBase.png",384,0);
		this.controleKnobRegiao = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.bitmapTextureAtlas, this, "controleknob.png",384,128);
		this.tubaraoRegiao = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.bitmapTextureAtlas, this, "Tubarao.png", 0, 310, 1, 1);
		this.ouricoRegiao = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.bitmapTextureAtlas, this, "Ourico.png", 100, 312,1,1);
		//Carrega as imagens para a engine
		this.mEngine.getTextureManager().loadTexture(this.bitmapTextureAtlas);
		
		//Planos de fundo
		this.mAutoParallaxBackgroundTexture = new BitmapTextureAtlas(1024, 1024);
		this.mParallaxLayerFront = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mAutoParallaxBackgroundTexture, this, "fundoAlgas.png", 0, 0);
		this.mParallaxLayerBack = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mAutoParallaxBackgroundTexture, this, "Background.png", 0, 188);
		this.mParallaxLayerMid = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mAutoParallaxBackgroundTexture, this, "fundoBolhas.png", 0, 669);
		this.mEngine.getTextureManager().loadTexture(mAutoParallaxBackgroundTexture);
		
		//Pontuação
		pontuacaoTextureAtlas = new BitmapTextureAtlas(256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.pontuacaoFont = new Font(pontuacaoTextureAtlas, Typeface.create(
				Typeface.DEFAULT, Typeface.BOLD), 20, true, Color.WHITE);
		this.mEngine.getTextureManager().loadTexture(pontuacaoTextureAtlas);
		this.mEngine.getFontManager().loadFont(pontuacaoFont);
	}

	public Scene onLoadScene() {
		// cria a cena do jogo
		scene = new Scene();
		
		//Plano de fundo
		final AutoParallaxBackground autoParallaxBackground = new AutoParallaxBackground(0, 0, 0, 5);
		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(0.0f, 
				new Sprite(0, alturaTela - this.mParallaxLayerBack.getHeight(), this.mParallaxLayerBack)));		
		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(-5.0f, 
				new Sprite(0, 80, this.mParallaxLayerMid)));
		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(-10.0f, 
				new Sprite(0, alturaTela - this.mParallaxLayerFront.getHeight(), this.mParallaxLayerFront)));
		scene.setBackground(autoParallaxBackground);
		
		//Mergulhador
		int centroXmergulhador = (this.larguraTela/2)-(this.mergulhadorRegiao.getWidth()/2);
		int centroYmergulhador = (this.alturaTela/2)-(this.mergulhadorRegiao.getHeight()/2);
		this.mergulhador = new Mergulhador(centroXmergulhador, centroYmergulhador, 
				this.mergulhadorRegiao,this.larguraTela, this.alturaTela);
		this.mergulhador.setScale(0.5f);
		scene.attachChild(mergulhador);
		
		//Controle
		AnalogOnScreenControl controle = new AnalogOnScreenControl(
				0,this.alturaTela-this.controleBaseRegiao.getHeight(),
				this.camera,this.controleBaseRegiao,this.controleKnobRegiao,
				0.1f, 200,new ControleListener(this.mergulhador)
				);
		controle.getControlBase().setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		controle.getControlBase().setAlpha(0.5f);
		controle.getControlBase().setScaleCenter(0, 128);
		controle.getControlBase().setScale(1f);
		controle.getControlKnob().setScale(1f);
		controle.refreshControlKnobPosition();
		scene.setChildScene(controle);
		
		//Perigos
		for (int i = 1; i < 4; i++) {
			this.ourico = new Perigos(this.ouricoRegiao, 50f*i, this.larguraTela, this.alturaTela, this.mergulhador,this);
			scene.attachChild(this.ourico);
		}
		for (int j = 1; j < 2; j++) {
			this.tubarao = new Perigos(this.tubaraoRegiao, 50f*j, this.larguraTela, this.alturaTela, this.mergulhador, this);
			this.tubarao.setScale(0.7f);
			scene.attachChild(this.tubarao);
		}
		
		//Pontos
		recorde = recordService.getRecorde();
		textoPontuacao = new ChangeableText(10, 10, this.pontuacaoFont,"PONTUAÇÃO: " + pontos + " RECORDE: "+ recorde + " ");
		scene.attachChild(textoPontuacao);
		
		return scene;
	}
	
	public void atualizaPontuacao(){
		this.pontos++;
		if(this.pontos > this.recorde){
			this.recorde = this.pontos;
			this.recordService.novoRecorde(recorde);
		}
		this.textoPontuacao.setText("PONTUAÇÃO: " + pontos + " RECORDE: "+ recorde);
		  if (this.pontos % 20 == 0) {
	            this.ourico = new Perigos(this.ouricoRegiao,50f*1,this.larguraTela, this.alturaTela, this.mergulhador, this);
	            scene.attachChild(this.ourico);
	        }
		  if (this.pontos % 50 == 0) {
	            this.tubarao = new Perigos(this.tubaraoRegiao,50f*1,this.larguraTela, this.alturaTela, this.mergulhador, this);
	            this.tubarao.setScale(0.7f);
	            scene.attachChild(this.tubarao);
	        }
	}
	
	
	public void gameOver(){
		Bundle bundle = new Bundle();
        bundle.putLong("pontos", pontos);
        bundle.putLong("recorde", recorde);
        Intent intent = new Intent(this,GameOverActivity.class);
        intent.putExtras(bundle);
        this.startActivity(intent);
        this.finish();
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("Continuar");
		menu.add(menu.NONE, 2, 2, "Sair");
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 2:
			this.finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}