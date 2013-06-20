package ve.co.bsc.sigai.web;

import java.lang.String;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.Alegato;
import ve.co.bsc.sigai.domain.Observacion;

privileged aspect AlegatoController_Roo_Controller {
    
    @RequestMapping(value = "/alegato", method = RequestMethod.GET)
    public String AlegatoController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("alegatoes", Alegato.findAlegatoEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) Alegato.countAlegatoes() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("alegatoes", Alegato.findAllAlegatoes());
        }
        return "alegato/list";
    }
    
    @RequestMapping(value = "find/ByObservacion/form", method = RequestMethod.GET)
    public String AlegatoController.findAlegatoesByObservacionForm(ModelMap modelMap) {
        modelMap.addAttribute("observacions", Observacion.findAllObservacions());
        return "alegato/findAlegatoesByObservacion";
    }
    
    @RequestMapping(value = "find/ByObservacion", method = RequestMethod.GET)
    public String AlegatoController.findAlegatoesByObservacion(@RequestParam("observacion") Observacion observacion, ModelMap modelMap) {
        if (observacion == null) throw new IllegalArgumentException("A Observacion is required.");
        modelMap.addAttribute("alegatoes", Alegato.findAlegatoesByObservacion(observacion).getResultList());
        return "alegato/list";
    }
    
}
