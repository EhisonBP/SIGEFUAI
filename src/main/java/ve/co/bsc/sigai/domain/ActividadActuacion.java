package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import java.util.Comparator;
import javax.persistence.Entity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Transient;
import org.apache.log4j.Logger;

/**
 * Representa una actividad (actividad general o prueba) parte de una actuacion
 * Esta clase representa un papel de trabajo de una auditoria.
 * 
 * @author jdeoliveira
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity(finders = { "findActividadActuacionsByCodigoActuacion",
		"findActividadActuacionsByActividadActuacion" })
public class ActividadActuacion implements ObjetoComentable, Serializable {

	private static final Logger logger = Logger
			.getLogger(ActividadActuacion.class);

	@ManyToOne(targetEntity = Actuacion.class)
	@JoinColumn
	private Actuacion codigoActuacion;

	@NotNull
	private String codigo;

	@NotNull
	@Lob
	@Size(min = 0, max = 10000)
	@Column(length = 10000)
	private String descripcion;

	@ManyToOne(targetEntity = EstadoActividadActuacion.class)
	@JoinColumn
	private EstadoActividadActuacion estadoActividadActuacion;

	@ManyToMany(cascade = CascadeType.PERSIST)
	private Set<Documento> documento = new HashSet<Documento>();

	@ManyToOne(targetEntity = ve.co.bsc.sigai.domain.ActividadActuacion.class)
	@JoinColumn
	private ve.co.bsc.sigai.domain.ActividadActuacion actividadActuacion;

	@Transient
	private String[] accionesPermitidas;
	
	@ManyToOne(targetEntity = Auditor.class)
	@JoinColumn
	private Auditor creador;

	public void setAccionesPermitidasFromString(
			String commaSeparadtedStringWithActionsList) {
		logger.debug("Setting acciones permitidas desde string con: "
				+ commaSeparadtedStringWithActionsList);

		this.setAccionesPermitidas(commaSeparadtedStringWithActionsList
				.split(","));

	}

	public String getCodigoCompleto() {
		if (this.actividadActuacion != null) {
			if ((this instanceof ActividadGeneral)
					&& ((this.actividadActuacion instanceof ActividadGeneral) == false)
					&& (this.objetivoAMitigar != null)) {
				return this.actividadActuacion.getCodigoCompleto() + "."
						+ this.objetivoAMitigar.getReferencia() + "."
						+ this.getCodigo();
			} else {
				return this.actividadActuacion.getCodigoCompleto() + "."
						+ this.getCodigo();
			}
		} else {
			if (this.objetivoAMitigar != null) {
				return this.getCodigoActuacion().getCodigoCompleto() + "-"
						+ this.objetivoAMitigar.getReferencia() + "."
						+ this.getCodigo();
			} else {
				// return this.getCodigoActuacion().getCodigoCompleto() + "-" +
				// this.getCodigo();
				return this.getCodigo();
			}
		}
	}

	public String getCodigoPadres() {
		if (this.actividadActuacion != null) {
			return this.actividadActuacion.getCodigoCompleto() + ".";
		} else {
			return this.getCodigoActuacion().getCodigoCompleto() + "-";
		}
	}

	public static final Comparator compararActividades = new Comparator() {

		@Override
		public int compare(Object arg0, Object arg1) {
			ActividadActuacion actividad1 = (ActividadActuacion) arg0;
			ActividadActuacion actividad2 = (ActividadActuacion) arg1;
			return actividad1.getCodigoCompleto().compareTo(
					actividad2.getCodigoCompleto());
		}
	};

	public static final Comparator compararActividadesPorId = new Comparator() {

		@Override
		public int compare(Object arg0, Object arg1) {
			ActividadActuacion actividad1 = (ActividadActuacion) arg0;
			ActividadActuacion actividad2 = (ActividadActuacion) arg1;
			return actividad1.getId().compareTo(actividad2.getId());
		}
	};

	@ManyToMany(cascade = CascadeType.ALL)
	private Set<Auditor> responsables = new HashSet<Auditor>();

	@ManyToOne(targetEntity = ObjetivoEspecifico.class)
	@JoinColumn
	private ObjetivoEspecifico objetivoAMitigar;

	public int getNivelIndentacion() {
		if (this.actividadActuacion != null) {
			return this.actividadActuacion.getNivelIndentacion() + 1;
		} else {
			return 0;
		}
	}

	public String toStringLimitado() {
		StringBuilder sb = new StringBuilder();
		sb.append(getCodigoCompleto()).append(" ");
		return sb.toString();
	}
}
