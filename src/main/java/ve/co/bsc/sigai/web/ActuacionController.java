/*
 * Licencia GPL v3
 * 
 * Copyright (C) 2013 Centro Nacional de Tecnologías de Información.
 * SIGEFUAI Sistema de Informacion Gerencial de Fortalecimiento de las Unidades de Auditoria Interna
 * 
 * Copyright (C) 2013 Ehison Perez ehisonbp@gmail.com, Gerardo Perez @gerardoperez132, Alexis Veliz. All Rights Reserved.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or any
 * later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see http://www.gnu.org/licenses
 */

package ve.co.bsc.sigai.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.drools.runtime.process.ProcessInstance;
import org.jbpm.task.query.TaskSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import ve.co.bsc.sigai.domain.ActividadActuacion;
import ve.co.bsc.sigai.domain.ActividadAuditor;
import ve.co.bsc.sigai.domain.Actuacion;
import ve.co.bsc.sigai.domain.ArchivoAdjunto;
import ve.co.bsc.sigai.domain.Auditor;
import ve.co.bsc.sigai.domain.Biblioteca;
import ve.co.bsc.sigai.domain.ClaseActuacion;
import ve.co.bsc.sigai.domain.EstadoActividadActuacion;
import ve.co.bsc.sigai.domain.EstadoActuacion;
import ve.co.bsc.sigai.domain.HistorialCambios;
import ve.co.bsc.sigai.domain.ItemPlanificacionActuacion;
import ve.co.bsc.sigai.domain.ObjetivoEspecifico;
import ve.co.bsc.sigai.domain.OcupacionAuditor;
import ve.co.bsc.sigai.domain.PapelDeTrabajo;
import ve.co.bsc.sigai.domain.PlanAnual;
import ve.co.bsc.sigai.domain.Proceso;
import ve.co.bsc.sigai.domain.RolUsuario;
import ve.co.bsc.sigai.domain.SeccionDefault;
import ve.co.bsc.sigai.domain.Unidad;
import ve.co.bsc.sigai.domain.UnidadDeMedida;
import ve.co.bsc.sigai.domain.Usuario;
import ve.co.bsc.sigai.service.HumanTaskService;
import ve.co.bsc.sigai.service.JbpmService;
import ve.co.bsc.sigai.service.exception.ServiceTemporarilyUnavailableException;
import ve.co.bsc.util.Util;

@RooWebScaffold(path = "actuacion", automaticallyMaintainView = true, formBackingObject = Actuacion.class)
@RequestMapping("/actuacion/**")
@SessionAttributes({ "actuacion", "idPlanAnual", "anoFiscal" })
@Controller
public class ActuacionController {

	Logger logger = Logger.getLogger(ActuacionController.class);

	@Autowired
	JbpmService jbpmService;

	@Autowired
	HumanTaskService humanTaskService;

