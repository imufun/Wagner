package br.com.pong;

import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.debug.Debug;

public class ResourceManager {
	
	private static ResourceManager INSTANCE;
	
	private BuildableBitmapTextureAtlas mBitmapTextureAtlas;
	
	private ITextureRegion bolaTextureRegion;
	private ITextureRegion jogadorTextureRegion;
	
	private ResourceManager() {}
	
	public static ResourceManager getInstance() {
		if(INSTANCE==null) {
			INSTANCE = new ResourceManager();
		}
		
		return INSTANCE;
	}
	
	public synchronized void loadTextures(BaseGameActivity activity) {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		this.mBitmapTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 512,512);
		this.bolaTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, activity, "bola.png");
		this.jogadorTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, activity, "jogador.png");
		
		try {
			this.mBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
			this.mBitmapTextureAtlas.load();
		} catch (TextureAtlasBuilderException e) {
			Debug.e(e);
		}

	}

	public BuildableBitmapTextureAtlas getmBitmapTextureAtlas() {
		return mBitmapTextureAtlas;
	}

	public ITextureRegion getBolaTextureRegion() {
		return bolaTextureRegion;
	}

	public ITextureRegion getJogadorTextureRegion() {
		return jogadorTextureRegion;
	}

}
