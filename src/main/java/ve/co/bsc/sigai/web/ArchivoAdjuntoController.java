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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import ve.co.bsc.sigai.domain.ActividadActuacion;
import ve.co.bsc.sigai.domain.Alegato;
import ve.co.bsc.sigai.domain.ArchivoAdjunto;
import ve.co.bsc.sigai.domain.AvanceActuacion;
import ve.co.bsc.sigai.domain.HistorialCambios;
import ve.co.bsc.sigai.domain.Observacion;
import ve.co.bsc.sigai.domain.Personalizacion;
import ve.co.bsc.sigai.domain.Requerimiento;
import ve.co.bsc.sigai.domain.Respuesta;
import ve.co.bsc.sigai.domain.SeccionDefault;

@RooWebScaffold(path = "archivoadjunto", automaticallyMaintainView = true, formBackingObject = ArchivoAdjunto.class)
@RequestMapping("/archivoadjunto/**")
@SessionAttributes({ "archivoAdjunto", "idReturn", "returnURL",
		"codigoCompleto", "esActividadActuacion" })
@Controller
public class ArchivoAdjuntoController {

	private static Logger logger = Logger
			.getLogger(ArchivoAdjuntoController.class);

	// we need a special property-editor that knows how to bind the data
	// from the request to a byte[]
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
	}

	@RequestMapping(value = "/archivoadjunto", method = RequestMethod.POST)
	public String create(
			@ModelAttribute("callback") String callback,
			@Valid @ModelAttribute("idReturn") long idReturn,
			@Valid @ModelAttribute("returnURL") String returnURL,
			@Valid @ModelAttribute("archivoAdjunto") ArchivoAdjunto archivoAdjunto,
			BindingResult result, MultipartHttpServletRequest request,
			ModelMap modelMap, SessionStatus status) {
		if (archivoAdjunto == null)
			throw new IllegalArgumentException("A archivo is required");
		if (result.hasErrors()) {
			modelMap.addAttribute("archivoAdjunto", archivoAdjunto);
			modelMap.addAttribute("idReturn", idReturn);
			modelMap.addAttribute("returnURL", returnURL);

			return "archivoadjunto/create";
		}
		String originalFilename = request.getFile("filedata")
				.getOriginalFilename();
		String[] originalFileNameArray = originalFilename.split("\\.");
		try {
			if (archivoAdjunto.getNombre().trim().length() == 0) {
				archivoAdjunto.setNombre(originalFileNameArray[0]);
			}
			archivoAdjunto.setNombre(archivoAdjunto.getNombre().replace(' ',
					'_'));
			archivoAdjunto
					.setFilesize(request.getFile("filedata").getSize() / 1000);
			archivoAdjunto.setExtension(originalFileNameArray[1]);

			Set<ActividadActuacion> setActividad = new HashSet();
			Set<AvanceActuacion> setAvanceActuacion = new HashSet();
			if ((returnURL.equals("prueba"))
					|| (returnURL.equals("papeldetrabajo"))
					|| (returnURL.equals("actividadgeneral"))) {
				ActividadActuacion miActividad = ActividadActuacion
						.findActividadActuacion(idReturn);
				setActividad.add(miActividad);
			} else if ((returnURL.equals("avanceactuacion"))) {
				AvanceActuacion miAvanceActuacion = AvanceActuacion
						.findAvanceActuacion(idReturn);
				archivoAdjunto.setAvanceActuacion(miAvanceActuacion);
			} else if ((returnURL.equals("alegato"))) {
				Alegato miAlegato = Alegato.findAlegato(idReturn);
				archivoAdjunto.setAlegato(miAlegato);
			} else if ((returnURL.equals("observacion"))) {
				Observacion miHallazgo = Observacion.findObservacion(idReturn);
				archivoAdjunto.setHallazgo(miHallazgo);
			} else if ((returnURL.equals("respuesta"))) {
				Respuesta miRespuesta = Respuesta.findRespuesta(idReturn);
				archivoAdjunto.setRespuesta(miRespuesta);
			} else if ((returnURL.equals("requerimiento"))) {
				Requerimiento miRequerimiento = Requerimiento
						.findRequerimiento(idReturn);
				archivoAdjunto.setRequerimiento(miRequerimiento);
			} else if ((returnURL.equals("secciondefault"))) {
				SeccionDefault miSeccion = SeccionDefault
						.findSeccionDefault(idReturn);
				archivoAdjunto.setSeccionDefault(miSeccion);
			} else if ((returnURL.equals("personalizacion"))) {
				Personalizacion miPersonalizacion = Personalizacion
						.findPersonalizacion(idReturn);
				archivoAdjunto.setPersonalizacion(miPersonalizacion);
			}
			// archivoAdjunto.setLoginUsuario("");
			archivoAdjunto.persist();

			if ((returnURL.equals("prueba"))
					|| (returnURL.equals("papeldetrabajo"))
					|| (returnURL.equals("actividadgeneral"))) {
				archivoAdjunto.setActividadActuacion(setActividad);
			}

			archivoAdjunto.merge();

			// Creamos el historial de cambios
			HistorialCambios historial = new HistorialCambios();
			historial.setArchivoAdjunto(archivoAdjunto);
			historial.setAccion("Crear archivo");
			Calendar cal = Calendar.getInstance();
			historial.setFecha(cal.getTime());
			Object principal = SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			historial.setUsuario(((User) principal).getUsername().toString());
			historial.persist();

			status.setComplete();
			return "redirect:/" + returnURL + "/" + idReturn;
		} catch (Exception e) {
			logger.error("imposible guardar adjunto", e);
			modelMap.addAttribute("archivoAdjunto", archivoAdjunto);
			modelMap.addAttribute("idReturn", idReturn);
			modelMap.addAttribute("returnURL", returnURL);
			result.addError(new ObjectError("archivo",
					"No fue posible almacenar el archivo seleccionado"));
			return "archivoadjunto/create";
		}
	}

	@RequestMapping(value = "/archivoadjunto/form", method = RequestMethod.GET)
	public String createForm(
			@RequestParam(value = "callback", required = false) String callback,
			@RequestParam("idReturn") long idReturn,
			@RequestParam("returnURL") String returnURL, ModelMap modelMap) {
		if ((returnURL.equals("prueba"))
				|| (returnURL.equals("papeldetrabajo"))
				|| (returnURL.equals("actividadgeneral"))) {
			ActividadActuacion miActividadActuacion = ActividadActuacion
					.findActividadActuacion(idReturn);
			modelMap.addAttribute("codigoCompleto",
					miActividadActuacion.getCodigoCompleto());
		}

		else if (returnURL.equals("avanceactuacion")) {
			AvanceActuacion miAvanceActuacion = AvanceActuacion
					.findAvanceActuacion(idReturn);
			modelMap.addAttribute("codigoCompleto",
					miAvanceActuacion.getJustificacion());
		} else if (returnURL.equals("respuesta")) {
			Respuesta miRespuesta = Respuesta.findRespuesta(idReturn);
			modelMap.addAttribute("codigoCompleto", miRespuesta.getNumero());
		} else if (returnURL.equals("personalizacion")) {
			Personalizacion miPersonalizacion = Personalizacion
					.findPersonalizacion(idReturn);
			modelMap.addAttribute("codigoCompleto",
					miPersonalizacion.getNombreInstitucion());
		} else if (returnURL.equals("requerimiento")) {
			Requerimiento miRequerimiento = Requerimiento
					.findRequerimiento(idReturn);
			modelMap.addAttribute("codigoCompleto",
					miRequerimiento.getNumeroSolicitud());
		} else if (returnURL.equals("observacion")) {
			Observacion miObservacion = Observacion.findObservacion(idReturn);
			modelMap.addAttribute("codigoCompleto",
					"AG-" + miObservacion.getReferencia());
		} else if (returnURL.equals("alegato")) {
			Alegato miAlegato = Alegato.findAlegato(idReturn);
			modelMap.addAttribute("codigoCompleto",
					(miAlegato.getDescripcion()).substring(0, 25) + "...");
		} else if (returnURL.equals("secciondefault")) {
			SeccionDefault miSeccion = SeccionDefault
					.findSeccionDefault(idReturn);
			modelMap.addAttribute("codigoCompleto", miSeccion.getCodigo());
		} else {
			modelMap.addAttribute("codigoCompleto", "vacio");
		}

		modelMap.addAttribute("idReturn", idReturn);
		modelMap.addAttribute("returnURL", returnURL);
		modelMap.addAttribute("archivoAdjunto", new ArchivoAdjunto());
		if (callback != null)
			modelMap.put("callback", callback);
		else
			modelMap.put("callback", "");
		return "archivoadjunto/create";
	}

	@RequestMapping(value = "/archivoadjunto/{id}", method = RequestMethod.GET)
	public String show(
			@PathVariable("id") Long id,
			@RequestParam("idReturn") long idReturn,
			@RequestParam("returnURL") String returnURL,
			@RequestParam(value = "callback", required = false) String callback,
			HttpServletRequest hsr, ModelMap modelMap) {
		if (id == null) {
			throw new IllegalArgumentException(
					"An ArchivoAdjunto Identifier is required");
		}
		ArchivoAdjunto archivo = ArchivoAdjunto.findArchivoAdjunto(id);

		// Verificamos si el archivo esta bloqueado por algun usuario, si es
		// asi, le damos la opcion
		// en la vista para subirlo de nuevo
		logger.debug("loginUsuario tiene" + archivo.getLoginUsuario());
		if ((archivo.getLoginUsuario()) == null) {
			logger.debug("loginUsuario vacio");
			modelMap.addAttribute("checkin", false);
		} else {
			logger.debug("loginUsuario lleno");
			modelMap.addAttribute("checkin", true);
		}

		if ((returnURL.equals("prueba"))
				|| (returnURL.equals("papeldetrabajo"))
				|| (returnURL.equals("actividadgeneral"))) {
			ActividadActuacion miActividadActuacion = ActividadActuacion
					.findActividadActuacion(idReturn);
			modelMap.addAttribute("codigoCompleto",
					miActividadActuacion.getCodigoCompleto());

		}
		// TODO: falta caso avanceactuacion
		else if (returnURL.equals("avanceactuacion")) {
			AvanceActuacion miAvanceActuacion = AvanceActuacion
					.findAvanceActuacion(idReturn);
			modelMap.addAttribute("codigoCompleto",
					miAvanceActuacion.getJustificacion());
		} else if (returnURL.equals("respuesta")) {
			Respuesta miRespuesta = Respuesta.findRespuesta(idReturn);
			modelMap.addAttribute("codigoCompleto", miRespuesta.getNumero());
		} else if (returnURL.equals("personalizacion")) {
			Personalizacion miPersonalizacion = Personalizacion
					.findPersonalizacion(idReturn);
			modelMap.addAttribute("codigoCompleto",
					miPersonalizacion.getNombreInstitucion());
		} else if (returnURL.equals("requerimiento")) {
			Requerimiento miRequerimiento = Requerimiento
					.findRequerimiento(idReturn);
			modelMap.addAttribute("codigoCompleto",
					miRequerimiento.getNumeroSolicitud());
		} else if (returnURL.equals("observacion")) {
			Observacion miObservacion = Observacion.findObservacion(idReturn);
			modelMap.addAttribute("codigoCompleto",
					"AG-" + miObservacion.getReferencia());
		} else if (returnURL.equals("alegato")) {
			Alegato miAlegato = Alegato.findAlegato(idReturn);
			modelMap.addAttribute("codigoCompleto",
					(miAlegato.getDescripcion()).substring(0, 25) + "...");
		} else if (returnURL.equals("secciondefault")) {
			SeccionDefault miSeccion = SeccionDefault
					.findSeccionDefault(idReturn);
			modelMap.addAttribute("codigoCompleto", miSeccion.getCodigo());
		} else {
			modelMap.addAttribute("codigoCompleto", "vacio");
		}

		modelMap.addAttribute("historialCambios_fecha_date_format",
				org.joda.time.format.DateTimeFormat.patternForStyle("FM",
						org.springframework.context.i18n.LocaleContextHolder
								.getLocale()));

		// Historial de modificaciones
		Query queryHistorialCambiosByArchivoAdjunto = HistorialCambios
				.findHistorialCambiosesByArchivoAdjunto(archivo);
		List<HistorialCambios> historialCambiosByArchivoAdjunto = queryHistorialCambiosByArchivoAdjunto
				.getResultList();
		modelMap.addAttribute("historialCambiosByArchivoAdjunto",
				historialCambiosByArchivoAdjunto);

		modelMap.addAttribute("archivoAdjunto", archivo);
		modelMap.addAttribute("idReturn", idReturn);
		modelMap.addAttribute("returnURL", returnURL);

		if (callback != null) {
			modelMap.put("callback", callback);
			modelMap.put("tieneCallback", true);
		} else {
			modelMap.put("tieneCallback", false);
		}

		return "archivoadjunto/show";

	}

	@RequestMapping(method = RequestMethod.PUT)
	public String update(
			@Valid @ModelAttribute("archivoAdjunto") ArchivoAdjunto archivoAdjunto,
			@Valid @ModelAttribute("idReturn") long idReturn,
			@Valid @ModelAttribute("returnURL") String returnURL,
			BindingResult result, ModelMap modelMap, SessionStatus status) {
		if (archivoAdjunto == null)
			throw new IllegalArgumentException("A archivoAdjunto is required");
		if (result.hasErrors()) {
			modelMap.addAttribute("archivoAdjunto", archivoAdjunto);
			modelMap.addAttribute("idReturn", idReturn);
			modelMap.addAttribute("returnURL", returnURL);

			return "archivoadjunto/update";
		}
		archivoAdjunto.merge();
		status.setComplete();

		return "redirect:/" + returnURL + "/" + idReturn;
	}

	@RequestMapping(value = "/archivoadjunto/{id}/form", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id,
			@RequestParam("idReturn") long idReturn,
			@RequestParam("returnURL") String returnURL, ModelMap modelMap) {
		if (id == null)
			throw new IllegalArgumentException("An Identifier is required");

		ArchivoAdjunto elArchivoAdjunto = ArchivoAdjunto.findArchivoAdjunto(id);

		if ((returnURL.equals("prueba"))
				|| (returnURL.equals("papeldetrabajo"))
				|| (returnURL.equals("actividadgeneral"))) {
			ActividadActuacion miActividadActuacion = ActividadActuacion
					.findActividadActuacion(idReturn);
			modelMap.addAttribute("codigoCompleto",
					miActividadActuacion.getCodigoCompleto());

		} else if (returnURL.equals("avanceactuacion")) {
			AvanceActuacion miAvanceActuacion = AvanceActuacion
					.findAvanceActuacion(idReturn);
			modelMap.addAttribute("codigoCompleto",
					miAvanceActuacion.getJustificacion());
		} else if (returnURL.equals("respuesta")) {
			Respuesta miRespuesta = Respuesta.findRespuesta(idReturn);
			modelMap.addAttribute("codigoCompleto", miRespuesta.getNumero());
		} else if (returnURL.equals("personalizacion")) {
			Personalizacion miPersonalizacion = Personalizacion
					.findPersonalizacion(idReturn);
			modelMap.addAttribute("codigoCompleto",
					miPersonalizacion.getNombreInstitucion());
		} else if (returnURL.equals("requerimiento")) {
			Requerimiento miRequerimiento = Requerimiento
					.findRequerimiento(idReturn);
			modelMap.addAttribute("codigoCompleto",
					miRequerimiento.getNumeroSolicitud());
		} else if (returnURL.equals("observacion")) {
			Observacion miObservacion = Observacion.findObservacion(idReturn);
			modelMap.addAttribute("codigoCompleto",
					"AG-" + miObservacion.getReferencia());
		} else if (returnURL.equals("alegato")) {
			Alegato miAlegato = Alegato.findAlegato(idReturn);
			modelMap.addAttribute("codigoCompleto",
					(miAlegato.getDescripcion()).substring(0, 25) + "...");
		} else if (returnURL.equals("secciondefault")) {
			SeccionDefault miSeccion = SeccionDefault
					.findSeccionDefault(idReturn);
			modelMap.addAttribute("codigoCompleto", miSeccion.getCodigo());
		} else {
			modelMap.addAttribute("codigoCompleto", "vacio");
		}

		modelMap.addAttribute("idReturn", idReturn);
		modelMap.addAttribute("returnURL", returnURL);
		modelMap.addAttribute("archivoAdjunto", elArchivoAdjunto);

		return "archivoadjunto/update";
	}

	@RequestMapping(value = "/archivoadjunto/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("id") Long id,
			@RequestParam("idReturn") long idReturn,
			@RequestParam("returnURL") String returnURL,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			ModelMap modelMap

	) {
		if (id == null)
			throw new IllegalArgumentException("An Identifier is required");

		// Historial de modificaciones
		Query queryHistorialCambiosByArchivoAdjunto = HistorialCambios
				.findHistorialCambiosesByArchivoAdjunto(ArchivoAdjunto
						.findArchivoAdjunto(id));
		List<HistorialCambios> historialCambiosByArchivoAdjunto = queryHistorialCambiosByArchivoAdjunto
				.getResultList();
		for (int i = 0; i < historialCambiosByArchivoAdjunto.size(); i++) {
			HistorialCambios elHistorial = historialCambiosByArchivoAdjunto
					.get(i);
			elHistorial.remove();
		}
		ArchivoAdjunto.findArchivoAdjunto(id).remove();
		// return "redirect:/archivoadjunto?page=" + ((page == null) ? "1" :
		// page.toString()) + "&size=" + ((size == null) ? "10" :
		// size.toString());

		return "redirect:/" + returnURL + "/" + idReturn;
	}

	@RequestMapping(value = "/archivoadjunto/render/{id}", method = RequestMethod.GET)
	public void render(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, @PathVariable("id") Long id) {
		ArchivoAdjunto archivo = ArchivoAdjunto.findArchivoAdjunto(id);
		try {
			response.setContentType("");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ archivo.getNombre() + "." + archivo.getExtension());
			response.getOutputStream().write(archivo.getFiledata());
		} catch (IOException e) {
			logger.error("Error renderizando archivo ", e);
		}
	}

	// Permite al usuario descargar el archivo y lo bloquea, seteando el
	// atributo loginUsuario con el login del usuario
	@RequestMapping(value = "/archivoadjunto/checkout/{id}", method = RequestMethod.GET)
	public void checkout(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, @PathVariable("id") Long id) {
		ArchivoAdjunto archivo = ArchivoAdjunto.findArchivoAdjunto(id);
		try {
			// Obtengo el usuario que esta descargando el archivo y lo seteo en
			// el campo
			Object principal = SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			// TODO: Volar esto baby
			logger.debug("el login del usuario que estoy seteando es: "
					+ ((User) principal).getUsername());
			// Creamos el historial de cambios
			HistorialCambios historial = new HistorialCambios();
			historial.setArchivoAdjunto(archivo);
			historial.setAccion("Descargar");
			Calendar cal = Calendar.getInstance();
			historial.setFecha(cal.getTime());
			historial.setUsuario(((User) principal).getUsername().toString());
			historial.persist();

			archivo.setLoginUsuario(((User) principal).getUsername());
			archivo.merge();
			response.setContentType("");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ archivo.getNombre() + "." + archivo.getExtension());
			response.getOutputStream().write(archivo.getFiledata());
		} catch (IOException e) {
			logger.error("Error renderizando archivo ", e);
		}
	}

	// Guarda la nueva version del archivo y lo desbloquea, seteando en null el
	// atributo loginUsuario
	@RequestMapping(value = "/archivoadjunto/checkin", method = RequestMethod.POST)
	public String checkIn(
			@ModelAttribute("callback") String callback,
			@Valid @ModelAttribute("archivoAdjunto") ArchivoAdjunto archivoAdjunto,
			BindingResult result, MultipartHttpServletRequest request,
			ModelMap modelMap, SessionStatus status) {

		// //Validamos que el usuario introduzca una ruta a un archivo
		// if ((archivoAdjunto.getFiledata())==null)
		// {
		// result.addError(new FieldError(result.getObjectName(), "filedata",
		// "Campo obligatorio"));
		// }
		if (archivoAdjunto == null)
			throw new IllegalArgumentException("A archivoAdjunto is required");
		if (result.hasErrors()) {
			result.addError(new ObjectError("filedata",
					"No fue posible almacenar el archivo seleccionado"));
			modelMap.addAttribute("archivoAdjunto", archivoAdjunto);

			return "archivoadjunto/" + archivoAdjunto.getId();
		}
		// Creamos el historial de cambios
		HistorialCambios historial = new HistorialCambios();
		historial.setArchivoAdjunto(archivoAdjunto);
		historial.setAccion("Subir nueva version");
		Calendar cal = Calendar.getInstance();
		historial.setFecha(cal.getTime());
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		historial.setUsuario(((User) principal).getUsername().toString());
		historial.persist();

		archivoAdjunto.setLoginUsuario(null);
		archivoAdjunto.merge();

		status.setComplete();

		logger.debug("Antes de redireccionar");

		return "redirect:/archivoadjunto/" + archivoAdjunto.getId();

	}

	@RequestMapping(value = "/archivoadjunto/list", method = RequestMethod.GET)
	public String list(ModelMap modelMap) {
		// Obtenemos la lista de todos los archivos adjuntos
		List<ArchivoAdjunto> allArchivosAdjuntos = ArchivoAdjunto
				.findAllArchivoAdjuntoes();
		List<ArchivoAdjunto> archivosCheckOut = new ArrayList();

		// Obtenemos solamente los archivos a los que se en cuentran en checkout
		for (int i = 0; i < allArchivosAdjuntos.size(); i++) {
			if (allArchivosAdjuntos.get(i).getLoginUsuario() != null) {
				archivosCheckOut.add(allArchivosAdjuntos.get(i));
			}
		}
		modelMap.addAttribute("archivoadjuntoes", archivosCheckOut);

		return "archivoadjunto/list";
	}

	@RequestMapping(value = "/archivoadjunto/liberar/{id}", method = RequestMethod.GET)
	public String liberar(ModelMap modelMap, @PathVariable("id") Long id) {
		// Buscamos el archivo a liberar y seteamos el usuario en null
		ArchivoAdjunto archivo = ArchivoAdjunto.findArchivoAdjunto(id);
		archivo.setLoginUsuario(null);
		archivo.merge();

		HistorialCambios historial = new HistorialCambios();
		historial.setArchivoAdjunto(archivo);
		historial
				.setAccion("Archivo desbloqueado por administrador del sistema");
		Calendar cal = Calendar.getInstance();
		historial.setFecha(cal.getTime());
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		historial.setUsuario(((User) principal).getUsername().toString());
		historial.persist();

		return "archivoadjunto/list";
	}

}
