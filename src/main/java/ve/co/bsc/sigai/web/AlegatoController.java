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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import ve.co.bsc.sigai.domain.Alegato;
import ve.co.bsc.sigai.domain.ArchivoAdjunto;
import ve.co.bsc.sigai.domain.Observacion;
import ve.co.bsc.util.Util;

@RooWebScaffold(path = "alegato", automaticallyMaintainView = true, formBackingObject = Alegato.class)
@RequestMapping("/alegato/**")
@SessionAttributes({ "alegato" })
@Controller
public class AlegatoController {

	Logger logger = Logger.getLogger(AlegatoController.class);

	@RequestMapping(value = "/alegato", method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute("alegato") Alegato elAlegato,
			BindingResult result, ModelMap modelMap, SessionStatus status,
			HttpServletRequest request) {

		if (elAlegato == null)
			throw new IllegalArgumentException("A alegato is required");
		if (result.hasErrors()) {
			modelMap.addAttribute("alegato", elAlegato);
			return "alegato/create";
		}

		elAlegato.persist();
		Util.registrarEntradaEnBitacora(elAlegato, "Se creó el Alegato ",
				request.getRemoteAddr());
		status.setComplete();

		return "redirect:/observacion/" + elAlegato.getObservacion().getId();
	}

	@RequestMapping(value = "/alegato/form", method = RequestMethod.GET)
	public String createForm(@RequestParam("idObservacion") long idObservacion,
			ModelMap modelMap) {
		Alegato miAlegato = new Alegato();

		Observacion laObservacion = Observacion.findObservacion(idObservacion);
		miAlegato.setObservacion(laObservacion);
		modelMap.addAttribute("alegato", miAlegato);

		return "alegato/create";
	}

	@RequestMapping(method = RequestMethod.PUT)
	public String update(@Valid @ModelAttribute("alegato") Alegato elAlegato,
			BindingResult result, ModelMap modelMap, SessionStatus status,
			HttpServletRequest request) {

		if (elAlegato == null)
			throw new IllegalArgumentException("A alegato is required");
		if (result.hasErrors()) {
			modelMap.addAttribute("alegato", elAlegato);
			return "alegato/update";
		}

		elAlegato.merge();
		Util.registrarEntradaEnBitacora(elAlegato, "Se modificó el Alegato ",
				request.getRemoteAddr());
		status.setComplete();

		return "redirect:/observacion/" + elAlegato.getObservacion().getId();
	}

	@RequestMapping(value = "/alegato/{id}/form", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
		if (id == null)
			throw new IllegalArgumentException("An Identifier is required");

		Alegato miAlegato = Alegato.findAlegato(id);

		logger.debug("UpdateForm - Alegato ID: " + miAlegato.getId());
		logger.debug("UpdateForm - Observacion ID: "
				+ miAlegato.getObservacion().getId());

		modelMap.addAttribute("alegato", miAlegato);

		return "alegato/update";
	}

	@RequestMapping(value = "/alegato/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") Long id, ModelMap modelMap) {

		if (id == null)
			throw new IllegalArgumentException("An Identifier is required");
		Alegato elAlegato = Alegato.findAlegato(id);
		Query queryArchivosAdjuntosByAlegato = ArchivoAdjunto
				.findArchivoAdjuntoesByAlegato(elAlegato);
		List<ArchivoAdjunto> archivosAdjuntosByAlegato = queryArchivosAdjuntosByAlegato
				.getResultList();
		modelMap.addAttribute("archivosAdjuntosByAlegato",
				archivosAdjuntosByAlegato);
		modelMap.addAttribute("alegato", elAlegato);
		modelMap.addAttribute("objetoComentable", elAlegato);

		return "alegato/show";
	}

	@RequestMapping(value = "/alegato/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("id") Long id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size) {
		if (id == null)
			throw new IllegalArgumentException("An Identifier is required");

		Alegato miAlegato = Alegato.findAlegato(id);
		Observacion laObservacion = miAlegato.getObservacion();

		Alegato.findAlegato(id).remove();

		return "redirect:/observacion/" + laObservacion.getId();
	}

}
