package ve.co.bsc.sigai.web;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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
import ve.co.bsc.sigai.domain.RolUsuario;
import ve.co.bsc.sigai.domain.TipoRol;

@RooWebScaffold(path = "rolusuario", automaticallyMaintainView = true, formBackingObject = RolUsuario.class)
@RequestMapping("/rolusuario/**")
@SessionAttributes({ "rolUsuario" })
@Controller
public class RolUsuarioController {

	/**
	 * Metodo Para Crear el Registro dentro de la base de datos
	 * 
	 * @author ehisonbp
	 * 
	 * @param rolUsuario
	 * @param result
	 * @param modelMap
	 * @param status
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/rolusuario", method = RequestMethod.POST)
	public String create(@Valid RolUsuario rolUsuario, BindingResult result,
			ModelMap modelMap, SessionStatus status, HttpServletRequest request) {

		// Metodo Para ingresar de forma automatica la fecha de creacion y de
		// actualizacion del registro

		// Metodo Para ingresar al sistema el Usuario que creo el Regirstro
		Object usuario = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		rolUsuario.setUsuario(((User) usuario).getUsername().toString());

		// Metodo para Verificar que el nombre del rol a registrar no este
		// repetido
		String nombre = rolUsuario.getNombre();
		Query query = rolUsuario.findRolUsuariosByNombreEquals(nombre);
		List<RolUsuario> rol = query.getResultList();
		if (rol.size() >= 1) {
			result.addError(new FieldError(result.getObjectName(), "nombre",
					"El rol para usuario " + nombre
							+ " se encuentra registrado en el sistema"));
		}

		if (rolUsuario == null)
			throw new IllegalArgumentException("A rolUsuario is required");
		if (result.hasErrors()) {
			modelMap.addAttribute("rolUsuario", rolUsuario);
			modelMap.addAttribute("estadoauditors",
					EstadoAuditor.findAllEstadoAuditors());
			modelMap.addAttribute("tiporols", TipoRol.findAllTipoRols());
			return "rolusuario/create";
		}
		java.util.Date fechaAct = new Date();
		rolUsuario.setFechaCreacion(fechaAct);
		rolUsuario.setFechaModificacion(fechaAct);
		rolUsuario.persist();
		return "redirect:/rolusuario/" + rolUsuario.getId();
	}

	/**
	 * Metodo que da las propiedades al formulario de RolUsuaio en la vista
	 * 
	 * @author ehisonbp
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/rolusuario/form", method = RequestMethod.GET)
	public String createForm(ModelMap modelMap) {
		modelMap.addAttribute("rolUsuario", new RolUsuario());
		modelMap.addAttribute("estadoauditors",
				EstadoAuditor.findAllEstadoAuditors());
		modelMap.addAttribute("tiporols", TipoRol.findAllTipoRols());
		return "rolusuario/create";
	}

	/**
	 * Metodo para Actualizar y versionar los registros en la base de datos
	 * 
	 * @author ehisonbp
	 * @param rolUsuario
	 * @param result
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public String update(@Valid RolUsuario rolUsuario, BindingResult result,
			ModelMap modelMap) {
		/**
		 * // Datos Para el versionamiento en la base de datos long id =
		 * rolUsuario.getId(); Long obj = new Long(id); int version =
		 * obj.intValue(); rolUsuario.setVersion(version); String nombre =
		 * rolUsuario.getNombre(); String descripcion =
		 * rolUsuario.getDescripcion(); EstadoAuditor estatus =
		 * rolUsuario.getEstatus(); TipoRol tipoRol = rolUsuario.getTipoRol();
		 * Date fechaC = rolUsuario.getFechaCreacion();
		 */
		// Modificaciones que se haran en el registro anterior

		if (rolUsuario == null)
			throw new IllegalArgumentException("A rolUsuario is required");
		if (result.hasErrors()) {
			modelMap.addAttribute("rolUsuario", rolUsuario);
			modelMap.addAttribute("estadoauditors",
					EstadoAuditor.findAllEstadoAuditors());
			modelMap.addAttribute("tiporols", TipoRol.findAllTipoRols());
			return "rolusuario/update";
		}
		/**
		 * rolUsuario.registrarRolUsuario(version, nombre, descripcion, estatus,
		 * tipoRol, fechaC);
		 */
		rolUsuario.merge();
		return "redirect:/rolusuario/" + rolUsuario.getId();
	}

	/**
	 * Metodo para dar las propiedades al formulario de actualizacion en la
	 * vista
	 * 
	 * @author ehisonbp
	 * @param id
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/rolusuario/{id}/form", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
		if (id == null)
			throw new IllegalArgumentException("An Identifier is required");
		modelMap.addAttribute("rolUsuario", RolUsuario.findRolUsuario(id));
		modelMap.addAttribute("estadoauditors",
				EstadoAuditor.findAllEstadoAuditors());
		modelMap.addAttribute("tiporols", TipoRol.findAllTipoRols());
		return "rolusuario/update";
	}

}