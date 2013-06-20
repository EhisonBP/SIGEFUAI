package ve.co.bsc.sigai.web;

import java.lang.String;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.Observacion;
import ve.co.bsc.sigai.domain.Recomendacion;

privileged aspect RecomendacionController_Roo_Controller {
    
    @RequestMapping(value = "/recomendacion", method = RequestMethod.GET)
    public String RecomendacionController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("recomendacions", Recomendacion.findRecomendacionEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) Recomendacion.countRecomendacions() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("recomendacions", Recomendacion.findAllRecomendacions());
        }
        return "recomendacion/list";
    }
    
    @RequestMapping(value = "find/ByObservacion/form", method = RequestMethod.GET)
    public String RecomendacionController.findRecomendacionsByObservacionForm(ModelMap modelMap) {
        modelMap.addAttribute("observacions", Observacion.findAllObservacions());
        return "recomendacion/findRecomendacionsByObservacion";
    }
    
    @RequestMapping(value = "find/ByObservacion", method = RequestMethod.GET)
    public String RecomendacionController.findRecomendacionsByObservacion(@RequestParam("observacion") Observacion observacion, ModelMap modelMap) {
        if (observacion == null) throw new IllegalArgumentException("A Observacion is required.");
        modelMap.addAttribute("recomendacions", Recomendacion.findRecomendacionsByObservacion(observacion).getResultList());
        return "recomendacion/list";
    }
    
}
