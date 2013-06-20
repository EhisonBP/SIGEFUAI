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

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import ve.co.bsc.sigai.domain.Comentario;

@RooWebScaffold(path = "comentario", automaticallyMaintainView = true, formBackingObject = Comentario.class)
@RequestMapping("/comentario/**")
@SessionAttributes({ "comentario" })
@Controller
public class ComentarioController {

	Logger logger = Logger.getLogger(ComentarioController.class);

	@RequestMapping(value = "/comentario", method = RequestMethod.GET)
	public String list(@RequestParam("id") Long id,
			@RequestParam("tipo") String tipo, ModelMap modelMap) {
		logger.debug("Ejecutando el list de comentarios");
		modelMap.addAttribute("comentarios", Comentario
				.findComentariosByTipoAndId(tipo, id).getResultList());
		// modelMap.addAttribute("comentarios",
		// Comentario.findAllComentarios());
		Comentario comentario = new Comentario();
		comentario.setId(id);
		comentario.setTipo(tipo);
		modelMap.addAttribute("comentario", comentario);

		modelMap.addAttribute("id", id);
		modelMap.addAttribute("tipo", tipo);
		logger.debug("Terminando de ejecutar el list de comentarios");
		return "comentario/list";
	}

	@RequestMapping(value = "/comentario", method = RequestMethod.POST)
	public String create(
			@Valid @ModelAttribute("comentario") Comentario comentario,
			BindingResult result, ModelMap modelMap, SessionStatus status) {
		if (comentario == null)
			throw new IllegalArgumentException("A comentario is required");
		if (result.hasErrors()) {
			modelMap.addAttribute("comentario", comentario);
			return "comentario/create";
		}
		logger.debug("Comentario id: " + comentario.getId() + " Comentario: "
				+ comentario.getRedaccionComentario());
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		comentario.setUsuario(((User) principal).getUsername().toString());
		comentario.persist();
		status.setComplete();

		return "redirect:/comentario?id=" + comentario.getId() + "&tipo="
				+ comentario.getTipo();
	}

}
