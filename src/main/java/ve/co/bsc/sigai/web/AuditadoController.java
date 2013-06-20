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

import java.util.List;

import javax.annotation.Resource;
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
import ve.co.bsc.sigai.domain.CodigoArea;
import ve.co.bsc.sigai.domain.Cuestionario;
import ve.co.bsc.sigai.domain.EstadoAuditor;
import ve.co.bsc.sigai.domain.EstatusRequerimiento;
import ve.co.bsc.sigai.domain.EstructuraCargos;
import ve.co.bsc.sigai.domain.Requerimiento;
import ve.co.bsc.sigai.domain.Usuario;
import ve.co.bsc.sigai.form.AuditadoUsuarioForm;
import ve.co.bsc.sigai.service.HumanTaskService;
import ve.co.bsc.util.Util;

@RooWebScaffold(path = "auditado", automaticallyMaintainView = true, formBackingObject = Auditado.class)
@RequestMapping("/auditado/**")
@SessionAttributes({ "usuario" })
@Controller
public class AuditadoController {

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

	@RequestMapping(method = RequestMethod.PUT)
	public String update(
			// @Valid Auditado auditado,
			@Valid @ModelAttribute("usuario") AuditadoUsuarioForm usuario,
			BindingResult result, ModelMap modelMap,
			HttpServletRequest request, SessionStatus status) {
		Util util = new Util();
		if ((util.rolUsuarioLoggeado().equalsIgnoreCase("[ADMIN_INSTITUCION]") || util
				.rolUsuarioLoggeado().equalsIgnoreCase("[ADMIN]"))
				&& validarU == false) {
			if (usuario == null) {
				throw new IllegalArgumentException("A usuario is required");
			}

			if (result.hasErrors()) {
				modelMap.addAttribute("usuario", usuario);
				modelMap.addAttribute("estadoauditors",
						EstadoAuditor.findAllEstadoAuditors());
				if (util.traerIdRif() != null) {
					modelMap.addAttribute(
							"estructuracargoses",
							EstructuraCargos.findEstructuraCargosesByRif(
									util.traerIdRif()).getResultList());
				}
				return "auditado/update";
			}

			if (usuario.getAuditado().getEstadoAuditado() == EstadoAuditor
					.findEstadoAuditor(new Long(1))) {
				usuario.getUsuario().setActivo(true);
			} else {
				usuario.getUsuario().setActivo(false);
			}

			usuario.getUsuario().merge();
			usuario.getAuditado().merge();

			Util.registrarEntradaEnBitacora(usuario.getAuditado(),
					"Se modificó el auditado ", request.getRemoteAddr());
			status.setComplete();
			return "redirect:/auditado/" + usuario.getAuditado().getId();
		} else {
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
				return "auditado/modificarPerfil";
			}

			if (!usuario.getPassword().trim().isEmpty()) {
				usuario.getUsuario().setPassword(
						passwordEncoder.encodePassword(usuario.getPassword(),
								null));
			}

			// Unir codigo de Area con el numero telefonico de oficina
			int telefono = usuario.getAuditado().getTelfOficina().getCodigo();
			String local = String.valueOf(telefono);
			local = "0" + local + "-" + usuario.getAuditado().getTelefono();

			// Unir codigo de area con el celular
			telefono = 0;
			telefono = usuario.getAuditado().getTelfCelular().getCodigo();
			String celular = String.valueOf(telefono);
			celular = "0" + celular + "-" + usuario.getAuditado().getCelular();

			usuario.getAuditado().setCelular(celular);
			usuario.getAuditado().setTelefono(local);
			usuario.getUsuario().merge();
			usuario.getAuditado().merge();

			Util.registrarEntradaEnBitacora(usuario.getAuditado(),
					"Se modificó el auditado ", request.getRemoteAddr());
			status.setComplete();
			return "redirect:/";
		}
	}

	@RequestMapping(value = "/auditado/{id}/form", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
		if (id == null) {
			throw new IllegalArgumentException("An Identifier is required");
		}
		Util util = new Util();
		validarU = false;
		AuditadoUsuarioForm usuario = new AuditadoUsuarioForm();
		usuario.setAuditado(Auditado.findAuditado(id));
		Usuario usu = (Usuario) Usuario.findUsuariosByLogin(
				usuario.getAuditado().getLogin()).getSingleResult();
		usuario.setUsuario(usu);
		modelMap.addAttribute("usuario", usuario);
		modelMap.addAttribute("estadoauditors",
				EstadoAuditor.findAllEstadoAuditors());
		if (util.traerIdRif() != null) {
			modelMap.addAttribute("estructuracargoses", EstructuraCargos
					.findEstructuraCargosesByRif(util.traerIdRif())
					.getResultList());
		}
		return "auditado/update";
	}

	@RequestMapping(value = "/auditado/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") Long id, ModelMap modelMap) {
		if (id == null)
			throw new IllegalArgumentException("An Identifier is required");
		Auditado audi = Auditado.findAuditado(id);
		modelMap.addAttribute("auditado", audi);
		Usuario usu = (Usuario) Usuario.findUsuariosByLogin(audi.getLogin())
				.getSingleResult();
		modelMap.addAttribute("usuario", usu);
		return "auditado/show";
	}

	@RequestMapping(value = "/auditado/actividades", method = RequestMethod.GET)
	public String actividades(ModelMap modelMap) {
		Auditado auditado = (Auditado) Auditado.findAuditadoesByLogin(
				Usuario.findUsuarioCurrentlyLoggedIn().getLogin())
				.getSingleResult();

		// Busco los cuestionarios que tengan asignado a "auditado"
		List<Cuestionario> cuestionarios = Cuestionario
				.findCuestionariosByAuditadoAndNotAnswered(auditado)
				.getResultList();
		modelMap.addAttribute("cuestionarios", cuestionarios);

		// Busco los requerimientos que tengan asignado a "auditado"
		EstatusRequerimiento estadoRequerimiento = EstatusRequerimiento
				.findEstatusRequerimiento(new Long(3));
		// Este finder busca los requerimientos asignados a un "auditado" y que
		// no estén en estatus "estadoRequerimiento"
		// En este caso se busca como estatus "Recibido Totalmente", es decir,
		// se buscarán todos los requerimientos de
		// un "auditado" pero que no estén en estatus Recibido Totalmente
		List<Requerimiento> requerimientos = Requerimiento
				.findRequerimientoesByNotEstadoRequerimientoAndAuditado(
						estadoRequerimiento, auditado).getResultList();
		modelMap.addAttribute("requerimientoes", requerimientos);
		modelMap.addAttribute("requerimiento_fechaSolicitud_date_format",
				org.joda.time.format.DateTimeFormat.patternForStyle("S-",
						org.springframework.context.i18n.LocaleContextHolder
								.getLocale()));

		return "auditado/actividades";
	}

	/**
	 * Metodo donde los ususarios con rol de auditado pueden modificar su
	 * informacion de ususario
	 * 
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/auditado/modificarPerfil/form", method = RequestMethod.GET)
	public String modifcarPerfilForm(ModelMap modelMap) {
		Util util = new Util();
		validarU = true;
		AuditadoUsuarioForm usuario = new AuditadoUsuarioForm();
		usuario.setUsuario(Usuario.findUsuario(util.traerIdUsuario()));
		Auditado au = (Auditado) Auditado.findAuditadoesByLogin(
				usuario.getUsuario().getLogin()).getSingleResult();
		usuario.setAuditado(au);
		if (au.getTelefono().length() > 7) {
			usuario.getAuditado().setTelefono(au.getTelefono().substring(5));
		}
		if (au.getCelular().length() > 7) {
			usuario.getAuditado().setCelular(au.getCelular().substring(5));
		}

		usuario.setPassword(usuario.getUsuario().getPassword());
		usuario.setPassword2(usuario.getUsuario().getPassword());
		modelMap.addAttribute("usuario", usuario);
		modelMap.addAttribute("codigoareas", CodigoArea.findAllCodigoAreas());
		return "auditado/modificarPerfil";
	}

	@RequestMapping(value = "/auditado/modificarPerfil", method = RequestMethod.POST)
	public String modifcarPerfil(
			// @Valid Auditado auditado,
			@Valid @ModelAttribute("usuario") AuditadoUsuarioForm usuario,
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
			return "auditado/modificarPerfil";
		}

		if (!usuario.getPassword().trim().isEmpty()) {
			usuario.getUsuario()
					.setPassword(
							passwordEncoder.encodePassword(
									usuario.getPassword(), null));
		}

		usuario.getUsuario().merge();
		usuario.getAuditado().merge();

		Util.registrarEntradaEnBitacora(usuario.getAuditado(),
				"Se modificó el auditado ", request.getRemoteAddr());
		status.setComplete();
		return "redirect:/";
	}

}
