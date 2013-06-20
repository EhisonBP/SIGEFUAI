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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
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

import ve.co.bsc.sigai.domain.Actuacion;
import ve.co.bsc.sigai.domain.ArchivoAdjunto;
import ve.co.bsc.sigai.domain.EstatusRequerimiento;
import ve.co.bsc.sigai.domain.ItemPlanificacionActuacion;
import ve.co.bsc.sigai.domain.Requerimiento;
import ve.co.bsc.sigai.domain.Respuesta;
import ve.co.bsc.sigai.domain.Unidad;
import ve.co.bsc.util.Util;

@RooWebScaffold(path = "requerimiento", automaticallyMaintainView = true, formBackingObject = Requerimiento.class)
@RequestMapping("/requerimiento/**")
@SessionAttributes({ "requerimiento", "idPapelDeTrabajo" })
@Controller
public class RequerimientoController {
	Logger logger = Logger.getLogger(RequerimientoController.class);
	private Util util = new Util();

	@RequestMapping(value = "/requerimiento", method = RequestMethod.POST)
	public String create(
			@ModelAttribute("idPapelDeTrabajo") long idPapelDeTrabajo,
			@Valid @ModelAttribute("requerimiento") Requerimiento requerimiento,
			BindingResult result, ModelMap modelMap, SessionStatus status,
			HttpServletRequest request) {
		if (requerimiento == null) {
			throw new IllegalArgumentException("A requerimiento is required");
		}

		int anoFiscal = 0;
		Actuacion actuacion = requerimiento.getActuacion();
		List<ItemPlanificacionActuacion> items = ItemPlanificacionActuacion
				.findItemPlanificacionActuacionsByActuacion(actuacion)
				.getResultList();
		for (int i = 0; i < items.size(); i++) {
			anoFiscal = items.get(i).getPlanAnual().getAnoFiscal();
		}
		if (anoFiscal != 0) {
			Date fecha = requerimiento.getFechaSolicitud();
			Calendar calendario = Calendar.getInstance();
			calendario.setTime(fecha);

			int anoSolicitud = calendario.get(Calendar.YEAR);
			int mesSolicitud = calendario.get(Calendar.MONTH);

			if (anoSolicitud == anoFiscal) {
				if ((mesSolicitud < actuacion.getMesDesdeTentativo())
						|| (mesSolicitud > actuacion.getMesHastaTentativo())) {
					result.addError(new FieldError(result.getObjectName(),
							"fechaSolicitud",
							"La fecha ingresada debe estar dentro de las fechas de la actuación"));
				}
			} else {
				result.addError(new FieldError(result.getObjectName(),
						"fechaSolicitud",
						"La fecha ingresada tiene a�o incorrecto"));
			}
		}

		Date fechaHoy = new Date();
		if (requerimiento.getFechaSolicitud().getTime() > fechaHoy.getTime()) {
			result.addError(new FieldError(result.getObjectName(),
					"fechaSolicitud",
					"La fecha ingresada es mayor a la fecha de hoy"));
		}

		if (result.hasErrors()) {
			modelMap.addAttribute("unidads",
					Unidad.findUnidadsByRif(util.traerIdRif()).getResultList());
			modelMap.addAttribute("requerimiento", requerimiento);
			modelMap.addAttribute("idPapelDeTrabajo", idPapelDeTrabajo);
			modelMap.addAttribute(
					"requerimiento_fechaSolicitud_date_format",
					org.joda.time.format.DateTimeFormat
							.patternForStyle(
									"S-",
									org.springframework.context.i18n.LocaleContextHolder
											.getLocale()));
			modelMap.addAttribute("estatusrequerimientoes",
					EstatusRequerimiento.findAllEstatusRequerimientoes());
			return "requerimiento/create";
		}

		// Si el estadiRequerimiento es "Recibido Totalmente" entonces guardo la
		// fecha de hoy
		if (requerimiento.getEstadoRequerimiento() == EstatusRequerimiento
				.findEstatusRequerimiento(new Long(3))) {
			requerimiento.setFechaRecibidoTotalmente(new Date());
		}

		requerimiento.setAuditado(requerimiento.getUnidad().getResponsable());
		requerimiento.persist();
		Util.registrarEntradaEnBitacora(requerimiento,
				"Se creó el Requerimiento ", request.getRemoteAddr());
		status.setComplete();
		return "redirect:/papeldetrabajo/" + idPapelDeTrabajo;
		// return "redirect:/requerimiento/" + requerimiento.getId();
	}

	@RequestMapping(value = "/requerimiento/form", method = RequestMethod.GET)
	public String createForm(@RequestParam("idActuacion") long idActuacion,
			@RequestParam("idPapelDeTrabajo") long idPapelDeTrabajo,
			ModelMap modelMap) {

		Date fechaDeHoy = new Date();
		Requerimiento miRequerimiento = new Requerimiento();
		miRequerimiento.setActuacion(Actuacion.findActuacion(idActuacion));
		miRequerimiento.setFechaSolicitud(fechaDeHoy);

		modelMap.addAttribute("unidads",
				Unidad.findUnidadsByRif(util.traerIdRif()).getResultList());
		modelMap.addAttribute("requerimiento", miRequerimiento);
		modelMap.addAttribute("idPapelDeTrabajo", idPapelDeTrabajo);
		modelMap.addAttribute("requerimiento_fechaSolicitud_date_format",
				org.joda.time.format.DateTimeFormat.patternForStyle("S-",
						org.springframework.context.i18n.LocaleContextHolder
								.getLocale()));
		modelMap.addAttribute("estatusrequerimientoes",
				EstatusRequerimiento.findAllEstatusRequerimientoes());
		return "requerimiento/create";
	}

