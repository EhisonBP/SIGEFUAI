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

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import ve.co.bsc.sigai.domain.Auditado;
import ve.co.bsc.sigai.domain.Auditor;
import ve.co.bsc.sigai.domain.CodigoArea;
import ve.co.bsc.sigai.domain.EstadoAuditor;
import ve.co.bsc.sigai.domain.EstructuraCargos;
import ve.co.bsc.sigai.domain.EstructuraOrganizativa;
import ve.co.bsc.sigai.domain.OrganismoEnte;
import ve.co.bsc.sigai.domain.RolUsuario;
import ve.co.bsc.sigai.domain.TipoRol;
import ve.co.bsc.sigai.domain.Usuario;
import ve.co.bsc.sigai.form.RegUsuarioSunaiForm;
import ve.co.bsc.sigai.service.HumanTaskService;
import ve.co.bsc.util.Util;

@RooWebScaffold(path = "usuario", automaticallyMaintainView = true, formBackingObject = Usuario.class)
@RequestMapping("/usuario/**")
@SessionAttributes({ "usuario", "auditor", "unidad" })
@Controller
public class UsuarioController {
	@Autowired
	private HumanTaskService humanTaskService;
	private static String cedulaA;
	private static String loginA;

	public HumanTaskService getHumanTaskService() {
		return humanTaskService;
	}

	public void setHumanTaskService(HumanTaskService humanTaskService) {
		this.humanTaskService = humanTaskService;
	}

	@Resource
	@Autowired
	public PasswordEncoder passwordEncoder;

	/**
	 * 
	 * 
	 * @param usuarios
	 * @param result
	 * @param modelMap
	 * @param request
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/usuario", method = RequestMethod.POST)
	public String create(
			@Valid @ModelAttribute("usuarios") RegUsuarioSunaiForm usuarios,
			BindingResult result, ModelMap modelMap,
			HttpServletRequest request, SessionStatus status) {

		if (!usuarios.getPassword2().equals(
				usuarios.getUsuarios().getPassword())) {
			result.addError(new ObjectError("password2",
					"Las contraseñas no coinciden"));
		}

		String login = usuarios.getUsuarios().getLogin();
		login = login.toLowerCase();
		if (Usuario.findUsuariosByLogin(login).getResultList().size() > 0) {
			result.addError(new ObjectError("usuarios.login",
					"El login ya existe"));
		}

		// Verificacion de fuerza de password
		String fullRegex = "((?=.*\\d)(?=.*[a-zA-Z]).{6,20})";
		if (!usuarios.getUsuarios().getPassword().matches(fullRegex)) {
			result.addError(new ObjectError(
					"usuarios.password",
					"La contraseña no cumple con los parámetros requeridos (de 6 a 20 caracteres incluyendo números y letras)"));
		}
		// Metodo para dar el tipo de cedula si es Extranjero o Venezolano
		String cedula = usuarios.getAuditor().getCedula();
		if (usuarios.getEffectTypes2() == 1) {
			cedula = "V-" + cedula;
		} else {
			cedula = "E-" + cedula;
		}

		// Verificar que no esten varios usuarios con la misma cedula
		Query query = Auditor.findAuditorsByCedulaEquals(cedula);
		List<Auditor> au = query.getResultList();
		if (au.size() >= 1) {
			result.addError(new ObjectError("cedula",
					"La cedula con la que desea registrar ya se encuentra en el sitema"));
		}
		/* Debo cablear el id cargo hacia usuario_estructura */

		/* Debo cablear el Login a Login de la tabla Auditor */
		usuarios.getAuditor().setLogin(login);
		usuarios.getUsuarios().setLogin(login);
		// usuarios.getUsuarios().getLogin();
		// /* Validacion de los Roles*///
		TipoRol tipoRol = new TipoRol();
		tipoRol = tipoRol.findTipoRol(new Long(2));
		// /* Validacion de los Roles*//

		// Unir codigo de Area con el numero telefonico de oficina
		int telefono = usuarios.getAuditor().getTelfOficina().getCodigo();
		String local = String.valueOf(telefono);
		local = "0" + local + "-" + usuarios.getAuditor().getTelefono();

