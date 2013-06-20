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

import java.util.LinkedList;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import ve.co.bsc.sigai.domain.AsignarOrganismo;
import ve.co.bsc.sigai.domain.Auditor;
import ve.co.bsc.sigai.domain.OrganismoEnte;
import ve.co.bsc.sigai.domain.RolUsuario;
import ve.co.bsc.sigai.domain.Usuario;
import ve.co.bsc.sigai.service.HumanTaskService;

@RooWebScaffold(path = "asignarorganismo", automaticallyMaintainView = true, formBackingObject = AsignarOrganismo.class)
@RequestMapping("/asignarorganismo/**")
@SessionAttributes({ "asignarOrganismo, auditor" })
@Controller
public class AsignarOrganismoController {

	Logger logger = Logger.getLogger(AsignarOrganismoController.class);

	@Autowired
	HumanTaskService humanTaskService;

	@RequestMapping(value = "/asignarorganismo", method = RequestMethod.POST)
	public String create(@Valid AsignarOrganismo asignarOrganismo,
			BindingResult result, ModelMap modelMap) {

		List<Auditor> todos = Auditor.findAllAuditors();
		List<Auditor> losQueSon = new LinkedList<Auditor>();
		for (Auditor a : todos) {
			List<Usuario> usuarios = Usuario.findUsuariosByLogin(a.getLogin())
					.getResultList();

			for (Usuario u : usuarios) {
				for (RolUsuario r : u.getRoles()) {
					if (r.getNombre().equals("ROLE_SUNAI_SUPERVISOR")) {
						losQueSon.add(a);
						break;
					}

				}
			}
		}

		if (asignarOrganismo == null)
			throw new IllegalArgumentException("A asignarOrganismo is required");
		if (result.hasErrors()) {
			modelMap.addAttribute("asignarOrganismo", asignarOrganismo);
			modelMap.addAttribute("auditors", losQueSon);
			modelMap.addAttribute("organismoentes",
					OrganismoEnte.findAllOrganismoEntes());
			return "asignarorganismo/create";
		}

		asignarOrganismo.persist();
		return "redirect:/asignarorganismo/" + asignarOrganismo.getId();
	}

	@RequestMapping(value = "/asignarorganismo/form", method = RequestMethod.GET)
	public String createForm(ModelMap modelMap) {

		List<Auditor> todos = Auditor.findAllAuditors();
		List<Auditor> losQueSon = new LinkedList<Auditor>();
		for (Auditor a : todos) {
			List<Usuario> usuarios = Usuario.findUsuariosByLogin(a.getLogin())
					.getResultList();

			for (Usuario u : usuarios) {
				for (RolUsuario r : u.getRoles()) {
					if (r.getNombre().equals("ROLE_SUNAI_ANALISTA")) {
						losQueSon.add(a);
						break;
					}

				}
			}
		}

		modelMap.addAttribute("asignarOrganismo", new AsignarOrganismo());
		modelMap.addAttribute("auditors", losQueSon);
		modelMap.addAttribute("organismoentes",
				OrganismoEnte.findAllOrganismoEntes());
		return "asignarorganismo/create";
	}

}
