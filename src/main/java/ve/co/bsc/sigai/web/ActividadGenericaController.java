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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ve.co.bsc.sigai.domain.ActividadGenerica;
import ve.co.bsc.util.Util;

@RooWebScaffold(path = "actividadgenerica", automaticallyMaintainView = true, formBackingObject = ActividadGenerica.class)
@RequestMapping("/actividadgenerica/**")
@Controller
public class ActividadGenericaController {
	@RequestMapping(value = "/actividadgenerica", method = RequestMethod.POST)
	public String create(@Valid ActividadGenerica actividadGenerica,
			BindingResult result, ModelMap modelMap, HttpServletRequest request) {
		if (actividadGenerica == null)
			throw new IllegalArgumentException(
					"A actividadGenerica is required");
		if (result.hasErrors()) {
			modelMap.addAttribute("actividadGenerica", actividadGenerica);
			return "actividadgenerica/create";
		}
		actividadGenerica.persist();
		Util.registrarEntradaEnBitacora(actividadGenerica,
				"Se creó la Actividad Genérica ", request.getRemoteAddr());
		return "redirect:/actividadgenerica";
	}

	@RequestMapping(method = RequestMethod.PUT)
	public String update(@Valid ActividadGenerica actividadGenerica,
			BindingResult result, ModelMap modelMap, HttpServletRequest request) {
		if (actividadGenerica == null)
			throw new IllegalArgumentException(
					"A actividadGenerica is required");
		if (result.hasErrors()) {
			modelMap.addAttribute("actividadGenerica", actividadGenerica);
			return "actividadgenerica/update";
		}
		actividadGenerica.merge();
		Util.registrarEntradaEnBitacora(actividadGenerica,
				"Se modificó la Actividad Genérica ", request.getRemoteAddr());
		return "redirect:/actividadgenerica";
	}

	@RequestMapping(value = "/actividadgenerica/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") Long id, ModelMap modelMap) {
		if (id == null)
			throw new IllegalArgumentException("An Identifier is required");
		ActividadGenerica actividadGenerica = ActividadGenerica
				.findActividadGenerica(id);
		modelMap.addAttribute("actividadGenerica", actividadGenerica);
		modelMap.addAttribute("objetoComentable", actividadGenerica);

		return "actividadgenerica/show";
	}
}