		// Unir codigo de area con el celular
		telefono = 0;
		telefono = usuarios.getAuditor().getTelfCelular().getCodigo();
		String celular = String.valueOf(telefono);
		celular = "0" + celular + "-" + usuarios.getAuditor().getCelular();

		if (usuarios == null)
			throw new IllegalArgumentException("A usuario is required");
		if (result.hasErrors()) {
			usuarios.getUsuarios().setPassword("");
			usuarios.setPassword2("");
			modelMap.addAttribute("usuarios", usuarios);
			modelMap.addAttribute("estructuraorganizativas",
					EstructuraOrganizativa.findAllEstructuraOrganizativas());
			modelMap.addAttribute("codigoareas",
					CodigoArea.findAllCodigoAreas());
			modelMap.addAttribute("estructuracargoses",
					EstructuraCargos.findAllEstructuraCargoses());
			modelMap.addAttribute("organismoentes",
					OrganismoEnte.findAllOrganismoEntes());
			modelMap.addAttribute("rolusuarios", RolUsuario
					.findRolUsuariosByTipoRol(tipoRol).getResultList());
			modelMap.addAttribute("estadoauditors",
					EstadoAuditor.findAllEstadoAuditors());
			return "usuario/create";
		}

		// Compara si el Usuario esta activo o Inactivo
		if (usuarios.getAuditor().getEstadoAuditor() == EstadoAuditor
				.findEstadoAuditor(new Long(1))) {
			usuarios.getUsuarios().setActivo(true);
		} else {
			usuarios.getUsuarios().setActivo(false);
		}

		List<RolUsuario> roles = usuarios.getUsuarios().getRoles();
		String rol = null;
		for (RolUsuario usuario : roles) {
			rol = usuario.getNombre();
		}

		if (!rol.equals("ROLE_ADMIN_INSTITUCION")) {
			usuarios.getAuditor().setId_OrganismoEnte(null);
		}

		usuarios.getAuditor().setCedula(cedula);
		usuarios.getAuditor().setTelefono(local);
		usuarios.getAuditor().setCelular(celular);
		usuarios.getUsuarios().setPassword(
				passwordEncoder.encodePassword(usuarios.getUsuarios()
						.getPassword(), null));
		usuarios.getAuditor().persist();
		usuarios.getUsuarios().persist();
		Util.registrarEntradaEnBitacora(usuarios.getAuditor(),
				"Se creó el Usuario ", request.getRemoteAddr());
		// usuarios.getUnidad().persist();
		// registramos el usuario en el motor de workflow
		humanTaskService.registerUser(usuarios.getUsuarios());

