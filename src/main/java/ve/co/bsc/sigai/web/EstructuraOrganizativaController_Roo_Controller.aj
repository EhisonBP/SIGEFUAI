package ve.co.bsc.sigai.web;

import java.lang.Boolean;
import java.lang.Long;
import java.lang.String;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.EstructuraOrganizativa;
import ve.co.bsc.sigai.domain.OrganismoEnte;

privileged aspect EstructuraOrganizativaController_Roo_Controller {
    
    @RequestMapping(value = "/estructuraorganizativa/{id}", method = RequestMethod.GET)
    public String EstructuraOrganizativaController.show(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("estructuraOrganizativa_fechaCreacion_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("estructuraOrganizativa_fechaModificacion_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("estructuraOrganizativa", EstructuraOrganizativa.findEstructuraOrganizativa(id));
        return "estructuraorganizativa/show";
    }
    
    @RequestMapping(value = "find/ByNombreUnidadEquals/form", method = RequestMethod.GET)
    public String EstructuraOrganizativaController.findEstructuraOrganizativasByNombreUnidadEqualsForm(ModelMap modelMap) {
        return "estructuraorganizativa/findEstructuraOrganizativasByNombreUnidadEquals";
    }
    
    @RequestMapping(value = "find/ByNombreUnidadEquals", method = RequestMethod.GET)
    public String EstructuraOrganizativaController.findEstructuraOrganizativasByNombreUnidadEquals(@RequestParam("nombreUnidad") String nombreUnidad, ModelMap modelMap) {
        if (nombreUnidad == null || nombreUnidad.length() == 0) throw new IllegalArgumentException("A NombreUnidad is required.");
        modelMap.addAttribute("estructuraorganizativas", EstructuraOrganizativa.findEstructuraOrganizativasByNombreUnidadEquals(nombreUnidad).getResultList());
        return "estructuraorganizativa/list";
    }
    
    @RequestMapping(value = "find/ByRif/form", method = RequestMethod.GET)
    public String EstructuraOrganizativaController.findEstructuraOrganizativasByRifForm(ModelMap modelMap) {
        modelMap.addAttribute("organismoentes", OrganismoEnte.findAllOrganismoEntes());
        return "estructuraorganizativa/findEstructuraOrganizativasByRif";
    }
    
    @RequestMapping(value = "find/ByRif", method = RequestMethod.GET)
    public String EstructuraOrganizativaController.findEstructuraOrganizativasByRif(@RequestParam("rif") OrganismoEnte rif, ModelMap modelMap) {
        if (rif == null) throw new IllegalArgumentException("A Rif is required.");
        modelMap.addAttribute("estructuraorganizativas", EstructuraOrganizativa.findEstructuraOrganizativasByRif(rif).getResultList());
        return "estructuraorganizativa/list";
    }
    
    @RequestMapping(value = "find/ByVerificarUnidad/form", method = RequestMethod.GET)
    public String EstructuraOrganizativaController.findEstructuraOrganizativasByVerificarUnidadForm(ModelMap modelMap) {
        return "estructuraorganizativa/findEstructuraOrganizativasByVerificarUnidad";
    }
    
    @RequestMapping(value = "find/ByVerificarUnidad", method = RequestMethod.GET)
    public String EstructuraOrganizativaController.findEstructuraOrganizativasByVerificarUnidad(@RequestParam("verificarUnidad") Boolean verificarUnidad, ModelMap modelMap) {
        if (verificarUnidad == null) throw new IllegalArgumentException("A VerificarUnidad is required.");
        modelMap.addAttribute("estructuraorganizativas", EstructuraOrganizativa.findEstructuraOrganizativasByVerificarUnidad(verificarUnidad).getResultList());
        return "estructuraorganizativa/list";
    }
    
}
