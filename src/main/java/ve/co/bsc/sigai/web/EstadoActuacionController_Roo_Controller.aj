package ve.co.bsc.sigai.web;

import java.lang.Long;
import java.lang.String;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.EstadoActuacion;

privileged aspect EstadoActuacionController_Roo_Controller {
    
    @RequestMapping(value = "/estadoactuacion/form", method = RequestMethod.GET)
    public String EstadoActuacionController.createForm(ModelMap modelMap) {
        modelMap.addAttribute("estadoActuacion", new EstadoActuacion());
        return "estadoactuacion/create";
    }
    
    @RequestMapping(value = "/estadoactuacion/{id}", method = RequestMethod.GET)
    public String EstadoActuacionController.show(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("estadoActuacion", EstadoActuacion.findEstadoActuacion(id));
        return "estadoactuacion/show";
    }
    
    @RequestMapping(value = "/estadoactuacion", method = RequestMethod.GET)
    public String EstadoActuacionController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("estadoactuacions", EstadoActuacion.findEstadoActuacionEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) EstadoActuacion.countEstadoActuacions() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("estadoactuacions", EstadoActuacion.findAllEstadoActuacions());
        }
        return "estadoactuacion/list";
    }
    
    @RequestMapping(value = "/estadoactuacion/{id}/form", method = RequestMethod.GET)
    public String EstadoActuacionController.updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("estadoActuacion", EstadoActuacion.findEstadoActuacion(id));
        return "estadoactuacion/update";
    }
    
    @RequestMapping(value = "/estadoactuacion/{id}", method = RequestMethod.DELETE)
    public String EstadoActuacionController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        EstadoActuacion.findEstadoActuacion(id).remove();
        return "redirect:/estadoactuacion?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
    @RequestMapping(value = "find/ByNombre/form", method = RequestMethod.GET)
    public String EstadoActuacionController.findEstadoActuacionsByNombreForm(ModelMap modelMap) {
        return "estadoactuacion/findEstadoActuacionsByNombre";
    }
    
    @RequestMapping(value = "find/ByNombre", method = RequestMethod.GET)
    public String EstadoActuacionController.findEstadoActuacionsByNombre(@RequestParam("nombre") String nombre, ModelMap modelMap) {
        if (nombre == null || nombre.length() == 0) throw new IllegalArgumentException("A Nombre is required.");
        modelMap.addAttribute("estadoactuacions", EstadoActuacion.findEstadoActuacionsByNombre(nombre).getResultList());
        return "estadoactuacion/list";
    }
    
}
