package br.tonykley.marblesbrazil.jogo;

import br.tonykley.marblesbrazil.R;

import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public class Mergulhador extends AnimatedSprite {
	private final PhysicsHandler physicsHandler;
	private int velocidade = 100;
	private int larguraTela;
	private int alturaTela;
	private int aceleracao = 200;
	private boolean paraTras = true;
	
	public Mergulhador(float pX, float pY,
			TiledTextureRegion pTiledTextureRegion, int plarguraTela, int palturaTela) {
		super(pX, pY, pTiledTextureRegion);
		this.animacaoPadrao();
		this.physicsHandler = new PhysicsHandler(this);
		this.registerUpdateHandler(this.physicsHandler);
		this.larguraTela = plarguraTela;
		this.alturaTela = palturaTela;
		Float escala = plarguraTela / 1000f;
		if(escala <0){
			escala = 0f;
		}
		this.setScale(escala);
	}
	public PhysicsHandler getPhysicsHandler() {
		return physicsHandler;
	}
	
	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		if (this.mX < 0) {
			this.physicsHandler.setVelocityX(this.velocidade);
		} else if (this.mX + this.getHeight() > this.larguraTela) {
			this.physicsHandler.setVelocityX(-this.velocidade);
		}
		if (this.mY < 0) {
			this.physicsHandler.setVelocityY(this.velocidade);
		} else if (this.mY + this.getHeight() > this.alturaTela) {
			this.physicsHandler.setVelocityY(-this.velocidade);
		}
		super.onManagedUpdate(pSecondsElapsed);
	}
	
	public void mover(float x,float y){
		if(x < 0){
			this.animacaoVoltar();
		}else{
			this.animacaoPadrao();
		}
		this.physicsHandler.setVelocity(x * this.aceleracao, y * this.aceleracao);
	}
	
	public void animacaoVoltar(){
		if(!this.paraTras){
			this.animate(new long[]{this.velocidade,this.velocidade,this.velocidade},1,3,true);
			this.paraTras = true;
		}
	}
	public void animacaoPadrao(){
		if(this.paraTras){
			this.animate(new long[]{this.velocidade,this.velocidade,this.velocidade},4,6,true);
			this.paraTras = false;
		}
	}

}
