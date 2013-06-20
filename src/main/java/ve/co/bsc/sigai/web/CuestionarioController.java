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

import java.io.IOException;
import java.util.List;

import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

import ve.co.bsc.sigai.domain.Actuacion;
import ve.co.bsc.sigai.domain.Cuestionario;
import ve.co.bsc.sigai.domain.ItemCuestionario;
import ve.co.bsc.sigai.domain.Unidad;
import ve.co.bsc.util.Util;

@RooWebScaffold(path = "cuestionario", automaticallyMaintainView = true, formBackingObject = Cuestionario.class)
@RequestMapping("/cuestionario/**")
@SessionAttributes({ "cuestionario", "idPapelDeTrabajo" })
@Controller
public class CuestionarioController {
	private static Logger logger = Logger
			.getLogger(CuestionarioController.class);

	@RequestMapping(value = "/cuestionario/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") Long id,
			@RequestParam("idPapelDeTrabajo") long idPapelDeTrabajo,
			ModelMap modelMap, SessionStatus status) {
		if (id == null)
			throw new IllegalArgumentException("An Identifier is required");

		Cuestionario miCuestionario = Cuestionario.findCuestionario(id);
		Query queryAvances = ItemCuestionario
				.findItemCuestionariosByCuestionario(miCuestionario);
		List<ItemCuestionario> itemCuestionarios = queryAvances.getResultList();
		modelMap.addAttribute("itemCuestionarios", itemCuestionarios);
		modelMap.addAttribute("cuestionario", miCuestionario);
		modelMap.addAttribute("idPapelDeTrabajo", idPapelDeTrabajo);
		status.setComplete();
		return "cuestionario/show";
	}

	@RequestMapping(value = "/cuestionario", method = RequestMethod.POST)
	public String create(
			@Valid @ModelAttribute("cuestionario") Cuestionario cuestionario,
			@Valid @ModelAttribute("idPapelDeTrabajo") long idPapelDeTrabajo,
			BindingResult result, ModelMap modelMap, SessionStatus status,
			HttpServletRequest request) {
		if (cuestionario == null)
			throw new IllegalArgumentException("A cuestionario is required");
		if (result.hasErrors()) {
			modelMap.addAttribute("cuestionario", cuestionario);
			modelMap.addAttribute("idPapelDeTrabajo", idPapelDeTrabajo);
			// modelMap.addAttribute("idPrueba", idPrueba);
			return "cuestionario/create";
		}

		cuestionario.setRespondido(false);
		cuestionario.persist();
		// modelMap.addAttribute("idPrueba",idPrueba);
		Util.registrarEntradaEnBitacora(cuestionario,
				"Se creó el Cuestionario ", request.getRemoteAddr());
		status.setComplete();

		return "redirect:/papeldetrabajo/" + idPapelDeTrabajo;
		// return "redirect:/cuestionario/" + cuestionario.getId();
	}

	@RequestMapping(value = "/cuestionario/form", method = RequestMethod.GET)
	public String createForm(@RequestParam("idActuacion") long idActuacion,
			@RequestParam("idPapelDeTrabajo") long idPapelDeTrabajo,
			ModelMap modelMap) {
		Cuestionario cuestionario = new Cuestionario();
		Actuacion actuacion = Actuacion.findActuacion(idActuacion);
		List<Cuestionario> misCuestionarios = Cuestionario
				.findCuestionariosByActuacion(actuacion).getResultList();

		int mayor = 0;
		for (int i = 0; i < misCuestionarios.size(); i++) {
			if (misCuestionarios.get(i).getCorrelativo() > mayor) {
				mayor = misCuestionarios.get(i).getCorrelativo();
			}
		}
		cuestionario.setCorrelativo(mayor + 1);

		cuestionario.setActuacion(actuacion);
		modelMap.addAttribute("cuestionario", cuestionario);
		modelMap.addAttribute("idPapelDeTrabajo", idPapelDeTrabajo);
		return "cuestionario/create";
	}

	@RequestMapping(method = RequestMethod.PUT)
	public String update(
			@Valid @ModelAttribute("cuestionario") Cuestionario cuestionario,
			@Valid @ModelAttribute("idPapelDeTrabajo") long idPapelDeTrabajo,
			BindingResult result, ModelMap modelMap, SessionStatus status,
			HttpServletRequest request) {
		if (cuestionario == null)
			throw new IllegalArgumentException("A cuestionario is required");
		if (result.hasErrors()) {
			modelMap.addAttribute("cuestionario", cuestionario);
			modelMap.addAttribute("idPapelDeTrabajo", idPapelDeTrabajo);
			return "cuestionario/update";
		}
		cuestionario.merge();
		Util.registrarEntradaEnBitacora(cuestionario,
				"Se modificó el Cuestionario ", request.getRemoteAddr());
		// return "redirect:/cuestionario/" + cuestionario.getId();
		status.setComplete();
		return "redirect:/papeldetrabajo/" + idPapelDeTrabajo;
	}

	@RequestMapping(value = "/cuestionario/{id}/form", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id,
			@RequestParam("idPapelDeTrabajo") long idPapelDeTrabajo,
			ModelMap modelMap) {
		if (id == null)
			throw new IllegalArgumentException("An Identifier is required");
		modelMap.addAttribute("cuestionario", Cuestionario.findCuestionario(id));
		modelMap.addAttribute("idPapelDeTrabajo", idPapelDeTrabajo);

		return "cuestionario/update";
	}

	@RequestMapping(value = "/cuestionario/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("id") Long id,
			@RequestParam("idPapelDeTrabajo") long idPapelDeTrabajo)

	{
		if (id == null)
			throw new IllegalArgumentException("An Identifier is required");
		Cuestionario c = Cuestionario.findCuestionario(id);
		c.remove();
		return "redirect:/papeldetrabajo/" + idPapelDeTrabajo;
	}

	@RequestMapping(value = "/cuestionario/aplicar/{id}/form", method = RequestMethod.GET)
	public String aplicarForm(@PathVariable("id") Long id,
			@RequestParam("idPapelDeTrabajo") long idPapelDeTrabajo,
			ModelMap modelMap) {
		Util util = new Util();
		if (id == null)
			throw new IllegalArgumentException("An Identifier is required");
		modelMap.addAttribute("cuestionario", Cuestionario.findCuestionario(id));
		// modelMap.addAttribute("auditadoes", Auditado.findAllAuditadoes());
		modelMap.addAttribute("unidades",
				Unidad.findUnidadsByRif(util.traerIdRif()).getResultList());
		modelMap.addAttribute("idPapelDeTrabajo", idPapelDeTrabajo);

		return "cuestionario/aplicar";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String aplicar(
			@Valid @ModelAttribute("cuestionario") Cuestionario cuestionario,
			@Valid @ModelAttribute("idPapelDeTrabajo") long idPapelDeTrabajo,
			BindingResult result, ModelMap modelMap, SessionStatus status,
			HttpServletRequest request) {
		Util util = new Util();
		if (cuestionario == null)
			throw new IllegalArgumentException("A cuestionario is required");
		if (result.hasErrors()) {
			modelMap.addAttribute("cuestionario", cuestionario);
			// modelMap.addAttribute("auditadoes",
			// Auditado.findAllAuditadoes());
			modelMap.addAttribute("unidades",
					Unidad.findUnidadsByRif(util.traerIdRif()).getResultList());
			modelMap.addAttribute("idPapelDeTrabajo", idPapelDeTrabajo);
			return "cuestionario/aplicar";
		}

		cuestionario.setAuditado(cuestionario.getUnidad().getResponsable());
		cuestionario.merge();
		Util.registrarEntradaEnBitacora(cuestionario,
				"Se asignó a un auditado el Cuestionario ",
				request.getRemoteAddr());
		status.setComplete();
		return "redirect:/cuestionario/" + cuestionario.getId()
				+ "?idPapelDeTrabajo=" + idPapelDeTrabajo;
	}

	@RequestMapping(value = "/cuestionario/completo/{id}", method = RequestMethod.GET)
	public String showCuestionarioCompleto(@PathVariable("id") Long id,
			@RequestParam("idPapelDeTrabajo") long idPapelDeTrabajo,
			// @RequestParam("idPrueba") long idPrueba,
			ModelMap modelMap, SessionStatus status) {
		if (id == null)
			throw new IllegalArgumentException("An Identifier is required");

		Cuestionario miCuestionario = Cuestionario.findCuestionario(id);
		Query queryAvances = ItemCuestionario
				.findItemCuestionariosByCuestionario(miCuestionario);
		List<ItemCuestionario> itemCuestionarios = queryAvances.getResultList();
		modelMap.addAttribute("itemCuestionarios", itemCuestionarios);
		// modelMap.addAttribute("idPrueba", idPrueba);
		// modelMap.addAttribute("actividad",
		// ActividadActuacion.findActividadActuacion(idPrueba));
		modelMap.addAttribute("cuestionario", miCuestionario);
		modelMap.addAttribute("idPapelDeTrabajo", idPapelDeTrabajo);
		status.setComplete();
		return "cuestionario/cuestionarioCompleto";
	}

	@RequestMapping(value = "/cuestionario/renderArchivoAdjunto/{id}", method = RequestMethod.GET)
	public void renderArchivoAdjunto(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response,
			@PathVariable("id") Long id) {
		ItemCuestionario miItem = ItemCuestionario.findItemCuestionario(id);
		try {
			response.setContentType("");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ miItem.getFileName() + "." + miItem.getFileExtension());
			response.getOutputStream().write(miItem.getFiledata());
		} catch (IOException e) {
			logger.error("Error renderizando archivo ", e);
		}
	}

	// Este metodo arma el cuestionario que verá el auditado
	@RequestMapping(value = "/cuestionario/mostrar/{id}", method = RequestMethod.GET)
	public String mostrar(@PathVariable("id") Long id, ModelMap modelMap,
			SessionStatus status) {
		if (id == null)
			throw new IllegalArgumentException("An Identifier is required");

		Cuestionario miCuestionario = Cuestionario.findCuestionario(id);
		List<ItemCuestionario> itemCuestionarios = ItemCuestionario
				.findItemCuestionariosByCuestionario(miCuestionario)
				.getResultList();
		modelMap.addAttribute("itemCuestionarios", itemCuestionarios);
		modelMap.addAttribute("cuestionario", miCuestionario);

		return "cuestionario/mostrar";
	}
}
