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

import ve.co.bsc.sigai.domain.Biblioteca;
import ve.co.bsc.sigai.domain.SeccionDefault;
import ve.co.bsc.util.Util;

@RooWebScaffold(path = "biblioteca", automaticallyMaintainView = true, formBackingObject = Biblioteca.class)
@RequestMapping("/biblioteca/**")
@Controller
public class BibliotecaController {

	@RequestMapping(value = "/biblioteca/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") Long id, ModelMap modelMap) {
		if (id == null)
			throw new IllegalArgumentException("An Identifier is required");
		Biblioteca miBiblioteca = Biblioteca.findBiblioteca(id);
		modelMap.addAttribute("biblioteca", miBiblioteca);
		modelMap.addAttribute("seccions", SeccionDefault
				.findSeccionDefaultsPadresByBiblioteca(miBiblioteca)
				.getResultList());
		return "biblioteca/show";
	}

	@RequestMapping(value = "/biblioteca", method = RequestMethod.POST)
	public String create(@Valid Biblioteca biblioteca, BindingResult result,
			ModelMap modelMap, HttpServletRequest request) {
		if (biblioteca == null)
			throw new IllegalArgumentException("A biblioteca is required");
		if (result.hasErrors()) {
			modelMap.addAttribute("biblioteca", biblioteca);
			return "biblioteca/create";
		}
		biblioteca.persist();
		Util.registrarEntradaEnBitacora(biblioteca, "Se creó la biblioteca ",
				request.getRemoteAddr());
		return "redirect:/biblioteca/" + biblioteca.getId();
	}

	@RequestMapping(method = RequestMethod.PUT)
	public String update(@Valid Biblioteca biblioteca, BindingResult result,
			ModelMap modelMap, HttpServletRequest request) {
		if (biblioteca == null)
			throw new IllegalArgumentException("A biblioteca is required");
		if (result.hasErrors()) {
			modelMap.addAttribute("biblioteca", biblioteca);
			return "biblioteca/update";
		}
		biblioteca.merge();
		Util.registrarEntradaEnBitacora(biblioteca,
				"Se modificó la biblioteca ", request.getRemoteAddr());
		return "redirect:/biblioteca/" + biblioteca.getId();
	}
}
