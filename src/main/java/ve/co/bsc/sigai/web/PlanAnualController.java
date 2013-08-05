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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import ve.co.bsc.sigai.domain.Actuacion;
import ve.co.bsc.sigai.domain.EstadoPlan;
import ve.co.bsc.sigai.domain.ItemPlanificacionActuacion;
import ve.co.bsc.sigai.domain.OrganismoEnte;
import ve.co.bsc.sigai.domain.OtraActividad;
import ve.co.bsc.sigai.domain.PlanAnual;
import ve.co.bsc.sigai.service.JbpmService;
import ve.co.bsc.util.Util;

/*
 * Controlador para plan anual (CRUD) generado por ROO
 * Recibe todas las solicitudes a URLs con el patron
 * "/plananual/**"
 */
@RooWebScaffold(path = "plananual", automaticallyMaintainView = true, formBackingObject = PlanAnual.class)
@RequestMapping("/plananual/**")
@SessionAttributes({ "planAnual" })
@Controller
public class PlanAnualController {

	Logger logger = Logger.getLogger(PlanAnual.class);
	@Autowired
	private JbpmService jbpmService;
	private static int año;

	@RequestMapping(value = "/plananual", method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			ModelMap modelMap) {
		Util util = new Util();
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			modelMap.addAttribute("plananuals", PlanAnual.findPlanAnualEntries(
					page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
			float nrOfPages = (float) PlanAnual.countPlanAnuals() / sizeNo;
			modelMap.addAttribute(
					"maxPages",
					(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
							: nrOfPages));
		} else {
			if (util.rolUsuarioLoggeado().equals("[UNIDAD_COORDINADOR]")
					|| util.rolUsuarioLoggeado().equals("[UNIDAD_AUDITOR]")
					|| util.rolUsuarioLoggeado().equals("[UNIDAD_GERENTE]")) {
				if (util.traerIdRif() != null) {
					modelMap.addAttribute("plananuals", PlanAnual
							.findPlanAnualsByRif(util.traerIdRif())
							.getResultList());
				}
			} else {
				modelMap.addAttribute("plananuals",
						PlanAnual.findAllPlanAnuals());
			}
		}
		return "plananual/list";
	}

	@RequestMapping(value = "/plananual", method = RequestMethod.POST)
	public String create(@Valid PlanAnual planAnual, BindingResult result,
			ModelMap modelMap, SessionStatus status, HttpServletRequest request) {
		Util util = new Util();

		if (planAnual == null)
			throw new IllegalArgumentException("A planAnual is required");

		List<PlanAnual> list = PlanAnual.findAllPlanAnuals();
		for (PlanAnual anual : list) {
			logger.info("El año seleccionada de la lista es: "
					+ anual.getAnoFiscal());
			logger.info("El año ingresado en la vista es: "
					+ planAnual.getAnoFiscal());
			int a = planAnual.getAnoFiscal();
			int b = anual.getAnoFiscal();
			if (a == b) {
				logger.info("Entrando a la validacion de los años fiscales iguales");
				result.addError(new FieldError(result.getObjectName(),
						"anoFiscal", "El año fical " + a + " ya se "
								+ "encuentra registrado en el sistema"));
				break;
			}
		}

		if (result.hasErrors()) {
			modelMap.addAttribute("planAnual", planAnual);
			modelMap.addAttribute("estadoplans",
					EstadoPlan.findAllEstadoPlans());
			modelMap.addAttribute("organismoentes",
					OrganismoEnte.findAllOrganismoEntes());
			return "plananual/create";
		}

		// Se asigna el estadoPlan por defecto que es "En Proceso" por eso se
		// cablea a id 1
		EstadoPlan estadoPlanEnProceso = EstadoPlan.findEstadoPlan(new Long(1));
		planAnual.setEstadoPlan(estadoPlanEnProceso);
		planAnual.setRif(util.traerIdRif());
		planAnual.persist();
		Util.registrarEntradaEnBitacora(planAnual, "Se creó el Plan Anual ",
				request.getRemoteAddr());
		status.setComplete();

		return "redirect:/plananual/" + planAnual.getId();
	}

	@RequestMapping(value = "/plananual/form", method = RequestMethod.GET)
	public String createForm(ModelMap modelMap) {

		modelMap.addAttribute("planAnual", new PlanAnual());
		modelMap.addAttribute("estadoplans", EstadoPlan.findAllEstadoPlans());
		modelMap.addAttribute("organismoentes",
				OrganismoEnte.findAllOrganismoEntes());
		return "plananual/create";
	}

	@RequestMapping(value = "/plananual/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") Long id, ModelMap modelMap) {
		if (id == null) {
			throw new IllegalArgumentException("An Identifier is required");
		}

		// Se arma la lista "actuacions" con las actuaciones que estén
		// relacionadas con el planAnual de id suministrado
		PlanAnual planAnual = PlanAnual.findPlanAnual(id);
		List<Actuacion> actuacions = new ArrayList<Actuacion>();
		;
		List<OtraActividad> otrasActividades = new ArrayList<OtraActividad>();
		;
		List<ItemPlanificacionActuacion> items = ItemPlanificacionActuacion
				.findItemPlanificacionActuacionsByPlanAnual(planAnual)
				.getResultList();
		for (int i = 0; i < items.size(); i++) {
			if (((ItemPlanificacionActuacion) items.get(i)).getActuacion() != null) {
				actuacions.add(((ItemPlanificacionActuacion) items.get(i))
						.getActuacion());
			}
			if (((ItemPlanificacionActuacion) items.get(i)).getOtraActividad() != null) {
				otrasActividades
						.add(((ItemPlanificacionActuacion) items.get(i))
								.getOtraActividad());
			}
		}

		Calendar FechaActual = Calendar.getInstance();
		int mes = FechaActual.get(Calendar.MONTH);
		int a = FechaActual.get(Calendar.YEAR);

		// Listo Los Auditores
		modelMap.addAttribute("actuacions", actuacions);
		modelMap.addAttribute("otrasActividades", otrasActividades);
		modelMap.addAttribute("planAnual", planAnual);
		modelMap.addAttribute("objetoComentable", planAnual);
		modelMap.addAttribute("fechaActual", mes + 1);
		modelMap.addAttribute("anoActual", a);

		logger.debug("solicitando revision de plan en motor de reglas");
		jbpmService.executeRulesStateless(planAnual);
		logger.debug("revision de plan anual en motor de reglas listo");
		modelMap.addAttribute("accionesPermitidas",
				planAnual.getAccionesPermitidas());

		return "plananual/show";
	}

