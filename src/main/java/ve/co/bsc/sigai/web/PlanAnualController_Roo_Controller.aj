package ve.co.bsc.sigai.web;

import java.lang.String;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.OrganismoEnte;
import ve.co.bsc.sigai.domain.PlanAnual;

privileged aspect PlanAnualController_Roo_Controller {
    
    @RequestMapping(value = "find/ByRif/form", method = RequestMethod.GET)
    public String PlanAnualController.findPlanAnualsByRifForm(ModelMap modelMap) {
        modelMap.addAttribute("organismoentes", OrganismoEnte.findAllOrganismoEntes());
        return "plananual/findPlanAnualsByRif";
    }
    
    @RequestMapping(value = "find/ByRif", method = RequestMethod.GET)
    public String PlanAnualController.findPlanAnualsByRif(@RequestParam("rif") OrganismoEnte rif, ModelMap modelMap) {
        if (rif == null) throw new IllegalArgumentException("A Rif is required.");
        modelMap.addAttribute("plananuals", PlanAnual.findPlanAnualsByRif(rif).getResultList());
        return "plananual/list";
    }
    
}
