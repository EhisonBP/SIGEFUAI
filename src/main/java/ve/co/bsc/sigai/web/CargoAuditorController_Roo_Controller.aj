package ve.co.bsc.sigai.web;

import java.lang.Long;
import java.lang.String;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.CargoAuditor;

privileged aspect CargoAuditorController_Roo_Controller {
    
    @RequestMapping(value = "/cargoauditor/form", method = RequestMethod.GET)
    public String CargoAuditorController.createForm(ModelMap modelMap) {
        modelMap.addAttribute("cargoAuditor", new CargoAuditor());
        return "cargoauditor/create";
    }
    
    @RequestMapping(value = "/cargoauditor/{id}", method = RequestMethod.GET)
    public String CargoAuditorController.show(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("cargoAuditor", CargoAuditor.findCargoAuditor(id));
        return "cargoauditor/show";
    }
    
    @RequestMapping(value = "/cargoauditor", method = RequestMethod.GET)
    public String CargoAuditorController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("cargoauditors", CargoAuditor.findCargoAuditorEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) CargoAuditor.countCargoAuditors() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("cargoauditors", CargoAuditor.findAllCargoAuditors());
        }
        return "cargoauditor/list";
    }
    
    @RequestMapping(value = "/cargoauditor/{id}/form", method = RequestMethod.GET)
    public String CargoAuditorController.updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("cargoAuditor", CargoAuditor.findCargoAuditor(id));
        return "cargoauditor/update";
    }
    
    @RequestMapping(value = "/cargoauditor/{id}", method = RequestMethod.DELETE)
    public String CargoAuditorController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        CargoAuditor.findCargoAuditor(id).remove();
        return "redirect:/cargoauditor?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
}
