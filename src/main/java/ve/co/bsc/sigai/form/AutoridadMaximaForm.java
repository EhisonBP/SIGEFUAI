package ve.co.bsc.sigai.form;

import ve.co.bsc.sigai.domain.AutoridadMaxima;
import ve.co.bsc.sigai.domain.DatosOrganismoEnte;
import ve.co.bsc.sigai.domain.OrganismoEnte;

public class AutoridadMaximaForm {
	private int effectTypes;
	private AutoridadMaxima autoridadMaxima1;

	public AutoridadMaximaForm() {
		autoridadMaxima1 = new AutoridadMaxima();
	}

	public int getEffectTypes() {
		return effectTypes;
	}

	public void setEffectTypes(int effectTypes) {
		this.effectTypes = effectTypes;
	}

	public AutoridadMaxima getAutoridadMaxima1() {
		return autoridadMaxima1;
	}

	public void setAutoridadMaxima1(AutoridadMaxima autoridadMaxima1) {
		this.autoridadMaxima1 = autoridadMaxima1;
	}

	
	
	
}
