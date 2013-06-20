package ve.co.bsc.sigai.web;

import java.lang.Long;
import java.lang.String;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.Actuacion;
import ve.co.bsc.sigai.domain.Prueba;

privileged aspect PruebaController_Roo_Controller {
    
    @RequestMapping(value = "/prueba", method = RequestMethod.GET)
    public String PruebaController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("pruebas", Prueba.findPruebaEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) Prueba.countPruebas() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("pruebas", Prueba.findAllPruebas());
        }
        return "prueba/list";
    }
    
    @RequestMapping(value = "/prueba/{id}", method = RequestMethod.DELETE)
    public String PruebaController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        Prueba.findPrueba(id).remove();
        return "redirect:/prueba?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
    @RequestMapping(value = "find/ByCodigoActuacion/form", method = RequestMethod.GET)
    public String PruebaController.findPruebasByCodigoActuacionForm(ModelMap modelMap) {
        modelMap.addAttribute("actuacions", Actuacion.findAllActuacions());
        return "prueba/findPruebasByCodigoActuacion";
    }
    
    @RequestMapping(value = "find/ByCodigoActuacion", method = RequestMethod.GET)
    public String PruebaController.findPruebasByCodigoActuacion(@RequestParam("codigoActuacion") Actuacion codigoActuacion, ModelMap modelMap) {
        if (codigoActuacion == null) throw new IllegalArgumentException("A CodigoActuacion is required.");
        modelMap.addAttribute("pruebas", Prueba.findPruebasByCodigoActuacion(codigoActuacion).getResultList());
        return "prueba/list";
    }
    
}
