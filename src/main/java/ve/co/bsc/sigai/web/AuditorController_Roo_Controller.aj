package ve.co.bsc.sigai.web;

import java.lang.Long;
import java.lang.String;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.Auditor;
import ve.co.bsc.sigai.domain.OrganismoEnte;

privileged aspect AuditorController_Roo_Controller {
    
    @RequestMapping(value = "/auditor/{id}", method = RequestMethod.DELETE)
    public String AuditorController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        Auditor.findAuditor(id).remove();
        return "redirect:/auditor?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
    @RequestMapping(value = "find/ByCedulaEquals/form", method = RequestMethod.GET)
    public String AuditorController.findAuditorsByCedulaEqualsForm(ModelMap modelMap) {
        return "auditor/findAuditorsByCedulaEquals";
    }
    
    @RequestMapping(value = "find/ByCedulaEquals", method = RequestMethod.GET)
    public String AuditorController.findAuditorsByCedulaEquals(@RequestParam("cedula") String cedula, ModelMap modelMap) {
        if (cedula == null || cedula.length() == 0) throw new IllegalArgumentException("A Cedula is required.");
        modelMap.addAttribute("auditors", Auditor.findAuditorsByCedulaEquals(cedula).getResultList());
        return "auditor/list";
    }
    
    @RequestMapping(value = "find/ByLoginEquals/form", method = RequestMethod.GET)
    public String AuditorController.findAuditorsByLoginEqualsForm(ModelMap modelMap) {
        return "auditor/findAuditorsByLoginEquals";
    }
    
    @RequestMapping(value = "find/ByLoginEquals", method = RequestMethod.GET)
    public String AuditorController.findAuditorsByLoginEquals(@RequestParam("login") String login, ModelMap modelMap) {
        if (login == null || login.length() == 0) throw new IllegalArgumentException("A Login is required.");
        modelMap.addAttribute("auditors", Auditor.findAuditorsByLoginEquals(login).getResultList());
        return "auditor/list";
    }
    
    @RequestMapping(value = "find/ById_OrganismoEnte/form", method = RequestMethod.GET)
    public String AuditorController.findAuditorsById_OrganismoEnteForm(ModelMap modelMap) {
        modelMap.addAttribute("organismoentes", OrganismoEnte.findAllOrganismoEntes());
        return "auditor/findAuditorsById_OrganismoEnte";
    }
    
    @RequestMapping(value = "find/ById_OrganismoEnte", method = RequestMethod.GET)
    public String AuditorController.findAuditorsById_OrganismoEnte(@RequestParam("id_OrganismoEnte") OrganismoEnte id_OrganismoEnte, ModelMap modelMap) {
        if (id_OrganismoEnte == null) throw new IllegalArgumentException("A Id_OrganismoEnte is required.");
        modelMap.addAttribute("auditors", Auditor.findAuditorsById_OrganismoEnte(id_OrganismoEnte).getResultList());
        return "auditor/list";
    }
    
}
