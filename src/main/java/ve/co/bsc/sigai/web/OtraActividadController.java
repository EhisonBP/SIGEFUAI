package ve.co.bsc.sigai.web;

import java.util.Calendar;
import java.util.Date;
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

import ve.co.bsc.sigai.domain.ItemPlanificacionActuacion;
import ve.co.bsc.sigai.domain.OtraActividad;
import ve.co.bsc.sigai.domain.PlanAnual;
import ve.co.bsc.sigai.domain.Unidad;
import ve.co.bsc.sigai.domain.UnidadDeMedida;
import ve.co.bsc.sigai.service.JbpmService;
import ve.co.bsc.util.Util;

@RooWebScaffold(path = "otraactividad", automaticallyMaintainView = true, formBackingObject = OtraActividad.class)
@RequestMapping("/otraactividad/**")
@SessionAttributes({ "otraActividad", "idPlanAnual" })
@Controller
public class OtraActividadController {

	private static final Logger logger = Logger
			.getLogger(OtraActividadController.class);

	@Autowired
	private JbpmService jbpmService;

	@RequestMapping(value = "/otraactividad", method = RequestMethod.POST)
	public String create(
			@ModelAttribute("idPlanAnual") long idPlanAnual,
			@Valid @ModelAttribute("otraActividad") OtraActividad otraActividad,
			BindingResult result, ModelMap modelMap, SessionStatus status,
			HttpServletRequest request) {
		Util util = new Util();
		PlanAnual miPlan = PlanAnual.findPlanAnual(idPlanAnual);
		if (otraActividad == null)
			throw new IllegalArgumentException("A otraActividad is required");

		Date fechaInicio = otraActividad.getFechaInicio();
		Date fechaFin = otraActividad.getFechaFin();
		if (fechaInicio == null) {
			result.addError(new FieldError(result.getObjectName(),
					"fechaInicio", "Campo obligatorio"));
		}
		if (fechaFin == null) {
			result.addError(new FieldError(result.getObjectName(), "fechaFin",
					"Campo obligatorio"));
		}
		if ((fechaInicio != null) && (fechaFin != null)) {
			if (fechaInicio.getTime() > fechaFin.getTime()) {
				result.addError(new FieldError(result.getObjectName(),
						"fechaFin", "Fecha inválida"));
			}
		}

		int añoI, añoF;
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(fechaInicio);
		añoI = calendario.get(Calendar.YEAR);
		calendario.setTime(fechaFin);
		añoF = calendario.get(Calendar.YEAR);
		int añoFiscal = miPlan.getAnoFiscal();
		// Validacion para que año seleccionado este dentro del año fiscal
		if (añoI != añoFiscal) {
			result.addError(new FieldError(result.getObjectName(),
					"fechaInicio",
					"El año seleccionado para el inicio de la actividad debe ser "
							+ "igual al año fiscal del plan anual."));
		}
		// Validacion para que año seleccionado este dentro del año fiscal
		if (añoF != añoFiscal) {
			result.addError(new FieldError(result.getObjectName(), "fechaFin",
					"El año seleccionado para la finalización de la actividad debe ser "
							+ "igual al año fiscal del plan anual."));
		}

		if (result.hasErrors()) {
			modelMap.addAttribute("otraActividad", otraActividad);
			modelMap.addAttribute("unidads",
					Unidad.findUnidadsByRif(util.traerIdRif()).getResultList());
			modelMap.addAttribute("unidaddemedidas",
					UnidadDeMedida.findAllUnidadDeMedidas());
			modelMap.addAttribute(
					"otraActividad_fechaFin_date_format",
					org.joda.time.format.DateTimeFormat
							.patternForStyle(
									"S-",
									org.springframework.context.i18n.LocaleContextHolder
											.getLocale()));
			modelMap.addAttribute(
					"otraActividad_fechaInicio_date_format",
					org.joda.time.format.DateTimeFormat
							.patternForStyle(
									"S-",
									org.springframework.context.i18n.LocaleContextHolder
											.getLocale()));
			return "otraactividad/create";
		}

		otraActividad.setRif(util.traerIdRif());

		otraActividad.persist();
		Util.registrarEntradaEnBitacora(otraActividad,
				"Se creó una actividad de tipo otra actividad",
				request.getRemoteAddr());

		// ******** Creo un itemPlanificacionActuacion con los datos de la
		// "otraactividad" ********
		ItemPlanificacionActuacion itemPlanificacion = new ItemPlanificacionActuacion();
		itemPlanificacion
				.setFechaInicioEstimada(otraActividad.getFechaInicio());
		itemPlanificacion.setFechaFinEstimada(otraActividad.getFechaFin());
		itemPlanificacion.setOtraActividad(otraActividad);
		itemPlanificacion.setPlanAnual(miPlan);
		itemPlanificacion.persist();
		Util.registrarEntradaEnBitacora(
				itemPlanificacion,
				"Para la actividad de tipo otras actividades "
						+ otraActividad.toStringLimitado()
						+ "se creó la planificación", request.getRemoteAddr());
		// **********************************************************************************

		status.setComplete();
		return "redirect:/plananual/" + idPlanAnual;
	}

