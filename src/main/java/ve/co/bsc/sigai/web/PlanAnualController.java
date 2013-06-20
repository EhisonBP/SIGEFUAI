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
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.drools.runtime.process.ProcessInstance;
import org.jbpm.task.query.TaskSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import ve.co.bsc.sigai.domain.Actuacion;
import ve.co.bsc.sigai.domain.Auditor;
import ve.co.bsc.sigai.domain.EstadoPlan;
import ve.co.bsc.sigai.domain.ItemPlanificacionActuacion;
import ve.co.bsc.sigai.domain.OrganismoEnte;
import ve.co.bsc.sigai.domain.OtraActividad;
import ve.co.bsc.sigai.domain.PlanAnual;
import ve.co.bsc.sigai.domain.RolUsuario;
import ve.co.bsc.sigai.domain.Usuario;
import ve.co.bsc.sigai.service.HumanTaskService;
import ve.co.bsc.sigai.service.JbpmService;
import ve.co.bsc.sigai.service.exception.ServiceTemporarilyUnavailableException;
import ve.co.bsc.util.Util;

/*
 * Controlador para plan anual (CRUD) generado por ROO
 * Recibe todas las solicitudes a URLs con el patron
 * "/plananual/**"
 */
@RooWebScaffold(path = "plananual", automaticallyMaintainView = true, formBackingObject = PlanAnual.class)
@RequestMapping("/plananual/**")
@SessionAttributes({ "planAnual" })
@Controller
public class PlanAnualController {

	Logger logger = Logger.getLogger(ActuacionController.class);
	@Autowired
	private JbpmService jbpmService;
	@Autowired
	private HumanTaskService humanTaskService;

