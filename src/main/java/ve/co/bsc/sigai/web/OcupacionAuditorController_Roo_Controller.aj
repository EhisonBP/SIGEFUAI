package ve.co.bsc.sigai.web;

import java.lang.String;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.ActividadAuditor;
import ve.co.bsc.sigai.domain.Auditor;
import ve.co.bsc.sigai.domain.OcupacionAuditor;
import ve.co.bsc.sigai.domain.PlanAnual;

privileged aspect OcupacionAuditorController_Roo_Controller {
    
    @RequestMapping(value = "find/ByAuditor/form", method = RequestMethod.GET)
    public String OcupacionAuditorController.findOcupacionAuditorsByAuditorForm(ModelMap modelMap) {
        modelMap.addAttribute("auditors", Auditor.findAllAuditors());
        return "ocupacionauditor/findOcupacionAuditorsByAuditor";
    }
    
    @RequestMapping(value = "find/ByAuditor", method = RequestMethod.GET)
    public String OcupacionAuditorController.findOcupacionAuditorsByAuditor(@RequestParam("auditor") Auditor auditor, ModelMap modelMap) {
        if (auditor == null) throw new IllegalArgumentException("A Auditor is required.");
        modelMap.addAttribute("ocupacionauditors", OcupacionAuditor.findOcupacionAuditorsByAuditor(auditor).getResultList());
        return "ocupacionauditor/list";
    }
    
    @RequestMapping(value = "find/ByActividadAuditor/form", method = RequestMethod.GET)
    public String OcupacionAuditorController.findOcupacionAuditorsByActividadAuditorForm(ModelMap modelMap) {
        modelMap.addAttribute("actividadauditors", ActividadAuditor.findAllActividadAuditors());
        return "ocupacionauditor/findOcupacionAuditorsByActividadAuditor";
    }
    
    @RequestMapping(value = "find/ByActividadAuditor", method = RequestMethod.GET)
    public String OcupacionAuditorController.findOcupacionAuditorsByActividadAuditor(@RequestParam("actividadAuditor") ActividadAuditor actividadAuditor, ModelMap modelMap) {
        if (actividadAuditor == null) throw new IllegalArgumentException("A ActividadAuditor is required.");
        modelMap.addAttribute("ocupacionauditors", OcupacionAuditor.findOcupacionAuditorsByActividadAuditor(actividadAuditor).getResultList());
        return "ocupacionauditor/list";
    }
    
    @RequestMapping(value = "find/ByAuditorAndPlanAnual/form", method = RequestMethod.GET)
    public String OcupacionAuditorController.findOcupacionAuditorsByAuditorAndPlanAnualForm(ModelMap modelMap) {
        modelMap.addAttribute("auditors", Auditor.findAllAuditors());
        modelMap.addAttribute("plananuals", PlanAnual.findAllPlanAnuals());
        return "ocupacionauditor/findOcupacionAuditorsByAuditorAndPlanAnual";
    }
    
    @RequestMapping(value = "find/ByAuditorAndPlanAnual", method = RequestMethod.GET)
    public String OcupacionAuditorController.findOcupacionAuditorsByAuditorAndPlanAnual(@RequestParam("auditor") Auditor auditor, @RequestParam("planAnual") PlanAnual planAnual, ModelMap modelMap) {
        if (auditor == null) throw new IllegalArgumentException("A Auditor is required.");
        if (planAnual == null) throw new IllegalArgumentException("A PlanAnual is required.");
        modelMap.addAttribute("ocupacionauditors", OcupacionAuditor.findOcupacionAuditorsByAuditorAndPlanAnual(auditor, planAnual).getResultList());
        return "ocupacionauditor/list";
    }
    
}