	@RequestMapping(value = "/actuacion", method = RequestMethod.POST)
	public String create(@ModelAttribute("idPlanAnual") long idPlanAnual,
			@Valid @ModelAttribute("actuacion") Actuacion actuacion,
			BindingResult result, ModelMap modelMap, SessionStatus status,
			HttpServletRequest request) {
		Util util = new Util();
		// Manejo de errores
		PlanAnual miPlan = PlanAnual.findPlanAnual(idPlanAnual);
		/*
		 * List<Actuacion> acts =
		 * Actuacion.findActuacionsByCodigoFromAnoFiscal(miPlan.getAnoFiscal(),
		 * actuacion.getCodigo(), actuacion.getId()).getResultList(); if
		 * (acts.size() > 0){ result.addError(new
		 * FieldError(result.getObjectName(), "codigo",
		 * "Ya existe una actuacion con ese código en este año fiscal")); }
		 */

		String loginGerente = util.loginGerente();

		String[] camposPermitidos = { "responsable", "codigo", "nombre",
				"claseActuacion", "alcance", "mesDesde", "mesHasta", "objetivo" };
		Arrays.sort(camposPermitidos);

		if (Arrays.binarySearch(camposPermitidos, "alcance") >= 0) {
			if (((String) actuacion.getAlcance()).length() < 1) {
				result.addError(new FieldError(result.getObjectName(),
						"alcance", "Campo obligatorio"));
			}
		}

		if (Arrays.binarySearch(camposPermitidos, "objetivo") >= 0) {
			if (((String) actuacion.getObjetivo()).length() < 1) {
				result.addError(new FieldError(result.getObjectName(),
						"objetivo", "Campo obligatorio"));
			}
		}

		if (Arrays.binarySearch(camposPermitidos, "enfoque") >= 0) {
			if (((String) actuacion.getEnfoque()).length() < 1) {
				result.addError(new FieldError(result.getObjectName(),
						"enfoque", "Campo obligatorio"));
			}
		}

		if (Arrays.binarySearch(camposPermitidos, "metodo") >= 0) {
			if (((String) actuacion.getMetodo()).length() < 1) {
				result.addError(new FieldError(result.getObjectName(),
						"metodo", "Campo obligatorio"));
			}
		}

		if (Arrays.binarySearch(camposPermitidos, "mesDesde") >= 0) {
			if (actuacion.getMesDesde() == 0) {
				result.addError(new FieldError(result.getObjectName(),
						"mesDesde", "Campo obligatorio"));
			}
		}

		if (Arrays.binarySearch(camposPermitidos, "mesHasta") >= 0) {
			if (actuacion.getMesHasta() == 0) {
				result.addError(new FieldError(result.getObjectName(),
						"mesHasta", "Campo obligatorio"));
			}
		}

		if ((Arrays.binarySearch(camposPermitidos, "mesHasta") >= 0)
				&& (Arrays.binarySearch(camposPermitidos, "mesDesde") >= 0)) {
			if (actuacion.getMesHasta() < actuacion.getMesDesde()) {
				result.addError(new FieldError(result.getObjectName(),
						"mesHasta", "Fecha inválida "));
			}
		}

		if (actuacion == null) {
			throw new IllegalArgumentException("A actuacion is required");
		}

		if (result.hasErrors()) {

			List<Auditor> todos = Auditor.findAllAuditors();
			List<Auditor> losQueSon = new LinkedList<Auditor>();
			for (Auditor a : todos) {
				List<Usuario> usuarios = Usuario.findUsuariosByLogin(
						a.getLogin()).getResultList();
				for (Usuario u : usuarios) {
					for (RolUsuario r : u.getRoles()) {
						if (r.getNombre().equals("ROLE_UNIDAD_COORDINADOR")) {
							if (a.getId_OrganismoEnte().getRif() == util
									.obtenerRif()) {
								losQueSon.add(a);
								break;
							}

						}
					}
				}
			}

			modelMap.addAttribute("actuacion", actuacion);
			modelMap.addAttribute("auditors", losQueSon);
			modelMap.addAttribute("estadoactuacions",
					EstadoActuacion.findAllEstadoActuacions());
			modelMap.addAttribute("claseactuacions",
					ClaseActuacion.findAllClaseActuacions());
			modelMap.addAttribute("unidads",
					Unidad.findUnidadsByRif(util.traerIdRif()).getResultList());
			modelMap.addAttribute("procesoes", Proceso.findAllProcesoes());
			modelMap.addAttribute("unidaddemedidas",
					UnidadDeMedida.findAllUnidadDeMedidas());
			modelMap.addAttribute("camposPermitidos", camposPermitidos);
			modelMap.addAttribute("idPlanAnual", idPlanAnual);
			return "actuacion/create";
		}
		if (actuacion.getActuacion() != null) {
			actuacion.setActuacion(actuacion.getActuacion());
		}
		// ***Insersion del Rif del Usiario loggeado***//
		actuacion.setRif(util.traerIdRif());
		// ***Insersion del Rif del Usiario loggeado***//

		// Hago set de codigoTotal
		// (Correlativo-CodigoPlanificacion-ClaseActuacion-AnoFiscal-SufijoOpcional)
		String codigoTotal = actuacion.getCorrelativo()
				+ "-"
				+ actuacion.getCodigoPlanificacion()
				+ "-"
				+ (((String) actuacion.getClaseActuacion().getNombre())
						.substring(0, 2)).toUpperCase() + "-"
				+ miPlan.getAnoFiscal();

		if ((actuacion.getCodigo() != null) && (actuacion.getCodigo() != "")) {
			codigoTotal = codigoTotal + "-" + actuacion.getCodigo();
		}
		actuacion.setCodigoTotal(codigoTotal);
		actuacion.setFechaRegistro(new Date());

		// ***** COPIO LAS FECHAS PLANIFICADAS A LAS TENTATIVAS *****
		actuacion.setMesDesdeTentativo(actuacion.getMesDesde());
		actuacion.setMesHastaTentativo(actuacion.getMesHasta());

		// Verifico si seleccionó un tipo de auditoría que no sea de Operativa
		// para asignar null al campo nombreOperativa
		String claseActuacion = actuacion.getClaseActuacion().getNombre();
		if (!claseActuacion.equals("Operativa")) {
			actuacion.setNombreProyecto(null);
		}

		actuacion.persist();
		Util.registrarEntradaEnBitacora(actuacion, "Se creó la Actuación ",
				request.getRemoteAddr());

		// ******** Creo un itemPlanificacionActuacion con los datos de la
		// actuacion ********
		ItemPlanificacionActuacion itemPlanificacion = new ItemPlanificacionActuacion();
		itemPlanificacion.setInicioEstimado(actuacion.getMesDesde());
		itemPlanificacion.setFinEstimado(actuacion.getMesHasta());
		itemPlanificacion.setActuacion(actuacion);
		itemPlanificacion.setPlanAnual(miPlan);
		itemPlanificacion.persist();
		// **********************************************************************************

		// inicia el workflow para esta actuacion
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("actuacion", actuacion);
		ProcessInstance p = jbpmService.startProcess(
				"ve.co.bsc.sigai.domain.bpm.lifecycle.Actuacion", parameters,
				actuacion);

		logger.debug("solicitando revision de actuacion en motor de reglas");
		jbpmService.executeRulesStateless(actuacion);
		logger.debug("revision de actuacion en motor de reglas listo");

		status.setComplete();
		return "redirect:/actuacion/" + actuacion.getId();
	}

