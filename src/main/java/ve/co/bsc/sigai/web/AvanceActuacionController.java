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

import java.util.List;

import javax.persistence.Query;
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

import ve.co.bsc.sigai.domain.ActividadActuacion;
import ve.co.bsc.sigai.domain.ArchivoAdjunto;
import ve.co.bsc.sigai.domain.AvanceActuacion;
import ve.co.bsc.util.Util;

@RooWebScaffold(path = "avanceactuacion", automaticallyMaintainView = true, formBackingObject = AvanceActuacion.class)
@RequestMapping("/avanceactuacion/**")
@SessionAttributes({ "avanceActuacion", "idReturn", "returnURL",
		"codigoCompleto" })
@Controller
public class AvanceActuacionController {

	Logger logger = Logger.getLogger(AvanceActuacionController.class);

	@RequestMapping(value = "/avanceactuacion/form", method = RequestMethod.GET)
	public String createForm(@RequestParam("idReturn") long idReturn,
			@RequestParam("returnURL") String returnURL, ModelMap modelMap) {
		ActividadActuacion miActividad = ActividadActuacion
				.findActividadActuacion(idReturn);
		modelMap.addAttribute("codigoCompleto", miActividad.getCodigoCompleto());
		modelMap.addAttribute("idReturn", idReturn);
		modelMap.addAttribute("returnURL", returnURL);
		modelMap.addAttribute("avanceActuacion", new AvanceActuacion());
		modelMap.addAttribute(
				"avanceActuacion_fechaInicioEstimada_date_format",
				org.joda.time.format.DateTimeFormat.patternForStyle("S-",
						org.springframework.context.i18n.LocaleContextHolder
								.getLocale()));
		modelMap.addAttribute("avanceActuacion_fechaFinEstimada_date_format",
				org.joda.time.format.DateTimeFormat.patternForStyle("S-",
						org.springframework.context.i18n.LocaleContextHolder
								.getLocale()));
		modelMap.addAttribute("avanceActuacion_fechaInicioReal_date_format",
				org.joda.time.format.DateTimeFormat.patternForStyle("S-",
						org.springframework.context.i18n.LocaleContextHolder
								.getLocale()));
		modelMap.addAttribute("avanceActuacion_fechaFinReal_date_format",
				org.joda.time.format.DateTimeFormat.patternForStyle("S-",
						org.springframework.context.i18n.LocaleContextHolder
								.getLocale()));
		return "avanceactuacion/create";
	}

	@RequestMapping(value = "/avanceactuacion", method = RequestMethod.POST)
	public String create(
			@Valid @ModelAttribute("avanceActuacion") AvanceActuacion avanceActuacion,
			@Valid @ModelAttribute("idReturn") long idReturn,
			@Valid @ModelAttribute("returnURL") String returnURL,
			BindingResult result, ModelMap modelMap, SessionStatus status,
			HttpServletRequest request) {
		if (avanceActuacion.getJustificacion() == "") {
			result.addError(new FieldError(result.getObjectName(),
					"justificacion", "Campo obligatorio"));
		}

		avanceActuacion.setCodigoActividad(ActividadActuacion
				.findActividadActuacion(idReturn));
		if (avanceActuacion == null)
			throw new IllegalArgumentException("A avanceActuacion is required");
		if (result.hasErrors()) {
			modelMap.addAttribute("avanceActuacion", avanceActuacion);
			modelMap.addAttribute("idReturn", idReturn);
			modelMap.addAttribute("returnURL", returnURL);
			modelMap.addAttribute(
					"avanceActuacion_fechaInicioEstimada_date_format",
					org.joda.time.format.DateTimeFormat
							.patternForStyle(
									"S-",
									org.springframework.context.i18n.LocaleContextHolder
											.getLocale()));
			modelMap.addAttribute(
					"avanceActuacion_fechaFinEstimada_date_format",
					org.joda.time.format.DateTimeFormat
							.patternForStyle(
									"S-",
									org.springframework.context.i18n.LocaleContextHolder
											.getLocale()));
			modelMap.addAttribute(
					"avanceActuacion_fechaInicioReal_date_format",
					org.joda.time.format.DateTimeFormat
							.patternForStyle(
									"S-",
									org.springframework.context.i18n.LocaleContextHolder
											.getLocale()));
			modelMap.addAttribute(
					"avanceActuacion_fechaFinReal_date_format",
					org.joda.time.format.DateTimeFormat
							.patternForStyle(
									"S-",
									org.springframework.context.i18n.LocaleContextHolder
											.getLocale()));
			return "avanceactuacion/create";
		}
		avanceActuacion.persist();
		Util.registrarEntradaEnBitacora(avanceActuacion, "Se creó el Avance ",
				request.getRemoteAddr());
		status.setComplete();
		return "redirect:/" + returnURL + "/" + idReturn;
	}

