package com.android.minicurso;

import java.io.IOException;

import org.anddev.andengine.audio.music.Music;
import org.anddev.andengine.audio.music.MusicFactory;
import org.anddev.andengine.audio.sound.Sound;
import org.anddev.andengine.audio.sound.SoundFactory;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.BoundCamera;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.primitive.Line;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.Debug;
import org.anddev.andengine.util.HorizontalAlign;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

public class MiniCursoAndEngine extends BaseGameActivity implements IOnSceneTouchListener{

	private final int CAMERA_WIDTH = 720;
	private final int CAMERA_HEIGHT = 480;

	private Texture texturaJogadores;
	private TextureRegion texturaJogadorO;
	private TextureRegion texturaJogadorX;

	private Texture texturaBotoes;
	private TextureRegion texturaBotaoNovoJogo;
	private Sprite botao;

	private Font fonte;
	private Texture texturaFonte;
	private Text textoJogador1;
	private Text textoJogador2;

	private int pontosJogador1=0;
	private int pontosJogador2=0;

	private boolean podeJogar = false;

	private Camera camera;
	private Scene cena;
	private Sound efeito;
	private Music musica;



	public Engine onLoadEngine() {
		this.camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.camera).setNeedsSound(true).setNeedsMusic(true));
	}


	public void onLoadResources() {
		TextureRegionFactory.setAssetBasePath("gfx/");
		SoundFactory.setAssetBasePath("mfx/");

		this.texturaJogadores = new Texture(256, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.texturaJogadorO = TextureRegionFactory.createFromAsset(this.texturaJogadores, this, "bola2.png", 0, 0);
		this.texturaJogadorX = TextureRegionFactory.createFromAsset(this.texturaJogadores, this, "xis2.png", 128, 0);

		this.texturaFonte = new Texture(256, 128, TextureOptions.NEAREST);  
		this.fonte = new Font(texturaFonte, Typeface.create(Typeface.DEFAULT, 0), 32, true, Color.BLACK);  
		this.mEngine.getTextureManager().loadTexture(texturaFonte);  
		this.mEngine.getFontManager().loadFont(this.fonte);

		this.texturaBotoes= new Texture(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.texturaBotaoNovoJogo= TextureRegionFactory.createFromAsset(this.texturaBotoes, this, "menu_ok.png", 0, 0);

		this.carregarEfeito();
		//this.carregarMusica();

		this.mEngine.getTextureManager().loadTextures(texturaJogadores,texturaFonte);

	}


	public Scene onLoadScene() {
		cena = new Scene();
		cena.setBackground(new ColorBackground(225,225,225));
		this.cena.setOnSceneTouchListener(this);

		this.novoJogo();

		this.montarTabuleiro();





		//Sprite bola = new Sprite(66,96, this.texturaJogadorO);
		//Sprite xis = new Sprite(320,96, this.texturaJogadorX);
		//this.cena.attachChild(bola);
		//this.cena.attachChild(xis);

		return cena;
	}


	public void onLoadComplete() {

	}

	private void montarTabuleiro(){

		Line line1 = new Line((CAMERA_WIDTH/2)/2+40, 85, (CAMERA_WIDTH/2)/2+40, CAMERA_HEIGHT-16, 6);
		Line line2 = new Line(CAMERA_WIDTH-(CAMERA_WIDTH/2)/2-40, 85, CAMERA_WIDTH-(CAMERA_WIDTH/2)/2-40, CAMERA_HEIGHT-16, 6);
		Line line3 = new Line(0, 70, CAMERA_WIDTH, 70, 2);
		Line line4 = new Line(16, CAMERA_HEIGHT/2-30, CAMERA_WIDTH -16 ,CAMERA_HEIGHT/2-30,6);
		Line line5 = new Line(16, CAMERA_HEIGHT-130, CAMERA_WIDTH-16 ,CAMERA_HEIGHT-130,6);

		line1.setColor(100, 69, 0);
		line2.setColor(100, 69, 0);
		line3.setColor(148, 0, 150);
		line4.setColor(100, 69, 0);
		line5.setColor(100, 69, 0);

		this.cena.attachChild(line1);
		this.cena.attachChild(line2);
		this.cena.attachChild(line3);
		this.cena.attachChild(line4);
		this.cena.attachChild(line5);
		//this.informacoesJogadores(pontosJogador1, pontosJogador2);
		textoJogador1 = new Text(10, 15, this.fonte, "Player 1: " +String.valueOf(pontosJogador1), HorizontalAlign.CENTER);  
		textoJogador2 = new Text(550, 15, this.fonte, "Player 2: " +String.valueOf(pontosJogador2), HorizontalAlign.CENTER);

		this.cena.attachChild(textoJogador1);
		this.cena.attachChild(textoJogador2);

		//this.musica.play();

	}


	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		if(podeJogar){
			if(pSceneTouchEvent.isActionDown()) {
				//this.jogada(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
				//	this.verificaGanhador();
				Sprite bola = new Sprite(pSceneTouchEvent.getX(), pSceneTouchEvent.getY(), this.texturaJogadorO);
				this.cena.attachChild(bola);
				this.efeito.play();
				return true;
			}
		}
		return false;
	}

	private void novoJogo(){

		botao = new Sprite(250,15,texturaBotaoNovoJogo){

			@Override 
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				switch(pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:  

					this.setScale(1f);
					podeJogar = true;
					break;
				case TouchEvent.ACTION_UP:

					this.setScale(0.8f);
				default:
					this.setScale(0.8f);
					break;
				}
				return true;
			}
		};

		this.cena.attachChild(botao);
		this.cena.registerTouchArea(botao);

	}

	private void carregarEfeito(){
		SoundFactory.setAssetBasePath("mfx/");
		try {
			this.efeito = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "efeito.mp3");
		} catch (final IOException e) {
			Debug.e(e);
		}

	}

	private void carregarMusica(){
		MusicFactory.setAssetBasePath("mfx/");
		try {
			this.musica = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "musica.mp3");
			this.musica.setLooping(true);
		} catch (final IOException e) {
			Debug.e(e);
		}
	}


}