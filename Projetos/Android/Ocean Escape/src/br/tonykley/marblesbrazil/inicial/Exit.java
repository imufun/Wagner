package br.tonykley.marblesbrazil.inicial;

import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

public class Exit extends Sprite{
	 private BaseGameActivity activity;
	    
	    public Exit(float pX, float pY, TextureRegion pTextureRegion, BaseGameActivity pactivity) {
	        super(pX, pY, pTextureRegion);
	        this.activity = pactivity;
	    }
	    
	    @Override
	    public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
	            float pTouchAreaLocalX, float pTouchAreaLocalY) {
	        
	        activity.finish();
	        return super
	                .onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
	    }

	}