	@RequestMapping(value = "/plananual", method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			ModelMap modelMap) {
		Util util = new Util();
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			modelMap.addAttribute("plananuals", PlanAnual.findPlanAnualEntries(
					page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
			float nrOfPages = (float) PlanAnual.countPlanAnuals() / sizeNo;
			modelMap.addAttribute(
					"maxPages",
					(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
							: nrOfPages));
		} else {
			if (util.rolUsuarioLoggeado().equals("[UNIDAD_COORDINADOR]")
					|| util.rolUsuarioLoggeado().equals("[UNIDAD_AUDITOR]")
					|| util.rolUsuarioLoggeado().equals("[UNIDAD_GERENTE]")) {
				if (util.traerIdRif() != null) {
					modelMap.addAttribute("plananuals", PlanAnual
							.findPlanAnualsByRif(util.traerIdRif())
							.getResultList());
				}
			} else {
				modelMap.addAttribute("plananuals",
						PlanAnual.findAllPlanAnuals());
			}
		}
		return "plananual/list";
	}

	@RequestMapping(value = "/plananual", method = RequestMethod.POST)
	public String create(
			@Valid @ModelAttribute("planAnual") PlanAnual planAnual,
			BindingResult result, ModelMap modelMap, SessionStatus status,
			HttpServletRequest request) {
		Util util = new Util();

		planAnual.setRif(util.traerIdRif());

		// Se asigna el estadoPlan por defecto que es "En Proceso" por eso se
		// cablea a id 1
		EstadoPlan estadoPlanEnProceso = EstadoPlan.findEstadoPlan(new Long(1));
		planAnual.setEstadoPlan(estadoPlanEnProceso);

		if (planAnual == null) {
			throw new IllegalArgumentException("A planAnual is required");
		}
		if (result.hasErrors()) {
			modelMap.addAttribute("planAnual", planAnual);
			modelMap.addAttribute("estadoplans",
					EstadoPlan.findAllEstadoPlans());
			return "plananual/create";
		}

		planAnual.persist();
		Util.registrarEntradaEnBitacora(planAnual, "Se creó el Plan Anual ",
				request.getRemoteAddr());
		status.setComplete();

		return "redirect:/plananual/" + planAnual.getId();
	}

	@RequestMapping(value = "/plananual/form", method = RequestMethod.GET)
	public String createForm(ModelMap modelMap) {

		modelMap.addAttribute("planAnual", new PlanAnual());
		modelMap.addAttribute("estadoplans", EstadoPlan.findAllEstadoPlans());
		modelMap.addAttribute("organismoentes",
				OrganismoEnte.findAllOrganismoEntes());
		return "plananual/create";
	}

	@RequestMapping(value = "/plananual/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") Long id, ModelMap modelMap) {
		if (id == null) {
			throw new IllegalArgumentException("An Identifier is required");
		}

		// Se arma la lista "actuacions" con las actuaciones que estén
		// relacionadas con el planAnual de id suministrado
		PlanAnual planAnual = PlanAnual.findPlanAnual(id);
		List<Actuacion> actuacions = new ArrayList<Actuacion>();
		;
		List<OtraActividad> otrasActividades = new ArrayList<OtraActividad>();
		;
		List<ItemPlanificacionActuacion> items = ItemPlanificacionActuacion
				.findItemPlanificacionActuacionsByPlanAnual(planAnual)
				.getResultList();
		for (int i = 0; i < items.size(); i++) {
			if (((ItemPlanificacionActuacion) items.get(i)).getActuacion() != null) {
				actuacions.add(((ItemPlanificacionActuacion) items.get(i))
						.getActuacion());
			}
			if (((ItemPlanificacionActuacion) items.get(i)).getOtraActividad() != null) {
				otrasActividades
						.add(((ItemPlanificacionActuacion) items.get(i))
								.getOtraActividad());
			}
		}

		// Listo
		List<Auditor> todos = Auditor.findAllAuditors();
		List<Auditor> ListCoordinadores = new LinkedList<Auditor>();
		List<Auditor> ListAuditores = new LinkedList<Auditor>();
		Util util = new Util();

		if (util.rolUsuarioLoggeado().equals("[UNIDAD_COORDINADOR]")
				|| util.rolUsuarioLoggeado().equals("[UNIDAD_AUDITOR]")
				|| util.rolUsuarioLoggeado().equals("[UNIDAD_GERENTE]")) {

			for (Auditor a : todos) {
				List<Usuario> usuarios = Usuario.findUsuariosByLogin(
						a.getLogin()).getResultList();
				for (Usuario u : usuarios) {
					for (RolUsuario r : u.getRoles()) {
						if (r.getNombre().equals("ROLE_UNIDAD_COORDINADOR")) {
							if (a.getId_OrganismoEnte().getRif() == util
									.obtenerRif()) {
								ListCoordinadores.add(a);// Lista los
															// Coordinadores
								break;
							}
						} else {
							if (r.getNombre().equals("ROLE_UNIDAD_AUDITOR")) {
								if (a.getId_OrganismoEnte().getRif() == util
										.obtenerRif()) {
									ListAuditores.add(a);// Listo Los Auditores
									break;
								}
							}
						}
					}
				}
			}
		}

		Calendar FechaActual = Calendar.getInstance();
		int mes = FechaActual.get(Calendar.MONTH);
		int a = FechaActual.get(Calendar.YEAR);

		// Listo Los Coordinadores
		modelMap.addAttribute("coordinadores", ListCoordinadores);
		// Lista los Coordinadores
		/****   ****/
		// Listo Los Auditores
		modelMap.addAttribute("auditores", ListAuditores);
		// Listo Los Auditores
		modelMap.addAttribute("actuacions", actuacions);
		modelMap.addAttribute("otrasActividades", otrasActividades);
		modelMap.addAttribute("planAnual", planAnual);
		modelMap.addAttribute("objetoComentable", planAnual);
		modelMap.addAttribute("fechaActual", mes + 1);
		modelMap.addAttribute("anoActual", a);

		try {
			List<TaskSummary> tasks = humanTaskService
					.getCorrelatedTasksAssignedAsPotentialOwnerToCurrentUser(planAnual);
			modelMap.addAttribute("tasks", tasks);
		} catch (ServiceTemporarilyUnavailableException e) {
			modelMap.addAttribute("tasks", new LinkedList<TaskSummary>());
			modelMap.addAttribute("unavailable", e);
		}

		logger.debug("solicitando revision de plan en motor de reglas");
		jbpmService.executeRulesStateless(planAnual);
		logger.debug("revision de plan anual en motor de reglas listo");
		modelMap.addAttribute("accionesPermitidas",
				planAnual.getAccionesPermitidas());

		return "plananual/show";
	}

