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
import ve.co.bsc.sigai.domain.ActividadAuditor;

privileged aspect ActividadAuditorController_Roo_Controller {
    
    @RequestMapping(value = "/actividadauditor", method = RequestMethod.POST)
    public String ActividadAuditorController.create(@Valid ActividadAuditor actividadAuditor, BindingResult result, ModelMap modelMap) {
        if (actividadAuditor == null) throw new IllegalArgumentException("A actividadAuditor is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("actividadAuditor", actividadAuditor);
            return "actividadauditor/create";
        }
        actividadAuditor.persist();
        return "redirect:/actividadauditor/" + actividadAuditor.getId();
    }
    
    @RequestMapping(value = "/actividadauditor/form", method = RequestMethod.GET)
    public String ActividadAuditorController.createForm(ModelMap modelMap) {
        modelMap.addAttribute("actividadAuditor", new ActividadAuditor());
        return "actividadauditor/create";
    }
    
    @RequestMapping(value = "/actividadauditor/{id}", method = RequestMethod.GET)
    public String ActividadAuditorController.show(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("actividadAuditor", ActividadAuditor.findActividadAuditor(id));
        return "actividadauditor/show";
    }
    
    @RequestMapping(value = "/actividadauditor", method = RequestMethod.GET)
    public String ActividadAuditorController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("actividadauditors", ActividadAuditor.findActividadAuditorEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) ActividadAuditor.countActividadAuditors() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("actividadauditors", ActividadAuditor.findAllActividadAuditors());
        }
        return "actividadauditor/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public String ActividadAuditorController.update(@Valid ActividadAuditor actividadAuditor, BindingResult result, ModelMap modelMap) {
        if (actividadAuditor == null) throw new IllegalArgumentException("A actividadAuditor is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("actividadAuditor", actividadAuditor);
            return "actividadauditor/update";
        }
        actividadAuditor.merge();
        return "redirect:/actividadauditor/" + actividadAuditor.getId();
    }
    
    @RequestMapping(value = "/actividadauditor/{id}/form", method = RequestMethod.GET)
    public String ActividadAuditorController.updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("actividadAuditor", ActividadAuditor.findActividadAuditor(id));
        return "actividadauditor/update";
    }
    
    @RequestMapping(value = "/actividadauditor/{id}", method = RequestMethod.DELETE)
    public String ActividadAuditorController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        ActividadAuditor.findActividadAuditor(id).remove();
        return "redirect:/actividadauditor?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
}
