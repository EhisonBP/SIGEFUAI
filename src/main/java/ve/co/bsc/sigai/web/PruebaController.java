package ve.co.bsc.sigai.web;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
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

import ve.co.bsc.sigai.domain.ActividadActuacion;
import ve.co.bsc.sigai.domain.ActividadGeneral;
import ve.co.bsc.sigai.domain.Actuacion;
import ve.co.bsc.sigai.domain.ArchivoAdjunto;
import ve.co.bsc.sigai.domain.Auditor;
import ve.co.bsc.sigai.domain.AvanceActuacion;
import ve.co.bsc.sigai.domain.Documento;
import ve.co.bsc.sigai.domain.EstadoActividadActuacion;
import ve.co.bsc.sigai.domain.ObjetivoEspecifico;
import ve.co.bsc.sigai.domain.Observacion;
import ve.co.bsc.sigai.domain.Prueba;
import ve.co.bsc.sigai.domain.Requerimiento;
import ve.co.bsc.sigai.domain.RolUsuario;
import ve.co.bsc.sigai.domain.TecnicaDeControl;
import ve.co.bsc.sigai.domain.Usuario;
import ve.co.bsc.sigai.form.TecnicasPorActuacionForm;
import ve.co.bsc.util.Util;

@RooWebScaffold(path = "prueba", automaticallyMaintainView = true, formBackingObject = Prueba.class)
@RequestMapping("/prueba/**")
@SessionAttributes({ "prueba" })
@Controller
public class PruebaController {

	Logger logger = Logger.getLogger(PruebaController.class);

	@RequestMapping(value = "/prueba", method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute("prueba") Prueba prueba,
			BindingResult result, ModelMap modelMap, SessionStatus status,
			HttpServletRequest request) {
		if (prueba == null)
			throw new IllegalArgumentException("A prueba is required");

		if ((prueba.getPorcentajeCompletitud() < 0)
				|| (prueba.getPorcentajeCompletitud() > 100)) {
			result.addError(new FieldError(result.getObjectName(),
					"porcentajeCompletitud",
					"El pocentaje debe estar entre 0 y 100"));
		}

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
			modelMap.addAttribute("prueba", prueba);
			// modelMap.addAttribute("actuacions",
			// Actuacion.findAllActuacions());
			modelMap.addAttribute("auditors", losQueSon);
			modelMap.addAttribute("documentoes", Documento.findAllDocumentoes());
			modelMap.addAttribute("estadoactividadactuacions",
					EstadoActividadActuacion.findAllEstadoActividadActuacions());
			modelMap.addAttribute(
					"requerimientoes",
					Requerimiento.findRequerimientoesByActuacion(
							prueba.getCodigoActuacion()).getResultList());
			return "prueba/create";
		}

