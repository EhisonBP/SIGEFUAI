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
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import ve.co.bsc.sigai.domain.ActividadAuditor;
import ve.co.bsc.sigai.domain.ActividadGenerica;
import ve.co.bsc.sigai.domain.Actuacion;
import ve.co.bsc.sigai.domain.Auditor;
import ve.co.bsc.sigai.domain.CargoAuditor;
import ve.co.bsc.sigai.domain.ItemPlanificacionActuacion;
import ve.co.bsc.sigai.domain.OcupacionAuditor;
import ve.co.bsc.sigai.domain.OtraActividad;
import ve.co.bsc.sigai.domain.PlanAnual;
import ve.co.bsc.sigai.domain.RolUsuario;
import ve.co.bsc.sigai.domain.Usuario;
import ve.co.bsc.sigai.form.OcupacionAuditorBusquedaForm;
import ve.co.bsc.util.Util;

/*
 * Controlador para ocupacion auditor (CRUD) generado por ROO y
 * particularizado. Recibe todas las solicitudes a URLs con el patron
 * "/ocupacionauditor/**"
 */
@RooWebScaffold(path = "ocupacionauditor", automaticallyMaintainView = true, formBackingObject = OcupacionAuditor.class)
@RequestMapping("/ocupacionauditor/**")
@SessionAttributes({ "ocupacionAuditor", "idActuacion" })
@Controller
public class OcupacionAuditorController {

	Logger logger = Logger.getLogger(OcupacionAuditorController.class);
	private static long idP = 0;