	@RequestMapping(value = "/otraactividad/form", method = RequestMethod.GET)
	public String createForm(@RequestParam("idPlanAnual") long idPlanAnual,
			ModelMap modelMap) {
		Util util = new Util();
		OtraActividad otraActividad = new OtraActividad();

		int mayor = 0;
		List<ItemPlanificacionActuacion> items = ItemPlanificacionActuacion
				.findItemPlanificacionActuacionsByPlanAnualAndOtraActividadNotNull(
						PlanAnual.findPlanAnual(idPlanAnual)).getResultList();

		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getOtraActividad().getNumero() > mayor) {
				mayor = items.get(i).getOtraActividad().getNumero();
			}
		}

		otraActividad.setNumero(mayor + 1);

		modelMap.addAttribute("otraActividad", otraActividad);
		modelMap.addAttribute("unidads",
				Unidad.findUnidadsByRif(util.traerIdRif()).getResultList());
		modelMap.addAttribute("unidaddemedidas",
				UnidadDeMedida.findAllUnidadDeMedidas());
		modelMap.addAttribute("otraActividad_fechaFin_date_format",
				org.joda.time.format.DateTimeFormat.patternForStyle("S-",
						org.springframework.context.i18n.LocaleContextHolder
								.getLocale()));
		modelMap.addAttribute("otraActividad_fechaInicio_date_format",
				org.joda.time.format.DateTimeFormat.patternForStyle("S-",
						org.springframework.context.i18n.LocaleContextHolder
								.getLocale()));
		modelMap.addAttribute("idPlanAnual", idPlanAnual);
		modelMap.addAttribute("planAnual", PlanAnual.findPlanAnual(idPlanAnual));

		return "otraactividad/create";
	}

	@RequestMapping(method = RequestMethod.PUT)
	public String update(@ModelAttribute("idPlanAnual") long idPlanAnual,
			@Valid OtraActividad otraActividad, BindingResult result,
			ModelMap modelMap, SessionStatus status, HttpServletRequest request) {
		Util util = new Util();
		PlanAnual miPlan = PlanAnual.findPlanAnual(idPlanAnual);

		if (otraActividad == null)
			throw new IllegalArgumentException("A otraActividad is required");

		Date fechaInicio = otraActividad.getFechaInicio();
		Date fechaFin = otraActividad.getFechaFin();
		if (fechaInicio == null) {
			result.addError(new FieldError(result.getObjectName(),
					"fechaInicio", "Campo obligatorio"));
		}
		if (fechaFin == null) {
			result.addError(new FieldError(result.getObjectName(), "fechaFin",
					"Campo obligatorio"));
		}
		if ((fechaInicio != null) && (fechaFin != null)) {
			if (fechaInicio.getTime() > fechaFin.getTime()) {
				result.addError(new FieldError(result.getObjectName(),
						"fechaFin", "Fecha inválida"));
			}
		}

		int añoI, añoF;
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(fechaInicio);
		añoI = calendario.get(Calendar.YEAR);
		calendario.setTime(fechaFin);
		añoF = calendario.get(Calendar.YEAR);
		int añoFiscal = miPlan.getAnoFiscal();
		// Validacion para que año seleccionado este dentro del año fiscal
		if (añoI != añoFiscal) {
			result.addError(new FieldError(result.getObjectName(),
					"fechaInicio",
					"El año seleccionado para el inicio de la actividad debe ser "
							+ "igual al año fiscal del plan anual."));
		}
		// Validacion para que año seleccionado este dentro del año fiscal
		if (añoF != añoFiscal) {
			result.addError(new FieldError(result.getObjectName(), "fechaFin",
					"El año seleccionado para la finalización de la actividad debe ser "
							+ "igual al año fiscal del plan anual."));
		}

		if (result.hasErrors()) {
			modelMap.addAttribute("otraActividad", otraActividad);
			modelMap.addAttribute("unidads",
					Unidad.findUnidadsByRif(util.traerIdRif()).getResultList());
			modelMap.addAttribute("unidaddemedidas",
					UnidadDeMedida.findAllUnidadDeMedidas());
			modelMap.addAttribute(
					"otraActividad_fechaFin_date_format",
					org.joda.time.format.DateTimeFormat
							.patternForStyle(
									"S-",
									org.springframework.context.i18n.LocaleContextHolder
											.getLocale()));
			modelMap.addAttribute(
					"otraActividad_fechaInicio_date_format",
					org.joda.time.format.DateTimeFormat
							.patternForStyle(
									"S-",
									org.springframework.context.i18n.LocaleContextHolder
											.getLocale()));
			return "otraactividad/update";
		}
		otraActividad.merge();
		Util.registrarEntradaEnBitacora(otraActividad,
				"Se modificó una actividad de tipo otra actividad ",
				request.getRemoteAddr());
		String returnUrl = "/plananual/" + idPlanAnual;
		status.setComplete();
		return "redirect:" + returnUrl;
	}

	@RequestMapping(value = "/otraactividad/{id}/form", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id,
			HttpServletRequest hsr, ModelMap modelMap) {
		Util util = new Util();
		long idPlanAnual = new Long(hsr.getParameter("idPlanAnual"));

		if (id == null)
			throw new IllegalArgumentException("An Identifier is required");
		modelMap.addAttribute("otraActividad",
				OtraActividad.findOtraActividad(id));
		modelMap.addAttribute("unidads",
				Unidad.findUnidadsByRif(util.traerIdRif()).getResultList());
		modelMap.addAttribute("unidaddemedidas",
				UnidadDeMedida.findAllUnidadDeMedidas());
		modelMap.addAttribute("otraActividad_fechaFin_date_format",
				org.joda.time.format.DateTimeFormat.patternForStyle("S-",
						org.springframework.context.i18n.LocaleContextHolder
								.getLocale()));
		modelMap.addAttribute("otraActividad_fechaInicio_date_format",
				org.joda.time.format.DateTimeFormat.patternForStyle("S-",
						org.springframework.context.i18n.LocaleContextHolder
								.getLocale()));
		modelMap.addAttribute("idPlanAnual", idPlanAnual);
		modelMap.addAttribute("planAnual", PlanAnual.findPlanAnual(idPlanAnual));
		return "otraactividad/update";
	}

	@RequestMapping(value = "/otraactividad/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") Long id, ModelMap modelMap,
			HttpServletRequest hsr) {
		if (id == null)
			throw new IllegalArgumentException("An Identifier is required");

		long idPlanAnual = new Long(hsr.getParameter("idPlanAnual"));

		OtraActividad otraActividad = OtraActividad.findOtraActividad(id);

		modelMap.addAttribute("otraActividad_fechaFin_date_format",
				org.joda.time.format.DateTimeFormat.patternForStyle("S-",
						org.springframework.context.i18n.LocaleContextHolder
								.getLocale()));
		modelMap.addAttribute("otraActividad_fechaInicio_date_format",
				org.joda.time.format.DateTimeFormat.patternForStyle("S-",
						org.springframework.context.i18n.LocaleContextHolder
								.getLocale()));
		modelMap.addAttribute("otraActividad", otraActividad);
		modelMap.addAttribute("planAnual", PlanAnual.findPlanAnual(idPlanAnual));

		modelMap.addAttribute("objetoComentable", otraActividad);

		logger.debug("solicitando revision de otraactividad en motor de reglas");
		jbpmService.executeRulesStateless(otraActividad);
		logger.debug("revision de otraactividad en motor de reglas listo");

		return "otraactividad/show";
	}

	@RequestMapping(value = "/otraactividad", method = RequestMethod.GET)
	public String list(ModelMap modelMap) {
		Util util = new Util();
		modelMap.addAttribute("otraactividads", OtraActividad
				.findOtraActividadsByRif(util.traerIdRif()).getResultList());
		return "otraactividad/list";
	}

	@RequestMapping(value = "/otraactividad/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("id") Long id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			HttpServletRequest hsr) {
		long idPlanAnual = new Long(hsr.getParameter("idPlanAnual"));

		if (id == null)
			throw new IllegalArgumentException("An Identifier is required");
		OtraActividad otraActividad = OtraActividad.findOtraActividad(id);

		List<ItemPlanificacionActuacion> items = ItemPlanificacionActuacion
				.findItemPlanificacionActuacionsByOtraActividad(otraActividad)
				.getResultList();
		for (int i = 0; i < items.size(); i++) {
			items.get(i).remove();
		}

		otraActividad.remove();

		return "redirect:/plananual/" + idPlanAnual;
		// return "redirect:/otraactividad?page=" + ((page == null) ? "1" :
		// page.toString()) + "&size=" + ((size == null) ? "10" :
		// size.toString());
	}

}
