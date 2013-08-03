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
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
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
import ve.co.bsc.sigai.domain.ActividadGeneral;
import ve.co.bsc.sigai.domain.Actuacion;
import ve.co.bsc.sigai.domain.Auditor;
import ve.co.bsc.sigai.domain.AvanceActuacion;
import ve.co.bsc.sigai.domain.EstadoActividadActuacion;
import ve.co.bsc.sigai.domain.ItemPlanificacionActuacion;
import ve.co.bsc.sigai.domain.ObjetivoEspecifico;
import ve.co.bsc.sigai.domain.RolUsuario;
import ve.co.bsc.sigai.domain.Usuario;
import ve.co.bsc.sigai.service.JbpmService;
import ve.co.bsc.util.Util;

@RooWebScaffold(path = "actividadgeneral", automaticallyMaintainView = true, formBackingObject = ActividadGeneral.class)
@RequestMapping("/actividadgeneral/**")
@SessionAttributes({ "idActuacion", "actividadGeneral" })
@Controller
public class ActividadGeneralController {

	private static final Logger logger = Logger
			.getLogger(ActividadGeneralController.class);

	@Autowired
	private JbpmService jbpmService;

	@RequestMapping(value = "/actividadgeneral/form", method = RequestMethod.GET)
	public String createForm(@RequestParam("idActuacion") long idActuacion,
			@RequestParam("idActividad") long idActividadPadre,
			HttpServletRequest hsr, ModelMap modelMap) {

		ActividadGeneral laActividad = new ActividadGeneral();

		if (hsr.getParameter("idActividad") != null) {
			// Entra aqui si tiene un padre
			ActividadActuacion miPadre = ActividadActuacion
					.findActividadActuacion(new Long(hsr
							.getParameter("idActividad")));
			laActividad.setActividadActuacion(miPadre);
			if (miPadre instanceof ActividadGeneral) {
				// Si la actividad es hija de una ActividadGeneral entonces es
				// de tipo Tarea
				// y debo hacer set del objetivoAMitigar del padre porque será
				// el mismo
				ObjetivoEspecifico elObjetivoDeMiPadre = miPadre
						.getObjetivoAMitigar();
				laActividad.setObjetivoAMitigar(elObjetivoDeMiPadre);
				modelMap.addAttribute("esActividadHija", "true");
				modelMap.addAttribute("objetivoAMitigar", elObjetivoDeMiPadre);
			} else {
				modelMap.addAttribute("esActividadHija", "false");
			}
		} else {
			modelMap.addAttribute("esActividadHija", "false");
		}

		Actuacion miActuacion = Actuacion.findActuacion(idActuacion);
		laActividad.setCodigoActuacion(miActuacion);

		// Buscamos todas las actividades generales de la actuacion
		Query queryActividades = ActividadGeneral
				.findActividadGeneralsByCodigoActuacion(miActuacion);
		List<ActividadGeneral> actividades = queryActividades.getResultList();

		// Verificamos cual es el ultimo codigo para generar el correlativo
		// siguiente
		int mayorActividad = 0;
		int mayorTarea = 0;
		for (int i = 0; i < actividades.size(); i++) {
			ActividadGeneral actividad = (ActividadGeneral) actividades.get(i);

			// El elemento encontrado es una actividad
			if (actividad.getActividadActuacion() == null) {

				if (Integer.parseInt(actividad.getCodigo()) > mayorActividad) {
					mayorActividad = Integer.parseInt(actividad.getCodigo());
				}
			} // El elemento encontrado es una tarea
			else {
				// Verifico que sean las tareas que pertenecen a esta actividad
				if (actividad.getActividadActuacion() == laActividad
						.getActividadActuacion()) {
					if (Integer.parseInt(actividad.getCodigo()) > mayorTarea) {
						mayorTarea = Integer.parseInt(actividad.getCodigo());

					}
				}
			}
		}

		// Verificamos si esta actividad general es actividad o tarea
		if (laActividad.getActividadActuacion() == null) {
			laActividad.setCodigo(String.valueOf(mayorActividad + 1));
		} else {
			laActividad.setCodigo(String.valueOf(mayorTarea + 1));
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

		laActividad.setActividadActuacion(ActividadActuacion
				.findActividadActuacion(idActividadPadre));
		modelMap.addAttribute("idActuacion", idActuacion);
		modelMap.addAttribute("actividadGeneral", laActividad);
		modelMap.addAttribute("auditors", losQueSon);
		modelMap.addAttribute("estadoactividadactuacions",
				EstadoActividadActuacion.findAllEstadoActividadActuacions());

		Query queryObjetivos = ObjetivoEspecifico
				.findObjetivoEspecificoesByActuacion(laActividad
						.getCodigoActuacion());
		List<ObjetivoEspecifico> objetivoespecificoes = queryObjetivos
				.getResultList();
		modelMap.addAttribute("objetivoespecificoes", objetivoespecificoes);

		modelMap.addAttribute(
				"actividadGeneral_fechaEstimadaDeInicio_date_format",
				org.joda.time.format.DateTimeFormat.patternForStyle("S-",
						org.springframework.context.i18n.LocaleContextHolder
								.getLocale()));
		modelMap.addAttribute(
				"actividadGeneral_fechaEstimadaDeFin_date_format",
				org.joda.time.format.DateTimeFormat.patternForStyle("S-",
						org.springframework.context.i18n.LocaleContextHolder
								.getLocale()));

		return "actividadgeneral/create";
	}

	@RequestMapping(value = "/actividadgeneral", method = RequestMethod.POST)
	public String create(
			@Valid @ModelAttribute("idActuacion") long idActuacion,
			@Valid @ModelAttribute("actividadGeneral") ActividadGeneral actividadCompleta,
			@Valid ActividadGeneral actividadGeneral, BindingResult result,
			ModelMap modelMap, SessionStatus status, HttpServletRequest request) {
		if (actividadGeneral.getDescripcion() == "") {
			result.addError(new FieldError(result.getObjectName(),
					"descripcion", "Campo obligatorio"));
		}

		/*
		 * if (actividadGeneral.getResultadosEsperados() == "") {
		 * result.addError(new FieldError(result.getObjectName(),
		 * "resultadosEsperados", "Campo obligatorio")); } if
		 * (actividadGeneral.getHitosDeControl() == "") { result.addError(new
		 * FieldError(result.getObjectName(), "hitosDeControl",
		 * "Campo obligatorio")); }
		 */

		Date fechaEstimadaDeInicio = actividadCompleta
				.getFechaEstimadaDeInicio();
		Date fechaEstimadaDeFin = actividadCompleta.getFechaEstimadaDeFin();
		if (fechaEstimadaDeInicio == null) {
			result.addError(new FieldError(result.getObjectName(),
					"fechaEstimadaDeInicio", "Campo obligatorio"));
		}
		if (fechaEstimadaDeFin == null) {
			result.addError(new FieldError(result.getObjectName(),
					"fechaEstimadaDeFin", "Campo obligatorio"));
		}
		if ((fechaEstimadaDeInicio != null) && (fechaEstimadaDeFin != null)) {
			if (fechaEstimadaDeInicio.getTime() > fechaEstimadaDeFin.getTime()) {
				result.addError(new FieldError(result.getObjectName(),
						"fechaEstimadaDeFin", "Fecha inválida"));
			}
		}

		Actuacion miActuacion = Actuacion.findActuacion(idActuacion);

		// Validamos el rango de fechas introducidas con respecto a la fecha de
		// la actuacion
		int anoFiscal = 0;
		List<ItemPlanificacionActuacion> items = ItemPlanificacionActuacion
				.findItemPlanificacionActuacionsByActuacion(miActuacion)
				.getResultList();
		for (int i = 0; i < items.size(); i++) {
			anoFiscal = items.get(i).getPlanAnual().getAnoFiscal();
		}

		if (anoFiscal != 0) {
			Date fechaInicial = fechaEstimadaDeInicio;
			Calendar calendarioInicial = Calendar.getInstance();
			calendarioInicial.setTime(fechaInicial);

			int anoActividad = calendarioInicial.get(Calendar.YEAR);
			int mesActividad = calendarioInicial.get(Calendar.MONTH) + 1;

			if (anoActividad == anoFiscal) {
				logger.debug("********FECHAS**********");
				logger.debug("MesActividad: " + mesActividad);
				logger.debug("mesDesdeTentativo (Actuacion): "
						+ miActuacion.getMesDesdeTentativo());
				logger.debug("mesHastaTentativo (Actuacion): "
						+ miActuacion.getMesHastaTentativo());

				if ((mesActividad < miActuacion.getMesDesdeTentativo())
						|| (mesActividad > miActuacion.getMesHastaTentativo())) {
					result.addError(new FieldError(result.getObjectName(),
							"fechaEstimadaDeInicio",
							"La fecha ingresada debe estar dentro de las fechas de la actuación"));
				}
			} else {
				result.addError(new FieldError(result.getObjectName(),
						"fechaEstimadaDeInicio",
						"La fecha ingresada tiene a�o incorrecto"));
			}

			// ********************************************************

			Date fechaFin = fechaEstimadaDeFin;
			Calendar calendarioFin = Calendar.getInstance();
			calendarioFin.setTime(fechaFin);

			int anoActividad2 = calendarioFin.get(Calendar.YEAR);
			int mesActividad2 = calendarioFin.get(Calendar.MONTH) + 1;

			if (anoActividad2 == anoFiscal) {
				if ((mesActividad2 < miActuacion.getMesDesdeTentativo())
						|| (mesActividad2 > miActuacion.getMesHastaTentativo())) {
					result.addError(new FieldError(result.getObjectName(),
							"fechaEstimadaDeFin",
							"La fecha ingresada debe estar dentro de las fechas de la actuación"));
				}
			} else {
				result.addError(new FieldError(result.getObjectName(),
						"fechaEstimadaDeFin",
						"La fecha ingresada tiene año incorrecto"));
			}
		}

		// ************************************************************************************

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

		if (actividadGeneral == null) {
			throw new IllegalArgumentException("A actividadGeneral is required");
		}
		if (result.hasErrors()) {
			modelMap.addAttribute("idActuacion", idActuacion);
			modelMap.addAttribute("actividadGeneral", actividadGeneral);
			modelMap.addAttribute("auditors", losQueSon);
			modelMap.addAttribute("estadoactividadactuacions",
					EstadoActividadActuacion.findAllEstadoActividadActuacions());
			Query queryObjetivos = ObjetivoEspecifico
					.findObjetivoEspecificoesByActuacion(actividadGeneral
							.getCodigoActuacion());
			List<ObjetivoEspecifico> objetivoespecificoes = queryObjetivos
					.getResultList();
			modelMap.addAttribute("objetivoespecificoes", objetivoespecificoes);

			if (actividadGeneral.getActividadActuacion() instanceof ActividadGeneral) {
				modelMap.addAttribute("esActividadHija", "true");
				modelMap.addAttribute("objetivoAMitigar", actividadGeneral
						.getActividadActuacion().getObjetivoAMitigar());
			} else {
				modelMap.addAttribute("esActividadHija", "false");
			}

			modelMap.addAttribute(
					"actividadGeneral_fechaEstimadaDeInicio_date_format",
					org.joda.time.format.DateTimeFormat
							.patternForStyle(
									"S-",
									org.springframework.context.i18n.LocaleContextHolder
											.getLocale()));
			modelMap.addAttribute(
					"actividadGeneral_fechaEstimadaDeFin_date_format",
					org.joda.time.format.DateTimeFormat
							.patternForStyle(
									"S-",
									org.springframework.context.i18n.LocaleContextHolder
											.getLocale()));

			return "actividadgeneral/create";
		}

		if (actividadCompleta.getActividadActuacion() != null) {
			actividadGeneral.setActividadActuacion(actividadCompleta
					.getActividadActuacion());
		}

		logger.info("Se va ingresar en el campo de creandro el usuario " + util.usuarioCreador());
		actividadGeneral.setCreador(util.usuarioCreador());

		actividadGeneral.persist();
		Util.registrarEntradaEnBitacora(actividadGeneral,
				"Se creó la Actividad ", request.getRemoteAddr());
		status.setComplete();
		return "redirect:/actividadgeneral/" + actividadGeneral.getId();
	}

	@RequestMapping(value = "/actividadgeneral/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") Long id, ModelMap modelMap) {
		if (id == null) {
			throw new IllegalArgumentException("An Identifier is required");
		}
		ActividadActuacion actividadActuacion = ActividadActuacion
				.findActividadActuacion(id);
		modelMap.addAttribute("actividadGeneral", actividadActuacion);

		Query queryAvances = AvanceActuacion
				.findAvanceActuacionsByCodigoActividad(actividadActuacion);
		List<AvanceActuacion> avances = queryAvances.getResultList();
		modelMap.addAttribute("avanceactuacions", avances);

		Query queryActividades = ActividadGeneral
				.findActividadActuacionsByActividadActuacion(actividadActuacion);
		List<ActividadGeneral> actividades = queryActividades.getResultList();
		Collections.sort(actividades, ActividadGeneral.compararActividades);
		modelMap.addAttribute("actividades", actividades);

		ActividadActuacion miPadre = actividadActuacion.getActividadActuacion();
		if (miPadre instanceof ActividadGeneral) {
			modelMap.addAttribute("esActividadHija", "true");
		} else {
			modelMap.addAttribute("esActividadHija", "false");
		}

		modelMap.addAttribute(
				"actividadGeneral_fechaEstimadaDeInicio_date_format",
				org.joda.time.format.DateTimeFormat.patternForStyle("S-",
						org.springframework.context.i18n.LocaleContextHolder
								.getLocale()));
		modelMap.addAttribute(
				"actividadGeneral_fechaEstimadaDeFin_date_format",
				org.joda.time.format.DateTimeFormat.patternForStyle("S-",
						org.springframework.context.i18n.LocaleContextHolder
								.getLocale()));

		modelMap.addAttribute("objetoComentable", actividadActuacion);

		logger.debug("solicitando revision de actividad actuacion en motor de reglas");
		jbpmService.executeRulesStateless(actividadActuacion);
		logger.debug("revision de actividad actuacion en motor de reglas listo");

		return "actividadgeneral/show";
	}

