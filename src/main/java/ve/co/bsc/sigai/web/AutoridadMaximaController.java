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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import ve.co.bsc.sigai.domain.AutoridadMaxima;
import ve.co.bsc.sigai.domain.EstructuraCargos;
import ve.co.bsc.sigai.domain.OrganismoEnte;
import ve.co.bsc.sigai.domain.Personalizacion;
import ve.co.bsc.util.Util;

@RooWebScaffold(path = "autoridadmaxima", automaticallyMaintainView = true, formBackingObject = AutoridadMaxima.class)
@RequestMapping("/autoridadmaxima/**")
@SessionAttributes({ "autoridadMaxima", "personalizacion" })
@Controller
public class AutoridadMaximaController {
	private Util util = new Util();

	@RequestMapping(value = "/autoridadmaxima", method = RequestMethod.POST)
	public String create(
			@Valid @ModelAttribute("autoridadMaxima") AutoridadMaxima autoridadMaxima,
			BindingResult result, ModelMap modelMap, SessionStatus status,
			HttpServletRequest request) {
		Personalizacion personalizacion = Personalizacion
				.findPersonalizacion(util.traerIdPersonalizacion());

		// Metodo Para seteo de nombre en la tabla personalizacion
		String nombreA = autoridadMaxima.getNombre();
		personalizacion.setNombreMaximaAutoridad(nombreA);

		// Metodo para seteo de cedula en la tabla Personalizacion
		String c = autoridadMaxima.getCedula();
		if (autoridadMaxima.getEffectTypes() == 1) {
			c = "V-" + c;
		} else {
			c = "E-" + c;
		}
		autoridadMaxima.setCedula(c);

		if (autoridadMaxima == null)
			throw new IllegalArgumentException("A autoridadMaxima is required");
		if (result.hasErrors()) {
			modelMap.addAttribute("autoridadMaxima", new AutoridadMaxima());
			modelMap.addAttribute("estructuracargoses", EstructuraCargos
					.findEstructuraCargosesByRif(util.traerIdRif())
					.getResultList());
			return "autoridadmaxima/create";
		}

		String cargo = autoridadMaxima.getCargoAutoridad().getNombreCargo();
		autoridadMaxima.setRif(util.traerIdRif());
		autoridadMaxima.persist();
		personalizacion.setCargoMaximaAutoridad(cargo);
		personalizacion.merge();

		return "redirect:/autoridadmaxima/";
		// + autoridadMaxima.getAutoridadMaxima().getId();
	}

	@RequestMapping(value = "/autoridadmaxima/form", method = RequestMethod.GET)
	public String createForm(ModelMap modelMap) {

		modelMap.addAttribute("autoridadMaxima", new AutoridadMaxima());
		modelMap.addAttribute("estructuracargoses", EstructuraCargos
				.findEstructuraCargosesByRif(util.traerIdRif()).getResultList());
		return "autoridadmaxima/create";
	}

	@RequestMapping(value = "/autoridadmaxima", method = RequestMethod.GET)
	public String list(ModelMap modelMap) {
		modelMap.addAttribute("autoridadmaximas", AutoridadMaxima
				.findAutoridadMaximasByRif(util.traerIdRif()).getResultList());
		return "autoridadmaxima/list";
	}

	@RequestMapping(method = RequestMethod.PUT)
	public String update(@Valid AutoridadMaxima autoridadMaxima,
			BindingResult result, ModelMap modelMap) {
		if (autoridadMaxima == null)
			throw new IllegalArgumentException("A autoridadMaxima is required");

		Personalizacion personalizacion = Personalizacion
				.findPersonalizacion(util.traerIdPersonalizacion());

		// Metodo Para seteo de nombre en la tabla personalizacion
		String nombreA = autoridadMaxima.getNombre();
		personalizacion.setNombreMaximaAutoridad(nombreA);

		// Metodo para seteo de cedula en la tabla Personalizacion
		String c = autoridadMaxima.getCedula();
		if (autoridadMaxima.getEffectTypes() == 1) {
			c = "V-" + c;
		} else {
			c = "E-" + c;
		}
		autoridadMaxima.setCedula(c);

		if (result.hasErrors()) {
			modelMap.addAttribute("autoridadMaxima", autoridadMaxima);
			modelMap.addAttribute("estructuracargoses", EstructuraCargos
					.findEstructuraCargosesByRif(util.traerIdRif())
					.getResultList());
			modelMap.addAttribute("organismoentes",
					OrganismoEnte.findAllOrganismoEntes());
			return "autoridadmaxima/update";
		}

		String cargo = autoridadMaxima.getCargoAutoridad().getNombreCargo();
		personalizacion.setCargoMaximaAutoridad(cargo);
		personalizacion.merge();
		autoridadMaxima.merge();
		return "redirect:/autoridadmaxima/" + autoridadMaxima.getId();
	}

	@RequestMapping(value = "/autoridadmaxima/{id}/form", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
		if (id == null)
			throw new IllegalArgumentException("An Identifier is required");
		AutoridadMaxima autoridadMaxima = AutoridadMaxima
				.findAutoridadMaxima(id);
		String cedula = autoridadMaxima.getCedula();
		cedula = cedula.substring(2);
		autoridadMaxima.setCedula(cedula);
		modelMap.addAttribute("autoridadMaxima", autoridadMaxima);
		modelMap.addAttribute("estructuracargoses", EstructuraCargos
				.findEstructuraCargosesByRif(util.traerIdRif()).getResultList());
		return "autoridadmaxima/update";
	}

}
