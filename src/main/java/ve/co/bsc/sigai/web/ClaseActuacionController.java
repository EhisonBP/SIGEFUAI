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

import ve.co.bsc.sigai.domain.ClaseActuacion;
import ve.co.bsc.util.Util;

/*
 * Controlador para clase actuacion (CRUD) generado por ROO.
 * Recibe todas las solicitudes a URLs con el patron
 * "/claseactuacion/**"
 */
@RooWebScaffold(path = "claseactuacion", automaticallyMaintainView = true, formBackingObject = ClaseActuacion.class)
@RequestMapping("/claseactuacion/**")
@Controller
public class ClaseActuacionController {

	@RequestMapping(value = "/claseactuacion", method = RequestMethod.POST)
	public String create(@Valid ClaseActuacion claseActuacion,
			BindingResult result, ModelMap modelMap, HttpServletRequest request) {
		if (claseActuacion == null)
			throw new IllegalArgumentException("A claseActuacion is required");
		if (result.hasErrors()) {
			modelMap.addAttribute("claseActuacion", claseActuacion);
			return "claseactuacion/create";
		}
		claseActuacion.persist();
		Util.registrarEntradaEnBitacora(claseActuacion,
				"Se creó el Tipo de Actuacion ", request.getRemoteAddr());
		return "redirect:/claseactuacion";
	}

	@RequestMapping(method = RequestMethod.PUT)
	public String update(@Valid ClaseActuacion claseActuacion,
			BindingResult result, ModelMap modelMap, HttpServletRequest request) {
		if (claseActuacion == null)
			throw new IllegalArgumentException("A claseActuacion is required");
		if (result.hasErrors()) {
			modelMap.addAttribute("claseActuacion", claseActuacion);
			return "claseactuacion/update";
		}
		claseActuacion.merge();
		Util.registrarEntradaEnBitacora(claseActuacion,
				"Se modificó el Tipo de Actuacion ", request.getRemoteAddr());
		return "redirect:/claseactuacion";
	}

}
