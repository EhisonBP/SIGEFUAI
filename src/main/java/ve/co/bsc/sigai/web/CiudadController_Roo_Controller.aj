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
import ve.co.bsc.sigai.domain.Ciudad;
import ve.co.bsc.sigai.domain.Estado;
import ve.co.bsc.sigai.domain.EstadoAuditor;

privileged aspect CiudadController_Roo_Controller {
    
    @RequestMapping(value = "/ciudad", method = RequestMethod.POST)
    public String CiudadController.create(@Valid Ciudad ciudad, BindingResult result, ModelMap modelMap) {
        if (ciudad == null) throw new IllegalArgumentException("A ciudad is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("ciudad", ciudad);
            modelMap.addAttribute("estadoes", Estado.findAllEstadoes());
            modelMap.addAttribute("estadoauditors", EstadoAuditor.findAllEstadoAuditors());
            return "ciudad/create";
        }
        ciudad.persist();
        return "redirect:/ciudad/" + ciudad.getId();
    }
    
    @RequestMapping(value = "/ciudad/form", method = RequestMethod.GET)
    public String CiudadController.createForm(ModelMap modelMap) {
        modelMap.addAttribute("ciudad", new Ciudad());
        modelMap.addAttribute("estadoes", Estado.findAllEstadoes());
        modelMap.addAttribute("estadoauditors", EstadoAuditor.findAllEstadoAuditors());
        return "ciudad/create";
    }
    
    @RequestMapping(value = "/ciudad/{id}", method = RequestMethod.GET)
    public String CiudadController.show(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("ciudad", Ciudad.findCiudad(id));
        return "ciudad/show";
    }
    
    @RequestMapping(value = "/ciudad", method = RequestMethod.GET)
    public String CiudadController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("ciudads", Ciudad.findCiudadEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) Ciudad.countCiudads() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("ciudads", Ciudad.findAllCiudads());
        }
        return "ciudad/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public String CiudadController.update(@Valid Ciudad ciudad, BindingResult result, ModelMap modelMap) {
        if (ciudad == null) throw new IllegalArgumentException("A ciudad is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("ciudad", ciudad);
            modelMap.addAttribute("estadoes", Estado.findAllEstadoes());
            modelMap.addAttribute("estadoauditors", EstadoAuditor.findAllEstadoAuditors());
            return "ciudad/update";
        }
        ciudad.merge();
        return "redirect:/ciudad/" + ciudad.getId();
    }
    
    @RequestMapping(value = "/ciudad/{id}/form", method = RequestMethod.GET)
    public String CiudadController.updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("ciudad", Ciudad.findCiudad(id));
        modelMap.addAttribute("estadoes", Estado.findAllEstadoes());
        modelMap.addAttribute("estadoauditors", EstadoAuditor.findAllEstadoAuditors());
        return "ciudad/update";
    }
    
    @RequestMapping(value = "/ciudad/{id}", method = RequestMethod.DELETE)
    public String CiudadController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        Ciudad.findCiudad(id).remove();
        return "redirect:/ciudad?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
}