	@RequestMapping(method = RequestMethod.PUT)
	public String update(
			@Valid @ModelAttribute("actividadGeneral") ActividadGeneral actividadCompleta,
			BindingResult result, ModelMap modelMap, SessionStatus status,
			HttpServletRequest request) {

		if (actividadCompleta.getDescripcion() == "") {
			result.addError(new FieldError(result.getObjectName(),
					"descripcion", "Campo obligatorio"));
		}

		/*
		 * if (actividadCompleta.getResultadosEsperados() == "") {
		 * result.addError(new FieldError(result.getObjectName(),
		 * "resultadosEsperados", "Campo obligatorio")); } if
		 * (actividadCompleta.getHitosDeControl() == "") { result.addError(new
		 * FieldError(result.getObjectName(), "hitosDeControl",
		 * "Campo obligatorio")); }
		 */

		Date fechaEstimadaDeInicio = actividadCompleta
				.getFechaEstimadaDeInicio();
		Date fechaEstimadaDeFin = actividadCompleta.getFechaEstimadaDeFin();
		if (fechaEstimadaDeInicio == null) {
			result.addError(new FieldError(result.getObjectName(),
					"fechaEstimadaDeInicio", "Campo obligatorio"));
		}
		if (fechaEstimadaDeFin == null) {
			result.addError(new FieldError(result.getObjectName(),
					"fechaEstimadaDeFin", "Campo obligatorio"));
		}
		if ((fechaEstimadaDeInicio != null) && (fechaEstimadaDeFin != null)) {
			if (fechaEstimadaDeInicio.getTime() > fechaEstimadaDeFin.getTime()) {
				result.addError(new FieldError(result.getObjectName(),
						"fechaEstimadaDeFin", "Fecha inválida"));
			}
		}

		Actuacion miActuacion = actividadCompleta.getCodigoActuacion();

		// Validamos el rango de fechas introducidas con respecto a la fecha de
		// la actuacion
		int anoFiscal = 0;
		List<ItemPlanificacionActuacion> items = ItemPlanificacionActuacion
				.findItemPlanificacionActuacionsByActuacion(miActuacion)
				.getResultList();
		for (int i = 0; i < items.size(); i++) {
			anoFiscal = items.get(i).getPlanAnual().getAnoFiscal();
		}

		if (anoFiscal != 0) {
			Date fechaInicial = fechaEstimadaDeInicio;
			Calendar calendarioInicial = Calendar.getInstance();
			calendarioInicial.setTime(fechaInicial);

			int anoActividad = calendarioInicial.get(Calendar.YEAR);
			int mesActividad = calendarioInicial.get(Calendar.MONTH) + 1;

			if (anoActividad == anoFiscal) {
				logger.debug("********FECHAS**********");
				logger.debug("MesActividad: " + mesActividad);
				logger.debug("mesDesdeTentativo (Actuacion): "
						+ miActuacion.getMesDesdeTentativo());
				logger.debug("mesHastaTentativo (Actuacion): "
						+ miActuacion.getMesHastaTentativo());

				if ((mesActividad < miActuacion.getMesDesdeTentativo())
						|| (mesActividad > miActuacion.getMesHastaTentativo())) {
					result.addError(new FieldError(result.getObjectName(),
							"fechaEstimadaDeInicio",
							"La fecha ingresada debe estar dentro de las fechas de la actuación"));
				}
			} else {
				result.addError(new FieldError(result.getObjectName(),
						"fechaEstimadaDeInicio",
						"La fecha ingresada tiene a�o incorrecto"));
			}

			// ********************************************************

			Date fechaFin = fechaEstimadaDeFin;
			Calendar calendarioFin = Calendar.getInstance();
			calendarioFin.setTime(fechaFin);

			int anoActividad2 = calendarioFin.get(Calendar.YEAR);
			int mesActividad2 = calendarioFin.get(Calendar.MONTH) + 1;

			if (anoActividad2 == anoFiscal) {
				if ((mesActividad2 < miActuacion.getMesDesdeTentativo())
						|| (mesActividad2 > miActuacion.getMesHastaTentativo())) {
					result.addError(new FieldError(result.getObjectName(),
							"fechaEstimadaDeFin",
							"La fecha ingresada debe estar dentro de las fechas de la actuación"));
				}
			} else {
				result.addError(new FieldError(result.getObjectName(),
						"fechaEstimadaDeFin",
						"La fecha ingresada tiene año incorrecto"));
			}
		}

		// ************************************************************************************

		if (actividadCompleta == null) {
			throw new IllegalArgumentException("A actividadGeneral is required");
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
			modelMap.addAttribute("actividadGeneral", actividadCompleta);
			modelMap.addAttribute("actuacions", Actuacion.findAllActuacions());
			modelMap.addAttribute("auditors", losQueSon);
			// modelMap.addAttribute("objetivoAMitigar",
			// actividadCompleta.getActividadActuacion().getObjetivoAMitigar());
			Query queryObjetivos = ObjetivoEspecifico
					.findObjetivoEspecificoesByActuacion(actividadCompleta
							.getCodigoActuacion());
			List<ObjetivoEspecifico> objetivoespecificoes = queryObjetivos
					.getResultList();
			modelMap.addAttribute("objetivoespecificoes", objetivoespecificoes);

			if (actividadCompleta.getActividadActuacion() instanceof ActividadGeneral) {
				modelMap.addAttribute("esActividadHija", "true");
				modelMap.addAttribute("objetivoAMitigar", actividadCompleta
						.getActividadActuacion().getObjetivoAMitigar());
			} else {
				modelMap.addAttribute("esActividadHija", "false");
			}

			modelMap.addAttribute(
					"actividadGeneral_fechaEstimadaDeInicio_date_format",
					org.joda.time.format.DateTimeFormat
							.patternForStyle(
									"S-",
									org.springframework.context.i18n.LocaleContextHolder
											.getLocale()));
			modelMap.addAttribute(
					"actividadGeneral_fechaEstimadaDeFin_date_format",
					org.joda.time.format.DateTimeFormat
							.patternForStyle(
									"S-",
									org.springframework.context.i18n.LocaleContextHolder
											.getLocale()));

			return "actividadgeneral/update";
		}

		// Existe la posibilidad de haber modificado el objetivoAMitigar
		// entonces es necesario actualizarlo en los hijos tambien
		ObjetivoEspecifico objetivoAMitigarPadre = actividadCompleta
				.getObjetivoAMitigar();
		Query queryActividadActuaciones = ActividadActuacion
				.findActividadActuacionsByActividadActuacion(actividadCompleta);
		ArrayList<ActividadActuacion> actividadesHijas = (ArrayList<ActividadActuacion>) queryActividadActuaciones
				.getResultList();
		// actividadesHijas es un arreglo que va a tener todas las
		// actividadeGenerals, Pruebas y PTs que sean hijas de actividadCompleta
		for (int i = 0; i < actividadesHijas.size(); i++) {
			actividadesHijas.get(i).setObjetivoAMitigar(objetivoAMitigarPadre);
			actividadesHijas.get(i).merge();

			// Hago lo mismo para los nietos (Tareas)
			Query queryActividadActuacionesNietas = ActividadActuacion
					.findActividadActuacionsByActividadActuacion(actividadesHijas
							.get(i));
			ArrayList<ActividadActuacion> actividadesNietas = (ArrayList<ActividadActuacion>) queryActividadActuacionesNietas
					.getResultList();
			for (int j = 0; j < actividadesNietas.size(); j++) {
				actividadesNietas.get(j).setObjetivoAMitigar(
						objetivoAMitigarPadre);
				actividadesNietas.get(j).merge();
			}
		}
		actividadCompleta.merge();
		Util.registrarEntradaEnBitacora(actividadCompleta,
				"Se modificó la Actividad ", request.getRemoteAddr());
		status.setComplete();
		return "redirect:/actividadgeneral/" + actividadCompleta.getId();
	}

