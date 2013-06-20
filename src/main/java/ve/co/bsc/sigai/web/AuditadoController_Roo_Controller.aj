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
import ve.co.bsc.sigai.domain.Auditado;
import ve.co.bsc.sigai.domain.CodigoArea;
import ve.co.bsc.sigai.domain.EstadoAuditor;
import ve.co.bsc.sigai.domain.EstructuraCargos;
import ve.co.bsc.sigai.domain.EstructuraOrganizativa;
import ve.co.bsc.sigai.domain.OrganismoEnte;

privileged aspect AuditadoController_Roo_Controller {
    
    @RequestMapping(value = "/auditado", method = RequestMethod.POST)
    public String AuditadoController.create(@Valid Auditado auditado, BindingResult result, ModelMap modelMap) {
        if (auditado == null) throw new IllegalArgumentException("A auditado is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("auditado", auditado);
            modelMap.addAttribute("codigoareas", CodigoArea.findAllCodigoAreas());
            modelMap.addAttribute("estadoauditors", EstadoAuditor.findAllEstadoAuditors());
            modelMap.addAttribute("estructuracargoses", EstructuraCargos.findAllEstructuraCargoses());
            modelMap.addAttribute("estructuraorganizativas", EstructuraOrganizativa.findAllEstructuraOrganizativas());
            modelMap.addAttribute("organismoentes", OrganismoEnte.findAllOrganismoEntes());
            return "auditado/create";
        }
        auditado.persist();
        return "redirect:/auditado/" + auditado.getId();
    }
    
    @RequestMapping(value = "/auditado/form", method = RequestMethod.GET)
    public String AuditadoController.createForm(ModelMap modelMap) {
        modelMap.addAttribute("auditado", new Auditado());
        modelMap.addAttribute("codigoareas", CodigoArea.findAllCodigoAreas());
        modelMap.addAttribute("estadoauditors", EstadoAuditor.findAllEstadoAuditors());
        modelMap.addAttribute("estructuracargoses", EstructuraCargos.findAllEstructuraCargoses());
        modelMap.addAttribute("estructuraorganizativas", EstructuraOrganizativa.findAllEstructuraOrganizativas());
        modelMap.addAttribute("organismoentes", OrganismoEnte.findAllOrganismoEntes());
        return "auditado/create";
    }
    
    @RequestMapping(value = "/auditado", method = RequestMethod.GET)
    public String AuditadoController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("auditadoes", Auditado.findAuditadoEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) Auditado.countAuditadoes() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("auditadoes", Auditado.findAllAuditadoes());
        }
        return "auditado/list";
    }
    
    @RequestMapping(value = "/auditado/{id}", method = RequestMethod.DELETE)
    public String AuditadoController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        Auditado.findAuditado(id).remove();
        return "redirect:/auditado?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
    @RequestMapping(value = "find/ByLogin/form", method = RequestMethod.GET)
    public String AuditadoController.findAuditadoesByLoginForm(ModelMap modelMap) {
        return "auditado/findAuditadoesByLogin";
    }
    
    @RequestMapping(value = "find/ByLogin", method = RequestMethod.GET)
    public String AuditadoController.findAuditadoesByLogin(@RequestParam("login") String login, ModelMap modelMap) {
        if (login == null || login.length() == 0) throw new IllegalArgumentException("A Login is required.");
        modelMap.addAttribute("auditadoes", Auditado.findAuditadoesByLogin(login).getResultList());
        return "auditado/list";
    }
    
    @RequestMapping(value = "find/ByCedulaEquals/form", method = RequestMethod.GET)
    public String AuditadoController.findAuditadoesByCedulaEqualsForm(ModelMap modelMap) {
        return "auditado/findAuditadoesByCedulaEquals";
    }
    
    @RequestMapping(value = "find/ByCedulaEquals", method = RequestMethod.GET)
    public String AuditadoController.findAuditadoesByCedulaEquals(@RequestParam("cedula") String cedula, ModelMap modelMap) {
        if (cedula == null || cedula.length() == 0) throw new IllegalArgumentException("A Cedula is required.");
        modelMap.addAttribute("auditadoes", Auditado.findAuditadoesByCedulaEquals(cedula).getResultList());
        return "auditado/list";
    }
    
    @RequestMapping(value = "find/ByIdOrganismoEnte/form", method = RequestMethod.GET)
    public String AuditadoController.findAuditadoesByIdOrganismoEnteForm(ModelMap modelMap) {
        modelMap.addAttribute("organismoentes", OrganismoEnte.findAllOrganismoEntes());
        return "auditado/findAuditadoesByIdOrganismoEnte";
    }
    
    @RequestMapping(value = "find/ByIdOrganismoEnte", method = RequestMethod.GET)
    public String AuditadoController.findAuditadoesByIdOrganismoEnte(@RequestParam("idOrganismoEnte") OrganismoEnte idOrganismoEnte, ModelMap modelMap) {
        if (idOrganismoEnte == null) throw new IllegalArgumentException("A IdOrganismoEnte is required.");
        modelMap.addAttribute("auditadoes", Auditado.findAuditadoesByIdOrganismoEnte(idOrganismoEnte).getResultList());
        return "auditado/list";
    }
    
}
