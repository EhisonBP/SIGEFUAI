package ve.co.bsc.sigai.web;

import java.lang.Long;
import java.lang.String;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.EstadoActividadActuacion;

privileged aspect EstadoActividadActuacionController_Roo_Controller {
    
    @RequestMapping(value = "/estadoactividadactuacion/form", method = RequestMethod.GET)
    public String EstadoActividadActuacionController.createForm(ModelMap modelMap) {
        modelMap.addAttribute("estadoActividadActuacion", new EstadoActividadActuacion());
        return "estadoactividadactuacion/create";
    }
    
    @RequestMapping(value = "/estadoactividadactuacion/{id}", method = RequestMethod.GET)
    public String EstadoActividadActuacionController.show(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("estadoActividadActuacion", EstadoActividadActuacion.findEstadoActividadActuacion(id));
        return "estadoactividadactuacion/show";
    }
    
    @RequestMapping(value = "/estadoactividadactuacion", method = RequestMethod.GET)
    public String EstadoActividadActuacionController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("estadoactividadactuacions", EstadoActividadActuacion.findEstadoActividadActuacionEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) EstadoActividadActuacion.countEstadoActividadActuacions() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("estadoactividadactuacions", EstadoActividadActuacion.findAllEstadoActividadActuacions());
        }
        return "estadoactividadactuacion/list";
    }
    
    @RequestMapping(value = "/estadoactividadactuacion/{id}/form", method = RequestMethod.GET)
    public String EstadoActividadActuacionController.updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("estadoActividadActuacion", EstadoActividadActuacion.findEstadoActividadActuacion(id));
        return "estadoactividadactuacion/update";
    }
    
    @RequestMapping(value = "/estadoactividadactuacion/{id}", method = RequestMethod.DELETE)
    public String EstadoActividadActuacionController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        EstadoActividadActuacion.findEstadoActividadActuacion(id).remove();
        return "redirect:/estadoactividadactuacion?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
}