	@RequestMapping(value = "/actividadgeneral/{id}/form", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
		if (id == null) {
			throw new IllegalArgumentException("An Identifier is required");
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

		ActividadGeneral laActividad = ActividadGeneral
				.findActividadGeneral(id);
		modelMap.addAttribute("actividadGeneral", laActividad);
		modelMap.addAttribute("auditors", losQueSon);
		modelMap.addAttribute("estadoactividadactuacions",
				EstadoActividadActuacion.findAllEstadoActividadActuacions());

		Query queryObjetivos = ObjetivoEspecifico
				.findObjetivoEspecificoesByActuacion(laActividad
						.getCodigoActuacion());
		List<ObjetivoEspecifico> objetivoespecificoes = queryObjetivos
				.getResultList();
		modelMap.addAttribute("objetivoespecificoes", objetivoespecificoes);

		if ((laActividad.getActividadActuacion() != null)
				&& (laActividad.getActividadActuacion() instanceof ActividadGeneral)) {
			// Si la actividad es hija de una ActividadGeneral entonces es de
			// tipo Tarea
			modelMap.addAttribute("esActividadHija", "true");
			modelMap.addAttribute("objetivoAMitigar",
					laActividad.getObjetivoAMitigar());
		} else {
			modelMap.addAttribute("esActividadHija", "false");
		}

		modelMap.addAttribute(
				"actividadGeneral_fechaEstimadaDeInicio_date_format",
				org.joda.time.format.DateTimeFormat.patternForStyle("S-",
						org.springframework.context.i18n.LocaleContextHolder
								.getLocale()));
		modelMap.addAttribute(
				"actividadGeneral_fechaEstimadaDeFin_date_format",
				org.joda.time.format.DateTimeFormat.patternForStyle("S-",
						org.springframework.context.i18n.LocaleContextHolder
								.getLocale()));

		return "actividadgeneral/update";
	}

}
