package ve.co.bsc.sigai.domain;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * Representa los datos básicos de una unidad de auditoría interna
 * 
 * @author carlosalvite
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity(finders = { "findPersonalizacionsByRifEquals" })
public class Personalizacion implements Cloneable, Serializable {

	@NotNull
	private String nombreInstitucion;

	@NotNull
	private String correoSunai;

	@Type(type = "binary")
	@Column(length = 16777216)
	@Basic(fetch = FetchType.LAZY)
	private byte[] archivoImagen;

	private String nombreTutelar;

	@ManyToOne(targetEntity = TipoOrganismo.class)
	@JoinColumn
	private TipoOrganismo tipoOrganismo;

	private String web;

	private String direccion;

	private String Municipio;
	
	private String ciudad;

	private String estado;

	private String nombreMaximaAutoridad;

	private String cargoMaximaAutoridad;

	private String telefonoMaster;

	private String telefonoFax;

	private String nombreCompletoAuditorInterno;

	@ManyToOne(targetEntity = CondicionAuditorInterno.class)
	@JoinColumn
	private CondicionAuditorInterno condicionAuditorInterno;

	private String cedula;

	private String profesion;

	private String tiempoEnElCargo;

	private String designadoSegun;

	private String experienciaAnos;

	private String experienciaMeses;

	private String telefonoOficina;

	private String telefonoMovil;

	private String correouai;

	private String correoInstitucional;

	private String correoPersonal;

	private Integer cantAuditorInterno;

	private Integer cantDirectivos;

	private Integer cantCoordinadores;

	private Integer cantProfesionales;

	private Integer cantTecnicos;

	private Integer cantNoProfesionales;

	private String Rif;

	public static String getCorreoInstitucionalSimple() {
		return Personalizacion.findPersonalizacion(new Long(1))
				.getCorreoInstitucional();
	}

	public static String getCorreouaiSimple() {
		return Personalizacion.findPersonalizacion(new Long(1)).getCorreouai();
	}

	public static String getCorreoSunaiSimple() {
		return Personalizacion.findPersonalizacion(new Long(1))
				.getCorreoSunai();
	}

	public String toStringExtendido() {
		StringBuilder sb = new StringBuilder();
		sb.append("NombreInstitucion: ").append(getNombreInstitucion());
		return sb.toString();
	}

	public String toStringLimitado() {
		StringBuilder sb = new StringBuilder();
		sb.append("NombreInstitucion: ").append(getNombreInstitucion());
		return sb.toString();
	}
}
