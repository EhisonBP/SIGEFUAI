package ve.co.bsc.sigai.web;

import java.lang.String;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.Actuacion;
import ve.co.bsc.sigai.domain.ObjetivoEspecifico;

privileged aspect ObjetivoEspecificoController_Roo_Controller {
    
    @RequestMapping(value = "/objetivoespecifico", method = RequestMethod.GET)
    public String ObjetivoEspecificoController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("objetivoespecificoes", ObjetivoEspecifico.findObjetivoEspecificoEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) ObjetivoEspecifico.countObjetivoEspecificoes() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("objetivoespecificoes", ObjetivoEspecifico.findAllObjetivoEspecificoes());
        }
        return "objetivoespecifico/list";
    }
    
    @RequestMapping(value = "find/ByActuacion/form", method = RequestMethod.GET)
    public String ObjetivoEspecificoController.findObjetivoEspecificoesByActuacionForm(ModelMap modelMap) {
        modelMap.addAttribute("actuacions", Actuacion.findAllActuacions());
        return "objetivoespecifico/findObjetivoEspecificoesByActuacion";
    }
    
    @RequestMapping(value = "find/ByActuacion", method = RequestMethod.GET)
    public String ObjetivoEspecificoController.findObjetivoEspecificoesByActuacion(@RequestParam("actuacion") Actuacion actuacion, ModelMap modelMap) {
        if (actuacion == null) throw new IllegalArgumentException("A Actuacion is required.");
        modelMap.addAttribute("objetivoespecificoes", ObjetivoEspecifico.findObjetivoEspecificoesByActuacion(actuacion).getResultList());
        return "objetivoespecifico/list";
    }
    
}
