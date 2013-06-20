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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ve.co.bsc.sigai.domain.CargoAuditor;
import ve.co.bsc.util.Util;

@RooWebScaffold(path = "cargoauditor", automaticallyMaintainView = true, formBackingObject = CargoAuditor.class)
@RequestMapping("/cargoauditor/**")
@Controller
public class CargoAuditorController {

	@RequestMapping(value = "/cargoauditor", method = RequestMethod.POST)
	public String create(@Valid CargoAuditor cargoAuditor,
			BindingResult result, ModelMap modelMap, HttpServletRequest request) {
		if (cargoAuditor == null)
			throw new IllegalArgumentException("A cargoAuditor is required");
		if (result.hasErrors()) {
			modelMap.addAttribute("cargoAuditor", cargoAuditor);
			return "cargoauditor/create";
		}
		cargoAuditor.persist();
		Util.registrarEntradaEnBitacora(cargoAuditor,
				"Se creó el Cargo de Auditor ", request.getRemoteAddr());
		return "redirect:/cargoauditor";
	}

	@RequestMapping(method = RequestMethod.PUT)
	public String update(@Valid CargoAuditor cargoAuditor,
			BindingResult result, ModelMap modelMap, HttpServletRequest request) {
		if (cargoAuditor == null)
			throw new IllegalArgumentException("A cargoAuditor is required");
		if (result.hasErrors()) {
			modelMap.addAttribute("cargoAuditor", cargoAuditor);
			return "cargoauditor/update";
		}
		cargoAuditor.merge();
		Util.registrarEntradaEnBitacora(cargoAuditor,
				"Se modificó el Cargo de Auditor ", request.getRemoteAddr());
		return "redirect:/cargoauditor";
	}
}
