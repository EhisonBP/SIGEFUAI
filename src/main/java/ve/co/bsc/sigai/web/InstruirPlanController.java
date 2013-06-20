package ve.co.bsc.sigai.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.drools.runtime.process.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import ve.co.bsc.sigai.domain.EstadoAuditor;
import ve.co.bsc.sigai.domain.InstruirPlan;
import ve.co.bsc.sigai.domain.RolUsuario;
import ve.co.bsc.sigai.domain.Usuario;
import ve.co.bsc.sigai.service.JbpmService;
import ve.co.bsc.util.Util;

@RooWebScaffold(path = "instruirplan", automaticallyMaintainView = true, formBackingObject = InstruirPlan.class)
@RequestMapping("/instruirplan/**")
@SessionAttributes({ "instruirPlan" })
@Controller
/**
 * @author Ehison Perez Benedetti
 * 
 * Controlador para la instuccion del plan anual del sistema 
 */
public class InstruirPlanController {

	@Autowired
	private JbpmService jbpmService;

	@RequestMapping(value = "/instruirplan", method = RequestMethod.POST)
	public String create(@Valid InstruirPlan instruirPlan,
			BindingResult result, ModelMap modelMap, SessionStatus status,
			HttpServletRequest request) {
		Date fFin = instruirPlan.getFechaFin();
		Date fInicio = instruirPlan.getFechaInicio();
		String estado = instruirPlan.getEstadoInstruccion();
		int ano = instruirPlan.getAno();
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		int añoActual = calendar.get(java.util.Calendar.YEAR);
		String año = String.valueOf(ano);

		if (año.length() < 4 || año.length() > 4) {
			result.addError(new FieldError(result.getObjectName(), "ano",
					"Ingrese el año con un maximo de y minimo de 4 digitos"));
		}

		// Metodo para comparar que las fecha final no sea igual a la fecha de
		// fin
		if (fInicio != null && fFin != null) {
			if (fInicio.after(fFin)) {
				result.addError(new FieldError(result.getObjectName(),
						"fechaFin",
						"La fecha de Finalizacion no puede ser menor a la fecha de Inicio"));
			}
			// Verificacion que la fecha de fin no sea igual que lafecha de
			// inicio
			if (fInicio.equals(fFin)) {
				result.addError(new FieldError(result.getObjectName(),
						"fechaFin",
						"La fecha de fin no puede ser igual a la fecha de inicio"));
			}
		}
		/**
		 * Metodo para verificar que el año a crear ya no existe en labase de
		 * datos
		 */
		Query query = InstruirPlan.findInstruirPlansByAnoEquals(ano);
		List<InstruirPlan> pA = query.getResultList();
		if (pA.size() >= 1) {
			result.addError(new FieldError(result.getObjectName(), "ano",
					"Este Año Fiscal ya se encuentra Registrado en el Sistema"));
		}

		if (instruirPlan == null)
			throw new IllegalArgumentException("A intruirPlan is required");
		if (result.hasErrors()) {
			modelMap.addAttribute("instruirPlan", instruirPlan);
			modelMap.addAttribute("estadoauditors",
					EstadoAuditor.findAllEstadoAuditors());
			modelMap.addAttribute(
					"instruirPlan_fechaFin_date_format",
					org.joda.time.format.DateTimeFormat
							.patternForStyle(
									"S-",
									org.springframework.context.i18n.LocaleContextHolder
											.getLocale()));
			modelMap.addAttribute(
					"instruirPlan_fechaInicio_date_format",
					org.joda.time.format.DateTimeFormat
							.patternForStyle(
									"S-",
									org.springframework.context.i18n.LocaleContextHolder
											.getLocale()));
			return "instruirplan/create";
		}
		instruirPlan.persist();
		Util.registrarEntradaEnBitacora(instruirPlan,
				"Se creó la instruccion de plan anual ",
				request.getRemoteAddr());
		status.setComplete();
		// Metodod para Ejecutar el Flujo de Trabajo

		if (estado.equals("Activo")) {
			query = null;
			query = RolUsuario
					.findRolUsuariosByNombreEquals("ROLE_UNIDAD_GERENTE");
			List<RolUsuario> rolUsuarios = query.getResultList();
			query = null;
			query = Usuario.findUsuariosByRoles(rolUsuarios);
			List<Usuario> usuarios = query.getResultList();
			String login;
			int i = 0;

			for (Usuario a : usuarios) {
				login = null;
				login = a.getLogin();
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("login", login);
				parameters.put("inplan", instruirPlan);
				ProcessInstance p = jbpmService.startProcess(
						"ve.co.bsc.sigai.domain.bpm.lifecycle.InicioPlan",
						parameters, instruirPlan);
			}
		}
		return "redirect:/instruirplan/" + instruirPlan.getId();
	}

