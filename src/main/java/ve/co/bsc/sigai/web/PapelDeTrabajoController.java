package ve.co.bsc.sigai.web;

import java.util.ArrayList;
import java.util.Collections;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import ve.co.bsc.sigai.domain.ActividadActuacion;
import ve.co.bsc.sigai.domain.Actuacion;
import ve.co.bsc.sigai.domain.ArchivoAdjunto;
import ve.co.bsc.sigai.domain.Auditor;
import ve.co.bsc.sigai.domain.Documento;
import ve.co.bsc.sigai.domain.EstadoActividadActuacion;
import ve.co.bsc.sigai.domain.Observacion;
import ve.co.bsc.sigai.domain.PapelDeTrabajo;
import ve.co.bsc.sigai.domain.Prueba;
import ve.co.bsc.sigai.domain.RolUsuario;
import ve.co.bsc.sigai.domain.Usuario;
import ve.co.bsc.util.Util;
import bsh.EvalError;
import bsh.Interpreter;

@RooWebScaffold(path = "papeldetrabajo", automaticallyMaintainView = true, formBackingObject = PapelDeTrabajo.class)
@RequestMapping("/papeldetrabajo/**")
@SessionAttributes({ "papelDeTrabajo", "esHija", "parametros" })
@Controller
public class PapelDeTrabajoController {

	Logger logger = Logger.getLogger(PapelDeTrabajoController.class);
	int nivel = 0;
	int mayorPapel = 0;

	@RequestMapping(value = "/papeldetrabajo", method = RequestMethod.POST)
	public String create(
			@Valid @ModelAttribute("papelDeTrabajo") PapelDeTrabajo papelDeTrabajoCompleto,
			@Valid PapelDeTrabajo papelDeTrabajo, BindingResult result,
			ModelMap modelMap, SessionStatus status, HttpServletRequest request) {

		if (papelDeTrabajo == null) {
			throw new IllegalArgumentException("A papelDeTrabajo is required");
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
			modelMap.addAttribute("papelDeTrabajo", papelDeTrabajo);
			modelMap.addAttribute("actividadactuacions",
					ActividadActuacion.findAllActividadActuacions());
			// modelMap.addAttribute("actuacions",
			// Actuacion.findAllActuacions());
			modelMap.addAttribute("auditors", losQueSon);
			modelMap.addAttribute("documentoes", Documento.findAllDocumentoes());
			modelMap.addAttribute("estadoactividadactuacions",
					EstadoActividadActuacion.findAllEstadoActividadActuacions());
			return "papeldetrabajo/create";
		}

		// if (papelDeTrabajoCompleto.getActividadActuacion() != null){
		// papelDeTrabajo.setActividadActuacion(papelDeTrabajoCompleto.getActividadActuacion());
		// }

		// papelDeTrabajo.setCodigoActuacion(miActuacion);
		papelDeTrabajo.persist();
		Util.registrarEntradaEnBitacora(papelDeTrabajo, "Se creó la Sección ",
				request.getRemoteAddr());
		status.setComplete();
		return "redirect:/papeldetrabajo/" + papelDeTrabajo.getId();
	}

