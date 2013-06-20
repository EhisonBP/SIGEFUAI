package ve.co.bsc.sigai.form;

import ve.co.bsc.sigai.domain.EstructuraOrganizativa;
import ve.co.bsc.sigai.domain.Personalizacion;
import ve.co.bsc.sigai.domain.Unidad;

public class EstructuraUnidadPerzonalizacionForm {

	private Unidad unidad;
	private Personalizacion personalizacion;
	private EstructuraOrganizativa estructuraOrganizativa;
	private int effectTypes2;;

	public EstructuraUnidadPerzonalizacionForm() {
		estructuraOrganizativa = new EstructuraOrganizativa();
		unidad = new Unidad();
		personalizacion = new Personalizacion();
	}

	public EstructuraUnidadPerzonalizacionForm(long id) {
		unidad = new Unidad();
		personalizacion = new Personalizacion();
		personalizacion = Personalizacion.findPersonalizacion(id);
		estructuraOrganizativa = new EstructuraOrganizativa();
	}

	public Unidad getUnidad() {
		return unidad;
	}

	public void setUnidad(Unidad unidad) {
		this.unidad = unidad;
	}

	public Personalizacion getPersonalizacion() {
		return personalizacion;
	}

	public void setPersonalizacion(Personalizacion personalizacion) {
		this.personalizacion = personalizacion;
	}

	public EstructuraOrganizativa getEstructuraOrganizativa() {
		return estructuraOrganizativa;
	}

	public void setEstructuraOrganizativa(
			EstructuraOrganizativa estructuraOrganizativa) {
		this.estructuraOrganizativa = estructuraOrganizativa;
	}

	public void setEffectTypes2(int effectTypes2) {
		this.effectTypes2 = effectTypes2;
	}

	public int getEffectTypes2() {
		return effectTypes2;
	}
}
