package ve.co.bsc.sigai.web;

import java.lang.Long;
import java.lang.String;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.Proceso;
import ve.co.bsc.sigai.domain.Riesgo;

privileged aspect RiesgoController_Roo_Controller {
    
    @RequestMapping(value = "/riesgo", method = RequestMethod.GET)
    public String RiesgoController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("riesgoes", Riesgo.findRiesgoEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) Riesgo.countRiesgoes() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("riesgoes", Riesgo.findAllRiesgoes());
        }
        return "riesgo/list";
    }
    
    @RequestMapping(value = "/riesgo/{id}", method = RequestMethod.DELETE)
    public String RiesgoController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        Riesgo.findRiesgo(id).remove();
        return "redirect:/riesgo?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
    @RequestMapping(value = "find/ByProceso/form", method = RequestMethod.GET)
    public String RiesgoController.findRiesgoesByProcesoForm(ModelMap modelMap) {
        modelMap.addAttribute("procesoes", Proceso.findAllProcesoes());
        return "riesgo/findRiesgoesByProceso";
    }
    
    @RequestMapping(value = "find/ByProceso", method = RequestMethod.GET)
    public String RiesgoController.findRiesgoesByProceso(@RequestParam("proceso") Proceso proceso, ModelMap modelMap) {
        if (proceso == null) throw new IllegalArgumentException("A Proceso is required.");
        modelMap.addAttribute("riesgoes", Riesgo.findRiesgoesByProceso(proceso).getResultList());
        return "riesgo/list";
    }
    
}