	@RequestMapping(value = "/papeldetrabajo/form", method = RequestMethod.GET)
	public String createForm(@RequestParam("idActuacion") long idActuacion,
			HttpServletRequest hsr, ModelMap modelMap) {

		PapelDeTrabajo elPapelDeTrabajo = new PapelDeTrabajo();

		String esHija = "0";

		if (hsr.getParameter("idActividad") != null) {
			// Entra aqui si tiene un padre
			ActividadActuacion miPadre = ActividadActuacion
					.findActividadActuacion(new Long(hsr
							.getParameter("idActividad")));
			elPapelDeTrabajo.setActividadActuacion(miPadre);
			esHija = "1";
		}

		Actuacion laActuacion = Actuacion.findActuacion(idActuacion);
		elPapelDeTrabajo.setCodigoActuacion(laActuacion);

		// Buscamos todas los papeles de trabajo de la actuacion
		Query queryPapeles = PapelDeTrabajo
				.findPapelDeTrabajoesByCodigoActuacion(laActuacion);
		List<PapelDeTrabajo> papeles = queryPapeles.getResultList();

		if (hsr.getParameter("nivel") != null) {
			nivel = Integer.parseInt(hsr.getParameter("nivel"));
		}

		// Esto solo se hace si es una subseccion
		if (hsr.getParameter("idActividad") != null) {

			if (nivel == 0) {
				if (papeles.size() > 0) {
					mayorPapel = Integer.parseInt(((PapelDeTrabajo) papeles
							.get(papeles.size() - 1)).getCodigo());
				} else {
					mayorPapel = 0;
				}
			} else {
				if (hsr.getParameter("idActividad") != null) {
					// Entra aqui si tiene un padre
					ActividadActuacion miPadre = ActividadActuacion
							.findActividadActuacion(new Long(hsr
									.getParameter("idActividad")));
					Query queryPapelesHijos = PapelDeTrabajo
							.findPapelDeTrabajoesByActividadActuacion(miPadre);
					List<PapelDeTrabajo> papelesHijos = queryPapelesHijos
							.getResultList();

					if (papelesHijos.size() > 0) {
						mayorPapel = Integer
								.parseInt(((PapelDeTrabajo) papelesHijos
										.get(papelesHijos.size() - 1))
										.getCodigo());
					} else {
						mayorPapel = 0;
					}
				}
			}
		}

		// Solo para las subsecciones se va a tener codigo consecutivo
		if (hsr.getParameter("idActividad") != null) {
			elPapelDeTrabajo.setCodigo(String.valueOf(mayorPapel + 1));
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

		modelMap.addAttribute("actuacion", laActuacion);
		modelMap.addAttribute("papelDeTrabajo", elPapelDeTrabajo);
		modelMap.addAttribute("actividadactuacions",
				ActividadActuacion.findAllActividadActuacions());
		// modelMap.addAttribute("actuacions", Actuacion.findAllActuacions());
		modelMap.addAttribute("auditors", losQueSon);
		modelMap.addAttribute("documentoes", Documento.findAllDocumentoes());
		modelMap.addAttribute("estadoactividadactuacions",
				EstadoActividadActuacion.findAllEstadoActividadActuacions());
		modelMap.addAttribute("esHija", esHija);
		return "papeldetrabajo/create";
	}

	@RequestMapping(method = RequestMethod.PUT)
	public String update(
			@Valid @ModelAttribute("papelDeTrabajo") PapelDeTrabajo papelDeTrabajo,
			BindingResult result, ModelMap modelMap, SessionStatus status,
			HttpServletRequest request) {
		if (papelDeTrabajo == null) {
			throw new IllegalArgumentException("A papelDeTrabajo is required");
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
			modelMap.addAttribute("papelDeTrabajo", papelDeTrabajo);
			modelMap.addAttribute("actividadactuacions",
					ActividadActuacion.findAllActividadActuacions());
			// modelMap.addAttribute("actuacions",
			// Actuacion.findAllActuacions());
			modelMap.addAttribute("auditors", losQueSon);
			modelMap.addAttribute("documentoes", Documento.findAllDocumentoes());
			modelMap.addAttribute("estadoactividadactuacions",
					EstadoActividadActuacion.findAllEstadoActividadActuacions());
			return "papeldetrabajo/update";
		}
		// papelDeTrabajo.setCodigoActuacion(miActuacion);
		papelDeTrabajo.merge();
		Util.registrarEntradaEnBitacora(papelDeTrabajo,
				"Se modificó la Sección ", request.getRemoteAddr());
		status.setComplete();
		return "redirect:/papeldetrabajo/" + papelDeTrabajo.getId();
	}

	@RequestMapping(value = "/papeldetrabajo/{id}/form", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
		PapelDeTrabajo elPapelDeTrabajo = PapelDeTrabajo.findPapelDeTrabajo(id);
		String esHija = "0";
		if (elPapelDeTrabajo.getActividadActuacion() != null) {
			esHija = "1";
		}

		if (id == null) {
			throw new IllegalArgumentException("An Identifier is required");
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

		modelMap.addAttribute("esHija", esHija);
		modelMap.addAttribute("papelDeTrabajo", elPapelDeTrabajo);
		modelMap.addAttribute("actuacion",
				elPapelDeTrabajo.getCodigoActuacion());
		modelMap.addAttribute("actividadactuacions",
				ActividadActuacion.findAllActividadActuacions());
		// modelMap.addAttribute("actuacions", Actuacion.findAllActuacions());
		modelMap.addAttribute("auditors", losQueSon);
		modelMap.addAttribute("documentoes", Documento.findAllDocumentoes());
		modelMap.addAttribute("estadoactividadactuacions",
				EstadoActividadActuacion.findAllEstadoActividadActuacions());
		return "papeldetrabajo/update";
	}

	@RequestMapping(value = "/papeldetrabajo/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") Long id, HttpServletRequest hsr,
			ModelMap modelMap) {

		if (id == null) {
			throw new IllegalArgumentException("An Identifier is required");
		}

		if (hsr.getParameter("contenidoGuardado") != null) {
			modelMap.addAttribute("contenidoGuardado", "false");
		}

		PapelDeTrabajo miPapelDeTrabajo = PapelDeTrabajo.findPapelDeTrabajo(id);
		ArrayList<ActividadActuacion> actividadActuaciones = buscarActividadActuacions(miPapelDeTrabajo);

		Collections.sort(actividadActuaciones,
				ActividadActuacion.compararActividades);
		modelMap.addAttribute("actividadActuaciones", actividadActuaciones);

		modelMap.addAttribute("papelDeTrabajo", miPapelDeTrabajo);

		Actuacion miActuacion = miPapelDeTrabajo.getCodigoActuacion();
		modelMap.addAttribute("actuacion", miActuacion);

		Set setPapelDeTrabajo = new HashSet();
		setPapelDeTrabajo.add(miPapelDeTrabajo);

		Query queryArchivosAdjuntosByPrueba = ArchivoAdjunto
				.findArchivoAdjuntoesByActividadActuacion(setPapelDeTrabajo);
		List<ArchivoAdjunto> archivosAdjuntosByPapelDeTrabajo = queryArchivosAdjuntosByPrueba
				.getResultList();
		// Collections.sort(actividades, ActividadGeneral.compararActividades);
		modelMap.addAttribute("archivosAdjuntosByPapelDeTrabajo",
				archivosAdjuntosByPapelDeTrabajo);

		modelMap.addAttribute("objetoComentable", miPapelDeTrabajo);

		// Manejo de tipo de secciones
		// Verificamos si la seccion es normal o es de algun tipo
		if (miPapelDeTrabajo.getTipoSeccion() != null) {
			try {
				// Inicializamos el interprete
				Interpreter i = new Interpreter();
				// "Enviamos" el modelMap al codigo a ejecutarse
				i.set("modelMap", modelMap);
				// Ejecutamos el codigo
				i.eval(miPapelDeTrabajo.getTipoSeccion().getCodigo());
				return "papeldetrabajo/"
						+ miPapelDeTrabajo.getTipoSeccion().getVista();

			} catch (EvalError e) {
				logger.error("EvalError: " + e);
				logger.debug("EvalError: " + e);
				// logger.error("no se puede aplicar condicion [" +
				// getEspecificacionCampo().getCondicion() + "] sobre " +
				// getPaciente().getContextMap() + ". Retornando FALSE", e);
				// return false;
			}
		} else {
			logger.debug("ELSE ******* ");
		}
		return "papeldetrabajo/show";
	}

	private ArrayList<ActividadActuacion> buscarActividadActuacions(
			ActividadActuacion actividadActuacion) {

		Query queryActividadActuaciones = ActividadActuacion
				.findActividadActuacionsByActividadActuacion(actividadActuacion);
		ArrayList<ActividadActuacion> actividadActuaciones = (ArrayList<ActividadActuacion>) ((ArrayList<ActividadActuacion>) queryActividadActuaciones
				.getResultList()).clone();

		// logger.debug("   tiene cant: " + actividadActuaciones.size());
		// //busco los hijos
		// Iterator it = actividadActuaciones.iterator();
		// while(it.hasNext()) {
		// ActividadActuacion miActividad = (ActividadActuacion)it.next();
		// ArrayList<ActividadActuacion> listaHijos =
		// buscarActividadActuacions(miActividad);
		// actividadActuaciones.addAll(listaHijos);
		// }

		return actividadActuaciones;
	}

	@RequestMapping(value = "/papeldetrabajo/contenido", method = RequestMethod.POST)
	public String saveContenido(
			@Valid @ModelAttribute("papelDeTrabajo") PapelDeTrabajo papeldetrabajo,
			BindingResult result, ModelMap modelMap, SessionStatus status,
			HttpServletRequest request) {
		if (papeldetrabajo == null)
			throw new IllegalArgumentException("A papeldetrabajo is required");
		if (result.hasErrors()) {
			return "redirect:/papeldetrabajo/" + papeldetrabajo.getId()
					+ "?contenidoGuardado=false";
		}

		papeldetrabajo.merge();
		Util.registrarEntradaEnBitacora(papeldetrabajo,
				"Se modificó el atributo contenido de la seccion ",
				request.getRemoteAddr());
		status.setComplete();
		return "redirect:/papeldetrabajo/" + papeldetrabajo.getId();
	}

	@RequestMapping(value = "/papeldetrabajo/recargarConclusion/{idActuacion}/{idPapel}", method = RequestMethod.GET)
	public String recargarConclusion(
			@PathVariable("idActuacion") Long idActuacion,
			@PathVariable("idPapel") Long idPapel, ModelMap modelMap,
			HttpServletRequest request) {
		Actuacion miActuacion = Actuacion.findActuacion(idActuacion);
		List<Prueba> misPruebas = Prueba.findPruebasByCodigoActuacion(
				miActuacion).getResultList();
		String conclusion = "";
		for (int i = 0; i < misPruebas.size(); i++) {
			List<Observacion> misObservacionesByPrueba = Observacion
					.findObservacionsByPrueba(misPruebas.get(i))
					.getResultList();
			for (int y = 0; y < misObservacionesByPrueba.size(); y++) {
				if (misObservacionesByPrueba.get(y).getConclusion() != null) {
					conclusion = conclusion
							+ misObservacionesByPrueba.get(y).getConclusion();
				}
			}
		}
		miActuacion.setConclusionGeneral(conclusion);
		miActuacion.merge();

		Util.registrarEntradaEnBitacora(
				miActuacion,
				"Se recargó la conclusión general a partir de las conclusiones de cada hallazgo ",
				request.getRemoteAddr());

		return "redirect:/papeldetrabajo/" + idPapel;
	}

}