		prueba.persist();
		Util.registrarEntradaEnBitacora(prueba, "Se creó la Prueba ",
				request.getRemoteAddr());
		status.setComplete();
		return "redirect:/prueba/" + prueba.getId();
	}

	@RequestMapping(value = "/prueba/form", method = RequestMethod.GET)
	public String createForm(@RequestParam("idActuacion") long idActuacion,
			HttpServletRequest hsr, ModelMap modelMap) {

		// String idActividad = "";
		Prueba laPrueba = new Prueba();
		//
		if (hsr.getParameter("idActividad") != null) {
			// idActividad = hsr.getParameter("idActividad");
			// Entra aqui si tiene un padre
			ActividadActuacion miPadre = ActividadActuacion
					.findActividadActuacion(new Long(hsr
							.getParameter("idActividad")));
			laPrueba.setActividadActuacion(miPadre);
			if (miPadre instanceof ActividadGeneral) {
				// Si la actividad es hija de una ActividadGeneral entonces es
				// de tipo Tarea
				// y debo hacer set del objetivoAMitigar del padre porque será
				// el mismo
				ObjetivoEspecifico elObjetivoDeMiPadre = miPadre
						.getObjetivoAMitigar();
				laPrueba.setObjetivoAMitigar(elObjetivoDeMiPadre);
				modelMap.addAttribute("esActividadHija", "true");
				modelMap.addAttribute("objetivoAMitigar", elObjetivoDeMiPadre);
			}
		} else {
			modelMap.addAttribute("esActividadHija", "false");
		}

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

		Actuacion miActuacion = Actuacion.findActuacion(idActuacion);
		laPrueba.setCodigoActuacion(miActuacion);

		modelMap.addAttribute("prueba", laPrueba);
		modelMap.addAttribute("actuacion", miActuacion);
		// modelMap.addAttribute("actuacions", Actuacion.findAllActuacions());
		modelMap.addAttribute("auditors", losQueSon);
		modelMap.addAttribute("documentoes", Documento.findAllDocumentoes());
		modelMap.addAttribute("estadoactividadactuacions",
				EstadoActividadActuacion.findAllEstadoActividadActuacions());

		Query queryObjetivos = ObjetivoEspecifico
				.findObjetivoEspecificoesByActuacion(Actuacion
						.findActuacion(idActuacion));
		List<ObjetivoEspecifico> objetivoespecificoes = queryObjetivos
				.getResultList();
		modelMap.addAttribute("objetivoespecificoes", objetivoespecificoes);

		modelMap.addAttribute("requerimientoes", Requerimiento
				.findRequerimientoesByActuacion(miActuacion).getResultList());

		return "prueba/create";
	}

	@RequestMapping(method = RequestMethod.PUT)
	public String update(@Valid @ModelAttribute("prueba") Prueba prueba,
			BindingResult result, ModelMap modelMap, SessionStatus status,
			HttpServletRequest request) {
		if (prueba == null)
			throw new IllegalArgumentException("A prueba is required");

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
			modelMap.addAttribute("prueba", prueba);
			// modelMap.addAttribute("actuacions",
			// Actuacion.findAllActuacions());
			modelMap.addAttribute("auditors", losQueSon);
			modelMap.addAttribute("documentoes", Documento.findAllDocumentoes());
			modelMap.addAttribute("estadoactividadactuacions",
					EstadoActividadActuacion.findAllEstadoActividadActuacions());
			modelMap.addAttribute(
					"requerimientoes",
					Requerimiento.findRequerimientoesByActuacion(
							prueba.getCodigoActuacion()).getResultList());
			return "prueba/update";
		}

		prueba.merge();
		Util.registrarEntradaEnBitacora(prueba, "Se modificó la Prueba ",
				request.getRemoteAddr());
		status.setComplete();

		return "redirect:/prueba/" + prueba.getId();
	}

	@RequestMapping(value = "/prueba/{id}/form", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
		if (id == null)
			throw new IllegalArgumentException("An Identifier is required");

		Prueba laPrueba = Prueba.findPrueba(id);

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

		modelMap.addAttribute("prueba", laPrueba);
		modelMap.addAttribute("actuacion", laPrueba.getCodigoActuacion());
		// modelMap.addAttribute("actuacions", Actuacion.findAllActuacions());
		modelMap.addAttribute("auditors", losQueSon);
		modelMap.addAttribute("documentoes", Documento.findAllDocumentoes());
		modelMap.addAttribute("estadoactividadactuacions",
				EstadoActividadActuacion.findAllEstadoActividadActuacions());

		Query queryObjetivos = ObjetivoEspecifico
				.findObjetivoEspecificoesByActuacion(laPrueba
						.getCodigoActuacion());
		List<ObjetivoEspecifico> objetivoespecificoes = queryObjetivos
				.getResultList();
		modelMap.addAttribute("objetivoespecificoes", objetivoespecificoes);

		modelMap.addAttribute("requerimientoes", Requerimiento
				.findRequerimientoesByActuacion(laPrueba.getCodigoActuacion())
				.getResultList());

		return "prueba/update";
	}

	@RequestMapping(value = "/prueba/{id}", method = RequestMethod.GET)
	public String show(
			@ModelAttribute("TecnicasForm") TecnicasPorActuacionForm form,
			@PathVariable("id") Long id, ModelMap modelMap,
			HttpServletRequest request) {
		if (id == null)
			throw new IllegalArgumentException("An Identifier is required");
		Prueba prueba = Prueba.findPrueba(id);
		modelMap.addAttribute("prueba", prueba);

		Query queryObservaciones = Observacion.findObservacionsByPrueba(prueba);
		List<Observacion> observaciones = queryObservaciones.getResultList();
		// Collections.sort(actividades, ActividadGeneral.compararActividades);
		modelMap.addAttribute("observaciones", observaciones);

		Set setPrueba = new HashSet();
		setPrueba.add(prueba);

		// ************** ARSEN *******************
		modelMap.addAttribute("allTecnicas", TecnicaDeControl
				.findTecnicasByActuacion(prueba.getCodigoActuacion()));
		if (form.getTecnica() != null) {
			TecnicaDeControl laTecnica = form.getTecnica();
			laTecnica.setPrueba(setPrueba);
			laTecnica.merge();
			Util.registrarEntradaEnBitacora(
					prueba,
					"Se asginó la técnica de control"
							+ laTecnica.toStringLimitado() + " a la prueba ",
					request.getRemoteAddr());
		}

		List<TecnicaDeControl> tecnicasByPrueba = TecnicaDeControl
				.findTecnicaDeControlsByPrueba(setPrueba).getResultList();
		modelMap.addAttribute("tecnicasDeControlByPrueba", tecnicasByPrueba);

		// *********************************

		modelMap.addAttribute("avanceActuacion_fechaInicio_date_format",
				org.joda.time.format.DateTimeFormat.patternForStyle("S-",
						org.springframework.context.i18n.LocaleContextHolder
								.getLocale()));
		modelMap.addAttribute("avanceActuacion_fechaFin_date_format",
				org.joda.time.format.DateTimeFormat.patternForStyle("S-",
						org.springframework.context.i18n.LocaleContextHolder
								.getLocale()));

		Query queryAvancesByPrueba = AvanceActuacion
				.findAvanceActuacionsByCodigoActividad(prueba);
		List<AvanceActuacion> avancesByPrueba = queryAvancesByPrueba
				.getResultList();
		// Collections.sort(actividades, ActividadGeneral.compararActividades);
		modelMap.addAttribute("avancesByPrueba", avancesByPrueba);

		Query queryArchivosAdjuntosByPrueba = ArchivoAdjunto
				.findArchivoAdjuntoesByActividadActuacion(setPrueba);
		List<ArchivoAdjunto> archivosAdjuntosByPrueba = queryArchivosAdjuntosByPrueba
				.getResultList();
		// Collections.sort(actividades, ActividadGeneral.compararActividades);
		modelMap.addAttribute("archivosAdjuntosByPrueba",
				archivosAdjuntosByPrueba);

		modelMap.addAttribute("objetoComentable", prueba);

		return "prueba/show";
	}
}
