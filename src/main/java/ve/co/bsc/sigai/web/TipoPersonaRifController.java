package ve.co.bsc.sigai.web;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import ve.co.bsc.sigai.domain.EstadoAuditor;
import ve.co.bsc.sigai.domain.TipoPersonaRif;

@RooWebScaffold(path = "tipopersonarif", automaticallyMaintainView = true, formBackingObject = TipoPersonaRif.class)
@RequestMapping("/tipopersonarif/**")
@SessionAttributes({ "tipoPersonaRif" })
@Controller
public class TipoPersonaRifController {

	@RequestMapping(value = "/tipopersonarif", method = RequestMethod.POST)
	public String create(@Valid TipoPersonaRif tipoPersonaRif,
			BindingResult result, ModelMap modelMap) {

		// Metodo Para ingresar de forma automatica la fecha de creacion y de
		// actualizacion del registro
		java.util.Date fechaAct = new Date();
		tipoPersonaRif.setFechaCreacion(fechaAct);
		tipoPersonaRif.setFechaModificacion(fechaAct);

		if (tipoPersonaRif == null) {
			throw new IllegalArgumentException("A tipoPersonaRif is required");
		}
		if (result.hasErrors()) {
			modelMap.addAttribute("tipoPersonaRif", tipoPersonaRif);
			modelMap.addAttribute("estadoauditors",
					EstadoAuditor.findAllEstadoAuditors());
			return "tipopersonarif/create";
		}
		tipoPersonaRif.persist();
		return "redirect:/tipopersonarif/" + tipoPersonaRif.getId();
	}

	@RequestMapping(value = "/tipopersonarif/form", method = RequestMethod.GET)
	public String createForm(ModelMap modelMap) {
		modelMap.addAttribute("tipoPersonaRif", new TipoPersonaRif());
		modelMap.addAttribute("estadoauditors",
				EstadoAuditor.findAllEstadoAuditors());
		return "tipopersonarif/create";
	}

	@RequestMapping(method = RequestMethod.PUT)
	public String update(@Valid TipoPersonaRif tipoPersonaRif,
			BindingResult result, ModelMap modelMap) {

		java.util.Date fechaAct = new Date();
		tipoPersonaRif.setFechaModificacion(fechaAct);

		if (tipoPersonaRif == null)
			throw new IllegalArgumentException("A tipoPersonaRif is required");
		if (result.hasErrors()) {
			modelMap.addAttribute("tipoPersonaRif", tipoPersonaRif);
			modelMap.addAttribute("estadoauditors",
					EstadoAuditor.findAllEstadoAuditors());
			return "tipopersonarif/update";
		}
		tipoPersonaRif.merge();
		return "redirect:/tipopersonarif/" + tipoPersonaRif.getId();
	}

	@RequestMapping(value = "/tipopersonarif/{id}/form", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
		if (id == null)
			throw new IllegalArgumentException("An Identifier is required");
		modelMap.addAttribute("tipoPersonaRif",
				TipoPersonaRif.findTipoPersonaRif(id));
		modelMap.addAttribute("estadoauditors",
				EstadoAuditor.findAllEstadoAuditors());
		return "tipopersonarif/update";
	}
}
