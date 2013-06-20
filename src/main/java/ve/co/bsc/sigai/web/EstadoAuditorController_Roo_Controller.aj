package ve.co.bsc.sigai.web;

import java.lang.Long;
import java.lang.String;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.EstadoAuditor;

privileged aspect EstadoAuditorController_Roo_Controller {
    
    @RequestMapping(value = "/estadoauditor/form", method = RequestMethod.GET)
    public String EstadoAuditorController.createForm(ModelMap modelMap) {
        modelMap.addAttribute("estadoAuditor", new EstadoAuditor());
        return "estadoauditor/create";
    }
    
    @RequestMapping(value = "/estadoauditor/{id}", method = RequestMethod.GET)
    public String EstadoAuditorController.show(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("estadoAuditor", EstadoAuditor.findEstadoAuditor(id));
        return "estadoauditor/show";
    }
    
    @RequestMapping(value = "/estadoauditor", method = RequestMethod.GET)
    public String EstadoAuditorController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("estadoauditors", EstadoAuditor.findEstadoAuditorEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) EstadoAuditor.countEstadoAuditors() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("estadoauditors", EstadoAuditor.findAllEstadoAuditors());
        }
        return "estadoauditor/list";
    }
    
    @RequestMapping(value = "/estadoauditor/{id}/form", method = RequestMethod.GET)
    public String EstadoAuditorController.updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("estadoAuditor", EstadoAuditor.findEstadoAuditor(id));
        return "estadoauditor/update";
    }
    
    @RequestMapping(value = "/estadoauditor/{id}", method = RequestMethod.DELETE)
    public String EstadoAuditorController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        EstadoAuditor.findEstadoAuditor(id).remove();
        return "redirect:/estadoauditor?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
}