	@RequestMapping(method = RequestMethod.PUT)
	public String update(
			@Valid @ModelAttribute("avanceActuacion") AvanceActuacion avanceActuacion,
			@Valid @ModelAttribute("idReturn") long idReturn,
			@Valid @ModelAttribute("returnURL") String returnURL,
			BindingResult result, ModelMap modelMap, SessionStatus status,
			HttpServletRequest request) {
		if (avanceActuacion.getJustificacion() == "") {
			result.addError(new FieldError(result.getObjectName(),
					"justificacion", "Campo obligatorio"));
		}
		if (avanceActuacion == null)
			throw new IllegalArgumentException("A avanceActuacion is required");
		if (result.hasErrors()) {
			modelMap.addAttribute("idReturn", idReturn);
			modelMap.addAttribute("returnURL", returnURL);
			modelMap.addAttribute("avanceActuacion", avanceActuacion);
			modelMap.addAttribute(
					"avanceActuacion_fechaInicioEstimada_date_format",
					org.joda.time.format.DateTimeFormat
							.patternForStyle(
									"S-",
									org.springframework.context.i18n.LocaleContextHolder
											.getLocale()));
			modelMap.addAttribute(
					"avanceActuacion_fechaFinEstimada_date_format",
					org.joda.time.format.DateTimeFormat
							.patternForStyle(
									"S-",
									org.springframework.context.i18n.LocaleContextHolder
											.getLocale()));
			modelMap.addAttribute(
					"avanceActuacion_fechaInicioReal_date_format",
					org.joda.time.format.DateTimeFormat
							.patternForStyle(
									"S-",
									org.springframework.context.i18n.LocaleContextHolder
											.getLocale()));
			modelMap.addAttribute(
					"avanceActuacion_fechaFinReal_date_format",
					org.joda.time.format.DateTimeFormat
							.patternForStyle(
									"S-",
									org.springframework.context.i18n.LocaleContextHolder
											.getLocale()));
			return "avanceactuacion/update";
		}
		avanceActuacion.merge();
		Util.registrarEntradaEnBitacora(avanceActuacion,
				"Se modificó el Avance ", request.getRemoteAddr());
		status.setComplete();
		return "redirect:/" + returnURL + "/" + idReturn;
	}

	@RequestMapping(value = "/avanceactuacion/{id}/form", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id,
			@RequestParam("idReturn") long idReturn,
			@RequestParam("returnURL") String returnURL, ModelMap modelMap) {
		if (id == null)
			throw new IllegalArgumentException("An Identifier is required");
		ActividadActuacion miActividad = ActividadActuacion
				.findActividadActuacion(idReturn);
		modelMap.addAttribute("codigoCompleto", miActividad.getCodigoCompleto());
		modelMap.addAttribute("idReturn", idReturn);
		modelMap.addAttribute("returnURL", returnURL);
		modelMap.addAttribute("avanceActuacion",
				AvanceActuacion.findAvanceActuacion(id));
		modelMap.addAttribute(
				"avanceActuacion_fechaInicioEstimada_date_format",
				org.joda.time.format.DateTimeFormat.patternForStyle("S-",
						org.springframework.context.i18n.LocaleContextHolder
								.getLocale()));
		modelMap.addAttribute("avanceActuacion_fechaFinEstimada_date_format",
				org.joda.time.format.DateTimeFormat.patternForStyle("S-",
						org.springframework.context.i18n.LocaleContextHolder
								.getLocale()));
		modelMap.addAttribute("avanceActuacion_fechaInicioReal_date_format",
				org.joda.time.format.DateTimeFormat.patternForStyle("S-",
						org.springframework.context.i18n.LocaleContextHolder
								.getLocale()));
		modelMap.addAttribute("avanceActuacion_fechaFinReal_date_format",
				org.joda.time.format.DateTimeFormat.patternForStyle("S-",
						org.springframework.context.i18n.LocaleContextHolder
								.getLocale()));
		return "avanceactuacion/update";
	}

