package ve.co.bsc.sigai.form;


import ve.co.bsc.sigai.domain.DatosOrganismoEnte;
import ve.co.bsc.sigai.domain.OrganismoEnte;
import ve.co.bsc.sigai.domain.Personalizacion;

public class OrganismoEnteForm {
	private int effectTypes;
	private OrganismoEnte organismoEnte;
	private DatosOrganismoEnte datosOrganismoEnte;
	private Personalizacion personalizacion;
	private String usState;
	private String city;
	private String municipio;

	public OrganismoEnteForm() {
		organismoEnte = new OrganismoEnte();
		datosOrganismoEnte = new DatosOrganismoEnte();
		personalizacion = new Personalizacion();
	}

	
	public String getMunicipio() {
		return municipio;
	}


	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}


	public String getUsState() {
		return usState;
	}


	public void setUsState(String usState) {
		this.usState = usState;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public OrganismoEnte getOrganismoEnte() {
		return organismoEnte;
	}

	public void setOrganismoEnte(OrganismoEnte organismoEnte) {
		this.organismoEnte = organismoEnte;
	}

	public DatosOrganismoEnte getDatosOrganismoEnte() {
		return datosOrganismoEnte;
	}

	public void setDatosOrganismoEnte(DatosOrganismoEnte datosOrganismoEnte) {
		this.datosOrganismoEnte = datosOrganismoEnte;
	}

	public int getEffectTypes() {
		return effectTypes;
	}

	public void setEffectTypes(int effectTypes) {
		this.effectTypes = effectTypes;
	}

	public void setPersonalizacion(Personalizacion personalizacion) {
		this.personalizacion = personalizacion;
	}

	public Personalizacion getPersonalizacion() {
		return personalizacion;
	}

}