	@RequestMapping(value = "/instruirplan/form", method = RequestMethod.GET)
	public String createForm(ModelMap modelMap) {
		modelMap.addAttribute("instruirPlan", new InstruirPlan());
		modelMap.addAttribute("estadoauditors",
				EstadoAuditor.findAllEstadoAuditors());
		modelMap.addAttribute("instruirPlan_fechaFin_date_format",
				org.joda.time.format.DateTimeFormat.patternForStyle("S-",
						org.springframework.context.i18n.LocaleContextHolder
								.getLocale()));
		modelMap.addAttribute("instruirPlan_fechaInicio_date_format",
				org.joda.time.format.DateTimeFormat.patternForStyle("S-",
						org.springframework.context.i18n.LocaleContextHolder
								.getLocale()));
		return "instruirplan/create";
	}

	@RequestMapping(method = RequestMethod.PUT)
	public String update(@Valid InstruirPlan instruirPlan,
			BindingResult result, ModelMap modelMap) {

		Date fFin = instruirPlan.getFechaFin();
		Date fInicio = instruirPlan.getFechaInicio();
		String estado = instruirPlan.getEstadoInstruccion();
		int ano = instruirPlan.getAno();
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		int añoActual = calendar.get(java.util.Calendar.YEAR);
		// Metodo para comparar que las fecha final no sea igual a la fecha de
		// fin
		if (fInicio != null && fFin != null) {
			if (fInicio.after(fFin)) {
				result.addError(new FieldError(result.getObjectName(),
						"fechaFin",
						"La fecha de Finalizacion no puede ser menor a la fecha de Inicio"));
			}
			// Verificacion que la fecha de fin no sea igual que lafecha de
			// inicio
			if (fInicio.equals(fFin)) {
				result.addError(new FieldError(result.getObjectName(),
						"fechaFin",
						"La fecha de fin no puede ser igual a la fecha de inicio"));
			}
		}

		if (instruirPlan == null)
			throw new IllegalArgumentException("A instruirPlan is required");
		if (result.hasErrors()) {
			modelMap.addAttribute("instruirPlan", instruirPlan);
			modelMap.addAttribute("estadoauditors",
					EstadoAuditor.findAllEstadoAuditors());
			modelMap.addAttribute(
					"instruirPlan_fechaFin_date_format",
					org.joda.time.format.DateTimeFormat
							.patternForStyle(
									"S-",
									org.springframework.context.i18n.LocaleContextHolder
											.getLocale()));
			modelMap.addAttribute(
					"instruirPlan_fechaInicio_date_format",
					org.joda.time.format.DateTimeFormat
							.patternForStyle(
									"S-",
									org.springframework.context.i18n.LocaleContextHolder
											.getLocale()));
			return "instruirplan/update";
		}
		if (estado.equals("Activo")) {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("inplan", instruirPlan);
			ProcessInstance p = jbpmService.startProcess(
					"ve.co.bsc.sigai.domain.bpm.lifecycle.InicioPlan",
					parameters, instruirPlan);
		}
		instruirPlan.merge();
		return "redirect:/instruirplan/" + instruirPlan.getId();
	}

	@RequestMapping(value = "/instruirplan/{id}/form", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
		if (id == null)
			throw new IllegalArgumentException("An Identifier is required");
		modelMap.addAttribute("instruirPlan", InstruirPlan.findInstruirPlan(id));
		modelMap.addAttribute("estadoauditors",
				EstadoAuditor.findAllEstadoAuditors());
		modelMap.addAttribute("instruirPlan_fechaFin_date_format",
				org.joda.time.format.DateTimeFormat.patternForStyle("S-",
						org.springframework.context.i18n.LocaleContextHolder
								.getLocale()));
		modelMap.addAttribute("instruirPlan_fechaInicio_date_format",
				org.joda.time.format.DateTimeFormat.patternForStyle("S-",
						org.springframework.context.i18n.LocaleContextHolder
								.getLocale()));
		return "instruirplan/update";
	}

}