package br.com.pong;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;

public class GameScene extends Scene {

	public GameScene(Engine engine) {
		createControllers(engine);
	}

	private void createControllers(Engine engine) {
		/* Create the button and add it to the scene. */
		final Sprite jogador = new Sprite(100, 100, ResourceManager.getInstance().getJogadorTextureRegion(), engine.getVertexBufferObjectManager());
		attachChild(jogador);
		
		/* Create the button and add it to the scene. */
		final Sprite bola = new Sprite(200, 200, ResourceManager.getInstance().getBolaTextureRegion(), engine.getVertexBufferObjectManager());
		attachChild(bola);
	}

}