	@RequestMapping(method = RequestMethod.PUT)
	public String update(
			@ModelAttribute("idPapelDeTrabajo") long idPapelDeTrabajo,
			@Valid @ModelAttribute("requerimiento") Requerimiento requerimiento,
			BindingResult result, ModelMap modelMap, SessionStatus status,
			HttpServletRequest request) {
		if (requerimiento == null) {
			throw new IllegalArgumentException("A requerimiento is required");
		}

		int anoFiscal = 0;
		Actuacion actuacion = requerimiento.getActuacion();
		List<ItemPlanificacionActuacion> items = ItemPlanificacionActuacion
				.findItemPlanificacionActuacionsByActuacion(actuacion)
				.getResultList();
		for (int i = 0; i < items.size(); i++) {
			anoFiscal = items.get(i).getPlanAnual().getAnoFiscal();
		}
		if (anoFiscal != 0) {
			Date fecha = requerimiento.getFechaSolicitud();
			Calendar calendario = Calendar.getInstance();
			calendario.setTime(fecha);

			int anoSolicitud = calendario.get(Calendar.YEAR);
			int mesSolicitud = calendario.get(Calendar.MONTH);

			if (anoSolicitud == anoFiscal) {
				if ((mesSolicitud < actuacion.getMesDesdeTentativo())
						|| (mesSolicitud > actuacion.getMesHastaTentativo())) {
					result.addError(new FieldError(result.getObjectName(),
							"fechaSolicitud",
							"La fecha ingresada debe estar dentro de las fechas de la actuación"));
				}
			} else {
				result.addError(new FieldError(result.getObjectName(),
						"fechaSolicitud",
						"La fecha ingresada tiene a�o incorrecto"));
			}
		}

		Date fechaHoy = new Date();
		if (requerimiento.getFechaSolicitud().getTime() > fechaHoy.getTime()) {
			result.addError(new FieldError(result.getObjectName(),
					"fechaSolicitud",
					"La fecha ingresada es mayor a la fecha de hoy"));
		}

		if (result.hasErrors()) {
			modelMap.addAttribute("unidads",
					Unidad.findUnidadsByRif(util.traerIdRif()).getResultList());
			modelMap.addAttribute("requerimiento", requerimiento);
			modelMap.addAttribute("idPapelDeTrabajo", idPapelDeTrabajo);
			modelMap.addAttribute(
					"requerimiento_fechaSolicitud_date_format",
					org.joda.time.format.DateTimeFormat
							.patternForStyle(
									"S-",
									org.springframework.context.i18n.LocaleContextHolder
											.getLocale()));
			modelMap.addAttribute("estatusrequerimientoes",
					EstatusRequerimiento.findAllEstatusRequerimientoes());
			return "requerimiento/update";
		}

		// Si el estadiRequerimiento es "Recibido Totalmente" entonces guardo la
		// fecha de hoy
		if (requerimiento.getEstadoRequerimiento() == EstatusRequerimiento
				.findEstatusRequerimiento(new Long(3))) {
			requerimiento.setFechaRecibidoTotalmente(new Date());
		} else {
			requerimiento.setFechaRecibidoTotalmente(null);
		}

		requerimiento.setAuditado(requerimiento.getUnidad().getResponsable());

		requerimiento.merge();
		Util.registrarEntradaEnBitacora(requerimiento,
				"Se modificó el Requerimiento ", request.getRemoteAddr());
		status.setComplete();
		return "redirect:/papeldetrabajo/" + idPapelDeTrabajo;
		// return "redirect:/requerimiento/" + requerimiento.getId();
	}

	@RequestMapping(value = "/requerimiento/{id}/form", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id,
			@RequestParam("idPapelDeTrabajo") long idPapelDeTrabajo,
			ModelMap modelMap) {

		if (id == null) {
			throw new IllegalArgumentException("An Identifier is required");
		}
		modelMap.addAttribute("unidads",
				Unidad.findUnidadsByRif(util.traerIdRif()).getResultList());
		modelMap.addAttribute("requerimiento",
				Requerimiento.findRequerimiento(id));
		modelMap.addAttribute("idPapelDeTrabajo", idPapelDeTrabajo);
		modelMap.addAttribute("requerimiento_fechaSolicitud_date_format",
				org.joda.time.format.DateTimeFormat.patternForStyle("S-",
						org.springframework.context.i18n.LocaleContextHolder
								.getLocale()));
		modelMap.addAttribute("estatusrequerimientoes",
				EstatusRequerimiento.findAllEstatusRequerimientoes());
		return "requerimiento/update";
	}