	@RequestMapping(value = "/ocupacionauditor", method = RequestMethod.POST)
	public String create(
			@Valid @ModelAttribute("ocupacionAuditor") OcupacionAuditor ocupacionAuditor,
			@Valid @ModelAttribute("idActuacion") long idActuacion,
			BindingResult result, ModelMap modelMap, SessionStatus status,
			HttpServletRequest request) {
		Date fInicio = ocupacionAuditor.getInicio();
		Date fFin = ocupacionAuditor.getFin();
		PlanAnual plan = PlanAnual.findPlanAnual(idP);

		if (ocupacionAuditor == null) {
			throw new IllegalArgumentException("A ocupacionAuditor is required");
		}

		// Validacion de las fechas
		if (fInicio != null && fFin != null) {
			if (fInicio.after(fFin)) {
				result.addError(new ObjectError("fin",
						"La fecha de Finalizacion no puede ser menor a la fecha de Inicio"));
			}
			// Verificacion que la fecha de fin no sea igual que lafecha de
			// inicio
			if (fInicio.equals(fFin)) {
				result.addError(new ObjectError("fin",
						"La fecha de fin no puede ser igual a la fecha de inicio"));
			}
		}

		if (result.hasErrors()) {
			OcupacionAuditor miOcupacion = new OcupacionAuditor();
			ocupacionAuditor.setPlanAnual(plan);

			if (request.getParameter("idAuditor") != null) {

				Auditor auditor = Auditor.findAuditor(new Long(request
						.getParameter("idAuditor")));
				ocupacionAuditor.setAuditor(auditor);
				modelMap.addAttribute("auditorSeleccionado", "true");
				modelMap.addAttribute("auditor", auditor);

			} else {

				// Metodo para traer los usuario de la UAI de una institucion en
				// especifico
				Util util = new Util();
				Query query = Auditor.findAuditorsById_OrganismoEnte(util
						.traerIdRif());
				List<Auditor> todos = query.getResultList();
				List<Auditor> losQueSon = new LinkedList<Auditor>();
				for (Auditor a : todos) {
					List<Usuario> usuarios = Usuario.findUsuariosByLogin(
							a.getLogin()).getResultList();
					for (Usuario u : usuarios) {
						for (RolUsuario r : u.getRoles()) {
							if (r.getNombre().equals("ROLE_UNIDAD_COORDINADOR")) {
								losQueSon.add(a);
								break;
							} else if (r.getNombre().equals(
									"ROLE_UNIDAD_AUDITOR")) {
								losQueSon.add(a);
								break;
							} else if (r.getNombre().equals(
									"ROLE_UNIDAD_GERENTE")) {
								losQueSon.add(a);
								break;
							}
						}
					}
				}

				modelMap.addAttribute("auditorSeleccionado", "false");
				modelMap.addAttribute("auditors", losQueSon);

			}

			if (request.getParameter("idActuacion") != null) {
				modelMap.addAttribute("idActuacion",
						new Long(request.getParameter("idActuacion")));
				Actuacion actuacion = Actuacion.findActuacion(new Long(request
						.getParameter("idActuacion")));
				ocupacionAuditor
						.setActividadAuditor((ActividadAuditor) actuacion);
				modelMap.addAttribute("actividadSeleccionada", "true");
				modelMap.addAttribute("actividadAuditor",
						(ActividadAuditor) actuacion);

			} else {
				modelMap.addAttribute("idActuacion", 0);
				modelMap.addAttribute("actividadSeleccionada", "false");
				// Busco las ActividadAuditor del plan anual "plan"
				List<ActividadAuditor> actividadauditors = new ArrayList<ActividadAuditor>();
				List<ActividadAuditor> actividadesAll = ActividadAuditor
						.findAllActividadAuditors();
				for (int i = 0; i < actividadesAll.size(); i++) {
					if (actividadesAll.get(i) instanceof ActividadGenerica) {
						actividadauditors.add(actividadesAll.get(i));
					}
					if (actividadesAll.get(i) instanceof Actuacion) {
						List<ItemPlanificacionActuacion> items = ItemPlanificacionActuacion
								.findItemPlanificacionActuacionsByActuacion(
										(Actuacion) actividadesAll.get(i))
								.getResultList();
						for (int j = 0; j < items.size(); j++) {
							if (items.get(j).getPlanAnual() == plan) {
								actividadauditors.add(actividadesAll.get(i));
							}
						}
					}
					if (actividadesAll.get(i) instanceof OtraActividad) {
						List<ItemPlanificacionActuacion> items = ItemPlanificacionActuacion
								.findItemPlanificacionActuacionsByOtraActividad(
										(OtraActividad) actividadesAll.get(i))
								.getResultList();
						for (int j = 0; j < items.size(); j++) {
							if (items.get(j).getPlanAnual() == plan) {
								actividadauditors.add(actividadesAll.get(i));
							}
						}
					}
				}

				modelMap.addAttribute("actividadauditors", actividadauditors);
			}

			ocupacionAuditor.setPorcentajeCompletado(0);

			modelMap.addAttribute("ocupacionAuditor", ocupacionAuditor);
			modelMap.addAttribute(
					"ocupacionAuditor_inicio_date_format",
					org.joda.time.format.DateTimeFormat
							.patternForStyle(
									"S-",
									org.springframework.context.i18n.LocaleContextHolder
											.getLocale()));
			modelMap.addAttribute(
					"ocupacionAuditor_fin_date_format",
					org.joda.time.format.DateTimeFormat
							.patternForStyle(
									"S-",
									org.springframework.context.i18n.LocaleContextHolder
											.getLocale()));
			modelMap.addAttribute("plan", plan);
			return "ocupacionauditor/create";
		}

		ocupacionAuditor.persist();
		Util.registrarEntradaEnBitacora(ocupacionAuditor,
				"Se creó la Actividad de Auditor ", request.getRemoteAddr());
		status.setComplete();
		// return "redirect:/ocupacionauditor/" + ocupacionAuditor.getId();

		if (idActuacion == 0) {
			return "redirect:/ocupacionauditor?idPlan="
					+ ocupacionAuditor.getPlanAnual().getId() + "&auditor="
					+ ocupacionAuditor.getAuditor().getId();
		} else {
			return "redirect:/actuacion/" + idActuacion;
		}

	}

