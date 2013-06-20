package ve.co.bsc.sigai.web;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
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
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import ve.co.bsc.sigai.domain.EstadoAuditor;
import ve.co.bsc.sigai.domain.EstructuraOrganizativa;
import ve.co.bsc.sigai.domain.Personalizacion;
import ve.co.bsc.sigai.domain.TipoUnidad;
import ve.co.bsc.sigai.domain.Unidad;
import ve.co.bsc.sigai.form.EstructuraUnidadPerzonalizacionForm;
import ve.co.bsc.util.Util;

@RooWebScaffold(path = "estructuraorganizativa", automaticallyMaintainView = true, formBackingObject = EstructuraOrganizativa.class)
@RequestMapping("/estructuraorganizativa/**")
@SessionAttributes({ "estructuraOrganizativa", "unidad", "personalizacion" })
@Controller
public class EstructuraOrganizativaController {

	private Util util = new Util();
	private Query query;
	private List<EstructuraOrganizativa> eo;
	private static String nombreUnidad;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
	}

	/**
	 * Metodo para Guardar el Registro de las estructura organizativa de cada
	 * institucion
	 * 
	 * @author ehisonbp
	 * @param unidad
	 * @param result
	 * @param modelMap
	 * @param status
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/estructuraorganizativa", method = RequestMethod.POST)
	public String create(
			@Valid @ModelAttribute("unidad") EstructuraUnidadPerzonalizacionForm unidad,
			BindingResult result, ModelMap modelMap, SessionStatus status,
			HttpServletRequest request) {
		// Ingesando nombre de unidad en la tabla unidad
		String nombreU = unidad.getEstructuraOrganizativa().getNombreUnidad();
		if (nombreU != null) {
			unidad.getUnidad().setNombre(nombreU);
		}

		// Valida que la unidad a crear no exista
		query = null;
		query = EstructuraOrganizativa.findEstructuraOrganizativasByRif(util
				.traerIdRif());
		eo = null;
		eo = query.getResultList();
		for (EstructuraOrganizativa organizativa : eo) {
			if (organizativa.getNombreUnidad().equals(nombreU)) {
				result.addError(new ObjectError(
						"nombreUnidad",
						"Ya se encuentra registrada bajo este nombre una Gerencia y/o Departamento para esta "
								+ "Institucion"));
				break;
			}
		}

		if (unidad.getEffectTypes2() == 2) {
			query = null;
			query = EstructuraOrganizativa
					.findEstructuraOrganizativasByRif(util.traerIdRif());
			eo = null;
			eo = query.getResultList();
			for (EstructuraOrganizativa organizativa : eo) {
				if (organizativa.getVerificarUnidad() == true) {
					result.addError(new ObjectError(
							"verificarUnidad",
							"No se puede procesar su Informacion ya que encuentra registrado una Unidad de "
									+ "Auditoria Interna para esta institucion "
									+ util.rolUsuarioLoggeado()));
				}
			}
		}

		if (unidad == null)
			throw new IllegalArgumentException(
					"A estructuraOrganizativa is required");
		if (result.hasErrors()) {
			modelMap.addAttribute("unidad", unidad);
			modelMap.addAttribute("estadoauditors",
					EstadoAuditor.findAllEstadoAuditors());
			if (util.traerIdRif() != null) {
				modelMap.addAttribute(
						"estructuraorganizativas",
						EstructuraOrganizativa
								.findEstructuraOrganizativasByRif(
										util.traerIdRif()).getResultList());
			}
			modelMap.addAttribute("tipounidads",
					TipoUnidad.findAllTipoUnidads());
			modelMap.addAttribute("estadoauditors",
					EstadoAuditor.findAllEstadoAuditors());
			return "estructuraorganizativa/create";
		}

		// Ingresando descripcion de unidad en la tabla unidad
		String des = unidad.getEstructuraOrganizativa().getDescripcionUnidad();
		if (des != null) {
			unidad.getUnidad().setDescripcion(des);
		}

		// Ingresar de directamente la fecha de creacion y de modificacion en
		// tabla estructura_organizativa
		java.util.Date fechaAct = new Date();
		unidad.getEstructuraOrganizativa().setFechaCreacion(fechaAct);
		unidad.getEstructuraOrganizativa().setFechaModificacion(fechaAct);
		unidad.getEstructuraOrganizativa().setRif(util.traerIdRif());
		// Guardado datos en la tabla de personalizacion en caso que sea una UAI
		if (unidad.getEffectTypes2() == 2) {
			unidad.getEstructuraOrganizativa().setVerificarUnidad(true);
			unidad.getPersonalizacion().setCantAuditorInterno(1);
			unidad.getPersonalizacion().merge();
		} else if (unidad.getEffectTypes2() == 1) {
			unidad.getEstructuraOrganizativa().setVerificarUnidad(false);
		}
		unidad.getUnidad().setTipoUnidad(
				unidad.getEstructuraOrganizativa().getTipoUnidad());
		unidad.getUnidad().setRif(util.traerIdRif());
		unidad.getUnidad().persist();
		unidad.getEstructuraOrganizativa().persist();
		status.setComplete();
		return "redirect:/estructuraorganizativa";
	}

	/**
	 * Metodo que le envia a la vista las propiedades con la que creara el
	 * formulario
	 * 
	 * @param modelMap
	 * @return
	 * @author ehisonbp
	 */
	@RequestMapping(value = "/estructuraorganizativa/form", method = RequestMethod.GET)
	public String createForm(ModelMap modelMap) {
		EstructuraUnidadPerzonalizacionForm form = new EstructuraUnidadPerzonalizacionForm(
				util.traerIdPersonalizacion());
		// Verificar que no existe UAI para enviarle la propiedad de
		// Personalizacion
		query = null;
		eo = null;
		query = EstructuraOrganizativa.findEstructuraOrganizativasByRif(util
				.traerIdRif());
		eo = query.getResultList();
		for (EstructuraOrganizativa organizativa : eo) {
			if (organizativa.getVerificarUnidad() == true) {
				form = new EstructuraUnidadPerzonalizacionForm();
				break;
			}
		}

		modelMap.addAttribute("unidad", form);
		modelMap.addAttribute("estadoauditors",
				EstadoAuditor.findAllEstadoAuditors());
		if (util.traerIdRif() != null) {
			modelMap.addAttribute(
					"estructuraorganizativas",
					EstructuraOrganizativa.findEstructuraOrganizativasByRif(
							util.traerIdRif()).getResultList());
		}
		modelMap.addAttribute("tipounidads", TipoUnidad.findAllTipoUnidads());
		modelMap.addAttribute("estadoauditors",
				EstadoAuditor.findAllEstadoAuditors());
		return "estructuraorganizativa/create";
	}

	/**
	 * Metodo que lista los registro de la unidades por institucion
	 * 
	 * @param page
	 * @param size
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/estructuraorganizativa", method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			ModelMap modelMap) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			modelMap.addAttribute("estructuraorganizativas",
					EstructuraOrganizativa.findEstructuraOrganizativaEntries(
							page == null ? 0 : (page.intValue() - 1) * sizeNo,
							sizeNo));
			float nrOfPages = (float) EstructuraOrganizativa
					.countEstructuraOrganizativas() / sizeNo;
			modelMap.addAttribute(
					"maxPages",
					(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
							: nrOfPages));
		} else {
			if (util.traerIdRif() != null) {
				modelMap.addAttribute(
						"estructuraorganizativas",
						EstructuraOrganizativa
								.findEstructuraOrganizativasByRif(
										util.traerIdRif()).getResultList());
			}
		}
		return "estructuraorganizativa/list";
	}

	/**
	 * Metodo para Actualizar los registro de las unidades creadas
	 * 
	 * @param unidad
	 * @param result
	 * @param modelMap
	 * @param request
	 * @param status
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public String update(
			@Valid @ModelAttribute("unidad") EstructuraUnidadPerzonalizacionForm unidad,
			BindingResult result, ModelMap modelMap,
			HttpServletRequest request, SessionStatus status) {

		// Ingesando nombre de unidad en la tabla unidad
		String nombreU = unidad.getEstructuraOrganizativa().getNombreUnidad();
		if (nombreU != null) {
			unidad.getUnidad().setNombre(nombreU);
		}
		// Valida que la unidad a crear no exista mientas el nombre no sea
		// diferente
		if (!nombreUnidad.equals(nombreU)) {
			query = null;
			query = EstructuraOrganizativa
					.findEstructuraOrganizativasByRif(util.traerIdRif());
			eo = null;
			eo = query.getResultList();
			for (EstructuraOrganizativa organizativa : eo) {
				if (organizativa.getNombreUnidad().equals(nombreU)) {
					result.addError(new ObjectError(
							"nombreUnidad",
							"Ya se encuentra registrada bajo este nombre una Gerencia y/o Departamento para esta "
									+ "Institucion"));
					break;
				}
			}

		}

		if (unidad.getEstructuraOrganizativa().getVerificarUnidad() == false) {
			if (unidad.getEffectTypes2() == 2) {
				result.addError(new ObjectError(
						"verificarUnidad",
						"No se puede procesar su Informacion ya que esta no es una Unidad de Auditoria Interna"));
			}
		}

		if (unidad == null) {
			throw new IllegalArgumentException(
					"A estructuraOrganizativa is required");
		}

		if (result.hasErrors()) {
			modelMap.addAttribute("unidad", unidad);
			modelMap.addAttribute("estadoauditors",
					EstadoAuditor.findAllEstadoAuditors());
			if (util.traerIdRif() != null) {
				modelMap.addAttribute(
						"estructuraorganizativas",
						EstructuraOrganizativa
								.findEstructuraOrganizativasByRif(
										util.traerIdRif()).getResultList());
			}
			modelMap.addAttribute("tipounidads",
					TipoUnidad.findAllTipoUnidads());
			modelMap.addAttribute("estadoauditors",
					EstadoAuditor.findAllEstadoAuditors());
			return "estructuraorganizativa/update";
		}

		if (unidad.getEstructuraOrganizativa().getVerificarUnidad() == true) {
			unidad.getPersonalizacion().merge();
		}
		unidad.getUnidad().merge();
		unidad.getEstructuraOrganizativa().merge();
		status.setComplete();
		return "redirect:/estructuraorganizativa";
	}

	/**
	 * Metodo para crear el formulario de actualizacion de los registro de las
	 * unidades
	 * 
	 * @param id
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/estructuraorganizativa/{id}/form", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
		if (id == null) {
			throw new IllegalArgumentException("An Identifier is required");
		}
		EstructuraOrganizativa organizativa = EstructuraOrganizativa
				.findEstructuraOrganizativa(id);
		nombreUnidad = organizativa.getNombreUnidad();
		// Seleccionando el registro de la tabla unidada que se va a modificar
		// en la vista
		query = null;
		query = Unidad
				.findUnidadsByNombreEquals(organizativa.getNombreUnidad());
		List<Unidad> unidads = query.getResultList();
		long idU = 0;
		for (Unidad u : unidads) {
			idU = u.getId();
		}
		// Ingresando los valores de personalizacion en el formulario
		EstructuraUnidadPerzonalizacionForm unidad = new EstructuraUnidadPerzonalizacionForm();
		if (organizativa.getVerificarUnidad() == true) {
			unidad = new EstructuraUnidadPerzonalizacionForm(
					util.traerIdPersonalizacion());
		}
		// Ingreasndo los valores de unidad y estructura organizativa en el
		// formulario
		unidad.setEstructuraOrganizativa(EstructuraOrganizativa
				.findEstructuraOrganizativa(id));
		unidad.setUnidad(Unidad.findUnidad(idU));

		modelMap.addAttribute("unidad", unidad);
		modelMap.addAttribute("estadoauditors",
				EstadoAuditor.findAllEstadoAuditors());
		if (util.traerIdRif() != null) {
			modelMap.addAttribute(
					"estructuraorganizativas",
					EstructuraOrganizativa.findEstructuraOrganizativasByRif(
							util.traerIdRif()).getResultList());
		}
		modelMap.addAttribute("tipounidads", TipoUnidad.findAllTipoUnidads());
		modelMap.addAttribute("estadoauditors",
				EstadoAuditor.findAllEstadoAuditors());
		return "estructuraorganizativa/update";
	}

	/**
	 * Metodo para eliminar los registro de Unidad, EstructuraOrganizativa y
	 * algunos campos en la tabla Personalizacion
	 * 
	 * @param id
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value = "/estructuraorganizativa/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("id") Long id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size) {
		if (id == null)
			throw new IllegalArgumentException("An Identifier is required");
		EstructuraOrganizativa organizativa = EstructuraOrganizativa
				.findEstructuraOrganizativa(id);
		// Buscando el registro de unidad
		query = null;
		query = Unidad
				.findUnidadsByNombreEquals(organizativa.getNombreUnidad());
		List<Unidad> unidads = query.getResultList();
		long idU = 0;
		for (Unidad u : unidads) {
			idU = u.getId();
		}
		// Eliminando los campos de personalizacion en caso de que se elimine el
		// registro de la UAI
		if (organizativa.getVerificarUnidad() == true) {
			Personalizacion personalizacion = new Personalizacion();
			personalizacion = Personalizacion.findPersonalizacion(util
					.traerIdPersonalizacion());
			personalizacion.setCantAuditorInterno(null);
			personalizacion.setCantDirectivos(null);
			personalizacion.setCantCoordinadores(null);
			personalizacion.setCantProfesionales(null);
			personalizacion.setCantTecnicos(null);
			personalizacion.setCantNoProfesionales(null);
		}

		// Removiendo los registro de unidad y EstructuraOrganizativa
		organizativa.remove();
		Unidad.findUnidad(idU).remove();
		return "redirect:/estructuraorganizativa?page="
				+ ((page == null) ? "1" : page.toString()) + "&size="
				+ ((size == null) ? "10" : size.toString());
	}
}