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

import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ve.co.bsc.sigai.domain.ActividadActuacion;
import ve.co.bsc.sigai.domain.ActividadGeneral;
import ve.co.bsc.sigai.domain.Actuacion;
import ve.co.bsc.sigai.domain.Auditor;
import ve.co.bsc.sigai.domain.Documento;
import ve.co.bsc.sigai.domain.EstadoActividadActuacion;
import ve.co.bsc.sigai.domain.ObjetivoEspecifico;
import ve.co.bsc.sigai.domain.RolUsuario;
import ve.co.bsc.sigai.domain.Usuario;
import ve.co.bsc.util.Util;

@RooWebScaffold(path = "actividadactuacion", automaticallyMaintainView = true, formBackingObject = ActividadActuacion.class)
@RequestMapping("/actividadactuacion/**")
@Controller
public class ActividadActuacionController {

	@RequestMapping(value = "/actividadactuacion/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") Long id, ModelMap modelMap) {
		if (id == null)
			throw new IllegalArgumentException("An Identifier is required");
		ActividadActuacion actividadActuacion = ActividadActuacion
				.findActividadActuacion(id);

		// **********************************************************************************************
		// Se verifica si la actividadActuacion es de tipo ActividadGeneral
		if (actividadActuacion.getClass().equals(ActividadGeneral.class)) {
			return "redirect:/actividadgeneral/"
					+ ((ActividadGeneral) actividadActuacion).getId();
		}
		// **********************************************************************************************

		modelMap.addAttribute("actividadActuacion", actividadActuacion);
		return "actividadactuacion/show";
	}

	@RequestMapping(value = "/actividadactuacion/{id}/form", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
		if (id == null)
			throw new IllegalArgumentException("An Identifier is required");
		ActividadActuacion actividadActuacion = ActividadActuacion
				.findActividadActuacion(id);

		// **********************************************************************************************
		// Se verifica si la actividadActuacion es de tipo ActividadGeneral
		if (actividadActuacion.getClass().equals(ActividadGeneral.class)) {
			return "redirect:/actividadgeneral/"
					+ ((ActividadGeneral) actividadActuacion).getId() + "/form";
		}
		// **********************************************************************************************

		// Metodo para traer los usuario de la UAI de una institucion en
		// especifico
		Util util = new Util();
		Query query = Auditor.findAuditorsById_OrganismoEnte(util.traerIdRif());
		List<Auditor> todos = query.getResultList();
		List<Auditor> losQueSon = new LinkedList<Auditor>();
		for (Auditor a : todos) {
			List<Usuario> usuarios = Usuario.findUsuariosByLogin(a.getLogin())
					.getResultList();
			for (Usuario u : usuarios) {
				for (RolUsuario r : u.getRoles()) {
					if (r.getNombre().equals("ROLE_UNIDAD_COORDINADOR")) {
						losQueSon.add(a);
						break;
					} else if (r.getNombre().equals("ROLE_UNIDAD_AUDITOR")) {
						losQueSon.add(a);
						break;
					} else if (r.getNombre().equals("ROLE_UNIDAD_GERENTE")) {
						losQueSon.add(a);
						break;
					}
				}
			}
		}

		modelMap.addAttribute("actividadActuacion", actividadActuacion);
		modelMap.addAttribute("actuacions", Actuacion.findAllActuacions());
		modelMap.addAttribute("auditors", losQueSon);
		return "actividadactuacion/update";
	}

	@RequestMapping(value = "/actividadactuacion", method = RequestMethod.POST)
	public String create(@Valid ActividadActuacion actividadActuacion,
			BindingResult result, ModelMap modelMap, HttpServletRequest request) {
		if (actividadActuacion == null)
			throw new IllegalArgumentException(
					"A actividadActuacion is required");

		// Metodo para traer los usuario de la UAI de una institucion en
		// especifico
		Util util = new Util();
		Query query = Auditor.findAuditorsById_OrganismoEnte(util.traerIdRif());
		List<Auditor> todos = query.getResultList();
		List<Auditor> losQueSon = new LinkedList<Auditor>();
		for (Auditor a : todos) {
			List<Usuario> usuarios = Usuario.findUsuariosByLogin(a.getLogin())
					.getResultList();
			for (Usuario u : usuarios) {
				for (RolUsuario r : u.getRoles()) {
					if (r.getNombre().equals("ROLE_UNIDAD_COORDINADOR")) {
						losQueSon.add(a);
						break;
					} else if (r.getNombre().equals("ROLE_UNIDAD_AUDITOR")) {
						losQueSon.add(a);
						break;
					} else if (r.getNombre().equals("ROLE_UNIDAD_GERENTE")) {
						losQueSon.add(a);
						break;
					}
				}
			}
		}

		if (result.hasErrors()) {
			modelMap.addAttribute("actividadActuacion", actividadActuacion);
			modelMap.addAttribute("actividadactuacions",
					ActividadActuacion.findAllActividadActuacions());
			modelMap.addAttribute("actuacions", Actuacion.findAllActuacions());
			modelMap.addAttribute("auditors", losQueSon);
			modelMap.addAttribute("documentoes", Documento.findAllDocumentoes());
			modelMap.addAttribute("estadoactividadactuacions",
					EstadoActividadActuacion.findAllEstadoActividadActuacions());
			modelMap.addAttribute("objetivoespecificoes",
					ObjetivoEspecifico.findAllObjetivoEspecificoes());
			return "actividadactuacion/create";
		}
		actividadActuacion.persist();
		Util.registrarEntradaEnBitacora(actividadActuacion,
				"Se creó la actividad ", request.getRemoteAddr());
		return "redirect:/actividadactuacion/" + actividadActuacion.getId();
	}

	@RequestMapping(method = RequestMethod.PUT)
	public String update(@Valid ActividadActuacion actividadActuacion,
			BindingResult result, ModelMap modelMap, HttpServletRequest request) {
		if (actividadActuacion == null)
			throw new IllegalArgumentException(
					"A actividadActuacion is required");

		// Metodo para traer los usuario de la UAI de una institucion en
		// especifico
		Util util = new Util();
		Query query = Auditor.findAuditorsById_OrganismoEnte(util.traerIdRif());
		List<Auditor> todos = query.getResultList();
		List<Auditor> losQueSon = new LinkedList<Auditor>();
		for (Auditor a : todos) {
			List<Usuario> usuarios = Usuario.findUsuariosByLogin(a.getLogin())
					.getResultList();
			for (Usuario u : usuarios) {
				for (RolUsuario r : u.getRoles()) {
					if (r.getNombre().equals("ROLE_UNIDAD_COORDINADOR")) {
						losQueSon.add(a);
						break;
					} else if (r.getNombre().equals("ROLE_UNIDAD_AUDITOR")) {
						losQueSon.add(a);
						break;
					} else if (r.getNombre().equals("ROLE_UNIDAD_GERENTE")) {
						losQueSon.add(a);
						break;
					}
				}
			}
		}

		if (result.hasErrors()) {
			modelMap.addAttribute("actividadActuacion", actividadActuacion);
			modelMap.addAttribute("actividadactuacions",
					ActividadActuacion.findAllActividadActuacions());
			modelMap.addAttribute("actuacions", Actuacion.findAllActuacions());
			modelMap.addAttribute("auditors", losQueSon);
			modelMap.addAttribute("documentoes", Documento.findAllDocumentoes());
			modelMap.addAttribute("estadoactividadactuacions",
					EstadoActividadActuacion.findAllEstadoActividadActuacions());
			modelMap.addAttribute("objetivoespecificoes",
					ObjetivoEspecifico.findAllObjetivoEspecificoes());
			return "actividadactuacion/update";
		}
		actividadActuacion.merge();
		Util.registrarEntradaEnBitacora(actividadActuacion,
				"Se modificó la actividad ", request.getRemoteAddr());
		return "redirect:/actividadactuacion/" + actividadActuacion.getId();
	}
	
}