	@RequestMapping(method = RequestMethod.PUT)
	public String update(
			@Valid @ModelAttribute("planAnual") PlanAnual planAnual,
			BindingResult result, ModelMap modelMap, SessionStatus status,
			HttpServletRequest request) {

		if (planAnual == null) {
			throw new IllegalArgumentException("A planAnual is required");
		}
		if (result.hasErrors()) {
			modelMap.addAttribute("planAnual", planAnual);
			modelMap.addAttribute("estadoplans",
					EstadoPlan.findAllEstadoPlans());
			return "plananual/update";
		}

		planAnual.merge();
		Util.registrarEntradaEnBitacora(planAnual,
				"Se modificó el plan anual ", request.getRemoteAddr());
		status.setComplete();
		return "redirect:/plananual/" + planAnual.getId();
	}

	@RequestMapping(value = "/plananual/{id}/form", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, ModelMap modelMap) {

		if (id == null) {
			throw new IllegalArgumentException("An Identifier is required");
		}
		modelMap.addAttribute("planAnual", PlanAnual.findPlanAnual(id));
		modelMap.addAttribute("estadoplans", EstadoPlan.findAllEstadoPlans());
		return "plananual/update";
	}

	@RequestMapping(value = "/plananual/actualizarEstatus/{id}", method = RequestMethod.POST)
	public String actualizarEstatus(@Valid PlanAnual planAnual,
			BindingResult result, ModelMap modelMap, HttpServletRequest request) {

		if (planAnual == null) {
			throw new IllegalArgumentException("A planAnual is required");
		}
		if (result.hasErrors()) {
			modelMap.addAttribute("planAnual", planAnual);
			modelMap.addAttribute("estadoplans",
					EstadoPlan.findAllEstadoPlans());
			return "plananual/actualizarEstatus";
		}
		planAnual.merge();
		Util.registrarEntradaEnBitacora(planAnual,
				"Se actualizó el estatus del plan anual ",
				request.getRemoteAddr());

		return "redirect:/plananual/" + planAnual.getId();
	}

	@RequestMapping(value = "/plananual/actualizarEstatus/{id}/form", method = RequestMethod.GET)
	public String actualizarEstatusForm(@PathVariable("id") Long id,
			ModelMap modelMap) {

		if (id == null) {
			throw new IllegalArgumentException("An Identifier is required");
		}
		modelMap.addAttribute("planAnual", PlanAnual.findPlanAnual(id));
		modelMap.addAttribute("estadoplans", EstadoPlan.findAllEstadoPlans());

		return "plananual/actualizarEstatus";
	}