	@RequestMapping(method = RequestMethod.PUT)
	public String update(
			@Valid @ModelAttribute("planAnual") PlanAnual planAnual,
			BindingResult result, ModelMap modelMap, SessionStatus status,
			HttpServletRequest request) {

		if (planAnual == null)
			throw new IllegalArgumentException("A planAnual is required");

		if (año != planAnual.getAnoFiscal()) {
			logger.info("El año fiscal que se desea actualizar es diferente al "
					+ "original");
			List<PlanAnual> list = PlanAnual.findAllPlanAnuals();
			for (PlanAnual anual : list) {
				logger.info("El año seleccionada de la lista es: "
						+ anual.getAnoFiscal());
				logger.info("El año ingresado en la vista es: "
						+ planAnual.getAnoFiscal());
				int a = planAnual.getAnoFiscal();
				int b = anual.getAnoFiscal();
				if (a == b) {
					logger.info("Entrando a la validacion de los años fiscales iguales");
					result.addError(new FieldError(result.getObjectName(),
							"anoFiscal", "El año fical " + a + " ya se "
									+ "encuentra registrado en el sistema"));
					break;
				}
			}
		}

		if (result.hasErrors()) {
			modelMap.addAttribute("planAnual", planAnual);
			modelMap.addAttribute("estadoplans",
					EstadoPlan.findAllEstadoPlans());
			return "plananual/update";
		}

		planAnual.merge();
		Util.registrarEntradaEnBitacora(planAnual,
				"Se modificó el plan anual ", request.getRemoteAddr());
		status.setComplete();
		return "redirect:/plananual/" + planAnual.getId();
	}

	@RequestMapping(value = "/plananual/{id}/form", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, ModelMap modelMap) {

		if (id == null) {
			throw new IllegalArgumentException("An Identifier is required");
		}

		PlanAnual planAnual = PlanAnual.findPlanAnual(id);
		año = planAnual.getAnoFiscal();
		logger.info("El año fiscal optenido del registro a actualizar es: "
				+ año);

		modelMap.addAttribute("planAnual", planAnual);
		modelMap.addAttribute("estadoplans", EstadoPlan.findAllEstadoPlans());
		return "plananual/update";
	}

	@RequestMapping(value = "/plananual/actualizarEstatus/{id}", method = RequestMethod.POST)
	public String actualizarEstatus(@Valid PlanAnual planAnual,
			BindingResult result, ModelMap modelMap, HttpServletRequest request) {

		if (planAnual == null) {
			throw new IllegalArgumentException("A planAnual is required");
		}
		if (result.hasErrors()) {
			modelMap.addAttribute("planAnual", planAnual);
			modelMap.addAttribute("estadoplans",
					EstadoPlan.findAllEstadoPlans());
			return "plananual/actualizarEstatus";
		}
		planAnual.merge();
		Util.registrarEntradaEnBitacora(planAnual,
				"Se actualizó el estatus del plan anual ",
				request.getRemoteAddr());

		return "redirect:/plananual/" + planAnual.getId();
	}

	@RequestMapping(value = "/plananual/actualizarEstatus/{id}/form", method = RequestMethod.GET)
	public String actualizarEstatusForm(@PathVariable("id") Long id,
			ModelMap modelMap) {

		if (id == null) {
			throw new IllegalArgumentException("An Identifier is required");
		}
		modelMap.addAttribute("planAnual", PlanAnual.findPlanAnual(id));
		modelMap.addAttribute("estadoplans", EstadoPlan.findAllEstadoPlans());

		return "plananual/actualizarEstatus";
	}

	@RequestMapping(value = "/plananual/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("id") Long id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size) {
		if (id == null) {
			throw new IllegalArgumentException("An Identifier is required");
		}
		// try {
		PlanAnual.findPlanAnual(id).remove();
		// }catch(Exception e)
		// {
		// logger.debug("La excepcion al eliminar es: " + e);
		// throw e;
		// }
		return "redirect:/plananual?page="
				+ ((page == null) ? "1" : page.toString()) + "&size="
				+ ((size == null) ? "10" : size.toString());
	}
}
