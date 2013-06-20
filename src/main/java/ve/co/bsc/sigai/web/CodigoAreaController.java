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

import java.util.Date;

import javax.validation.Valid;

import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ve.co.bsc.sigai.domain.CodigoArea;
import ve.co.bsc.sigai.domain.Estado;

@RooWebScaffold(path = "codigoarea", automaticallyMaintainView = true, formBackingObject = CodigoArea.class)
@RequestMapping("/codigoarea/**")
@Controller
public class CodigoAreaController {
	@RequestMapping(value = "/codigoarea", method = RequestMethod.POST)
	public String create(@Valid CodigoArea codigoArea, BindingResult result,
			ModelMap modelMap) {

		java.util.Date FechaActual = new Date();
		codigoArea.setFechaCreacion(FechaActual);
		codigoArea.setFechaModificacion(FechaActual);
		if (codigoArea == null)
			throw new IllegalArgumentException("A codigoArea is required");
		if (result.hasErrors()) {
			modelMap.addAttribute("codigoArea", codigoArea);
			// modelMap.addAttribute("ciudads", Ciudad.findAllCiudads());
			modelMap.addAttribute("estadoes", Estado.findAllEstadoes());
			// modelMap.addAttribute("codigoArea_fechaCreacion_date_format",
			// org.joda.time.format.DateTimeFormat.patternForStyle("S-",
			// org.springframework.context.i18n.LocaleContextHolder.getLocale()));
			// modelMap.addAttribute("codigoArea_fechaModificacion_date_format",
			// org.joda.time.format.DateTimeFormat.patternForStyle("S-",
			// org.springframework.context.i18n.LocaleContextHolder.getLocale()));
			return "codigoarea/create";
		}
		codigoArea.persist();
		return "redirect:/codigoarea/" + codigoArea.getId();
	}

	@RequestMapping(value = "/codigoarea/form", method = RequestMethod.GET)
	public String createForm(ModelMap modelMap) {
		modelMap.addAttribute("codigoArea", new CodigoArea());
		// modelMap.addAttribute("ciudads", Ciudad.findAllCiudads());
		modelMap.addAttribute("estadoes", Estado.findAllEstadoes());
		modelMap.addAttribute("codigoArea_fechaCreacion_date_format",
				org.joda.time.format.DateTimeFormat.patternForStyle("S-",
						org.springframework.context.i18n.LocaleContextHolder
								.getLocale()));
		modelMap.addAttribute("codigoArea_fechaModificacion_date_format",
				org.joda.time.format.DateTimeFormat.patternForStyle("S-",
						org.springframework.context.i18n.LocaleContextHolder
								.getLocale()));
		return "codigoarea/create";
	}
}