	@RequestMapping(value = "/ocupacionauditor/form", method = RequestMethod.GET)
	public String createForm(ModelMap modelMap,
			@RequestParam("idPlan") long idPlan, HttpServletRequest hsr) {

		idP = 0;
		idP = idPlan;
		PlanAnual plan = PlanAnual.findPlanAnual(idPlan);
		OcupacionAuditor miOcupacion = new OcupacionAuditor();
		miOcupacion.setPlanAnual(plan);

		if (hsr.getParameter("idAuditor") != null) {

			Auditor auditor = Auditor.findAuditor(new Long(hsr
					.getParameter("idAuditor")));
			miOcupacion.setAuditor(auditor);
			modelMap.addAttribute("auditorSeleccionado", "true");
			modelMap.addAttribute("auditor", auditor);

		} else {

			// Metodo para traer los usuario de la UAI de una institucion en
			// especifico
			Util util = new Util();
			Query query = Auditor.findAuditorsById_OrganismoEnte(util
					.traerIdRif());
			List<Auditor> todos = query.getResultList();
			List<Auditor> losQueSon = new LinkedList<Auditor>();
			for (Auditor a : todos) {
				List<Usuario> usuarios = Usuario.findUsuariosByLogin(
						a.getLogin()).getResultList();
				for (Usuario u : usuarios) {
					for (RolUsuario r : u.getRoles()) {
						if (r.getNombre().equals("ROLE_UNIDAD_COORDINADOR")) {
							losQueSon.add(a);
							break;
						} else if (r.getNombre().equals("ROLE_UNIDAD_AUDITOR")) {
							losQueSon.add(a);
							break;
						} else if (r.getNombre().equals("ROLE_UNIDAD_GERENTE")) {
							losQueSon.add(a);
							break;
						}
					}
				}
			}

			modelMap.addAttribute("auditorSeleccionado", "false");
			modelMap.addAttribute("auditors", losQueSon);

		}

		if (hsr.getParameter("idActuacion") != null) {
			modelMap.addAttribute("idActuacion",
					new Long(hsr.getParameter("idActuacion")));
			Actuacion actuacion = Actuacion.findActuacion(new Long(hsr
					.getParameter("idActuacion")));
			miOcupacion.setActividadAuditor((ActividadAuditor) actuacion);
			modelMap.addAttribute("actividadSeleccionada", "true");
			modelMap.addAttribute("actividadAuditor",
					(ActividadAuditor) actuacion);

		} else {
			modelMap.addAttribute("idActuacion", 0);
			modelMap.addAttribute("actividadSeleccionada", "false");
			// Busco las ActividadAuditor del plan anual "plan"
			List<ActividadAuditor> actividadauditors = new ArrayList<ActividadAuditor>();
			List<ActividadAuditor> actividadesAll = ActividadAuditor
					.findAllActividadAuditors();
			for (int i = 0; i < actividadesAll.size(); i++) {
				if (actividadesAll.get(i) instanceof ActividadGenerica) {
					actividadauditors.add(actividadesAll.get(i));
				}
				if (actividadesAll.get(i) instanceof Actuacion) {
					List<ItemPlanificacionActuacion> items = ItemPlanificacionActuacion
							.findItemPlanificacionActuacionsByActuacion(
									(Actuacion) actividadesAll.get(i))
							.getResultList();
					for (int j = 0; j < items.size(); j++) {
						if (items.get(j).getPlanAnual() == plan) {
							actividadauditors.add(actividadesAll.get(i));
						}
					}
				}
				if (actividadesAll.get(i) instanceof OtraActividad) {
					List<ItemPlanificacionActuacion> items = ItemPlanificacionActuacion
							.findItemPlanificacionActuacionsByOtraActividad(
									(OtraActividad) actividadesAll.get(i))
							.getResultList();
					for (int j = 0; j < items.size(); j++) {
						if (items.get(j).getPlanAnual() == plan) {
							actividadauditors.add(actividadesAll.get(i));
						}
					}
				}
			}

			modelMap.addAttribute("actividadauditors", actividadauditors);
		}

		miOcupacion.setPorcentajeCompletado(0);

		modelMap.addAttribute("ocupacionAuditor", miOcupacion);
		modelMap.addAttribute("cargoauditors",
				CargoAuditor.findAllCargoAuditors());
		modelMap.addAttribute("ocupacionAuditor_inicio_date_format",
				org.joda.time.format.DateTimeFormat.patternForStyle("S-",
						org.springframework.context.i18n.LocaleContextHolder
								.getLocale()));
		modelMap.addAttribute("ocupacionAuditor_fin_date_format",
				org.joda.time.format.DateTimeFormat.patternForStyle("S-",
						org.springframework.context.i18n.LocaleContextHolder
								.getLocale()));
		modelMap.addAttribute("plan", plan);
		return "ocupacionauditor/create";
	}

