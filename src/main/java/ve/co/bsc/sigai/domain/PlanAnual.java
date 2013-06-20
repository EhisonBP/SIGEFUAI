package ve.co.bsc.sigai.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.apache.log4j.Logger;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import ve.co.bsc.sigai.service.RestAPIServiceClient;

/**
 * Representa la planificación anual en la que se ejecutarán un conjunto de
 * actuaciones. Un plan anual estará comprendido dentro de una fecha de incio y
 * una de fin
 * 
 * @author adernersissian
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity(finders = { "findPlanAnualsByRif" })
public class PlanAnual implements Cloneable, ObjetoComentable,
		ObjetoNombreClase, Serializable {

	private static Logger logger = Logger.getLogger(PlanAnual.class);

	@ManyToOne(targetEntity = EstadoPlan.class)
	@JoinColumn
	private EstadoPlan estadoPlan;

	@Lob
	@Size(min = 0, max = 10000)
	@Column(length = 10000)
	private String descripcion;

	private Integer anoFiscal;

	@Transient
	private String[] accionesPermitidas;

	@ManyToOne(targetEntity = InstruirPlan.class)
	@JoinColumn
	private InstruirPlan anoPlan;

	@ManyToOne(targetEntity = OrganismoEnte.class)
	@JoinColumn
	private OrganismoEnte rif;

	// @javax.validation.constraints.NotNull
	@ManyToOne(targetEntity = ve.co.bsc.sigai.domain.Auditor.class)
	@JoinColumn
	private ve.co.bsc.sigai.domain.Auditor responsable;

	// @javax.validation.constraints.NotNull
	@ManyToOne(targetEntity = ve.co.bsc.sigai.domain.Auditor.class)
	@JoinColumn
	private ve.co.bsc.sigai.domain.Auditor responsable2;

	public void setAccionesPermitidasFromString(
			String commaSeparadtedStringWithActionsList) {
		logger.debug("Setting acciones permitidas desde string con: "
				+ commaSeparadtedStringWithActionsList);
		this.setAccionesPermitidas(commaSeparadtedStringWithActionsList
				.split(","));
	}

	public void setEstadoSimple(String nombreEstado) {
		RestAPIServiceClient.instance().updatePlanAnualEstado(this.getId(),
				nombreEstado);
	}

	@Override
	public Object clone() {
		PlanAnual obj = new PlanAnual();
		obj.setEstadoPlan(this.estadoPlan);
		obj.setDescripcion(this.descripcion);
		obj.setAnoFiscal(this.anoFiscal);
		obj.setAnoPlan(this.anoPlan);
		return obj;
	}

	public String toStringExtendido() {
		StringBuilder sb = new StringBuilder();
		sb.append("EstadoPlan: ")
				.append(getEstadoPlan() == null ? "n/a" : getEstadoPlan()
						.toStringLimitado()).append(", ");
		sb.append("Descripcion: ").append(getDescripcion()).append(", ");
		sb.append("AnoFiscal: ").append(getAnoFiscal());
		return sb.toString();
	}

	public String toStringLimitado() {
		StringBuilder sb = new StringBuilder();
		sb.append(getDescripcion()).append(", ");
		return sb.toString();
	}

	public String getUrl() {
		return "/plananual/" + this.getId();
	}

	public String getDescripcionSimple() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append(getRif().getNombre());
		sb.append("] Año fiscal ");
		sb.append(getAnoFiscal());
		return sb.toString();
	}

	@Override
	public String getNombreClase() {
		return "Plan Anual";
	}

	public String coordinador() {
		logger.debug("Buscando login del Usuario para el JPBM");
		String login = PlanAnual.findPlanAnual(this.getId()).getResponsable()
				.getLogin();
		logger.debug("el login Es " + login);
		return login;
	}

	public String auditor() {
		logger.debug("Buscando login del Usuario para el JPBM");
		String login = PlanAnual.findPlanAnual(this.getId()).getResponsable2()
				.getLogin();
		logger.debug("el login Es " + login);
		return login;
	}

	public String getEstadoSimple() {
		logger.debug("Buscando estado simple de plan");
		String out = PlanAnual.findPlanAnual(this.getId()).getEstadoPlan()
				.getNombre();
		logger.debug("Estado simple de plan es: " + out);
		return out;
	}
}
