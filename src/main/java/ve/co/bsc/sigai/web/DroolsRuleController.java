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

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import ve.co.bsc.sigai.domain.DroolsRule;
import ve.co.bsc.sigai.domain.DroolsRuleTypeEnum;

@RooWebScaffold(path = "droolsrule", automaticallyMaintainView = true, formBackingObject = DroolsRule.class)
@RequestMapping("/droolsrule/**")
@Controller
public class DroolsRuleController {

	private static Logger logger = Logger.getLogger(DroolsRuleController.class);

	// we need a special property-editor that knows how to bind the data
	// from the request to a byte[]
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
	}

	@RequestMapping(value = "/droolsrule", method = RequestMethod.POST)
	public String create(@Valid DroolsRule droolsRule, BindingResult result,
			ModelMap modelMap) {
		if (droolsRule == null)
			throw new IllegalArgumentException("A droolsRule is required");
		if (result.hasErrors()) {
			modelMap.addAttribute("droolsRule", droolsRule);
			modelMap.addAttribute("droolsruletypeenum_enum",
					DroolsRuleTypeEnum.class.getEnumConstants());
			return "droolsrule/create";
		}
		droolsRule.persist();
		return "redirect:/droolsrule/" + droolsRule.getId();
	}

	@RequestMapping(value = "/droolsrule/form", method = RequestMethod.GET)
	public String createForm(ModelMap modelMap) {
		modelMap.addAttribute("droolsRule", new DroolsRule());
		modelMap.addAttribute("droolsruletypeenum_enum",
				DroolsRuleTypeEnum.class.getEnumConstants());
		return "droolsrule/create";
	}

	@RequestMapping(value = "/droolsrule/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") Long id, ModelMap modelMap) {
		if (id == null)
			throw new IllegalArgumentException("An Identifier is required");
		modelMap.addAttribute("droolsRule", DroolsRule.findDroolsRule(id));
		return "droolsrule/show";
	}

	@RequestMapping(value = "/droolsrule", method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			ModelMap modelMap) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			modelMap.addAttribute("droolsrules", DroolsRule
					.findDroolsRuleEntries(page == null ? 0
							: (page.intValue() - 1) * sizeNo, sizeNo));
			float nrOfPages = (float) DroolsRule.countDroolsRules() / sizeNo;
			modelMap.addAttribute(
					"maxPages",
					(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
							: nrOfPages));
		} else {
			modelMap.addAttribute("droolsrules",
					DroolsRule.findAllDroolsRules());
		}
		return "droolsrule/list";
	}

	@RequestMapping(method = RequestMethod.PUT)
	public String update(@Valid DroolsRule droolsRule, BindingResult result,
			ModelMap modelMap) {
		if (droolsRule == null)
			throw new IllegalArgumentException("A droolsRule is required");
		if (result.hasErrors()) {
			modelMap.addAttribute("droolsRule", droolsRule);
			modelMap.addAttribute("droolsruletypeenum_enum",
					DroolsRuleTypeEnum.class.getEnumConstants());
			return "droolsrule/update";
		}
		droolsRule.merge();
		return "redirect:/droolsrule/" + droolsRule.getId();
	}

	@RequestMapping(value = "/droolsrule/{id}/form", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
		if (id == null)
			throw new IllegalArgumentException("An Identifier is required");
		modelMap.addAttribute("droolsRule", DroolsRule.findDroolsRule(id));
		modelMap.addAttribute("droolsruletypeenum_enum",
				DroolsRuleTypeEnum.class.getEnumConstants());
		return "droolsrule/update";
	}

	@RequestMapping(value = "/droolsrule/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("id") Long id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size) {
		if (id == null)
			throw new IllegalArgumentException("An Identifier is required");
		DroolsRule.findDroolsRule(id).remove();
		return "redirect:/droolsrule?page="
				+ ((page == null) ? "1" : page.toString()) + "&size="
				+ ((size == null) ? "10" : size.toString());
	}

}