	@RequestMapping(value = "/actuacion/form", method = RequestMethod.GET)
	public String createForm(@RequestParam("idPlanAnual") long idPlanAnual,
			HttpServletRequest hsr, ModelMap modelMap) {
		Actuacion miActuacion = new Actuacion();

		if (hsr.getParameter("idActuacion") != null) {
			// Entra aqui si tiene un padre
			Actuacion miPadre = Actuacion.findActuacion(new Long(hsr
					.getParameter("idActuacion")));
			miActuacion.setActuacion(miPadre);
		}

		// Buscamos el plan anual al cual pertenece la actuacion para mostrar el
		// año en la fecha
		PlanAnual miPlan = PlanAnual.findPlanAnual(idPlanAnual);

		// ***Insersion del Año del Plan Anual ***//
		miActuacion.setAnoFiscal(miPlan.getAnoFiscal());
		// ***Insersion del Año del Plan Anual***//

		// Asigno el correlativo
		int mayor = 0;
		List<ItemPlanificacionActuacion> items = ItemPlanificacionActuacion
				.findItemPlanificacionActuacionsByPlanAnualAndActuacionNotNull(
						miPlan).getResultList();
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getActuacion().getCorrelativo() > mayor) {
				mayor = items.get(i).getActuacion().getCorrelativo();
			}
		}
		miActuacion.setCorrelativo(mayor + 1);

		// Asigno el codigoPlanificacion AP o ANP
		/**
		 * if (miPlan.getEstadoPlan() == EstadoPlan.findEstadoPlan(new Long(6)))
		 * { miActuacion.setCodigoPlanificacion("ANP"); } else {
		 * miActuacion.setCodigoPlanificacion("AP"); }
		 */
		miActuacion.setCodigoPlanificacion("AP");

		List<Auditor> todos = Auditor.findAllAuditors();
		List<Auditor> losQueSon = new LinkedList<Auditor>();
		Util util = new Util();
		for (Auditor a : todos) {
			List<Usuario> usuarios = Usuario.findUsuariosByLogin(a.getLogin())
					.getResultList();
			for (Usuario u : usuarios) {
				for (RolUsuario r : u.getRoles()) {
					if (r.getNombre().equals("ROLE_UNIDAD_COORDINADOR")) {
						if (a.getId_OrganismoEnte().getRif() == util
								.obtenerRif()) {
							losQueSon.add(a);
							break;
						}

					}
				}
			}
		}

		modelMap.addAttribute("anoFiscal", miPlan.getAnoFiscal());
		modelMap.addAttribute("actuacion", miActuacion);
		modelMap.addAttribute("auditors", losQueSon);
		modelMap.addAttribute("estadoactuacions",
				EstadoActuacion.findAllEstadoActuacions());
		modelMap.addAttribute("claseactuacions",
				ClaseActuacion.findAllClaseActuacions());
		modelMap.addAttribute("unidads",
				Unidad.findUnidadsByRif(util.traerIdRif()).getResultList());
		modelMap.addAttribute("procesoes", Proceso.findAllProcesoes());
		modelMap.addAttribute("idPlanAnual", idPlanAnual);
		modelMap.addAttribute("unidaddemedidas",
				UnidadDeMedida.findAllUnidadDeMedidas());

		String[] camposPermitidos = { "responsable", "codigo", "nombre",
				"claseActuacion", "alcance", "mesDesde", "mesHasta", "objetivo" };
		modelMap.addAttribute("camposPermitidos", camposPermitidos);

		return "actuacion/create";
	}

	@RequestMapping(value = "/actuacion/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") Long id, ModelMap modelMap) {
		if (id == null) {
			throw new IllegalArgumentException("An Identifier is required");
		}
		Actuacion actuacion = Actuacion.findActuacion(id);
		modelMap.addAttribute("actuacion", actuacion);

		Query queryActuaciones = Actuacion.findActuacionsByActuacion(actuacion);
		List<Actuacion> actuaciones = queryActuaciones.getResultList();
		Collections.sort(actuaciones, Actuacion.compararActuaciones);
		modelMap.addAttribute("actuacions", actuaciones);

		Query queryObjetivos = ObjetivoEspecifico
				.findObjetivoEspecificoesByActuacion(actuacion);
		List<ObjetivoEspecifico> objetivosEspecificos = queryObjetivos
				.getResultList();
		modelMap.addAttribute("objetivosEspecificos", objetivosEspecificos);
		List<ActividadActuacion> actividadActuaciones = new ArrayList<ActividadActuacion>();
		Query queryActividadActuaciones = ActividadActuacion
				.findActividadActuacionsByCodigoActuacion(actuacion);
		List<ActividadActuacion> actividadActuacionesTodo = queryActividadActuaciones
				.getResultList();
		List<ActividadActuacion> actividadActuacionesPadres = new ArrayList<ActividadActuacion>();

		for (int i = 0; i < actividadActuacionesTodo.size(); i++) {
			if (actividadActuacionesTodo.get(i).getActividadActuacion() == null) {
				actividadActuacionesPadres.add(actividadActuacionesTodo.get(i));
			}
		}
		if (actividadActuacionesPadres.size() > 0) {
			Collections.sort(actividadActuacionesPadres,
					ActividadActuacion.compararActividadesPorId);

			for (int j = 0; j < actividadActuacionesPadres.size(); j++) {
				// agrego el padre
				actividadActuaciones.add(actividadActuacionesPadres.get(j));
				// busco los hijos
				actividadActuaciones.addAll(bucarActividadActuacionHijos(
						actividadActuacionesPadres.get(j),
						actividadActuacionesTodo));
			}
		}

		// debo iterar actividadActuaciones y voy llamando al motor con cada
		// actividadActuacion
		// para que llene el campo de operacionesPermitidas
		// en la vista por cada elemento que itero pregunto si tiene operacion
		// permitida "ver"

		// modelMap.addAttribute("objetoComentable",actuacion);

		modelMap.addAttribute("actividadActuaciones", actividadActuaciones);

		List<ItemPlanificacionActuacion> it = ItemPlanificacionActuacion
				.findItemPlanificacionActuacionsByActuacion(actuacion)
				.getResultList();
		modelMap.addAttribute("planAnual", it.get(0).getPlanAnual());

		List<OcupacionAuditor> ocupaciones = OcupacionAuditor
				.findOcupacionAuditorsByActividadAuditor(
						(ActividadAuditor) actuacion).getResultList();
		modelMap.addAttribute("ocupacionauditors", ocupaciones);
		modelMap.addAttribute("ocupacion_inicio_date_format",
				org.joda.time.format.DateTimeFormat.patternForStyle("S-",
						org.springframework.context.i18n.LocaleContextHolder
								.getLocale()));
		modelMap.addAttribute("ocupacion_fin_date_format",
				org.joda.time.format.DateTimeFormat.patternForStyle("S-",
						org.springframework.context.i18n.LocaleContextHolder
								.getLocale()));

		// String[] camposPermitidos = {"codigo", "nombre", "claseActuacion",
		// "alcance", "mesDesde", "mesHasta", "mesDesdeTentativo",
		// "mesHastaTentativo", "objetivo", "enfoque", "metodo", "unidades",
		// "responsable", "estadoActuacion", "unidadDeMedida", "porProcesos",
		// "procesos", "comentarios", "objetivosEspecificos"};
		logger.debug("solicitando revision de actuacion en motor de reglas");
		jbpmService.executeRulesStateless(actuacion);
		logger.debug("revision de actuacion en motor de reglas listo");
		modelMap.addAttribute("camposPermitidos",
				actuacion.getSeccionesVisibles());
		modelMap.addAttribute("accionesPermitidas",
				actuacion.getAccionesPermitidas());

		for (ActividadActuacion act : actividadActuaciones) {
			logger.debug("solicitando revision de actividad actuacion en motor de reglas");
			jbpmService.executeRulesStateless(act);
			logger.debug("revision de actividad en motor de reglas listo");
		}

		try {
			List<TaskSummary> tasks = humanTaskService
					.getCorrelatedTasksAssignedAsPotentialOwnerToCurrentUser(actuacion);
			modelMap.addAttribute("tasks", tasks);
		} catch (ServiceTemporarilyUnavailableException e) {
			modelMap.addAttribute("tasks", new LinkedList<TaskSummary>());
			modelMap.addAttribute("unavailable", e);
		}

		modelMap.addAttribute("objetoComentable", actuacion);

		logger.info("\n" + actuacion.auditores());
		return "actuacion/show";
	}

	private List<ActividadActuacion> bucarActividadActuacionHijos(
			ActividadActuacion padre, List<ActividadActuacion> todos) {
		List<ActividadActuacion> result = new ArrayList<ActividadActuacion>();
		for (int i = 0; i < todos.size(); i++) {
			ActividadActuacion actividad = todos.get(i);
			ActividadActuacion actividadPadre = actividad
					.getActividadActuacion();
			if (actividadPadre != null) {
				if (actividadPadre.getId() == padre.getId()) {
					result.add(todos.get(i));
					result.addAll(bucarActividadActuacionHijos(todos.get(i),
							todos));
				}
			}
		}

		return result;
	}

	@RequestMapping(method = RequestMethod.PUT)
	public String update(@Valid Actuacion actuacion, BindingResult result,
			ModelMap modelMap, HttpServletRequest request) {

		// Manejo de errores
		List<ItemPlanificacionActuacion> it = ItemPlanificacionActuacion
				.findItemPlanificacionActuacionsByActuacion(actuacion)
				.getResultList();
		/*
		 * List<Actuacion> acts =
		 * Actuacion.findActuacionsByCodigoFromAnoFiscal(it
		 * .get(0).getPlanAnual().getAnoFiscal(), actuacion.getCodigo(),
		 * actuacion.getId()).getResultList(); if (acts.size() > 0){
		 * result.addError(new FieldError(result.getObjectName(), "codigo",
		 * "Ya existe una actuacion con ese código en este año fiscal")); }
		 */

		String[] camposPermitidos = actuacion.getSeccionesVisibles();

		if (camposPermitidos != null) {
			Arrays.sort(camposPermitidos);
		}

		if (Arrays.binarySearch(camposPermitidos, "alcance") >= 0) {
			if (((String) actuacion.getAlcance()).length() < 1) {
				result.addError(new FieldError(result.getObjectName(),
						"alcance", "Campo obligatorio"));
			}
		}

		if (Arrays.binarySearch(camposPermitidos, "objetivo") >= 0) {
			if (((String) actuacion.getObjetivo()).length() < 1) {
				result.addError(new FieldError(result.getObjectName(),
						"objetivo", "Campo obligatorio"));
			}
		}

		if (Arrays.binarySearch(camposPermitidos, "enfoque") >= 0) {
			if (((String) actuacion.getEnfoque()).length() < 1) {
				result.addError(new FieldError(result.getObjectName(),
						"enfoque", "Campo obligatorio"));
			}
		}

		if (Arrays.binarySearch(camposPermitidos, "metodo") >= 0) {
			if (((String) actuacion.getMetodo()).length() < 1) {
				result.addError(new FieldError(result.getObjectName(),
						"metodo", "Campo obligatorio"));
			}
		}

		if (Arrays.binarySearch(camposPermitidos, "mesDesde") >= 0) {
			if (actuacion.getMesDesde() == 0) {
				result.addError(new FieldError(result.getObjectName(),
						"mesDesde", "Campo obligatorio"));
			}
		}

		if (Arrays.binarySearch(camposPermitidos, "mesHasta") >= 0) {
			if (actuacion.getMesHasta() == 0) {
				result.addError(new FieldError(result.getObjectName(),
						"mesHasta", "Campo obligatorio"));
			}
		}

		if ((Arrays.binarySearch(camposPermitidos, "mesHasta") >= 0)
				&& (Arrays.binarySearch(camposPermitidos, "mesDesde") >= 0)) {
			if (actuacion.getMesHasta() < actuacion.getMesDesde()) {
				result.addError(new FieldError(result.getObjectName(),
						"mesHasta", "Fecha inválida "));
			}
		}

		if (Arrays.binarySearch(camposPermitidos, "mesDesdeTentativo") >= 0) {
			if (actuacion.getMesDesdeTentativo() == 0) {
				result.addError(new FieldError(result.getObjectName(),
						"mesDesdeTentativo", "Campo obligatorio"));
			}
		}

		if (Arrays.binarySearch(camposPermitidos, "mesHastaTentativo") >= 0) {
			if (actuacion.getMesHastaTentativo() == 0) {
				result.addError(new FieldError(result.getObjectName(),
						"mesHastaTentativo", "Campo obligatorio"));
			}
		}

		if ((Arrays.binarySearch(camposPermitidos, "mesHastaTentativo") >= 0)
				&& (Arrays.binarySearch(camposPermitidos, "mesDesdeTentativo") >= 0)) {
			if (actuacion.getMesHastaTentativo() < actuacion
					.getMesDesdeTentativo()) {
				result.addError(new FieldError(result.getObjectName(),
						"mesHastaTentativo", "Fecha inválida "));
			}
		}

		if (actuacion == null) {
			throw new IllegalArgumentException("A actuacion is required");
		}

		List<Auditor> todos = Auditor.findAllAuditors();
		List<Auditor> losQueSon = new LinkedList<Auditor>();
		Util util = new Util();
		for (Auditor a : todos) {
			List<Usuario> usuarios = Usuario.findUsuariosByLogin(a.getLogin())
					.getResultList();
			for (Usuario u : usuarios) {
				for (RolUsuario r : u.getRoles()) {
					if (r.getNombre().equals("ROLE_UNIDAD_COORDINADOR")) {
						if (a.getId_OrganismoEnte().getRif() == util
								.obtenerRif()) {
							losQueSon.add(a);
							break;
						}

					}
				}
			}
		}

		// Metodo para traer los usuario de la UAI de una institucion en
		// especifico
		Query query = Auditor.findAuditorsById_OrganismoEnte(util.traerIdRif());
		List<Auditor> todos2 = query.getResultList();
		List<Auditor> losQueSon2 = new LinkedList<Auditor>();
		for (Auditor a : todos2) {
			List<Usuario> usuarios = Usuario.findUsuariosByLogin(a.getLogin())
					.getResultList();
			for (Usuario u : usuarios) {
				for (RolUsuario r : u.getRoles()) {
					if (r.getNombre().equals("ROLE_UNIDAD_AUDITOR")) {
						losQueSon2.add(a);
						break;
					}
				}
			}
		}

		int anoFiscal = 0;
		List<ItemPlanificacionActuacion> items2 = ItemPlanificacionActuacion
				.findItemPlanificacionActuacionsByActuacion(actuacion)
				.getResultList();
		for (int i = 0; i < items2.size(); i++) {
			anoFiscal = items2.get(i).getPlanAnual().getAnoFiscal();
		}

		if (result.hasErrors()) {
			modelMap.addAttribute("actuacion", actuacion);
			// modelMap.addAttribute("actuacions",
			// Actuacion.findAllActuacions());
			modelMap.addAttribute("auditors", losQueSon);
			modelMap.addAttribute("auditors", losQueSon2);
			modelMap.addAttribute("estadoactuacions",
					EstadoActuacion.findAllEstadoActuacions());
			modelMap.addAttribute("claseactuacions",
					ClaseActuacion.findAllClaseActuacions());
			modelMap.addAttribute("unidads",
					Unidad.findUnidadsByRif(util.traerIdRif()).getResultList());
			modelMap.addAttribute("procesoes", Proceso.findAllProcesoes());
			modelMap.addAttribute("unidaddemedidas",
					UnidadDeMedida.findAllUnidadDeMedidas());
			modelMap.addAttribute("camposPermitidos",
					actuacion.getSeccionesVisibles());
			modelMap.addAttribute("anoFiscal", anoFiscal);
			// modelMap.addAttribute("actuacion_fechaDesde_date_format",
			// org.joda.time.format.DateTimeFormat.patternForStyle("S-",
			// org.springframework.context.i18n.LocaleContextHolder.getLocale()));
			// modelMap.addAttribute("actuacion_fechaHasta_date_format",
			// org.joda.time.format.DateTimeFormat.patternForStyle("S-",
			// org.springframework.context.i18n.LocaleContextHolder.getLocale()));
			return "actuacion/update";
		}

		// Hago set de codigoTotal
		// (Correlativo-CodigoPlanificacion-ClaseActuacion-AnoFiscal-SufijoOpcional)
		String codigoTotal = actuacion.getCorrelativo()
				+ "-"
				+ actuacion.getCodigoPlanificacion()
				+ "-"
				+ (((String) actuacion.getClaseActuacion().getNombre())
						.substring(0, 2)).toUpperCase() + "-"
				+ it.get(0).getPlanAnual().getAnoFiscal();

		if ((actuacion.getCodigo() != null) && (actuacion.getCodigo() != "")) {
			codigoTotal = codigoTotal + "-" + actuacion.getCodigo();
		}
		actuacion.setCodigoTotal(codigoTotal);

		// Verifico si seleccionó un tipo de auditoría que no sea de Operativa
		// para asignar null al campo nombreOperativa
		String claseActuacion = actuacion.getClaseActuacion().getNombre();
		if (!claseActuacion.equals("Operativa")) {
			actuacion.setNombreProyecto(null);
		}

		if (actuacion.getEstadoActuacion().getId() == Long.parseLong("1")) {
			actuacion.setMesDesdeTentativo(actuacion.getMesDesde());
			actuacion.setMesHastaTentativo(actuacion.getMesHasta());
		}

		actuacion.merge();
		Util.registrarEntradaEnBitacora(actuacion, "Se modificó la Actuación ",
				request.getRemoteAddr());

		// ******** Actualizo los itemPlanificacionActuacions con las fechas
		// nuevas de la actuacion ********
		List<ItemPlanificacionActuacion> items = ItemPlanificacionActuacion
				.findItemPlanificacionActuacionsByActuacion(actuacion)
				.getResultList();
		for (int i = 0; i < items.size(); i++) {
			items.get(i).setInicioEstimado(actuacion.getMesDesde());
			items.get(i).setFinEstimado(actuacion.getMesHasta());
			items.get(i).merge();
			Util.registrarEntradaEnBitacora(items.get(i),
					"Se modificaron las fechas de planificacion de la actuacion "
							+ actuacion.getCodigoCompleto(),
					request.getRemoteAddr());
		}

		// **********************************************************************************

		return "redirect:/actuacion/" + actuacion.getId();
	}

	@RequestMapping(value = "/actuacion/{id}/form", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
		if (id == null) {
			throw new IllegalArgumentException("An Identifier is required");
		}
		Actuacion actuacion = Actuacion.findActuacion(id);

		int anoFiscal = 0;
		List<ItemPlanificacionActuacion> items = ItemPlanificacionActuacion
				.findItemPlanificacionActuacionsByActuacion(actuacion)
				.getResultList();
		for (int i = 0; i < items.size(); i++) {
			anoFiscal = items.get(i).getPlanAnual().getAnoFiscal();
		}

		List<Auditor> todos = Auditor.findAllAuditors();
		List<Auditor> losQueSon = new LinkedList<Auditor>();
		Util util = new Util();
		for (Auditor a : todos) {
			List<Usuario> usuarios = Usuario.findUsuariosByLogin(a.getLogin())
					.getResultList();
			for (Usuario u : usuarios) {
				for (RolUsuario r : u.getRoles()) {
					if (r.getNombre().equals("ROLE_UNIDAD_COORDINADOR")) {
						if (a.getId_OrganismoEnte().getRif() == util
								.obtenerRif()) {
							losQueSon.add(a);
							break;
						}

					}
				}
			}
		}

		// Metodo para traer los usuario de la UAI de una institucion en
		// especifico
		Query query = Auditor.findAuditorsById_OrganismoEnte(util.traerIdRif());
		List<Auditor> todos2 = query.getResultList();
		List<Auditor> losQueSon2 = new LinkedList<Auditor>();
		for (Auditor a : todos2) {
			List<Usuario> usuarios = Usuario.findUsuariosByLogin(a.getLogin())
					.getResultList();
			for (Usuario u : usuarios) {
				for (RolUsuario r : u.getRoles()) {
					if (r.getNombre().equals("ROLE_UNIDAD_AUDITOR")) {
						losQueSon2.add(a);
						break;
					}
				}
			}
		}

		modelMap.addAttribute("actuacion", actuacion);
		// modelMap.addAttribute("actuacions", Actuacion.findAllActuacions());
		modelMap.addAttribute("auditors", losQueSon);
		modelMap.addAttribute("auditores", losQueSon2);
		modelMap.addAttribute("estadoactuacions",
				EstadoActuacion.findAllEstadoActuacions());
		modelMap.addAttribute("claseactuacions",
				ClaseActuacion.findAllClaseActuacions());
		modelMap.addAttribute("unidads",
				Unidad.findUnidadsByRif(util.traerIdRif()).getResultList());
		modelMap.addAttribute("procesoes", Proceso.findAllProcesoes());
		modelMap.addAttribute("unidaddemedidas",
				UnidadDeMedida.findAllUnidadDeMedidas());
		modelMap.addAttribute("anoFiscal", anoFiscal);
		// modelMap.addAttribute("actuacion_fechaDesde_date_format",
		// org.joda.time.format.DateTimeFormat.patternForStyle("S-",
		// org.springframework.context.i18n.LocaleContextHolder.getLocale()));
		// modelMap.addAttribute("actuacion_fechaHasta_date_format",
		// org.joda.time.format.DateTimeFormat.patternForStyle("S-",
		// org.springframework.context.i18n.LocaleContextHolder.getLocale()));

		// String[] camposPermitidos = {"codigo", "nombre", "claseActuacion",
		// "alcance", "mesDesdeTentativo", "mesHastaTentativo", "objetivo",
		// "enfoque", "metodo", "unidades", "responsable", "estadoActuacion",
		// "unidadDeMedida", "porProcesos", "procesos", "comentarios"};
		logger.debug("solicitando revision de actuacion en motor de reglas");
		jbpmService.executeRulesStateless(actuacion);
		logger.debug("revision de plan anual en motor de reglas listo");
		modelMap.addAttribute("camposPermitidos",
				actuacion.getSeccionesVisibles());

		return "actuacion/update";
	}

	@RequestMapping(value = "/actuacion/search", method = RequestMethod.GET)
	public String search(
			@RequestParam(value = "vez", required = false) String vez,
			@RequestParam(value = "anoFiscal", required = false) String anoFiscal,
			@RequestParam(value = "referencia", required = false) String referencia,
			@RequestParam(value = "nombre", required = false) String nombre,
			@RequestParam(value = "claseActuacion", required = false) Long claseActuacion,
			@RequestParam(value = "estadoActuacion", required = false) Long estadoActuacion,
			ModelMap modelMap) {

		if (vez == null) {
			// si vez es null se toma por defecto como que es la primera vez que
			// entro
			vez = "1";
		}

		List<Actuacion> actuacions = null;

		if (!vez.equals("1")) {
			actuacions = Actuacion.findActuacionsByParams(anoFiscal,
					(!anoFiscal.equals("")), referencia,
					(!referencia.equals("")), nombre, (!nombre.equals("")),
					claseActuacion, (claseActuacion != null), estadoActuacion,
					(estadoActuacion != null));
			modelMap.addAttribute("actuacions", actuacions);
			modelMap.addAttribute("anoFiscal", anoFiscal);
			modelMap.addAttribute("nombre", nombre);
			modelMap.addAttribute("referencia", referencia);
			modelMap.addAttribute("claseActuacion", claseActuacion);
			modelMap.addAttribute("estadoActuacion", estadoActuacion);
		}

		modelMap.addAttribute("claseActuacions",
				ClaseActuacion.findAllClaseActuacions());
		modelMap.addAttribute("estadoActuacions",
				EstadoActuacion.findAllEstadoActuacions());

		return "actuacion/search";
	}

	@RequestMapping(value = "/actuacion/conclusion", method = RequestMethod.POST)
	public String saveConclusion(
			@RequestParam("idPapelDeTrabajo") Long idPapelDeTrabajo,
			// @Valid @ModelAttribute("actuacion") Actuacion actuacion,
			@Valid Actuacion actuacion, BindingResult result,
			ModelMap modelMap, SessionStatus status, HttpServletRequest request) {
		if (actuacion == null) {
			throw new IllegalArgumentException("An actuacion is required");
		}
		PapelDeTrabajo miPapelDeTrabajo = PapelDeTrabajo
				.findPapelDeTrabajo(idPapelDeTrabajo);

		if (result.hasErrors()) {
			return "redirect:/papeldetrabajo/" + miPapelDeTrabajo.getId()
					+ "?contenidoGuardado=false";
		}

		/*
		 * if (result.hasErrors()) { modelMap.addAttribute("actuacion",
		 * actuacion); return "redirect:/papeldetrabajo/" + idPapelDeTrabajo; }
		 * 
		 * modelMap.addAttribute("actuacion", actuacion);
		 * modelMap.addAttribute("papelDeTrabajo", miPapelDeTrabajo);
		 * actuacion.merge();
		 */
		agregarConclusion(actuacion.getId(), actuacion.getConclusionGeneral(),
				request);

		// return "papeldetrabajo/" +
		// miPapelDeTrabajo.getTipoSeccion().getVista();
		return "redirect:/papeldetrabajo/" + idPapelDeTrabajo;
	}

	public void agregarConclusion(Long idActuacion, String conclusion,
			HttpServletRequest request) {
		Actuacion actuacion = Actuacion.findActuacion(idActuacion);
		actuacion.setConclusionGeneral(conclusion);
		actuacion.merge();
		Util.registrarEntradaEnBitacora(actuacion,
				"Se modificó la conclusión de la actuación ",
				request.getRemoteAddr());
	}

	@RequestMapping(method = RequestMethod.POST)
	public String asignar(
			@Valid @ModelAttribute("actuacion") Actuacion actuacion,
			BindingResult result, ModelMap modelMap, SessionStatus status) {

		// ******** Creo las secciones básicas basándome en la biblioteca
		// seleccionada en la vista ********
		List<SeccionDefault> secciones = SeccionDefault
				.findSeccionDefaultsByBiblioteca(actuacion.getBiblioteca())
				.getResultList();
		for (int i = 0; i < secciones.size(); i++) {
			PapelDeTrabajo elPapelDeTrabajo = new PapelDeTrabajo();
			elPapelDeTrabajo.setCodigoActuacion(actuacion);
			elPapelDeTrabajo.setCodigo(secciones.get(i).getCodigo());
			elPapelDeTrabajo.setDescripcion(secciones.get(i).getDescripcion());
			if (secciones.get(i).getTipoSeccion() != null) {
				elPapelDeTrabajo.setTipoSeccion(secciones.get(i)
						.getTipoSeccion());
			}
			if (secciones.get(i).getSeccionDefault() != null) {
				List<PapelDeTrabajo> papeles = PapelDeTrabajo
						.findPapelDeTrabajoesByCodigoEqualsAndActuacion(
								secciones.get(i).getSeccionDefault()
										.getCodigo(), actuacion)
						.getResultList();
				elPapelDeTrabajo.setActividadActuacion(papeles.get(0));
			}
			elPapelDeTrabajo
					.setEstadoActividadActuacion(EstadoActividadActuacion
							.findEstadoActividadActuacion(new Long(1)));

			// **** Verifico si esa seccionDefault tiene archivos adjuntos ****
			List<ArchivoAdjunto> archivosAdjuntos = ArchivoAdjunto
					.findArchivoAdjuntoesBySeccionDefault(secciones.get(i))
					.getResultList();
			Set<ActividadActuacion> setActividad = new HashSet<ActividadActuacion>();
			setActividad.add(elPapelDeTrabajo);
			for (int j = 0; j < archivosAdjuntos.size(); j++) {
				ArchivoAdjunto archivoAdjunto = new ArchivoAdjunto();
				archivoAdjunto.setFiledata(archivosAdjuntos.get(j)
						.getFiledata());
				archivoAdjunto.setCodigo(archivosAdjuntos.get(j).getCodigo());
				archivoAdjunto.setNombre(archivosAdjuntos.get(j).getNombre());
				archivoAdjunto.setFilesize(archivosAdjuntos.get(j)
						.getFilesize());
				archivoAdjunto.setExtension(archivosAdjuntos.get(j)
						.getExtension());
				archivoAdjunto.setActividadActuacion(setActividad);
				archivoAdjunto.persist();

				// Creamos el historial de cambios
				HistorialCambios historial = new HistorialCambios();
				historial.setArchivoAdjunto(archivoAdjunto);
				historial.setAccion("Generación automática de plantilla");
				Calendar cal = Calendar.getInstance();
				historial.setFecha(cal.getTime());
				Object principal = SecurityContextHolder.getContext()
						.getAuthentication().getPrincipal();
				historial.setUsuario(((User) principal).getUsername()
						.toString());
				historial.persist();
			}
			// ****************************************************************
			elPapelDeTrabajo.persist();
		}
		// ********************************************
		actuacion.merge();
		long idActuacion = actuacion.getId();

		status.setComplete();
		return "redirect:/actuacion/" + idActuacion;
	}

	@RequestMapping(value = "/actuacion", method = RequestMethod.GET)
	public String list(ModelMap modelMap) {
		Util util = new Util();
		Calendar FechaActual = Calendar.getInstance();
		int mes = FechaActual.get(Calendar.MONTH);
		int a = FechaActual.get(Calendar.YEAR);
		modelMap.addAttribute("actuacions",
				Actuacion.findActuacionsByRif(util.traerIdRif())
						.getResultList());
		modelMap.addAttribute("fechaActual", mes + 1);
		modelMap.addAttribute("anoActual", a);

		return "actuacion/list";
	}

	@RequestMapping(value = "/actuacion/asignarbiblioteca/{id}/form", method = RequestMethod.GET)
	public String asignarForm(@PathVariable("id") Long id, ModelMap modelMap) {
		if (id == null) {
			throw new IllegalArgumentException("An Identifier is required");
		}
		Actuacion actuacion = Actuacion.findActuacion(id);

		modelMap.addAttribute("actuacion", actuacion);
		modelMap.addAttribute("bibliotecas", Biblioteca.findAllBibliotecas());

		return "actuacion/asignarbiblioteca";
	}
}
