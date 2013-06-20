package ve.co.bsc.sigai.web;

import java.lang.String;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.Observacion;
import ve.co.bsc.sigai.domain.Prueba;

privileged aspect ObservacionController_Roo_Controller {
    
    @RequestMapping(value = "/observacion", method = RequestMethod.GET)
    public String ObservacionController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("observacions", Observacion.findObservacionEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) Observacion.countObservacions() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("observacions", Observacion.findAllObservacions());
        }
        return "observacion/list";
    }
    
    @RequestMapping(value = "find/ByPrueba/form", method = RequestMethod.GET)
    public String ObservacionController.findObservacionsByPruebaForm(ModelMap modelMap) {
        modelMap.addAttribute("pruebas", Prueba.findAllPruebas());
        return "observacion/findObservacionsByPrueba";
    }
    
    @RequestMapping(value = "find/ByPrueba", method = RequestMethod.GET)
    public String ObservacionController.findObservacionsByPrueba(@RequestParam("prueba") Prueba prueba, ModelMap modelMap) {
        if (prueba == null) throw new IllegalArgumentException("A Prueba is required.");
        modelMap.addAttribute("observacions", Observacion.findObservacionsByPrueba(prueba).getResultList());
        return "observacion/list";
    }
    
}
