package ve.co.bsc.sigai.web;

import java.lang.Long;
import java.lang.String;
import javax.validation.Valid;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.EstadoAuditor;
import ve.co.bsc.sigai.domain.EstructuraCargos;
import ve.co.bsc.sigai.domain.EstructuraOrganizativa;
import ve.co.bsc.sigai.domain.OrganismoEnte;

privileged aspect EstructuraCargosController_Roo_Controller {
    
    @RequestMapping(value = "/estructuracargos/{id}", method = RequestMethod.GET)
    public String EstructuraCargosController.show(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("estructuraCargos_fechaCreacion_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("estructuraCargos_fechaModificacion_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("estructuraCargos", EstructuraCargos.findEstructuraCargos(id));
        return "estructuracargos/show";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public String EstructuraCargosController.update(@Valid EstructuraCargos estructuraCargos, BindingResult result, ModelMap modelMap) {
        if (estructuraCargos == null) throw new IllegalArgumentException("A estructuraCargos is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("estructuraCargos", estructuraCargos);
            modelMap.addAttribute("estadoauditors", EstadoAuditor.findAllEstadoAuditors());
            modelMap.addAttribute("estructuraorganizativas", EstructuraOrganizativa.findAllEstructuraOrganizativas());
            modelMap.addAttribute("organismoentes", OrganismoEnte.findAllOrganismoEntes());
            modelMap.addAttribute("estructuraCargos_fechaCreacion_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
            modelMap.addAttribute("estructuraCargos_fechaModificacion_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
            return "estructuracargos/update";
        }
        estructuraCargos.merge();
        return "redirect:/estructuracargos/" + estructuraCargos.getId();
    }
    
    @RequestMapping(value = "/estructuracargos/{id}/form", method = RequestMethod.GET)
    public String EstructuraCargosController.updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("estructuraCargos", EstructuraCargos.findEstructuraCargos(id));
        modelMap.addAttribute("estadoauditors", EstadoAuditor.findAllEstadoAuditors());
        modelMap.addAttribute("estructuraorganizativas", EstructuraOrganizativa.findAllEstructuraOrganizativas());
        modelMap.addAttribute("organismoentes", OrganismoEnte.findAllOrganismoEntes());
        modelMap.addAttribute("estructuraCargos_fechaCreacion_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("estructuraCargos_fechaModificacion_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        return "estructuracargos/update";
    }
    
    @RequestMapping(value = "/estructuracargos/{id}", method = RequestMethod.DELETE)
    public String EstructuraCargosController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        EstructuraCargos.findEstructuraCargos(id).remove();
        return "redirect:/estructuracargos?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
    @RequestMapping(value = "find/ByNombreCargoEquals/form", method = RequestMethod.GET)
    public String EstructuraCargosController.findEstructuraCargosesByNombreCargoEqualsForm(ModelMap modelMap) {
        return "estructuracargos/findEstructuraCargosesByNombreCargoEquals";
    }
    
    @RequestMapping(value = "find/ByNombreCargoEquals", method = RequestMethod.GET)
    public String EstructuraCargosController.findEstructuraCargosesByNombreCargoEquals(@RequestParam("nombreCargo") String nombreCargo, ModelMap modelMap) {
        if (nombreCargo == null || nombreCargo.length() == 0) throw new IllegalArgumentException("A NombreCargo is required.");
        modelMap.addAttribute("estructuracargoses", EstructuraCargos.findEstructuraCargosesByNombreCargoEquals(nombreCargo).getResultList());
        return "estructuracargos/list";
    }
    
    @RequestMapping(value = "find/ByRif/form", method = RequestMethod.GET)
    public String EstructuraCargosController.findEstructuraCargosesByRifForm(ModelMap modelMap) {
        modelMap.addAttribute("organismoentes", OrganismoEnte.findAllOrganismoEntes());
        return "estructuracargos/findEstructuraCargosesByRif";
    }
    
    @RequestMapping(value = "find/ByRif", method = RequestMethod.GET)
    public String EstructuraCargosController.findEstructuraCargosesByRif(@RequestParam("rif") OrganismoEnte rif, ModelMap modelMap) {
        if (rif == null) throw new IllegalArgumentException("A Rif is required.");
        modelMap.addAttribute("estructuracargoses", EstructuraCargos.findEstructuraCargosesByRif(rif).getResultList());
        return "estructuracargos/list";
    }
    
}
