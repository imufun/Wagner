package br.tonykley.marblesbrazil.jogo;

import br.tonykley.marblesbrazil.R;

import java.util.Random;

import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public class Perigos extends AnimatedSprite {
	
	private PhysicsHandler physicsHandler;
	private float velocidadeX;
	private float velocidadeY;
	private Random randon;
	private int larguraTela;
	private int alturaTela;
	private Mergulhador mergulhador;
	private FulgaOceano activity;

	public Perigos(TiledTextureRegion pTiledTextureRegion, float pVelocidade, 
			int plargura, int paltura, Mergulhador pmergulhador, FulgaOceano pactivity) {
		super(-1, -1, pTiledTextureRegion);
		this.physicsHandler = new PhysicsHandler(this);
		this.registerUpdateHandler(this.physicsHandler);
		this.randon = new Random();
		this.velocidadeX = getVelocidadeX();
		this.velocidadeY = getVelocidadeY();
		this.larguraTela = plargura;
		this.alturaTela = paltura;
		this.mergulhador = pmergulhador;
		this.activity = pactivity;
		
		this.physicsHandler.setVelocity(-this.velocidadeX, this.velocidadeY);
		this.setPosition(this.larguraTela, this.getPosicaoInicial());
		Float escala = larguraTela / 1000f;
		if(escala <0){
			escala = 0f;
		}
		this.setScale(escala);
	}
	
	private float getVelocidadeY() {
		return (this.randon.nextBoolean() ? this.randon.nextFloat() * 50 : this.randon.nextFloat() * (-50));
	}
	
	private float getVelocidadeX(){
        return (this.randon.nextInt(2)+1) * 50f;
	}
	
	public float getPosicaoInicial(){
		return this.randon.nextFloat() * this.alturaTela;
	}
	
	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		if (this.mY < 0) {
			this.physicsHandler.setVelocity(-this.velocidadeX,-this.velocidadeY);
		} else if (this.mY + this.getHeight() > this.alturaTela) {
			this.physicsHandler.setVelocity(-this.velocidadeX,-this.velocidadeY);
		}
		if (this.mX < 0) {
			this.setPosition(this.larguraTela, this.getPosicaoInicial());
			this.velocidadeX =  getVelocidadeX();
	        this.velocidadeY = getVelocidadeY();
			this.physicsHandler.setVelocity(-this.velocidadeX, this.velocidadeY);
			this.activity.atualizaPontuacao();
		}
		//colidiu game over - ainda não tem as vidas ><
		if(this.collidesWith(this.mergulhador)){
			this.activity.gameOver();
		}
		super.onManagedUpdate(pSecondsElapsed);
	}

}
