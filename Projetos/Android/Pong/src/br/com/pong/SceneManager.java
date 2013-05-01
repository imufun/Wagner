package br.com.pong;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;

public class SceneManager {
	
	private static SceneManager INSTANCE;

	private SceneManager() {}
	
	public static SceneManager getInstance() {
		if(INSTANCE==null) {
			INSTANCE = new SceneManager();
		}
		
		return INSTANCE;
	}	
	
	public Scene loadScene(Engine engine) {
		return new GameScene(engine);
	}

}