	@RequestMapping(value = "/plananual/duplicarPlan/{id}", method = RequestMethod.GET)
	public String duplicarPlanForm(@PathVariable("id") Long id,
			ModelMap modelMap, HttpServletRequest request) {

		if (id == null) {
			throw new IllegalArgumentException("An Identifier is required");
		}

		// *************** DUPLICANDO PLANANUAL ***************
		PlanAnual planOriginal = PlanAnual.findPlanAnual(id);
		PlanAnual planNuevo = (PlanAnual) planOriginal.clone();
		planNuevo.setEstadoPlan(EstadoPlan.findEstadoPlan(new Long(1)));
		planNuevo.persist();
		Util.registrarEntradaEnBitacora(planNuevo, "A partir del plan "
				+ planOriginal.toStringLimitado() + "se creó el plan ",
				request.getRemoteAddr());
		modelMap.addAttribute("planAnual", planNuevo);
		// ****************************************************

		// ********************** NUEVO ARSEN ******************
		// inicia el workflow para este plan anual
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("plan", planNuevo);
		ProcessInstance proc = jbpmService.startProcess(
				"ve.co.bsc.sigai.domain.bpm.lifecycle.PlanAnual", params,
				planNuevo);
		// *****************************************************

		// ****** DUPLICANDO ITEMPLANIFICACIONACTUACIONS ******
		List<ItemPlanificacionActuacion> itemsOriginales = ItemPlanificacionActuacion
				.findItemPlanificacionActuacionsByPlanAnual(planOriginal)
				.getResultList();
		for (int i = 0; i < itemsOriginales.size(); i++) {
			// **** DUPLICANDO ITEMPLANIFICACIONACTUACIONS ****
			ItemPlanificacionActuacion itemOriginal = itemsOriginales.get(i);
			ItemPlanificacionActuacion itemNuevo = (ItemPlanificacionActuacion) itemOriginal
					.clone();
			itemNuevo.setPlanAnual(planNuevo);
			// ************************************************
			if (itemOriginal.getActuacion() != null) {
				// *********** DUPLICANDO ACTUACION ***********
				Actuacion actuacionOriginal = itemOriginal.getActuacion();
				Actuacion actuacionNueva = (Actuacion) actuacionOriginal
						.clone();
				actuacionNueva.persist();
				Util.registrarEntradaEnBitacora(
						actuacionNueva,
						"A partir de la actuación "
								+ actuacionOriginal.toStringLimitado()
								+ "se creó el actuacion ",
						request.getRemoteAddr());
				itemNuevo.setActuacion(actuacionNueva);
				// ********************************************

				// *****************NUEVO ARSEN*****************
				// inicia el workflow para esta actuacion
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("actuacion", actuacionNueva);
				ProcessInstance p = jbpmService.startProcess(
						"ve.co.bsc.sigai.domain.bpm.lifecycle.Actuacion",
						parameters, actuacionNueva);
				// *********************************************
			}

			if (itemOriginal.getOtraActividad() != null) {
				// *********** DUPLICANDO OTRAACTIVIDAD ***********
				OtraActividad otraActividadOriginal = itemOriginal
						.getOtraActividad();
				OtraActividad otraActividadNueva = (OtraActividad) otraActividadOriginal
						.clone();
				otraActividadNueva.setDescripcionCorta(otraActividadOriginal
						.getDescripcionCorta());
				otraActividadNueva.persist();
				itemNuevo.setOtraActividad(otraActividadNueva);
				// ********************************************
			}

			itemNuevo.persist();

			// Map<String, Object> parameters = new HashMap<String, Object>();
			// parameters.put("plan", itemNuevo);
			// ProcessInstance p =
			// jbpmService.startProcess("ve.co.bsc.sigai.domain.bpm.lifecycle.PlanAnual",
			// parameters, itemNuevo);

			Util.registrarEntradaEnBitacora(
					itemNuevo,
					"A partir de la planificacion  "
							+ itemOriginal.toStringLimitado()
							+ "se creó la planificacion ",
					request.getRemoteAddr());
		}

		return "plananual/duplicarPlan";
	}

	@RequestMapping(value = "/plananual/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("id") Long id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size) {
		if (id == null) {
			throw new IllegalArgumentException("An Identifier is required");
		}
		// try {
		PlanAnual.findPlanAnual(id).remove();
		// }catch(Exception e)
		// {
		// logger.debug("La excepcion al eliminar es: " + e);
		// throw e;
		// }
		return "redirect:/plananual?page="
				+ ((page == null) ? "1" : page.toString()) + "&size="
				+ ((size == null) ? "10" : size.toString());
	}
}