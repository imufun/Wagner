package br.tonykley.marblesbrazil.jogo;

import br.tonykley.marblesbrazil.R;

import org.anddev.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.anddev.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.anddev.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.anddev.andengine.entity.modifier.ScaleModifier;
import org.anddev.andengine.entity.modifier.SequenceEntityModifier;

public class ControleListener implements IAnalogOnScreenControlListener {
	private Mergulhador mergulhador;
	public ControleListener(Mergulhador pmergulhador) {
		this.mergulhador = pmergulhador;
	}

	public void onControlChange(BaseOnScreenControl arg0, float pValueX, float pValueY) {
		mergulhador.mover(pValueX, pValueY);
	}

	public void onControlClick(AnalogOnScreenControl arg0) {

	}

}