	@RequestMapping(value = "/ocupacionauditor", method = RequestMethod.GET)
	public String list(
			@ModelAttribute("OcupacionForm") OcupacionAuditorBusquedaForm form,
			ModelMap modelMap, @RequestParam("idPlan") long idPlan) {

		// Metodo para traer los usuario de la UAI de una institucion en
		// especifico
		Util util = new Util();
		Query query = Auditor.findAuditorsById_OrganismoEnte(util.traerIdRif());
		List<Auditor> todos = query.getResultList();
		List<Auditor> losQueSon = new LinkedList<Auditor>();
		for (Auditor a : todos) {
			List<Usuario> usuarios = Usuario.findUsuariosByLogin(a.getLogin())
					.getResultList();
			for (Usuario u : usuarios) {
				for (RolUsuario r : u.getRoles()) {
					if (r.getNombre().equals("ROLE_UNIDAD_COORDINADOR")) {
						losQueSon.add(a);
						break;
					} else if (r.getNombre().equals("ROLE_UNIDAD_AUDITOR")) {
						losQueSon.add(a);
						break;
					} else if (r.getNombre().equals("ROLE_UNIDAD_GERENTE")) {
						losQueSon.add(a);
						break;
					}
				}
			}
		}

		PlanAnual plan = PlanAnual.findPlanAnual(idPlan);
		modelMap.addAttribute("auditors", losQueSon);
		modelMap.addAttribute("planAnual", plan);
		// modelMap.addAttribute("planAnual_inicio_date_format",
		// org.joda.time.format.DateTimeFormat.patternForStyle("S-",
		// org.springframework.context.i18n.LocaleContextHolder.getLocale()));
		// modelMap.addAttribute("planAnual_fin_date_format",
		// org.joda.time.format.DateTimeFormat.patternForStyle("S-",
		// org.springframework.context.i18n.LocaleContextHolder.getLocale()));

		if (form.getAuditor() != null) {
			Auditor auditor = form.getAuditor();
			Query queryOcupaciones = OcupacionAuditor
					.findOcupacionAuditorsByAuditorAndPlanAnual(auditor, plan);
			List<OcupacionAuditor> ocupaciones = queryOcupaciones
					.getResultList();
			modelMap.addAttribute("ocupacionauditors", ocupaciones);
			modelMap.addAttribute(
					"ocupacion_inicio_date_format",
					org.joda.time.format.DateTimeFormat
							.patternForStyle(
									"S-",
									org.springframework.context.i18n.LocaleContextHolder
											.getLocale()));
			modelMap.addAttribute(
					"ocupacion_fin_date_format",
					org.joda.time.format.DateTimeFormat
							.patternForStyle(
									"S-",
									org.springframework.context.i18n.LocaleContextHolder
											.getLocale()));
			modelMap.addAttribute("auditor", auditor);
		}

		return "ocupacionauditor/list";
	}

	@RequestMapping(method = RequestMethod.PUT)
	public String update(
			@Valid @ModelAttribute("ocupacionAuditor") OcupacionAuditor ocupacionAuditor,
			@Valid @ModelAttribute("idActuacion") long idActuacion,
			BindingResult result, ModelMap modelMap, SessionStatus status,
			HttpServletRequest request) {
		if (ocupacionAuditor == null) {
			throw new IllegalArgumentException("A ocupacionAuditor is required");
		}

		// Metodo para traer los usuario de la UAI de una institucion en
		// especifico
		Util util = new Util();
		Query query = Auditor.findAuditorsById_OrganismoEnte(util.traerIdRif());
		List<Auditor> todos = query.getResultList();
		List<Auditor> losQueSon = new LinkedList<Auditor>();
		for (Auditor a : todos) {
			List<Usuario> usuarios = Usuario.findUsuariosByLogin(a.getLogin())
					.getResultList();
			for (Usuario u : usuarios) {
				for (RolUsuario r : u.getRoles()) {
					if (r.getNombre().equals("ROLE_UNIDAD_COORDINADOR")) {
						losQueSon.add(a);
						break;
					} else if (r.getNombre().equals("ROLE_UNIDAD_AUDITOR")) {
						losQueSon.add(a);
						break;
					} else if (r.getNombre().equals("ROLE_UNIDAD_GERENTE")) {
						losQueSon.add(a);
						break;
					}
				}
			}
		}

		if (result.hasErrors()) {
			modelMap.addAttribute("ocupacionAuditor", ocupacionAuditor);
			modelMap.addAttribute("actividadauditors",
					ActividadAuditor.findAllActividadAuditors());
			modelMap.addAttribute("auditors", losQueSon);
			modelMap.addAttribute("cargoauditors",
					CargoAuditor.findAllCargoAuditors());
			modelMap.addAttribute("plananuals", PlanAnual.findAllPlanAnuals());
			modelMap.addAttribute(
					"ocupacionAuditor_inicio_date_format",
					org.joda.time.format.DateTimeFormat
							.patternForStyle(
									"S-",
									org.springframework.context.i18n.LocaleContextHolder
											.getLocale()));
			modelMap.addAttribute(
					"ocupacionAuditor_fin_date_format",
					org.joda.time.format.DateTimeFormat
							.patternForStyle(
									"S-",
									org.springframework.context.i18n.LocaleContextHolder
											.getLocale()));
			return "ocupacionauditor/update";
		}
		ocupacionAuditor.merge();
		Util.registrarEntradaEnBitacora(ocupacionAuditor,
				"Se modificó la Actividad de Auditor ", request.getRemoteAddr());
		status.setComplete();

		if (idActuacion == 0) {
			return "redirect:/ocupacionauditor/" + ocupacionAuditor.getId();
		} else {
			return "redirect:/actuacion/" + idActuacion;
		}
	}

