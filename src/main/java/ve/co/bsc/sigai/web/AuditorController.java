/*
 * Licencia GPL v3
 * 
 * Copyright (C) 2013 Centro Nacional de Tecnologías de Información.
 * SIGEFUAI Sistema de Informacion Gerencial de Fortalecimiento de las Unidades de Auditoria Interna
 * 
 * Copyright (C) 2013 Ehison Perez, Gerardo Perez, Alexis Veliz. All Rights Reserved.
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
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.security.authentication.encoding.PasswordEncoder;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import ve.co.bsc.sigai.domain.Auditado;
import ve.co.bsc.sigai.domain.Auditor;
import ve.co.bsc.sigai.domain.CodigoArea;
import ve.co.bsc.sigai.domain.CondicionAuditorInterno;
import ve.co.bsc.sigai.domain.EstadoAuditor;
import ve.co.bsc.sigai.domain.EstructuraCargos;
import ve.co.bsc.sigai.domain.EstructuraOrganizativa;
import ve.co.bsc.sigai.domain.Personalizacion;
import ve.co.bsc.sigai.domain.RolUsuario;
import ve.co.bsc.sigai.domain.TipoRol;
import ve.co.bsc.sigai.domain.Unidad;
import ve.co.bsc.sigai.domain.Usuario;
import ve.co.bsc.sigai.form.AuditorUsuarioForm;
import ve.co.bsc.sigai.service.HumanTaskService;
import ve.co.bsc.util.Util;

@RooWebScaffold(path = "auditor", automaticallyMaintainView = true, formBackingObject = Auditor.class)
@RequestMapping("/auditor/**")
@SessionAttributes({ "auditor", "usuario", "personalizacion" })
@Controller
public class AuditorController {

	static Logger logger = Logger.getLogger(AuditorController.class);
	@Autowired
	private HumanTaskService humanTaskService;

	public HumanTaskService getHumanTaskService() {
		return humanTaskService;
	}

	private static boolean validarU;

	public void setHumanTaskService(HumanTaskService humanTaskService) {
		this.humanTaskService = humanTaskService;
	}

	@Resource
	@Autowired
	public PasswordEncoder passwordEncoder;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
	}

	/**
	 * 
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/auditor", method = RequestMethod.GET)
	public String list(ModelMap modelMap) {
		Util util = new Util();

		// Metodo para traer los usuario de la UAI de una institucion en
		// especifico
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

		if (util.traerIdRif() != null) {
			modelMap.addAttribute("auditors", losQueSon);
			modelMap.addAttribute("auditadoes", Auditado
					.findAuditadoesByIdOrganismoEnte(util.traerIdRif())
					.getResultList());
		}
		return "auditor/list";
	}

	/**
	 * Metodo para guardar el usuario en base de datos
	 * 
	 * @author Ehisonbp
	 * 
	 * @param usuario
	 * @param result
	 * @param modelMap
	 * @param request
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/auditor", method = RequestMethod.POST)
	public String create(
			// @Valid Auditor auditor,
			@Valid @ModelAttribute("usuario") AuditorUsuarioForm usuario,
			BindingResult result, ModelMap modelMap,
			HttpServletRequest request, SessionStatus status) {
		Util util = new Util();
		if (usuario == null) {
			throw new IllegalArgumentException("A usuario is required");
		}

		String login = usuario.getUsuario().getLogin();
		login = login.toLowerCase();
		// Metodo para Validad la Existencia del longin a registrar
		if (Usuario.findUsuariosByLogin(login).getResultList().size() > 0) {
			result.addError(new ObjectError("login", "El login ya existe"));
		}
		// Metodo Para validar que la contraseña a crear sea valida en el
		// segundo campo
		if (!usuario.getPassword2().equals(usuario.getPassword())) {
			result.addError(new ObjectError("password2",
					"Las contraseñas no coinciden"));
		}
		// Verificacion de fuerza de password
		String fullRegex = "((?=.*\\d)(?=.*[a-zA-Z]).{6,20})";
		if (!usuario.getPassword().matches(fullRegex)) {
			result.addError(new ObjectError(
					"password",
					"La contraseña no cumple con los parámetros requeridos (de 6 a 20 caracteres incluyendo números y letras)"));
		}

		// Metodo para dar el tipo de cedula si es Extranjero o Venezolano
		String cedula = usuario.getAuditor().getCedula();
		if (usuario.getEffectTypes2() == 1) {
			cedula = "V-" + cedula;
		} else {
			cedula = "E-" + cedula;
		}
		// Verificar que no esten varios usuarios con la misma cedula
		Query query = Auditado.findAuditadoesByCedulaEquals(cedula);
		List<Auditado> aud = query.getResultList();
		query = null;
		query = Auditor.findAuditorsByCedulaEquals(cedula);
		List<Auditor> au = query.getResultList();
		if (aud.size() >= 1 || au.size() >= 1) {
			result.addError(new ObjectError("cedula",
					"La cedula con la que se desea registrar ya se encuentra en el sitema"));
		}

		// Verificar El rol que no sea Nulo
		List<RolUsuario> rolU = usuario.getUsuario().getRoles();
		String out = rolU.toString();

		// Metodo para Traer solos roles de la unidad de Auditoria Interna
		TipoRol tipoRol = new TipoRol();
		tipoRol = tipoRol.findTipoRol(new Long(3));

		if (result.hasErrors()) {
			usuario.setPassword("");
			usuario.setPassword2("");
			modelMap.addAttribute("usuario", usuario);
			modelMap.addAttribute("codigoareas",
					CodigoArea.findAllCodigoAreas());
			modelMap.addAttribute("estadoauditors",
					EstadoAuditor.findAllEstadoAuditors());
			if (util.traerIdRif() != null) {
				modelMap.addAttribute("estructuracargoses", EstructuraCargos
						.findEstructuraCargosesByRif(util.traerIdRif())
						.getResultList());
			}

			if (util.traerIdRif() != null) {
				modelMap.addAttribute(
						"estructuraorganizativas",
						EstructuraOrganizativa
								.findEstructuraOrganizativasByRif(
										util.traerIdRif()).getResultList());
			}
			modelMap.addAttribute("rolusuarios", RolUsuario
					.findRolUsuariosByTipoRol(tipoRol).getResultList());
			modelMap.addAttribute("condicionauditorinternoes",
					CondicionAuditorInterno.findAllCondicionAuditorInternoes());
			return "auditor/create";
		}

		// Unir codigo de Area con el numero telefonico de oficina
		int telefono = usuario.getAuditor().getTelfOficina().getCodigo();
		String local = String.valueOf(telefono);
		local = "0" + local + "-" + usuario.getAuditor().getTelefono();

		// Unir codigo de area con el celular
		telefono = 0;
		telefono = usuario.getAuditor().getTelfCelular().getCodigo();
		String celular = String.valueOf(telefono);
		celular = "0" + celular + "-" + usuario.getAuditor().getCelular();

		// Compara si el Usuario esta activo o Inactivo
		if (usuario.getAuditor().getEstadoAuditor() == EstadoAuditor
				.findEstadoAuditor(new Long(1))) {
			usuario.getUsuario().setActivo(true);
		} else {
			usuario.getUsuario().setActivo(false);
		}

		// Metodo para validar con el rol de usuario que se va a registrar en el
		// sistema
		// En caso de que se este registrado un usuario diferente a AUDITADO se
		// buscara la UAI de la institucion y se le relacionara con el usuario
		long idEO = 0;
		EstructuraOrganizativa organizativa = new EstructuraOrganizativa();
		if (out.equals("[UNIDAD_AUDITADO]")) {
			organizativa = usuario.getAuditor().getId_estructura();
		} else {
			query = EstructuraOrganizativa
					.findEstructuraOrganizativasByVerificarUnidad(true);
			List<EstructuraOrganizativa> list = query.getResultList();
			for (EstructuraOrganizativa or : list) {
				if (or.getRif() == util.traerIdRif()) {
					idEO = or.getId();
					break;
				}
			}
		}

		// Validar que solo exista un Auditor interno con el rol de titular

		EstructuraCargos cargos = usuario.getAuditor().getCargo();
		List<EstructuraCargos> carU = new ArrayList<EstructuraCargos>();
		carU.add(usuario.getAuditor().getCargo());
		usuario.getUsuario().setCargos(carU);
		usuario.getUsuario().setLogin(login);
		usuario.getUsuario().setPassword(
				passwordEncoder.encodePassword(usuario.getPassword(), null));
		usuario.getUsuario().persist();

		// En caso del Rol ingredaso sea AUDITADO
		if (out.equals("[UNIDAD_AUDITADO]")) {
			Auditado auditado = new Auditado();
			auditado.setIdEstructura(organizativa);
			auditado.setNombre(usuario.getAuditor().getNombre());
			auditado.setApellido(usuario.getAuditor().getApellido());
			auditado.setCorreo(usuario.getAuditor().getCorreo());
			auditado.setTelefono(local);
			auditado.setCelular(celular);
			auditado.setTelfOficina(usuario.getAuditor().getTelfOficina());
			auditado.setTelfCelular(usuario.getAuditor().getTelfCelular());
			auditado.setCedula(cedula);
			auditado.setCargo(cargos);
			auditado.setLogin(login);
			auditado.setEstadoAuditado(usuario.getAuditor().getEstadoAuditor());
			auditado.setIdOrganismoEnte(util.traerIdRif());
			auditado.persist();
			if (usuario.getEffectTypes() == 2) {
				long idU = 0;
				query = null;
				query = Unidad.findUnidadsByNombreEquals(usuario.getAuditor()
						.getId_estructura().getNombreUnidad().toString());
				List<Unidad> unList = query.getResultList();
				for (Unidad unidad : unList) {
					idU = unidad.getId();
				}
				Unidad unidad = Unidad.findUnidad(idU);
				unidad.setResponsable(auditado);
				unidad.merge();
			}
		} else {
			if (out.equals("[UNIDAD_GERENTE]")) {
				usuario.getPersonalizacion().setNombreCompletoAuditorInterno(
						usuario.getAuditor().getNombre() + " "
								+ usuario.getAuditor().getApellido());
				usuario.getPersonalizacion().setCedula(cedula);
				usuario.getPersonalizacion().setTelefonoOficina(local);
				usuario.getPersonalizacion().setTelefonoMovil(celular);
				usuario.getPersonalizacion().setCorreoInstitucional(
						usuario.getAuditor().getCorreo());
				usuario.getAuditor().setCondicionAuditorInterno(
						usuario.getPersonalizacion()
								.getCondicionAuditorInterno());
				usuario.getPersonalizacion().merge();
			}
			usuario.getAuditor().setLogin(login);
			usuario.getAuditor().setCedula(cedula);
			usuario.getAuditor().setTelefono(local);
			usuario.getAuditor().setCelular(celular);
			usuario.getAuditor().setId_OrganismoEnte(util.traerIdRif());
			usuario.getAuditor().setId_estructura(
					EstructuraOrganizativa.findEstructuraOrganizativa(idEO));
			usuario.getAuditor().persist();
			Util.registrarEntradaEnBitacora(usuario.getAuditor(),
					"Se creó el Auditor ", request.getRemoteAddr());
		}
		// registramos el usuario en el motor de workflow
		humanTaskService.registerUser(usuario.getUsuario());
		status.setComplete();
		return "redirect:/auditor";
	}

	/**
	 * 
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/auditor/form", method = RequestMethod.GET)
	public String createForm(ModelMap modelMap) {
		Util util = new Util();
		TipoRol rol = new TipoRol();
		rol = rol.findTipoRol(new Long(3));
		AuditorUsuarioForm form = new AuditorUsuarioForm();
		form.setPersonalizacion(Personalizacion.findPersonalizacion(util
				.traerIdPersonalizacion()));
		modelMap.addAttribute("usuario", form);
		modelMap.addAttribute("codigoareas", CodigoArea.findAllCodigoAreas());
		modelMap.addAttribute("estadoauditors",
				EstadoAuditor.findAllEstadoAuditors());
		if (util.traerIdRif() != null) {
			modelMap.addAttribute("estructuracargoses", EstructuraCargos
					.findEstructuraCargosesByRif(util.traerIdRif())
					.getResultList());
		}
		if (util.traerIdRif() != null) {
			modelMap.addAttribute(
					"estructuraorganizativas",
					EstructuraOrganizativa.findEstructuraOrganizativasByRif(
							util.traerIdRif()).getResultList());
		}
		modelMap.addAttribute("rolusuarios", RolUsuario
				.findRolUsuariosByTipoRol(rol).getResultList());
		modelMap.addAttribute("condicionauditorinternoes",
				CondicionAuditorInterno.findAllCondicionAuditorInternoes());
		return "auditor/create";
	}

	/**
	 * 
	 * @param usuario
	 * @param result
	 * @param modelMap
	 * @param request
	 * @param status
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public String update(
			@Valid @ModelAttribute("usuario") AuditorUsuarioForm usuario,
			BindingResult result, ModelMap modelMap,
			HttpServletRequest request, SessionStatus status) {
		Util util = new Util();
		if ((util.rolUsuarioLoggeado().equalsIgnoreCase("[ADMIN_INSTITUCION]") || util
				.rolUsuarioLoggeado().equalsIgnoreCase("[ADMIN]"))
				&& validarU == false) {
			TipoRol rol = new TipoRol();
			rol = rol.findTipoRol(new Long(3));
			if (usuario == null) {
				throw new IllegalArgumentException("A usuario is required");
			}

			if (result.hasErrors()) {
				modelMap.addAttribute("usuario", usuario);
				modelMap.addAttribute("estadoauditors",
						EstadoAuditor.findAllEstadoAuditors());
				modelMap.addAttribute("rolusuarios", RolUsuario
						.findRolUsuariosByTipoRol(rol).getResultList());
				if (util.traerIdRif() != null) {
					modelMap.addAttribute(
							"estructuracargoses",
							EstructuraCargos.findEstructuraCargosesByRif(
									util.traerIdRif()).getResultList());
				}
				return "auditor/update";
			}
			if (usuario.getAuditor().getEstadoAuditor() == EstadoAuditor
					.findEstadoAuditor(new Long(1))) {
				usuario.getUsuario().setActivo(true);
			} else {
				usuario.getUsuario().setActivo(false);
			}

			usuario.getUsuario().merge();
			usuario.getAuditor().merge();

			Util.registrarEntradaEnBitacora(usuario.getAuditor(),
					"Se modificó el Auditor ", request.getRemoteAddr());
			status.setComplete();
			return "redirect:/auditor";
		} else {
			if (!usuario.getPassword().trim().isEmpty()) {
				if (!usuario.getPassword2().equals(usuario.getPassword())) {
					result.addError(new ObjectError("password2",
							"Las contraseñas no coinciden"));
				}
				// Verificacion de fuerza de password
				String fullRegex = "((?=.*\\d)(?=.*[a-zA-Z]).{6,20})";
				if (!usuario.getPassword().matches(fullRegex)) {
					result.addError(new ObjectError(
							"password",
							"La contraseña no cumple con los parámetros requeridos (de 6 a 20 caracteres incluyendo números y letras)"));
				}
			}
			if (result.hasErrors()) {
				usuario.setPassword("");
				usuario.setPassword2("");
				modelMap.addAttribute("usuario", usuario);
				modelMap.addAttribute("codigoareas",
						CodigoArea.findAllCodigoAreas());
				return "auditor/modificarPerfil";
			}
			if (!usuario.getPassword().trim().isEmpty()) {
				usuario.getUsuario().setPassword(
						passwordEncoder.encodePassword(usuario.getPassword(),
								null));
			}

			// Unir codigo de Area con el numero telefonico de oficina
			int telefono = usuario.getAuditor().getTelfOficina().getCodigo();
			String local = String.valueOf(telefono);
			local = "0" + local + "-" + usuario.getAuditor().getTelefono();

			// Unir codigo de area con el celular
			telefono = 0;
			telefono = usuario.getAuditor().getTelfCelular().getCodigo();
			String celular = String.valueOf(telefono);
			celular = "0" + celular + "-" + usuario.getAuditor().getCelular();

			usuario.getAuditor().setCelular(celular);
			usuario.getAuditor().setTelefono(local);
			usuario.getUsuario().merge();
			usuario.getAuditor().merge();
			Util.registrarEntradaEnBitacora(usuario.getAuditor(),
					"Se modificó el Auditor ", request.getRemoteAddr());
			status.setComplete();
			return "redirect:/";
		}
	}

	/**
	 * 
	 * @param id
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/auditor/{id}/form", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
		Util util = new Util();
		if (id == null) {
			throw new IllegalArgumentException("An Identifier is required");
		}
		validarU = false;
		TipoRol rol = new TipoRol();
		rol = rol.findTipoRol(new Long(3));
		AuditorUsuarioForm usuario = new AuditorUsuarioForm();
		usuario.setAuditor(Auditor.findAuditor(id));
		Usuario usu = (Usuario) Usuario.findUsuariosByLogin(
				usuario.getAuditor().getLogin()).getSingleResult();
		usuario.setUsuario(usu);
		usuario.setPassword(usuario.getUsuario().getPassword());
		usuario.setPassword2(usuario.getUsuario().getPassword());

		modelMap.addAttribute("usuario", usuario);
		modelMap.addAttribute("rolusuarios", RolUsuario
				.findRolUsuariosByTipoRol(rol).getResultList());
		modelMap.addAttribute("estadoauditors",
				EstadoAuditor.findAllEstadoAuditors());
		if (util.traerIdRif() != null) {
			modelMap.addAttribute("estructuracargoses", EstructuraCargos
					.findEstructuraCargosesByRif(util.traerIdRif())
					.getResultList());
		}

		return "auditor/update";
	}

	/**
	 * 
	 * @param id
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/auditor/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") Long id, ModelMap modelMap) {
		if (id == null) {
			throw new IllegalArgumentException("An Identifier is required");
		}
		Auditor auditor = Auditor.findAuditor(id);
		Usuario usu = (Usuario) Usuario.findUsuariosByLogin(auditor.getLogin())
				.getSingleResult();
		modelMap.addAttribute("auditor", auditor);
		modelMap.addAttribute("usuario", usu);

		return "auditor/show";
	}

	/**
	 * Metodo para crear el formulario donde el usuario pueda modificar su
	 * propia informacion
	 * 
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/auditor/modificarPerfil/form", method = RequestMethod.GET)
	public String modifcarPerfilForm(ModelMap modelMap) {
		modelMap.clear();
		Util util = new Util();
		validarU = true;
		AuditorUsuarioForm usuario = new AuditorUsuarioForm();
		usuario.setUsuario(Usuario.findUsuario(util.traerIdUsuario()));
		Auditor au = (Auditor) Auditor.findAuditorsByLoginEquals(
				usuario.getUsuario().getLogin()).getSingleResult();
		usuario.setAuditor(au);
		if (au.getTelefono() != null && au.getCelular() != null) {
			if (au.getTelefono().length() > 7) {
				usuario.getAuditor().setTelefono(au.getTelefono().substring(5));
			}
			if (au.getCelular().length() > 7) {
				usuario.getAuditor().setCelular(au.getCelular().substring(5));
			}
		}

		usuario.setPassword(usuario.getUsuario().getPassword());
		usuario.setPassword2(usuario.getUsuario().getPassword());
		modelMap.addAttribute("usuario", usuario);
		modelMap.addAttribute("codigoareas", CodigoArea.findAllCodigoAreas());
		return "auditor/modificarPerfil";
	}

	/**
	 * Metodo para que el ususario pueda modificar su contraseña e informacion
	 * de contacto
	 * 
	 * @param usuario
	 * @param result
	 * @param modelMap
	 * @param request
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/auditor/modificarPerfil", method = RequestMethod.POST)
	public String modifcarPerfil(
			@Valid @ModelAttribute("usuario") AuditorUsuarioForm usuario,
			BindingResult result, ModelMap modelMap,
			HttpServletRequest request, SessionStatus status) {
		if (usuario == null) {
			throw new IllegalArgumentException("A usuario is required");
		}
		if (!usuario.getPassword().trim().isEmpty()) {
			if (!usuario.getPassword2().equals(usuario.getPassword())) {
				result.addError(new ObjectError("password2",
						"Las contraseñas no coinciden"));
			}
			// Verificacion de fuerza de password
			String fullRegex = "((?=.*\\d)(?=.*[a-zA-Z]).{6,20})";
			if (!usuario.getPassword().matches(fullRegex)) {
				result.addError(new ObjectError(
						"password",
						"La contraseña no cumple con los parámetros requeridos (de 6 a 20 caracteres incluyendo números y letras)"));
			}
		}
		if (result.hasErrors()) {
			usuario.setPassword("");
			usuario.setPassword2("");
			modelMap.addAttribute("usuario", usuario);

			modelMap.addAttribute("codigoareas",
					CodigoArea.findAllCodigoAreas());
			return "auditor/modificarPerfil";
		}
		if (!usuario.getPassword().trim().isEmpty()) {
			usuario.getUsuario()
					.setPassword(
							passwordEncoder.encodePassword(
									usuario.getPassword(), null));
		}
		usuario.getUsuario().merge();
		usuario.getAuditor().merge();
		Util.registrarEntradaEnBitacora(usuario.getAuditor(),
				"Se modificó el Auditor ", request.getRemoteAddr());
		status.setComplete();
		return "redirect:/";
	}

}
