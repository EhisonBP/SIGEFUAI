package ve.co.bsc.sigai.web;

import java.lang.String;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.OrganismoEnte;
import ve.co.bsc.sigai.domain.OtraActividad;

privileged aspect OtraActividadController_Roo_Controller {
    
    @RequestMapping(value = "find/ByRif/form", method = RequestMethod.GET)
    public String OtraActividadController.findOtraActividadsByRifForm(ModelMap modelMap) {
        modelMap.addAttribute("organismoentes", OrganismoEnte.findAllOrganismoEntes());
        return "otraactividad/findOtraActividadsByRif";
    }
    
    @RequestMapping(value = "find/ByRif", method = RequestMethod.GET)
    public String OtraActividadController.findOtraActividadsByRif(@RequestParam("rif") OrganismoEnte rif, ModelMap modelMap) {
        if (rif == null) throw new IllegalArgumentException("A Rif is required.");
        modelMap.addAttribute("otraactividads", OtraActividad.findOtraActividadsByRif(rif).getResultList());
        return "otraactividad/list";
    }
    
}