		status.setComplete();
		return "redirect:/usuario/";// + usuarios.getUsuarios().getId();
	}

	/**
	 * 
	 * 
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/usuario/form", method = RequestMethod.GET)
	public String createForm(ModelMap modelMap) {
		// /* Validacion de los Roles*///
		TipoRol tipoRol = new TipoRol();
		tipoRol = tipoRol.findTipoRol(new Long(2));
		// /* Validacion de los Roles*///
		modelMap.addAttribute("usuarios", new RegUsuarioSunaiForm());
		modelMap.addAttribute("estructuraorganizativas",
				EstructuraOrganizativa.findAllEstructuraOrganizativas());
		modelMap.addAttribute("codigoareas", CodigoArea.findAllCodigoAreas());
		modelMap.addAttribute("estructuracargoses",
				EstructuraCargos.findAllEstructuraCargoses());
		modelMap.addAttribute("organismoentes",
				OrganismoEnte.findAllOrganismoEntes());
		modelMap.addAttribute("rolusuarios", RolUsuario
				.findRolUsuariosByTipoRol(tipoRol).getResultList());
		modelMap.addAttribute("estadoauditors",
				EstadoAuditor.findAllEstadoAuditors());
		return "usuario/create";
	}

	@RequestMapping(value = "/usuario/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") Long id, ModelMap modelMap) {

		RegUsuarioSunaiForm form = new RegUsuarioSunaiForm();
		form.setUsuarios(Usuario.findUsuario(id));
		String rol = form.getUsuarios().getRoles().toString();
		if (!rol.equals("[UNIDAD_AUDITADO]")) {
			Auditor au = (Auditor) Auditor.findAuditorsByLoginEquals(
					form.getUsuarios().getLogin()).getSingleResult();
			form.setAuditor(au);
		} else {
			Auditado auditado = (Auditado) Auditado.findAuditadoesByLogin(
					form.getUsuarios().getLogin()).getSingleResult();
			form.getAuditor().setNombre(auditado.getNombre());
			form.getAuditor()
					.setId_OrganismoEnte(auditado.getIdOrganismoEnte());
			form.getAuditor().setApellido(auditado.getApellido());
			form.getAuditor().setCedula(auditado.getCedula());
			form.getAuditor().setCorreo(auditado.getCorreo());
			form.getAuditor().setTelefono(auditado.getTelefono());
			form.getAuditor().setCelular(auditado.getCelular());
			form.getAuditor().setEstadoAuditor(auditado.getEstadoAuditado());
		}

		if (id == null)
			throw new IllegalArgumentException("An Identifier is required");
		modelMap.addAttribute("usuario", form);
		return "usuario/show";
	}

	/**
	 * 
	 * @param usuario
	 * @param result
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public String update(
			@Valid @ModelAttribute("auditor") RegUsuarioSunaiForm form,
			BindingResult result, ModelMap modelMap,
			HttpServletRequest request, SessionStatus status) {
		Query query = null;
		String rol = form.getUsuarios().getRoles().toString();
		String login = form.getUsuarios().getLogin();
		login = login.toLowerCase();

		// Metodo para dar el tipo de cedula si es Extranjero o Venezolano
		String cedula = form.getAuditor().getCedula();
		if (form.getEffectTypes2() == 1) {
			cedula = "V-" + cedula;
		} else {
			cedula = "E-" + cedula;
		}
		// Validaciones para la cedula de identidad del Usuario que se va editar
		if (!cedulaA.equals(cedula)) {
			query = Auditado.findAuditadoesByCedulaEquals(cedula);
			List<Auditado> aud = query.getResultList();
			query = null;
			query = Auditor.findAuditorsByCedulaEquals(cedula);
			List<Auditor> au = query.getResultList();
			if (aud.size() >= 1 || au.size() >= 1) {
				result.addError(new ObjectError("cedula",
						"La cedula con la que se desea registrar ya se encuentra en el sitema"));
			}
		}
		// Validacion para que el login no se encuentre repetido en el sistema
		if (!loginA.equals(login)) {
			if (Usuario.findUsuariosByLogin(login).getResultList().size() > 0) {
				result.addError(new ObjectError("usuarios.login",
						"El login ya existe"));
			}
		}

		// Verificacion de fuerza de password
		String fullRegex = "((?=.*\\d)(?=.*[a-zA-Z]).{6,20})";
		if (!form.getUsuarios().getPassword().matches(fullRegex)) {
			result.addError(new ObjectError(
					"usuarios.password",
					"La contraseña no cumple con los parámetros requeridos (de 6 a 20 caracteres incluyendo números y letras)"));
		}

		// Metodo Para validar que la contraseña a crear sea valida en el
		// segundo campo
		if (!form.getPassword2().equals(form.getUsuarios().getPassword())) {
			result.addError(new ObjectError("password2",
					"Las contraseñas no coinciden"));
		}

		if (form == null) {
			throw new IllegalArgumentException("A usuario is required");
		}
		if (result.hasErrors()) {
			form.getUsuarios().setPassword("");
			form.setPassword2("");
			modelMap.addAttribute("auditor", form);
			modelMap.addAttribute("rolusuarios",
					RolUsuario.findAllRolUsuarios());
			modelMap.addAttribute("estadoauditors",
					EstadoAuditor.findAllEstadoAuditors());
			return "usuario/update";
		}

		// Unir codigo de Area con el numero telefonico de oficina
		int telefono = form.getAuditor().getTelfOficina().getCodigo();
		String local = String.valueOf(telefono);
		local = "0" + local + "-" + form.getAuditor().getTelefono();

		// Unir codigo de area con el celular
		telefono = 0;
		telefono = form.getAuditor().getTelfCelular().getCodigo();
		String celular = String.valueOf(telefono);
		celular = "0" + celular + "-" + form.getAuditor().getCelular();

		// Compara si el Usuario esta activo o Inactivo para darles los permisos
		// de entrar al sistema
		if (form.getAuditor().getEstadoAuditor() == EstadoAuditor
				.findEstadoAuditor(new Long(1))) {
			form.getUsuarios().setActivo(true);
		} else {
			form.getUsuarios().setActivo(false);
		}

		// Validacion para que pueda guardar en la tabla Auditado o Auditor
		if (!rol.equals("[UNIDAD_AUDITADO]")) {
			form.getAuditor().setCedula(cedula);
			form.getAuditor().setLogin(login);
			form.getAuditor().setCelular(celular);
			form.getAuditor().setTelefono(local);
			form.getAuditor().merge();
		} else {
			form.getAuditado().setCedula(cedula);
			form.getAuditado().setLogin(login);
			form.getAuditado().setCelular(celular);
			form.getAuditado().setTelefono(local);
			form.getAuditado().setTelfOficina(
					form.getAuditor().getTelfOficina());
			form.getAuditado().setTelfCelular(
					form.getAuditor().getTelfCelular());
			form.getAuditado().setNombre(form.getAuditor().getNombre());
			form.getAuditado().setApellido(form.getAuditor().getApellido());
			form.getAuditado().setEstadoAuditado(
					form.getAuditor().getEstadoAuditor());
			form.getAuditado().setCorreo(form.getAuditor().getCorreo());

			form.getAuditado().merge();
		}

		form.getUsuarios().setLogin(login);
		form.getUsuarios().setPassword(
				passwordEncoder.encodePassword(
						form.getUsuarios().getPassword(), null));

		form.getUsuarios().merge();
		status.setComplete();
		return "redirect:/usuario/" + form.getUsuarios().getId();
	}

	/**
	 * 
	 * @param id
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/usuario/{id}/form", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
		if (id == null) {
			throw new IllegalArgumentException("An Identifier is required");
		}
		cedulaA = null;
		loginA = null;
		RegUsuarioSunaiForm form = new RegUsuarioSunaiForm();
		form.setUsuarios(Usuario.findUsuario(id));
		String rolUsuario = form.getUsuarios().getRoles().toString();
		if (rolUsuario.equals("[UNIDAD_AUDITADO]")) {
			Auditado auditado = (Auditado) Auditado.findAuditadoesByLogin(
					form.getUsuarios().getLogin()).getSingleResult();
			form.setAuditado(auditado);
			form.getAuditor().setNombre(auditado.getNombre());
			form.getAuditor().setApellido(auditado.getApellido());
			form.getAuditor().setCedula(auditado.getCedula().substring(2));
			form.getAuditor().setCorreo(auditado.getCorreo());
			form.getAuditor().setTelefono(auditado.getTelefono().substring(5));
			form.getAuditor().setCelular(auditado.getCelular().substring(5));
			form.getAuditor().setTelfOficina(auditado.getTelfOficina());
			form.getAuditor().setTelfCelular(auditado.getTelfCelular());
			form.getAuditor().setEstadoAuditor(auditado.getEstadoAuditado());
			cedulaA = auditado.getCedula();
		} else {
			Auditor au = (Auditor) Auditor.findAuditorsByLoginEquals(
					form.getUsuarios().getLogin()).getSingleResult();
			form.setAuditor(au);
			cedulaA = au.getCedula();
			form.getAuditor().setCedula(
					form.getAuditor().getCedula().substring(2));
			form.getAuditor().setTelefono(
					form.getAuditor().getTelefono().substring(5));
			form.getAuditor().setCelular(
					form.getAuditor().getCelular().substring(5));
		}
		loginA = form.getUsuarios().getLogin();
		modelMap.addAttribute("auditor", form);
		modelMap.addAttribute("rolusuarios", RolUsuario.findAllRolUsuarios());
		modelMap.addAttribute("estadoauditors",
				EstadoAuditor.findAllEstadoAuditors());
		modelMap.addAttribute("codigoareas", CodigoArea.findAllCodigoAreas());
		form.setPassword2(form.getUsuarios().getPassword());
		return "usuario/update";
	}
}