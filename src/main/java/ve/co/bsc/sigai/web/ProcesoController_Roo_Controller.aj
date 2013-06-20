package ve.co.bsc.sigai.web;

import java.lang.String;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.Proceso;

privileged aspect ProcesoController_Roo_Controller {
    
    @RequestMapping(value = "find/ByProceso/form", method = RequestMethod.GET)
    public String ProcesoController.findProcesoesByProcesoForm(ModelMap modelMap) {
        modelMap.addAttribute("procesoes", Proceso.findAllProcesoes());
        return "proceso/findProcesoesByProceso";
    }
    
    @RequestMapping(value = "find/ByProceso", method = RequestMethod.GET)
    public String ProcesoController.findProcesoesByProceso(@RequestParam("proceso") Proceso proceso, ModelMap modelMap) {
        if (proceso == null) throw new IllegalArgumentException("A Proceso is required.");
        modelMap.addAttribute("procesoes", Proceso.findProcesoesByProceso(proceso).getResultList());
        return "proceso/list";
    }
    
}
