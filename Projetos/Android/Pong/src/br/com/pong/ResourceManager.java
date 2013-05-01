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
	private ITextureRegion jogador1TextureRegion;
	private ITextureRegion jogador2TextureRegion;
	
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
		this.jogador1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, activity, "jogador.png");
		this.jogador2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, activity, "jogador.png");
		
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

	public void setmBitmapTextureAtlas(
			BuildableBitmapTextureAtlas mBitmapTextureAtlas) {
		this.mBitmapTextureAtlas = mBitmapTextureAtlas;
	}

	public ITextureRegion getBolaTextureRegion() {
		return bolaTextureRegion;
	}

	public void setBolaTextureRegion(ITextureRegion bolaTextureRegion) {
		this.bolaTextureRegion = bolaTextureRegion;
	}

	public ITextureRegion getJogador1TextureRegion() {
		return jogador1TextureRegion;
	}

	public void setJogador1TextureRegion(ITextureRegion jogador1TextureRegion) {
		this.jogador1TextureRegion = jogador1TextureRegion;
	}

	public ITextureRegion getJogador2TextureRegion() {
		return jogador2TextureRegion;
	}

	public void setJogador2TextureRegion(ITextureRegion jogador2TextureRegion) {
		this.jogador2TextureRegion = jogador2TextureRegion;
	}

}
