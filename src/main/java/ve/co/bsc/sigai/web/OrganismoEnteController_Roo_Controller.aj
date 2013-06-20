package ve.co.bsc.sigai.web;

import java.lang.Long;
import java.lang.String;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.OrganismoEnte;

privileged aspect OrganismoEnteController_Roo_Controller {
    
    @RequestMapping(value = "/organismoente/{id}", method = RequestMethod.GET)
    public String OrganismoEnteController.show(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("organismoEnte_fechaCreacion_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("organismoEnte_fechaModificacion_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("organismoEnte", OrganismoEnte.findOrganismoEnte(id));
        return "organismoente/show";
    }
    
    @RequestMapping(value = "/organismoente/{id}", method = RequestMethod.DELETE)
    public String OrganismoEnteController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        OrganismoEnte.findOrganismoEnte(id).remove();
        return "redirect:/organismoente?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
    @RequestMapping(value = "find/ByRifEquals/form", method = RequestMethod.GET)
    public String OrganismoEnteController.findOrganismoEntesByRifEqualsForm(ModelMap modelMap) {
        return "organismoente/findOrganismoEntesByRifEquals";
    }
    
    @RequestMapping(value = "find/ByRifEquals", method = RequestMethod.GET)
    public String OrganismoEnteController.findOrganismoEntesByRifEquals(@RequestParam("rif") String Rif, ModelMap modelMap) {
        if (Rif == null || Rif.length() == 0) throw new IllegalArgumentException("A Rif is required.");
        modelMap.addAttribute("organismoentes", OrganismoEnte.findOrganismoEntesByRifEquals(Rif).getResultList());
        return "organismoente/list";
    }
    
}
