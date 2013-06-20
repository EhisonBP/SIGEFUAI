package ve.co.bsc.sigai.web;

import java.lang.Long;
import java.lang.String;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.ClaseActuacion;

privileged aspect ClaseActuacionController_Roo_Controller {
    
    @RequestMapping(value = "/claseactuacion/form", method = RequestMethod.GET)
    public String ClaseActuacionController.createForm(ModelMap modelMap) {
        modelMap.addAttribute("claseActuacion", new ClaseActuacion());
        return "claseactuacion/create";
    }
    
    @RequestMapping(value = "/claseactuacion/{id}", method = RequestMethod.GET)
    public String ClaseActuacionController.show(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("claseActuacion", ClaseActuacion.findClaseActuacion(id));
        return "claseactuacion/show";
    }
    
    @RequestMapping(value = "/claseactuacion", method = RequestMethod.GET)
    public String ClaseActuacionController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("claseactuacions", ClaseActuacion.findClaseActuacionEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) ClaseActuacion.countClaseActuacions() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("claseactuacions", ClaseActuacion.findAllClaseActuacions());
        }
        return "claseactuacion/list";
    }
    
    @RequestMapping(value = "/claseactuacion/{id}/form", method = RequestMethod.GET)
    public String ClaseActuacionController.updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("claseActuacion", ClaseActuacion.findClaseActuacion(id));
        return "claseactuacion/update";
    }
    
    @RequestMapping(value = "/claseactuacion/{id}", method = RequestMethod.DELETE)
    public String ClaseActuacionController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        ClaseActuacion.findClaseActuacion(id).remove();
        return "redirect:/claseactuacion?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
}
