package ve.co.bsc.sigai.web;

import java.lang.Long;
import java.lang.String;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.EstadoObservacion;

privileged aspect EstadoObservacionController_Roo_Controller {
    
    @RequestMapping(value = "/estadoobservacion/form", method = RequestMethod.GET)
    public String EstadoObservacionController.createForm(ModelMap modelMap) {
        modelMap.addAttribute("estadoObservacion", new EstadoObservacion());
        return "estadoobservacion/create";
    }
    
    @RequestMapping(value = "/estadoobservacion/{id}", method = RequestMethod.GET)
    public String EstadoObservacionController.show(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("estadoObservacion", EstadoObservacion.findEstadoObservacion(id));
        return "estadoobservacion/show";
    }
    
    @RequestMapping(value = "/estadoobservacion", method = RequestMethod.GET)
    public String EstadoObservacionController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("estadoobservacions", EstadoObservacion.findEstadoObservacionEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) EstadoObservacion.countEstadoObservacions() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("estadoobservacions", EstadoObservacion.findAllEstadoObservacions());
        }
        return "estadoobservacion/list";
    }
    
    @RequestMapping(value = "/estadoobservacion/{id}/form", method = RequestMethod.GET)
    public String EstadoObservacionController.updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("estadoObservacion", EstadoObservacion.findEstadoObservacion(id));
        return "estadoobservacion/update";
    }
    
    @RequestMapping(value = "/estadoobservacion/{id}", method = RequestMethod.DELETE)
    public String EstadoObservacionController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        EstadoObservacion.findEstadoObservacion(id).remove();
        return "redirect:/estadoobservacion?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
}
