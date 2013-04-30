package br.tonykley.marblesbrazil.inicial;

import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import br.tonykley.marblesbrazil.jogo.FulgaOceano;

import android.content.Intent;

public class ImagemInicial extends Sprite {
	
	 private BaseGameActivity activity;
	
	    
	    public ImagemInicial(float pX, float pY, TextureRegion pTextureRegion, BaseGameActivity pactivity) {
	        super(pX, pY, pTextureRegion);
	        this.activity = pactivity;
	    }
	    
	    @Override
	    public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
	            float pTouchAreaLocalX, float pTouchAreaLocalY) {
	        Intent intent = new Intent(activity,FulgaOceano.class);
	        activity.startActivity(intent);
	        activity.finish();
	        return super
	                .onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
	    }

}