	@RequestMapping(value = "/ocupacionauditor/{id}/form", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, ModelMap modelMap,
			HttpServletRequest hsr) {
		if (id == null) {
			throw new IllegalArgumentException("An Identifier is required");
		}
		if (hsr.getParameter("idActuacion") != null) {
			modelMap.addAttribute("idActuacion",
					new Long(hsr.getParameter("idActuacion")));
		} else {
			modelMap.addAttribute("idActuacion", 0);
		}

		// Metodo para traer los usuario de la UAI de una institucion en
		// especifico
		Util util = new Util();
		Query query = Auditor.findAuditorsById_OrganismoEnte(util.traerIdRif());
		List<Auditor> todos = query.getResultList();
		List<Auditor> losQueSon = new LinkedList<Auditor>();
		for (Auditor a : todos) {
			List<Usuario> usuarios = Usuario.findUsuariosByLogin(a.getLogin())
					.getResultList();
			for (Usuario u : usuarios) {
				for (RolUsuario r : u.getRoles()) {
					if (r.getNombre().equals("ROLE_UNIDAD_COORDINADOR")) {
						losQueSon.add(a);
						break;
					} else if (r.getNombre().equals("ROLE_UNIDAD_AUDITOR")) {
						losQueSon.add(a);
						break;
					} else if (r.getNombre().equals("ROLE_UNIDAD_GERENTE")) {
						losQueSon.add(a);
						break;
					}
				}
			}
		}

		OcupacionAuditor ocupacionAuditor = OcupacionAuditor
				.findOcupacionAuditor(id);

		modelMap.addAttribute("ocupacionAuditor", ocupacionAuditor);
		modelMap.addAttribute("actividadauditors",
				ActividadAuditor.findAllActividadAuditors());
		modelMap.addAttribute("auditors", losQueSon);
		modelMap.addAttribute("cargoauditors",
				CargoAuditor.findAllCargoAuditors());
		modelMap.addAttribute("plananuals", PlanAnual.findAllPlanAnuals());
		modelMap.addAttribute("ocupacionAuditor_inicio_date_format",
				org.joda.time.format.DateTimeFormat.patternForStyle("S-",
						org.springframework.context.i18n.LocaleContextHolder
								.getLocale()));
		modelMap.addAttribute("ocupacionAuditor_fin_date_format",
				org.joda.time.format.DateTimeFormat.patternForStyle("S-",
						org.springframework.context.i18n.LocaleContextHolder
								.getLocale()));
		return "ocupacionauditor/update";
	}

	@RequestMapping(value = "/ocupacionauditor/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") Long id, ModelMap modelMap) {

		if (id == null)
			throw new IllegalArgumentException("An Identifier is required");
		OcupacionAuditor ocupacionAuditor = OcupacionAuditor
				.findOcupacionAuditor(id);

		ActividadAuditor actividad = ocupacionAuditor.getActividadAuditor();
		String tipoDeActividad = "";
		if (actividad instanceof Actuacion) {
			tipoDeActividad = "actuacion";
		} else if (actividad instanceof ActividadGenerica) {
			tipoDeActividad = "actividadgenerica";
		} else if (actividad instanceof OtraActividad) {
			tipoDeActividad = "otraactividad";
		}

		modelMap.addAttribute("tipoDeActividad", tipoDeActividad);
		modelMap.addAttribute("ocupacionAuditor_inicio_date_format",
				org.joda.time.format.DateTimeFormat.patternForStyle("S-",
						org.springframework.context.i18n.LocaleContextHolder
								.getLocale()));
		modelMap.addAttribute("ocupacionAuditor_fin_date_format",
				org.joda.time.format.DateTimeFormat.patternForStyle("S-",
						org.springframework.context.i18n.LocaleContextHolder
								.getLocale()));
		modelMap.addAttribute("ocupacionAuditor", ocupacionAuditor);
		modelMap.addAttribute("objetoComentable", ocupacionAuditor);

		return "ocupacionauditor/show";
	}

	@RequestMapping(value = "/ocupacionauditor/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("id") Long id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			HttpServletRequest hsr) {

		if (id == null) {
			throw new IllegalArgumentException("An Identifier is required");
		}

		OcupacionAuditor ocupacion = OcupacionAuditor.findOcupacionAuditor(id);
		String urlReturn = "redirect:/ocupacionauditor/?idPlan="
				+ ocupacion.getPlanAnual().getId() + "&auditor="
				+ ocupacion.getAuditor().getId();
		ocupacion.remove();

		if (hsr.getParameter("idActuacion") != null) {
			return "redirect:/actuacion/" + hsr.getParameter("idActuacion");
		} else {
			return urlReturn;
		}

	}
}
