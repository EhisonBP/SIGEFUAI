package ve.co.bsc.sigai.web;

import java.lang.Long;
import java.lang.String;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.EstadoPlanDeAccion;

privileged aspect EstadoPlanDeAccionController_Roo_Controller {
    
    @RequestMapping(value = "/estadoplandeaccion/form", method = RequestMethod.GET)
    public String EstadoPlanDeAccionController.createForm(ModelMap modelMap) {
        modelMap.addAttribute("estadoPlanDeAccion", new EstadoPlanDeAccion());
        return "estadoplandeaccion/create";
    }
    
    @RequestMapping(value = "/estadoplandeaccion/{id}", method = RequestMethod.GET)
    public String EstadoPlanDeAccionController.show(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("estadoPlanDeAccion", EstadoPlanDeAccion.findEstadoPlanDeAccion(id));
        return "estadoplandeaccion/show";
    }
    
    @RequestMapping(value = "/estadoplandeaccion", method = RequestMethod.GET)
    public String EstadoPlanDeAccionController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("estadoplandeaccions", EstadoPlanDeAccion.findEstadoPlanDeAccionEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) EstadoPlanDeAccion.countEstadoPlanDeAccions() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("estadoplandeaccions", EstadoPlanDeAccion.findAllEstadoPlanDeAccions());
        }
        return "estadoplandeaccion/list";
    }
    
    @RequestMapping(value = "/estadoplandeaccion/{id}/form", method = RequestMethod.GET)
    public String EstadoPlanDeAccionController.updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("estadoPlanDeAccion", EstadoPlanDeAccion.findEstadoPlanDeAccion(id));
        return "estadoplandeaccion/update";
    }
    
    @RequestMapping(value = "/estadoplandeaccion/{id}", method = RequestMethod.DELETE)
    public String EstadoPlanDeAccionController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        EstadoPlanDeAccion.findEstadoPlanDeAccion(id).remove();
        return "redirect:/estadoplandeaccion?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
}
