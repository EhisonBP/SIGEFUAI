/*
 * Licencia GPL v3
 * 
 * Copyright (C) 2013 Centro Nacional de Tecnologías de Información.
 * SIGEFUAI Sistema de Informacion Gerencial de Fortalecimiento de las Unidades de Auditoria Interna
 * 
 * Copyright (C) 2013 Ehison Perez, Gerardo Perez @gerardoperez132, Alexis Veliz. All Rights Reserved.
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

import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import ve.co.bsc.sigai.domain.Ciudad;
import ve.co.bsc.sigai.domain.CodigoArea;
import ve.co.bsc.sigai.domain.DatosOrganismoEnte;
import ve.co.bsc.sigai.domain.Estado;
import ve.co.bsc.sigai.domain.EstadoAuditor;
import ve.co.bsc.sigai.domain.Municipios;
import ve.co.bsc.sigai.domain.OrganismoEnte;
import ve.co.bsc.sigai.domain.Personalizacion;
import ve.co.bsc.sigai.domain.TipoOrganismo;
import ve.co.bsc.sigai.domain.TipoPersonaRif;
import ve.co.bsc.sigai.form.OrganismoEnteForm;
import ve.co.bsc.util.Util;

//import com.sun.star.bridge.oleautomation.Date;

@RooWebScaffold(path = "organismoente", automaticallyMaintainView = true, formBackingObject = OrganismoEnte.class)
@RequestMapping("/organismoente/**")
@SessionAttributes({ "organismoEnte", "datosOrganismoEnte" })
@Controller
public class OrganismoEnteController {
	private static String estatus;
	Logger logger = Logger.getLogger(OrganismoEnteController.class);
	private static String r;

	/**
	 * 
	 * @param datosOrganismoEnte
	 * @param result
	 * @param modelMap
	 * @param request
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/organismoente", method = RequestMethod.POST)
	public String create(
			// @Valid OrganismoEnte organismoEnte,
			@Valid @ModelAttribute("datosOrganismoEnte") OrganismoEnteForm datosOrganismoEnte,
			BindingResult result, ModelMap modelMap,
			HttpServletRequest request, SessionStatus status) {

		/* Cableo Hacia Personalizacion */
		/** Extraigo los Datos **/
		String nombre = datosOrganismoEnte.getOrganismoEnte().getNombre();
		String rif = datosOrganismoEnte.getOrganismoEnte().getRif();
		String paginaWeb = datosOrganismoEnte.getDatosOrganismoEnte()
				.getPagina_web();
		String direccion = datosOrganismoEnte.getDatosOrganismoEnte()
				.getDirecion();
		String estado = datosOrganismoEnte.getUsState();
		String ciudad = datosOrganismoEnte.getCity();
		String municipio = datosOrganismoEnte.getMunicipio();
		int telefonoMaster = datosOrganismoEnte.getDatosOrganismoEnte()
				.getTelefono_master();

		/** Extraigo los Datos **/
		/** Valido que los Datos a registrar en personalizacion no van nulos **/
		if (nombre != null) {
			datosOrganismoEnte.getPersonalizacion()
					.setNombreInstitucion(nombre);
		}
		if (paginaWeb != null) {
			datosOrganismoEnte.getPersonalizacion().setWeb(paginaWeb);
		}
		if (direccion != null) {
			datosOrganismoEnte.getPersonalizacion().setDireccion(direccion);
		}
		if (estado != null) {
			datosOrganismoEnte.getPersonalizacion().setEstado(estado);
		}
		if (ciudad != null) {
			datosOrganismoEnte.getPersonalizacion().setCiudad(ciudad);
		}
		if (municipio != null) {
			datosOrganismoEnte.getPersonalizacion().setMunicipio(municipio);
		}
		/* Campo Obligatorio en la Vista */
		String tel = String.valueOf(datosOrganismoEnte.getDatosOrganismoEnte()
				.getCodigoArea().getCodigo())
				+ "-" + String.valueOf(telefonoMaster);
		datosOrganismoEnte.getPersonalizacion().setTelefonoMaster(tel);

		String fax = String.valueOf(datosOrganismoEnte.getDatosOrganismoEnte()
				.getCodigoArea().getCodigo())
				+ "-"
				+ datosOrganismoEnte.getPersonalizacion().getTelefonoFax();
		/* Carga de Rif Con Letra segun tipo */
		TipoPersonaRif tiporif = datosOrganismoEnte.getOrganismoEnte()
				.getTipo_rif();
		long tipo = tiporif.getId();

		List<TipoPersonaRif> t = TipoPersonaRif.findAllTipoPersonaRifs();
		String r = null;
		for (TipoPersonaRif tt : t) {
			long idTipo = tt.getId();

			if (idTipo == tipo) {
				String TipoRif = tt.getNombre();
				char o = rif.charAt(8);
				r = TipoRif.substring(0, 1) + "-" + rif.substring(0, 8) + "-"
						+ o;
				break;
			}
		}
		/***** Valido si el Rif no se encuentra Registrado *******/
		// int a = 0;
		List<OrganismoEnte> OrganismoEnteRifList = OrganismoEnte
				.findAllOrganismoEntes();
		for (OrganismoEnte d : OrganismoEnteRifList) {
			String b = d.getRif();
			if (b.equals(r)) {
				result.addError(new ObjectError("rif", "El RIF " + r
						+ " se encuentra registrado en el sistema"));
				// variable = d.getId();
				// a = 1;
				break;
			}
		}

		/**** Valido si el Rif no se encuentra Registrado *****/
		/* Carga de Rif Con Letra segun tipo */
		/* Cableo Hacia Personalizacion */
		/** Valido que los Datos a registrar en personalizacion no van nulos **/
		/* Se autoRegistra la Fecha de Creacion */
		// java.util.Calendar calendar = java.util.Calendar.getInstance();
		java.util.Date FechaActual = new Date();
		datosOrganismoEnte.getOrganismoEnte().setFechaCreacion(FechaActual);
		datosOrganismoEnte.getOrganismoEnte().setFechaModificacion(FechaActual);
		/***
		 * Valido Que el Ente Tutelar Este vacio cuado El organismo no depende
		 * de Ninguno
		 ***/
		if (datosOrganismoEnte.getEffectTypes() == 1) {
			datosOrganismoEnte.getOrganismoEnte().setId_organismo_padre(null);
			datosOrganismoEnte.getPersonalizacion().setNombreTutelar(
					"No Posee un Ente Tutelar");
		} else if (datosOrganismoEnte.getEffectTypes() == 2) {
			datosOrganismoEnte.getPersonalizacion().setNombreTutelar(
					datosOrganismoEnte.getOrganismoEnte()
							.getId_organismo_padre().getNombre().toString());
		}
		/***
		 * Valido Que el Ente Tutelar Este vacio cuado El organismo no depende
		 * de Ninguno
		 ***/

		if (datosOrganismoEnte == null)
			throw new IllegalArgumentException("A organismoEnte is required");
		if (result.hasErrors()) {
			modelMap.addAttribute("datosOrganismoEnte", datosOrganismoEnte);
			modelMap.addAttribute("estadoauditors",
					EstadoAuditor.findAllEstadoAuditors());
			modelMap.addAttribute("codigoareas",
					CodigoArea.findAllCodigoAreas());
			modelMap.addAttribute("tipoorganismoes",
					TipoOrganismo.findAllTipoOrganismoes());
			modelMap.addAttribute("tipopersonarifs",
					TipoPersonaRif.findAllTipoPersonaRifs());
			return "organismoente/create";
		}
		datosOrganismoEnte.getDatosOrganismoEnte().setEstatus(
				datosOrganismoEnte.getOrganismoEnte().getEstatus());
		datosOrganismoEnte.getOrganismoEnte().setRif(r);
		datosOrganismoEnte.getDatosOrganismoEnte().setRif(r);
		datosOrganismoEnte.getPersonalizacion().setRif(r);
		// datosOrganismoEnte.getDatosOrganismoEnte().setFax(fax);
		datosOrganismoEnte.getDatosOrganismoEnte().setCiudad(ciudad);
		datosOrganismoEnte.getDatosOrganismoEnte().setEstado(estado);
		datosOrganismoEnte.getDatosOrganismoEnte().setMunicipios(municipio);
		datosOrganismoEnte.getDatosOrganismoEnte().persist();
		datosOrganismoEnte.getOrganismoEnte().persist();
		datosOrganismoEnte.getPersonalizacion().setTipoOrganismo(
				datosOrganismoEnte.getOrganismoEnte().getId_tipo_organismo());
		datosOrganismoEnte.getPersonalizacion().setTelefonoFax(fax);
		datosOrganismoEnte.getPersonalizacion().persist();
		status.setComplete();
		return "redirect:/organismoente/"
				+ datosOrganismoEnte.getOrganismoEnte().getId();
	}

	/**
	 * 
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/organismoente/form", method = RequestMethod.GET)
	public String createForm(ModelMap modelMap) {
		modelMap.addAttribute("datosOrganismoEnte", new OrganismoEnteForm());
		modelMap.addAttribute("estadoauditors",
				EstadoAuditor.findAllEstadoAuditors());
		modelMap.addAttribute("codigoareas", CodigoArea.findAllCodigoAreas());
		modelMap.addAttribute("organismoentes",
				OrganismoEnte.findAllOrganismoEntes());
		modelMap.addAttribute("tipoorganismoes",
				TipoOrganismo.findAllTipoOrganismoes());
		modelMap.addAttribute("tipopersonarifs",
				TipoPersonaRif.findAllTipoPersonaRifs());
		return "organismoente/create";
	}

	/**
	 * 
	 * @param datosOrganismoEnte
	 * @param result
	 * @param modelMap
	 * @param request
	 * @param status
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public String update(
			@Valid @ModelAttribute("datosOrganismoEnte") OrganismoEnteForm datosOrganismoEnte,
			BindingResult result, ModelMap modelMap,
			HttpServletRequest request, SessionStatus status) {
		Util util = new Util();
		java.util.Date FechaActual = new Date();
		datosOrganismoEnte.getOrganismoEnte().setFechaModificacion(FechaActual);
		String out = util.rolUsuarioLoggeado();
		String rif = null;

		// Validacion para el Rif
		if (!out.equals("[ADMIN_INSTITUCION]")) {
			// Uniedo Tipo de rif con rif
			String tipoRif = datosOrganismoEnte.getOrganismoEnte()
					.getTipo_rif().getNombre();
			rif = datosOrganismoEnte.getOrganismoEnte().getRif();
			char o = rif.charAt(8);
			rif = tipoRif.substring(0, 1) + "-" + rif.substring(0, 8) + "-" + o;
			if (!r.equals(rif)) {
				/***** Valido si el Rif no se encuentra Registrado *******/
				List<OrganismoEnte> OrganismoEnteRifList = OrganismoEnte
						.findAllOrganismoEntes();
				for (OrganismoEnte d : OrganismoEnteRifList) {
					String b = d.getRif();
					if (b.equals(rif)) {
						result.addError(new ObjectError("rif", "El RIF " + rif
								+ " se encuentra registrado en el sistema"));
						break;
					}
				}
			}
		}

		if (datosOrganismoEnte == null)
			throw new IllegalArgumentException("A organismoEnte is required");
		if (result.hasErrors()) {
			modelMap.addAttribute("datosOrganismoEnte", datosOrganismoEnte);
			modelMap.addAttribute("estadoauditors",
					EstadoAuditor.findAllEstadoAuditors());
			modelMap.addAttribute("codigoareas",
					CodigoArea.findAllCodigoAreas());
			modelMap.addAttribute("organismoentes",
					OrganismoEnte.findAllOrganismoEntes());
			modelMap.addAttribute("tipoorganismoes",
					TipoOrganismo.findAllTipoOrganismoes());
			modelMap.addAttribute("tipopersonarifs",
					TipoPersonaRif.findAllTipoPersonaRifs());
			return "organismoente/update";
		}

		/***
		 * Valido Que el Ente Tutelar Este vacio cuado El organismo no depende
		 * de Ninguno
		 ***/
		if (datosOrganismoEnte.getEffectTypes() == 1) {
			datosOrganismoEnte.getOrganismoEnte().setId_organismo_padre(null);
			datosOrganismoEnte.getPersonalizacion().setNombreTutelar(
					"No Posee un Ente Tutelar");
		} else if (datosOrganismoEnte.getEffectTypes() == 2) {
			datosOrganismoEnte.getPersonalizacion().setNombreTutelar(
					datosOrganismoEnte.getOrganismoEnte()
							.getId_organismo_padre().getNombre().toString());
		}

		String fax = String.valueOf(datosOrganismoEnte.getDatosOrganismoEnte()
				.getCodigoArea().getCodigo())
				+ "-"
				+ datosOrganismoEnte.getPersonalizacion().getTelefonoFax();

		String nombre = datosOrganismoEnte.getOrganismoEnte().getNombre();
		String paginaWeb = datosOrganismoEnte.getDatosOrganismoEnte()
				.getPagina_web();
		String direccion = datosOrganismoEnte.getDatosOrganismoEnte()
				.getDirecion();
		String estado = datosOrganismoEnte.getDatosOrganismoEnte().getEstado();
		String ciudad = datosOrganismoEnte.getDatosOrganismoEnte().getCiudad();
		String municipio = datosOrganismoEnte.getDatosOrganismoEnte()
				.getMunicipios();
		int telefonoMaster = datosOrganismoEnte.getDatosOrganismoEnte()
				.getTelefono_master();

		// Registrando cambios en la Tabla de Personalizacion.
		datosOrganismoEnte.getPersonalizacion().setNombreInstitucion(nombre);
		datosOrganismoEnte.getPersonalizacion().setWeb(paginaWeb);
		datosOrganismoEnte.getPersonalizacion().setDireccion(direccion);
		datosOrganismoEnte.getPersonalizacion().setEstado(estado);
		datosOrganismoEnte.getPersonalizacion().setCiudad(ciudad);
		datosOrganismoEnte.getPersonalizacion().setMunicipio(municipio);
		String tel = String.valueOf(datosOrganismoEnte.getDatosOrganismoEnte()
				.getCodigoArea().getCodigo())
				+ "-" + String.valueOf(telefonoMaster);
		datosOrganismoEnte.getPersonalizacion().setTelefonoMaster(tel);
		String estatusN = datosOrganismoEnte.getOrganismoEnte().getEstatus()
				.getNombre();
		// Solo en caso que se Inaviliten la institucion para desactivar todos
		// los usuarios
		if (!out.equals("[ADMIN_INSTITUCION]")) {
			if (estatus.equals("Activo")) {
				if (estatusN.equals("Inactivo")) {
					util.desabilitarUsuariosAuditores(datosOrganismoEnte
							.getOrganismoEnte());
					util.desabilitarUsuariosAuditados(datosOrganismoEnte
							.getOrganismoEnte());
				}
			} else if (estatus.equals("Inactivo")) {
				if (estatusN.equals("Activo")) {
					util.habilitarUsuariosAuditores(datosOrganismoEnte
							.getOrganismoEnte());
					util.habilitarUsuariosAuditados(datosOrganismoEnte
							.getOrganismoEnte());
				}
			}
			datosOrganismoEnte.getOrganismoEnte().setRif(rif);
			datosOrganismoEnte.getDatosOrganismoEnte().setRif(rif);
			datosOrganismoEnte.getPersonalizacion().setRif(rif);
		}
		datosOrganismoEnte.getDatosOrganismoEnte().setEstatus(
				datosOrganismoEnte.getOrganismoEnte().getEstatus());
		datosOrganismoEnte.getDatosOrganismoEnte().setCiudad(ciudad);
		datosOrganismoEnte.getDatosOrganismoEnte().setEstado(estado);
		datosOrganismoEnte.getDatosOrganismoEnte().setMunicipios(municipio);
		datosOrganismoEnte.getPersonalizacion().setTipoOrganismo(
				datosOrganismoEnte.getOrganismoEnte().getId_tipo_organismo());
		datosOrganismoEnte.getPersonalizacion().setTelefonoFax(fax);

		datosOrganismoEnte.getDatosOrganismoEnte().merge();
		datosOrganismoEnte.getOrganismoEnte().merge();
		datosOrganismoEnte.getPersonalizacion().merge();
		status.setComplete();
		return "redirect:/organismoente/"
				+ datosOrganismoEnte.getOrganismoEnte().getId();
	}

	/**
	 * 
	 * @param id
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/organismoente/{id}/form", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
		long variable = 0;
		Util util = new Util();
		String out = util.rolUsuarioLoggeado();
		if (id == null)
			throw new IllegalArgumentException("An Identifier is required");
		OrganismoEnteForm datosOrganismoEnte = new OrganismoEnteForm();
		datosOrganismoEnte
				.setOrganismoEnte(OrganismoEnte.findOrganismoEnte(id));

		// traer datos de la tabla Personalizacion para su actualizacion
		Query query = Personalizacion
				.findPersonalizacionsByRifEquals(datosOrganismoEnte
						.getOrganismoEnte().getRif().toString());
		List<Personalizacion> list = query.getResultList();
		for (Personalizacion per : list) {
			variable = per.getId();
			break;
		}
		datosOrganismoEnte.setPersonalizacion(Personalizacion
				.findPersonalizacion(variable));

		// Traer datos de la tabla datosOrganismoEnte para su actualizacion
		query = DatosOrganismoEnte
				.findDatosOrganismoEntesByRifEquals(datosOrganismoEnte
						.getOrganismoEnte().getRif().toString());
		List<DatosOrganismoEnte> listDO = query.getResultList();
		for (DatosOrganismoEnte ente : listDO) {
			variable = ente.getId();
		}
		// Recortar Numero telefonico
		if (out.equals("[ADMIN]")) {
			String rif = datosOrganismoEnte.getOrganismoEnte().getRif();
			r = null;
			r = datosOrganismoEnte.getOrganismoEnte().getRif();
			String a = rif.substring(2, 10);
			String b = rif.substring(11);
			rif = a + b;
			datosOrganismoEnte.getOrganismoEnte().setRif(rif);
		}

		String tf = datosOrganismoEnte.getPersonalizacion().getTelefonoFax();
		datosOrganismoEnte.getPersonalizacion().setTelefonoFax(tf.substring(4));
		estatus = datosOrganismoEnte.getOrganismoEnte().getEstatus()
				.getNombre();
		datosOrganismoEnte.setDatosOrganismoEnte(DatosOrganismoEnte
				.findDatosOrganismoEnte(variable));
		// Mapa de Modelos
		modelMap.addAttribute("datosOrganismoEnte", datosOrganismoEnte);
		modelMap.addAttribute("estadoauditors",
				EstadoAuditor.findAllEstadoAuditors());
		modelMap.addAttribute("codigoareas", CodigoArea.findAllCodigoAreas());
		modelMap.addAttribute("organismoentes",
				OrganismoEnte.findAllOrganismoEntes());
		modelMap.addAttribute("tipoorganismoes",
				TipoOrganismo.findAllTipoOrganismoes());
		modelMap.addAttribute("tipopersonarifs",
				TipoPersonaRif.findAllTipoPersonaRifs());
		return "organismoente/update";
	}

	/**
	 * 
	 * @param page
	 * @param size
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/organismoente", method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			ModelMap modelMap) {
		Util util = new Util();
		String out = util.rolUsuarioLoggeado();

		if (out.equals("[ADMIN_INSTITUCION]")) {
			modelMap.addAttribute("organismoentes", OrganismoEnte
					.findOrganismoEntesByRifEquals(util.obtenerRif())
					.getResultList());
		} else {
			modelMap.addAttribute("organismoentes",
					OrganismoEnte.findAllOrganismoEntes());
		}
		return "organismoente/list";
	}

	/** Retorno todos los estados en formato json **/
	@RequestMapping(value = "/organismoente/estados", method = RequestMethod.GET)
	public @ResponseBody
	String estados() {
		StringBuilder linea = new StringBuilder();
		Estado estado = new Estado();
		String idEstado = "";
		String nombreEstado = "";
		List<Estado> listEstado = estado.findAllEstadoes();
		for (Estado c : listEstado) {
			// logger.debug("Jugando Vivo" + c.toString());
			nombreEstado = c.getNombre();
			idEstado = c.getId().toString();
			// idCiudad = c.getId().toString();
			// logger.debug("Prueba1" + idEstado);
			linea.append("{\"name\":\"" + nombreEstado /*
														 * Se construlle el
														 * Formato tipo json
														 */
					+ "\",\"id\":" + idEstado + "},");
		}
		// logger.debug("Prueba" + linea.toString());
		String formatoJson = linea.toString();

		return "[" + formatoJson.substring(0, formatoJson.length() - 1) + "]"; // Elimino
																				// la
																				// ultima
																				// coma
																				// para
																				// no
																				// crear
																				// inconvenientes
																				// con
																				// el
																				// formato
	}

	/** Retorno todos las ciudades casadas a un estado en formato json **/
	@RequestMapping(value = "/organismoente/ciudad.js", method = RequestMethod.GET)
	public @ResponseBody
	String ciudad(
			@RequestParam(value = "stateName", required = true) String state) { // recibo
																				// como
																				// parametro
																				// el
																				// Estado
																				// para
																				// debolver
																				// sus
																				// ciudades

		StringBuilder linea = new StringBuilder();
		Ciudad ciudad = new Ciudad();

		List<Ciudad> listCiudad = ciudad.findAllCiudads();
		for (Ciudad c : listCiudad) {
			// logger.debug("Jugando Vivo" + c.toString());
			String nombreCiudad = "";
			String nombreEstado = "";
			String idCiudad = "";
			nombreEstado = c.getId_estado().getNombre();
			// logger.debug("Par " + state+" Par2 "+nombreEstado);
			if (nombreEstado.equalsIgnoreCase(state)) { // comparo si el Estado
														// corresponde a la
														// ciudad para
														// agregarlos
				nombreCiudad = c.getNombre();
				idCiudad = c.getId().toString();
				// logger.debug("Prueba1" + idEstado);
				linea.append("{\"name\":\"" + nombreCiudad /*
															 * Se construlle el
															 * Formato tipo json
															 */
						+ "\",\"id\":" + idCiudad + "},");
			}// if
		}// for
			// logger.debug("PruebaJson" + linea.toString());
		String formatoJson = linea.toString();

		return "[" + formatoJson.substring(0, formatoJson.length() - 1) + "]"; // Elimino
																				// la
																				// ultima
																				// coma
																				// para
																				// no
																				// crear
																				// inconvenientes
																				// con
																				// el
																				// formato
	}

	/** Retorno todos los municipios casados a una ciudad **/
	@RequestMapping(value = "/organismoente/municipios.js", method = RequestMethod.GET)
	public @ResponseBody
	String ciudadPorEstado(
			@RequestParam(value = "ciudadName", required = true) String ciudadName) {// recibo
																						// como
																						// parametro
																						// la
																						// ciudad
																						// para
																						// debolver
																						// sus
																						// municipios
		StringBuilder linea = new StringBuilder();
		Municipios municipios = new Municipios();

		List<Municipios> listMunicipios = municipios.findAllMunicipioses();
		for (Municipios m : listMunicipios) {
			String nombreCiudad = "";
			String idMunicipio = "";
			String nombreMunicipio = "";
			nombreCiudad = m.getId_ciudad().getNombre();
			// logger.debug("Parametro1:"+ ciudadName + "Parametro2:" +
			// nombreCiudad);
			if (nombreCiudad.equalsIgnoreCase(ciudadName)) {
				idMunicipio = m.getId().toString();
				nombreMunicipio = m.getNombre();

				linea.append("{\"name\":\"" + nombreMunicipio /*
															 * Se construlle el
															 * Formato tipo json
															 */
						+ "\",\"id\":" + idMunicipio + "},");
			}// if
		}// for
			// logger.debug("PruebaJson" + linea.toString());
		String formatoJson = linea.toString();

		return "[" + formatoJson.substring(0, formatoJson.length() - 1) + "]"; // Elimino
																				// la
																				// ultima
																				// coma
																				// para
																				// no
																				// crear
																				// inconvenientes
																				// con
																				// el
																				// formato
	}

}
