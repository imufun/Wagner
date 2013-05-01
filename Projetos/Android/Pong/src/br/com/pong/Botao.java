package br.com.pong;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.color.Color;

public class Botao extends Rectangle {

	private Color originalColor;
	private Engine engine; 
	private TimerHandler spriteTimerHandler;
	public Botao(float pX, float pY, Color color1,
			Engine engine) {
		super(pX, pY, 200, 100, engine.getVertexBufferObjectManager());
		
		this.originalColor = color1;
		this.engine = engine;
		
		setColor(originalColor);
		criaAcaoPiscar();
	}

	@Override
	public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y) {
		if (touchEvent.isActionUp()) {
			pisca();
		}
		return true;
	}
	
	private void criaAcaoPiscar() {
		float mEffectSpawnDelay = 0.25f;
		
		spriteTimerHandler = new TimerHandler(mEffectSpawnDelay, true,
				new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				Botao.this.setColor(originalColor);
			}
			
			
		});
		
		engine.registerUpdateHandler(spriteTimerHandler);
		
	}
	
	public void pisca() {
		this.setColor(Color.WHITE);
		spriteTimerHandler.reset();
	}

}
