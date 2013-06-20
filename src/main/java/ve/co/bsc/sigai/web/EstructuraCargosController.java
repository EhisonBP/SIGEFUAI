package ve.co.bsc.sigai.web;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import ve.co.bsc.sigai.domain.EstadoAuditor;
import ve.co.bsc.sigai.domain.EstructuraCargos;
import ve.co.bsc.sigai.domain.EstructuraOrganizativa;
import ve.co.bsc.util.Util;

@RooWebScaffold(path = "estructuracargos", automaticallyMaintainView = true, formBackingObject = EstructuraCargos.class)
@RequestMapping("/estructuracargos/**")
@SessionAttributes({ "estructuraCargos" })
@Controller
public class EstructuraCargosController {

	@RequestMapping(value = "/estructuracargos", method = RequestMethod.POST)
	public String create(@Valid EstructuraCargos estructuraCargos,
			BindingResult result, ModelMap modelMap) {
		Util util = new Util();
		// Carga de Fecha de forma Automatica
		java.util.Date fechaAct = new Date();
		estructuraCargos.setFechaCreacion(fechaAct);
		estructuraCargos.setFechaModificacion(fechaAct);

		if (estructuraCargos == null)
			throw new IllegalArgumentException("A estructuraCargos is required");
		if (result.hasErrors()) {
			modelMap.addAttribute("estructuraCargos", estructuraCargos);
			modelMap.addAttribute("estadoauditors",
					EstadoAuditor.findAllEstadoAuditors());
			if (util.traerIdRif() != null) {
				modelMap.addAttribute(
						"estructuraorganizativas",
						EstructuraOrganizativa
								.findEstructuraOrganizativasByRif(
										util.traerIdRif()).getResultList());
			}
			return "estructuracargos/create";
		}
		estructuraCargos.setRif(util.traerIdRif());
		estructuraCargos.persist();
		return "redirect:/estructuracargos/" + estructuraCargos.getId();
	}

	@RequestMapping(value = "/estructuracargos/form", method = RequestMethod.GET)
	public String createForm(ModelMap modelMap) {
		Util util = new Util();
		modelMap.addAttribute("estructuraCargos", new EstructuraCargos());
		modelMap.addAttribute("estadoauditors",
				EstadoAuditor.findAllEstadoAuditors());
		if (util.traerIdRif() != null) {
			modelMap.addAttribute(
					"estructuraorganizativas",
					EstructuraOrganizativa.findEstructuraOrganizativasByRif(
							util.traerIdRif()).getResultList());
		}
		return "estructuracargos/create";
	}

	@RequestMapping(value = "/estructuracargos", method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			ModelMap modelMap) {
		Util util = new Util();
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			modelMap.addAttribute("estructuracargoses", EstructuraCargos
					.findEstructuraCargosEntries(
							page == null ? 0 : (page.intValue() - 1) * sizeNo,
							sizeNo));
			float nrOfPages = (float) EstructuraCargos
					.countEstructuraCargoses() / sizeNo;
			modelMap.addAttribute(
					"maxPages",
					(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
							: nrOfPages));
		} else {
			if (util.traerIdRif() != null) {
				modelMap.addAttribute("estructuracargoses", EstructuraCargos
						.findEstructuraCargosesByRif(util.traerIdRif())
						.getResultList());
			}
		}
		return "estructuracargos/list";
	}

}
