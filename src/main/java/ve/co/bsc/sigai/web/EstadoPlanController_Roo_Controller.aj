package ve.co.bsc.sigai.web;

import java.lang.Long;
import java.lang.String;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.EstadoPlan;

privileged aspect EstadoPlanController_Roo_Controller {
    
    @RequestMapping(value = "/estadoplan/form", method = RequestMethod.GET)
    public String EstadoPlanController.createForm(ModelMap modelMap) {
        modelMap.addAttribute("estadoPlan", new EstadoPlan());
        return "estadoplan/create";
    }
    
    @RequestMapping(value = "/estadoplan/{id}", method = RequestMethod.GET)
    public String EstadoPlanController.show(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("estadoPlan", EstadoPlan.findEstadoPlan(id));
        return "estadoplan/show";
    }
    
    @RequestMapping(value = "/estadoplan", method = RequestMethod.GET)
    public String EstadoPlanController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("estadoplans", EstadoPlan.findEstadoPlanEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) EstadoPlan.countEstadoPlans() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("estadoplans", EstadoPlan.findAllEstadoPlans());
        }
        return "estadoplan/list";
    }
    
    @RequestMapping(value = "/estadoplan/{id}/form", method = RequestMethod.GET)
    public String EstadoPlanController.updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("estadoPlan", EstadoPlan.findEstadoPlan(id));
        return "estadoplan/update";
    }
    
    @RequestMapping(value = "/estadoplan/{id}", method = RequestMethod.DELETE)
    public String EstadoPlanController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        EstadoPlan.findEstadoPlan(id).remove();
        return "redirect:/estadoplan?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
}