	@RequestMapping(value = "/requerimiento/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") Long id, ModelMap modelMap) {
		if (id == null) {
			throw new IllegalArgumentException("An Identifier is required");
		}
		Requerimiento requerimiento = Requerimiento.findRequerimiento(id);
		List<Respuesta> respuestas = Respuesta.findRespuestasByRequerimiento(
				requerimiento).getResultList();

		// Lleno un arreglo con las fechas de las respuestas
		List<Date> fechaRespuestas = new ArrayList<Date>();
		for (int i = 0; i < respuestas.size(); i++) {
			fechaRespuestas.add(respuestas.get(i).getFecha());
		}

		Date fechaMayor = null;
		// Ordeno las fechas y obtengo la mayor
		for (int i = 0; i < fechaRespuestas.size(); i++) {
			if (fechaMayor == null) {
				fechaMayor = fechaRespuestas.get(i);
			} else {
				if (fechaRespuestas.get(i).getTime() > fechaMayor.getTime()) {
					fechaMayor = fechaRespuestas.get(i);
				}
			}
		}

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if (fechaMayor != null) {

			String fechaFin = sdf.format(fechaMayor);
			String fechaInicio = sdf.format(requerimiento.getFechaSolicitud());
			int diasHabiles = Requerimiento.getDiasHabiles(fechaInicio,
					fechaFin);
			modelMap.addAttribute("diasHabiles", diasHabiles);
		}

		Date fechaHoy = new Date();
		String fechaIn = sdf.format(requerimiento.getFechaSolicitud());
		String fechaHoyS;
		if (requerimiento.getFechaRecibidoTotalmente() == null) {
			fechaHoyS = sdf.format(fechaHoy);
		} else {
			fechaHoyS = sdf.format(requerimiento.getFechaRecibidoTotalmente());
		}

		int dias = Requerimiento.getDiasHabiles(fechaIn, fechaHoyS);
		modelMap.addAttribute("dias", dias);

		List<ArchivoAdjunto> archivosAdjuntosByRequerimiento = ArchivoAdjunto
				.findArchivoAdjuntoesByRequerimiento(requerimiento)
				.getResultList();
		modelMap.addAttribute("archivosAdjuntosByRequerimiento",
				archivosAdjuntosByRequerimiento);

		modelMap.addAttribute("requerimiento_fechaSolicitud_date_format",
				org.joda.time.format.DateTimeFormat.patternForStyle("S-",
						org.springframework.context.i18n.LocaleContextHolder
								.getLocale()));
		modelMap.addAttribute("requerimiento", requerimiento);
		modelMap.addAttribute("respuestas", respuestas);
		modelMap.addAttribute("respuesta_fecha_date_format",
				org.joda.time.format.DateTimeFormat.patternForStyle("S-",
						org.springframework.context.i18n.LocaleContextHolder
								.getLocale()));

		return "requerimiento/show";
	}

	@RequestMapping(value = "/requerimiento/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("id") Long id,
			@RequestParam("idPapelDeTrabajo") long idPapelDeTrabajo,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size) {
		if (id == null) {
			throw new IllegalArgumentException("An Identifier is required");
		}
		Requerimiento.findRequerimiento(id).remove();
		return "redirect:/papeldetrabajo/" + idPapelDeTrabajo;
		// return "redirect:/requerimiento?page=" + ((page == null) ? "1" :
		// page.toString()) + "&size=" + ((size == null) ? "10" :
		// size.toString());
	}

	@RequestMapping(value = "/requerimiento/actualizarEstatus/{id}", method = RequestMethod.POST)
	public String actualizarEstatus(@Valid Requerimiento requerimiento,
			BindingResult result, ModelMap modelMap, SessionStatus status,
			HttpServletRequest request) {

		if (requerimiento == null)
			throw new IllegalArgumentException("A planAnual is required");
		if (result.hasErrors()) {
			modelMap.addAttribute("requerimiento", requerimiento);
			modelMap.addAttribute("estatusrequerimientoes",
					EstatusRequerimiento.findAllEstatusRequerimientoes());
			modelMap.addAttribute("idPapelDeTrabajo", 0);
			return "requerimiento/actualizarEstatus";
		}
		requerimiento.merge();
		Util.registrarEntradaEnBitacora(requerimiento,
				"Se modificó se actualizó el estatus del requerimiento ",
				request.getRemoteAddr());
		status.setComplete();

		// return "redirect:/requerimiento/" + requerimiento.getId();
		return "redirect:/auditado/actividades";
	}

	@RequestMapping(value = "/requerimiento/actualizarEstatus/{id}/form", method = RequestMethod.GET)
	public String actualizarEstatusForm(@PathVariable("id") Long id,
			ModelMap modelMap) {

		if (id == null)
			throw new IllegalArgumentException("An Identifier is required");
		modelMap.addAttribute("requerimiento",
				Requerimiento.findRequerimiento(id));
		modelMap.addAttribute("estatusrequerimientoes",
				EstatusRequerimiento.findAllEstatusRequerimientoes());
		modelMap.addAttribute("idPapelDeTrabajo", 0);

		return "requerimiento/actualizarEstatus";
	}
}