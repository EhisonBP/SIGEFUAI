package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Query;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.log4j.Logger;
import org.hibernate.StaleObjectStateException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import ve.co.bsc.sigai.service.RestAPIServiceClient;

/**
 * Representa una actuacion (una instancia de una auditoria)
 * 
 * @author jdeoliveira
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity(finders = { "findActuacionsByActuacion", "findActuacionsByCodigo",
		"findActuacionsByRif" })
public class Actuacion extends ActividadAuditor implements Cloneable,
		ObjetoComentable, ObjetoNombreClase, Serializable {

	private static Logger logger = Logger.getLogger(Actuacion.class);

	private String codigo;

	@Column(length = 200)
	@Size(min = 2, max = 200)
	private String nombre;

	@Lob
	@Size(min = 0, max = 10000)
	@Column(length = 10000)
	private String alcance;

	@Lob
	@Size(min = 0, max = 10000)
	@Column(length = 10000)
	private String objetivo;

	@Lob
	@Size(min = 0, max = 10000)
	@Column(length = 10000)
	private String enfoque;

	@Lob
	@Size(min = 0, max = 10000)
	@Column(length = 10000)
	private String metodo;

	@ManyToOne(targetEntity = Auditor.class)
	@JoinColumn
	private Auditor responsable;

	@ManyToMany(cascade = CascadeType.ALL)
	private Set<Auditor> responsableAuditor = new HashSet<Auditor>();

	@ManyToOne(targetEntity = EstadoActuacion.class)
	@JoinColumn
	private EstadoActuacion estadoActuacion;

	@ManyToOne(targetEntity = ve.co.bsc.sigai.domain.Actuacion.class)
	@JoinColumn
	private ve.co.bsc.sigai.domain.Actuacion actuacion;

	@ManyToOne(targetEntity = ClaseActuacion.class)
	@JoinColumn
	private ClaseActuacion claseActuacion;

	@Size(min = 0, max = 10000)
	@Column(length = 10000)
	private String[] seccionesVisibles;

	@ManyToMany(cascade = CascadeType.ALL)
	private Set<Unidad> unidades = new HashSet<Unidad>();

	@NotNull
	private Integer mesDesde;

	@NotNull
	private Integer mesHasta;

	private Integer anoFiscal;

	private Integer mesDesdeTentativo;

	private Integer mesHastaTentativo;

	private Integer mesDesdeReal;

	private Integer mesHastaReal;

	@Lob
	@Size(min = 0, max = 10000)
	@Column(length = 10000)
	private String conclusionGeneral;

	@ManyToOne(targetEntity = Biblioteca.class)
	@JoinColumn
	private Biblioteca biblioteca;

	private String comentarios;

	@ManyToOne(targetEntity = UnidadDeMedida.class)
	@JoinColumn
	private UnidadDeMedida unidadDeMedida;

	private Integer correlativo;

	private String codigoPlanificacion;

	private String codigoTotal;

	private Boolean porProcesos;

	@ManyToMany(cascade = CascadeType.ALL)
	private Set<Proceso> procesos = new HashSet<Proceso>();

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "S-")
	private Date fechaRegistro;

	@Column(length = 200)
	@Size(min = 0, max = 200)
	private String nombreProyecto;

	@Transient
	private String[] accionesPermitidas;

	public void setAccionesPermitidasFromString(
			String commaSeparadtedStringWithActionsList) {
		logger.debug("Setting acciones permitidas desde string con: "
				+ commaSeparadtedStringWithActionsList);
		this.setAccionesPermitidas(commaSeparadtedStringWithActionsList
				.split(","));
	}

	public void setSeccionesVisiblesSimple(
			String commaSeparadtedStringWithSectionsList) {
		logger.debug("Setting secciones visibles simples con: "
				+ commaSeparadtedStringWithSectionsList);
		Actuacion x = Actuacion.findActuacion(this.getId());
		this.setSeccionesVisibles(commaSeparadtedStringWithSectionsList
				.split(","));
		x.setSeccionesVisibles(commaSeparadtedStringWithSectionsList.split(","));
		try {
			logger.debug("antes de merge");
			x.merge();
			logger.debug("despues de merge");
		} catch (StaleObjectStateException e) {
			logger.debug("Silently ignorign StaleObjectStateException", e);
		} catch (Exception e) {
			logger.debug("Excepcion durante merge", e);
			throw new RuntimeException(e);
		}
	}

	public String getEstadoSimple() {
<<<<<<< HEAD
		logger.debug("Buscando Estado de la Actuacion pra el JBPM ---------->");
		String estado = "";
		estado = Actuacion.findActuacion(this.getId()).getEstadoActuacion()
				.getNombre();
		logger.debug("El estado de la Actuacion es: " + estado);
		if (!estado.equals("")) {
			logger.debug("Esta Validacion es por si estado tiene algo");
			return estado;
		} else {
			logger.debug("Esta es si el Estado no tiene nada");
			return "Error no se consiguio un estado";
=======
		logger.debug("Buscando estado de la actuacion para la validadcon del JBPM --->");
		String estado = Actuacion.findActuacion(this.getId()).getEstadoActuacion().getNombre();
		logger.debug("Estado de la actuacion es: "+ estado);
		if (!estado.equals("")) {
			logger.debug("Entrado en la validacion de true");
			return estado;
		} else {
			logger.debug("Entrando en la validacion de false")
			return "No se encontro el estado de la Actuacion";
>>>>>>> e863551f75a54345c62a7de9066844030df29ad2
		}
	}

	public void setEstadoSimple(String nombreEstado) {
		RestAPIServiceClient.instance().updateActuacionEstado(this.getId(),
				nombreEstado);
	}

	public void setFechaDesdeReal(Date fecha) {
		RestAPIServiceClient.instance().updateActuacionFechaDesdeReal(
				this.getId(), fecha);
	}

	public void setFechaHastaReal(Date fecha) {
		RestAPIServiceClient.instance().updateActuacionFechaHastaReal(
				this.getId(), fecha);
	}

	public void setEstadoYFechaDesdeRealSimple(String nombreEstado, Date fecha) {
		RestAPIServiceClient.instance().updateActuacionEstado(this.getId(),
				nombreEstado);
		RestAPIServiceClient.instance().updateActuacionFechaDesdeReal(
				this.getId(), fecha);
	}

	public void setEstadoYFechaHastaRealSimple(String nombreEstado, Date fecha) {
		RestAPIServiceClient.instance().updateActuacionEstado(this.getId(),
				nombreEstado);
		RestAPIServiceClient.instance().updateActuacionFechaHastaReal(
				this.getId(), fecha);
	}

	public Actuacion getFreshCopy() {
		this.flush();
		Actuacion.entityManager().clear();
		return Actuacion.findActuacion(this.getId());
	}

	public String getCodigoCompleto() {
		if (this.actuacion != null) {
			return this.actuacion.getCodigoCompleto() + "."
					+ this.getCodigoActuacionTodo();
		} else {
			return this.getCodigoActuacionTodo();
		}
	}

	public String getCodigoPadres() {
		if (this.actuacion != null) {
			return this.actuacion.getCodigoCompleto() + ".";
		} else {
			return "";
		}
	}

	public static final Comparator compararActuaciones = new Comparator() {

		@Override
		public int compare(Object arg0, Object arg1) {
			Actuacion actuacion1 = (Actuacion) arg0;
			Actuacion actuacion2 = (Actuacion) arg1;
			return actuacion1.getCodigoCompleto().compareTo(
					actuacion2.getCodigoCompleto());
		}
	};

	@ManyToOne(targetEntity = ve.co.bsc.sigai.domain.OrganismoEnte.class)
	@JoinColumn
	private ve.co.bsc.sigai.domain.OrganismoEnte rif;

	public String getCodigoActuacionTodo() {
		String clase = ((String) this.getClaseActuacion().getNombre())
				.substring(0, 2);
		int anoFiscal = 0;
		List<ItemPlanificacionActuacion> items = ItemPlanificacionActuacion
				.findItemPlanificacionActuacionsByActuacion(this)
				.getResultList();
		for (int i = 0; i < items.size(); i++) {
			anoFiscal = items.get(i).getPlanAnual().getAnoFiscal();
		}
		String cod = "";
		if ((this.getCodigo() != null) && ((this.getCodigo() != ""))) {
			cod = "-" + this.getCodigo();
		}
		return this.getCorrelativo() + "-" + this.getCodigoPlanificacion()
				+ "-" + clase.toUpperCase() + "-" + anoFiscal + cod;
	}

	// //////////////////////////

	public String getCodigoActuacionSinCod() {
		String clase = ((String) this.getClaseActuacion().getNombre())
				.substring(0, 1);
		int anoFiscal = 0;
		List<ItemPlanificacionActuacion> items = ItemPlanificacionActuacion
				.findItemPlanificacionActuacionsByActuacion(this)
				.getResultList();
		for (int i = 0; i < items.size(); i++) {
			anoFiscal = items.get(i).getPlanAnual().getAnoFiscal();
		}
		return this.getCorrelativo() + "-" + this.getCodigoPlanificacion()
				+ "-" + clase.toUpperCase() + "-" + anoFiscal;
	}

	@Override
	public Object clone() {
		Actuacion obj = new Actuacion();
		obj.setCodigo(this.codigo + ".DUP");
		obj.setNombre(this.nombre);
		obj.setAlcance(this.alcance);
		obj.setObjetivo(this.objetivo);
		obj.setEnfoque(this.enfoque);
		obj.setMetodo(this.metodo);
		obj.setResponsable(this.responsable);
		obj.setEstadoActuacion(this.estadoActuacion);
		obj.setActuacion(this.actuacion);
		obj.setClaseActuacion(this.claseActuacion);
		obj.setMesDesde(this.mesDesde);
		obj.setMesHasta(this.mesHasta);
		obj.setMesDesdeTentativo(this.mesDesdeTentativo);
		obj.setMesHastaTentativo(this.mesHastaTentativo);
		obj.setConclusionGeneral(this.conclusionGeneral);
		obj.setBiblioteca(this.biblioteca);
		obj.setComentarios(this.comentarios);
		obj.setUnidadDeMedida(this.unidadDeMedida);
		obj.setCorrelativo(this.correlativo);
		obj.setCodigoPlanificacion(this.codigoPlanificacion);
		obj.setCodigoTotal(this.codigoTotal);
		obj.setFechaRegistro(this.fechaRegistro);
		obj.setPorProcesos(this.porProcesos);
		Set<Proceso> procNuevos = new HashSet<Proceso>();
		Set<Proceso> p = this.procesos;
		Iterator it = p.iterator();
		while (it.hasNext()) {
			Proceso procesoActual = (Proceso) it.next();
			procNuevos.add(procesoActual);
		}
		obj.setProcesos(procNuevos);
		Set<Unidad> unidNuevas = new HashSet<Unidad>();
		Set<Unidad> u = this.unidades;
		Iterator i = u.iterator();
		while (i.hasNext()) {
			Unidad unidadActual = (Unidad) i.next();
			unidNuevas.add(unidadActual);
		}
		obj.setUnidades(unidNuevas);
		return obj;
	}

	@Transient
	@SuppressWarnings("unchecked")
	public static List<Actuacion> findActuacionsByParams(String _anoFiscal,
			boolean _porAnoFiscal, String _referencia, boolean _porReferencia,
			String _nombre, boolean _porNombre, Long _claseActuacion,
			boolean _porClaseActuacion, Long _estadoActuacion,
			boolean _porEstadoActuacion) {
		StringBuffer condition = null;
		if (_porAnoFiscal) {
			condition = new StringBuffer(
					"SELECT i FROM ItemPlanificacionActuacion AS item INNER JOIN item.actuacion AS i INNER JOIN item.planAnual as p WHERE ((1=1) AND p.anoFiscal = :anoFiscal  ");
		} else {
			condition = new StringBuffer(
					"SELECT i FROM Actuacion AS i WHERE ((1=1) ");
		}
		EntityManager em = Actuacion.entityManager();
		if (_porReferencia) {
			condition.append(" AND (i.codigoTotal LIKE :referencia) ");
		}
		if (_porNombre) {
			condition.append(" AND (i.nombre LIKE :nombre) ");
		}
		if (_porClaseActuacion) {
			condition.append(" AND (i.claseActuacion.id = :claseActuacion) ");
		}
		if (_porEstadoActuacion) {
			condition.append(" AND (i.estadoActuacion.id = :estadoActuacion) ");
		}
		condition.append(")");
		logger.debug("EL QUERY ES: " + condition);
		Query query = em.createQuery(condition.toString());
		if (_porAnoFiscal) {
			Integer ano = Integer.parseInt(_anoFiscal);
			query.setParameter("anoFiscal", ano);
		}
		if (_porReferencia) {
			query.setParameter("referencia", "%" + _referencia + "%");
		}
		if (_porNombre) {
			query.setParameter("nombre", "%" + _nombre + "%");
		}
		if (_porClaseActuacion) {
			query.setParameter("claseActuacion", _claseActuacion);
		}
		if (_porEstadoActuacion) {
			query.setParameter("estadoActuacion", _estadoActuacion);
		}
		return query.getResultList();
	}

	@Transient
	@SuppressWarnings("unchecked")
	public static Query findActuacionsByCodigoFromAnoFiscal(Integer anoFiscal,
			String codigo, Long idActuacion) {
		if (codigo == null || codigo.length() == 0) {
			throw new IllegalArgumentException(
					"The codigo argument is required");
		}
		EntityManager em = Actuacion.entityManager();
		Query q = em
				.createQuery("SELECT i FROM ItemPlanificacionActuacion AS item INNER JOIN item.actuacion AS i INNER JOIN item.planAnual as p WHERE ( i.codigo = :codigo AND p.anoFiscal = :anoFiscal AND i.id != :idActuacion )");
		q.setParameter("codigo", codigo);
		q.setParameter("anoFiscal", anoFiscal);
		q.setParameter("idActuacion", idActuacion);
		return q;
	}

	public String toStringExtendido() {
		StringBuilder sb = new StringBuilder();
		sb.append("DescripcionGeneral: ")
				.append(getDescripcionGeneral() == null ? "n/a"
						: getDescripcionGeneral()).append(", ");
		sb.append("Codigo: ")
				.append(getCodigoCompleto() == null ? "n/a"
						: getCodigoCompleto()).append(", ");
		sb.append("Nombre: ").append(getNombre() == null ? "n/a" : getNombre())
				.append(", ");
		sb.append("Alcance: ")
				.append(getAlcance() == null ? "n/a" : getAlcance())
				.append(", ");
		sb.append("Objetivo: ")
				.append(getObjetivo() == null ? "n/a" : getObjetivo())
				.append(", ");
		sb.append("Enfoque: ")
				.append(getEnfoque() == null ? "n/a" : getEnfoque())
				.append(", ");
		sb.append("Metodo: ").append(getMetodo() == null ? "n/a" : getMetodo())
				.append(", ");
		sb.append("Responsable: ")
				.append(getResponsable() == null ? "n/a" : getResponsable()
						.toStringLimitado()).append(", ");
		sb.append("EstadoActuacion: ")
				.append(getEstadoActuacion() == null ? "n/a"
						: getEstadoActuacion()).append(", ");
		sb.append("Actuacion Padre: ")
				.append(getActuacion() == null ? "n/a" : getActuacion())
				.append(", ");
		sb.append("ClaseActuacion: ")
				.append(getClaseActuacion() == null ? "n/a"
						: getClaseActuacion()).append(", ");
		sb.append("MesDesde: ")
				.append(getMesDesde() == null ? "n/a" : getMesDesde())
				.append(", ");
		sb.append("MesHasta: ")
				.append(getMesHasta() == null ? "n/a" : getMesHasta())
				.append(", ");
		sb.append("ConclusionGeneral: ")
				.append(getConclusionGeneral() == null ? "n/a"
						: getConclusionGeneral()).append(", ");
		sb.append("Biblioteca: ")
				.append(getBiblioteca() == null ? "n/a" : getBiblioteca())
				.append(", ");
		sb.append("Comentarios: ")
				.append(getComentarios() == null ? "n/a" : getComentarios())
				.append(", ");
		sb.append("UnidadDeMedida: ").append(
				getUnidadDeMedida() == null ? "n/a" : getUnidadDeMedida());
		return sb.toString();
	}

	@Override
	public String toStringLimitado() {
		StringBuilder sb = new StringBuilder();
		sb.append(getCodigoCompleto());
		return sb.toString();
	}

	public String getUrl() {
		return "/actuacion/" + this.getId();
	}

	public String getUrlCorreo() {
		return "http://10.16.17.101:8081/sigefuai/actuacion/" + this.getId();
	}

	public String getDescripcionSimple() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append(getId());
		sb.append("] Codigo ");
		sb.append(getCodigoCompleto());
		return sb.toString();
	}

	public String getDescripcionSimpleCorreo() {
		StringBuilder sb = new StringBuilder();
		sb.append(getCodigoCompleto());
		return sb.toString();
	}

	public String getEstadoPlanSimple() {
		logger.debug("Buscando estado simple de plan");
		List<ItemPlanificacionActuacion> items = ItemPlanificacionActuacion
				.findItemPlanificacionActuacionsByActuacion(this)
				.getResultList();
		logger.debug("Buscando estado simple de plan: hay " + items.size()
				+ " items planificados para esta actuacion");
		long maxId = -1;
		String maxEstado = "";
		for (ItemPlanificacionActuacion item : items) {
			PlanAnual plan = item.getPlanAnual();
			if (plan != null) {
				logger.debug("Revisando plan " + plan.getId());
				EstadoPlan estado = plan.getEstadoPlan();
				if (estado.getId() > maxId) {
					maxId = estado.getId();
					maxEstado = estado.getNombre();
				}
			} else {
				logger.debug("El plan " + plan.getId() + " es null");
			}
		}
		logger.debug("Estado simple de plan es: " + maxEstado);
		return maxEstado;
	}

	@Override
	public String getNombreClase() {
		return "Actuacion";
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("DescripcionGeneral: ").append(getDescripcionGeneral())
				.append(", ");
		sb.append("Id: ").append(getId()).append(", ");
		sb.append("Version: ").append(getVersion()).append(", ");
		sb.append("EstadoSimple: ").append(getEstadoSimple()).append(", ");
		sb.append("CodigoCompleto: ").append(getCodigoCompleto()).append(", ");
		sb.append("CodigoPadres: ").append(getCodigoPadres()).append(", ");
		sb.append("CodigoActuacionTodo: ").append(getCodigoActuacionTodo())
				.append(", ");
		sb.append("CodigoActuacionSinCod: ").append(getCodigoActuacionSinCod())
				.append(", ");
		sb.append("Url: ").append(getUrl()).append(", ");
		sb.append("DescripcionSimple: ").append(getDescripcionSimple())
				.append(", ");
		sb.append("EstadoPlanSimple: ").append(getEstadoPlanSimple())
				.append(", ");
		sb.append("NombreClase: ").append(getNombreClase()).append(", ");
		sb.append("Codigo: ").append(getCodigo()).append(", ");
		sb.append("Nombre: ").append(getNombre()).append(", ");
		sb.append("Alcance: ").append(getAlcance()).append(", ");
		sb.append("Objetivo: ").append(getObjetivo()).append(", ");
		sb.append("Enfoque: ").append(getEnfoque()).append(", ");
		sb.append("Metodo: ").append(getMetodo()).append(", ");
		sb.append("Responsable: ").append(getResponsable()).append(", ");
		sb.append("EstadoActuacion: ").append(getEstadoActuacion())
				.append(", ");
		sb.append("Actuacion: ").append(getActuacion()).append(", ");
		sb.append("ClaseActuacion: ").append(getClaseActuacion()).append(", ");
		sb.append("SeccionesVisibles: ")
				.append(java.util.Arrays.toString(getSeccionesVisibles()))
				.append(", ");
		sb.append("Unidades: ")
				.append(getUnidades() == null ? "null" : getUnidades().size())
				.append(", ");
		sb.append("MesDesde: ").append(getMesDesde()).append(", ");
		sb.append("MesHasta: ").append(getMesHasta()).append(", ");
		sb.append("MesDesdeTentativo: ").append(getMesDesdeTentativo())
				.append(", ");
		sb.append("MesHastaTentativo: ").append(getMesHastaTentativo())
				.append(", ");
		sb.append("MesDesdeReal: ").append(getMesDesdeReal()).append(", ");
		sb.append("MesHastaReal: ").append(getMesHastaReal()).append(", ");
		sb.append("ConclusionGeneral: ").append(getConclusionGeneral())
				.append(", ");
		sb.append("Biblioteca: ").append(getBiblioteca()).append(", ");
		sb.append("Comentarios: ").append(getComentarios()).append(", ");
		sb.append("UnidadDeMedida: ").append(getUnidadDeMedida()).append(", ");
		sb.append("Correlativo: ").append(getCorrelativo()).append(", ");
		sb.append("CodigoPlanificacion: ").append(getCodigoPlanificacion())
				.append(", ");
		sb.append("CodigoTotal: ").append(getCodigoTotal()).append(", ");
		sb.append("PorProcesos: ").append(getPorProcesos()).append(", ");
		sb.append("Procesos: ")
				.append(getProcesos() == null ? "null" : getProcesos().size())
				.append(", ");
		sb.append("FechaRegistro: ").append(getFechaRegistro()).append(", ");
		sb.append("NombreProyecto: ").append(getNombreProyecto()).append(", ");
		sb.append("AccionesPermitidas: ").append(
				java.util.Arrays.toString(getAccionesPermitidas()));
		return sb.toString();
	}

	public String auditores() {
		logger.debug("Buscando login del Usuario para el JPBM--------->>");
		String login = "";
		Set<Auditor> auditors = Actuacion.findActuacion(this.getId())
				.getResponsableAuditor();
		if (auditors != null) {
			logger.debug("Entrando a la lista de auditores");
			for (Auditor auditor : auditors) {
				if (login.equals("")) {
					logger.debug("Entrando en la primera validacion de auditores");
					login = auditor.getLogin();
				} else {
					logger.debug("Entrando en la segunda validacion de auditores");
					login = login + ", " + auditor.getLogin();
				}
				logger.debug("Los auditore son: " + login);

			}
			return login;
		} else {
			logger.debug("No se Encontro la lista de responsables");
			login = "gerente";
			return login;
		}
	}

	public Set<Auditor> getListAuditors() {
		Set<Auditor> set = Actuacion.findActuacion(this.getId())
				.getResponsableAuditor();
		return set;
	}

	public String nombreResponsable() {
		String nombre = this.getResponsable().getNombre() + " "
				+ this.getResponsable().getApellido();
		return nombre;
	}
}