	@RequestMapping(value = "/avanceactuacion/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("id") Long id,
			@RequestParam("idReturn") long idReturn,
			@RequestParam("returnURL") String returnURL) {

		if (id == null)
			throw new IllegalArgumentException("An Identifier is required");

		AvanceActuacion miAvanceActuacion = AvanceActuacion
				.findAvanceActuacion(id);

		// TODO: IMPORTANTE! Verificar que si el avanceActuacion esta
		// relacionado con prueba, se elimine de prueba
		// y no del padre (ActividadActuacion). Igual para el caso de
		// ActividadGeneral

		// Prueba laPrueba = (Prueba)miAvanceActuacion.getCodigoActividad();

		// String response = "";

		// if (tipo.equals("p"))
		// response = "redirect:/prueba/" +
		// miAvanceActuacion.getCodigoActividad().getId();
		// else if (tipo.equals("a"))
		// response = "redirect:/actividadgeneral/" +
		// miAvanceActuacion.getCodigoActividad().getId();
		// else
		// response = "redirect:/avanceactuacion/" + miAvanceActuacion.getId();

		miAvanceActuacion.remove();

		return "redirect:/" + returnURL + "/" + idReturn;
	}

	@RequestMapping(value = "/avanceactuacion/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") Long id, ModelMap modelMap) {
		if (id == null)
			throw new IllegalArgumentException("An Identifier is required");

		AvanceActuacion elAvance = AvanceActuacion.findAvanceActuacion(id);
		ActividadActuacion miActividad = elAvance.getCodigoActividad();
		modelMap.addAttribute("miActividadActuacion", miActividad);
		modelMap.addAttribute(
				"avanceActuacion_fechaInicioEstimada_date_format",
				org.joda.time.format.DateTimeFormat.patternForStyle("S-",
						org.springframework.context.i18n.LocaleContextHolder
								.getLocale()));
		modelMap.addAttribute("avanceActuacion_fechaFinEstimada_date_format",
				org.joda.time.format.DateTimeFormat.patternForStyle("S-",
						org.springframework.context.i18n.LocaleContextHolder
								.getLocale()));
		modelMap.addAttribute("avanceActuacion_fechaInicioReal_date_format",
				org.joda.time.format.DateTimeFormat.patternForStyle("S-",
						org.springframework.context.i18n.LocaleContextHolder
								.getLocale()));
		modelMap.addAttribute("avanceActuacion_fechaFinReal_date_format",
				org.joda.time.format.DateTimeFormat.patternForStyle("S-",
						org.springframework.context.i18n.LocaleContextHolder
								.getLocale()));
		modelMap.addAttribute("avanceActuacion", elAvance);
		Query queryArchivosAdjuntosByAvanceActuacion = ArchivoAdjunto
				.findArchivoAdjuntoesByAvanceActuacion(elAvance);
		List<ArchivoAdjunto> archivosAdjuntosByAvanceActuacion = queryArchivosAdjuntosByAvanceActuacion
				.getResultList();
		modelMap.addAttribute("archivosAdjuntosByAvanceActuacion",
				archivosAdjuntosByAvanceActuacion);

		modelMap.addAttribute("objetoComentable", elAvance);

		return "avanceactuacion/show";
	}
}
