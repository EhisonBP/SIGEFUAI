package ve.co.bsc.sigai.web;

import java.lang.Boolean;
import java.lang.String;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.Actuacion;
import ve.co.bsc.sigai.domain.Cuestionario;

privileged aspect CuestionarioController_Roo_Controller {
    
    @RequestMapping(value = "/cuestionario", method = RequestMethod.GET)
    public String CuestionarioController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("cuestionarios", Cuestionario.findCuestionarioEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) Cuestionario.countCuestionarios() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("cuestionarios", Cuestionario.findAllCuestionarios());
        }
        return "cuestionario/list";
    }
    
    @RequestMapping(value = "find/ByActuacion/form", method = RequestMethod.GET)
    public String CuestionarioController.findCuestionariosByActuacionForm(ModelMap modelMap) {
        modelMap.addAttribute("actuacions", Actuacion.findAllActuacions());
        return "cuestionario/findCuestionariosByActuacion";
    }
    
    @RequestMapping(value = "find/ByActuacion", method = RequestMethod.GET)
    public String CuestionarioController.findCuestionariosByActuacion(@RequestParam("actuacion") Actuacion actuacion, ModelMap modelMap) {
        if (actuacion == null) throw new IllegalArgumentException("A Actuacion is required.");
        modelMap.addAttribute("cuestionarios", Cuestionario.findCuestionariosByActuacion(actuacion).getResultList());
        return "cuestionario/list";
    }
    
    @RequestMapping(value = "find/ByRespondido/form", method = RequestMethod.GET)
    public String CuestionarioController.findCuestionariosByRespondidoForm(ModelMap modelMap) {
        return "cuestionario/findCuestionariosByRespondido";
    }
    
    @RequestMapping(value = "find/ByRespondido", method = RequestMethod.GET)
    public String CuestionarioController.findCuestionariosByRespondido(@RequestParam("respondido") Boolean respondido, ModelMap modelMap) {
        if (respondido == null) throw new IllegalArgumentException("A Respondido is required.");
        modelMap.addAttribute("cuestionarios", Cuestionario.findCuestionariosByRespondido(respondido).getResultList());
        return "cuestionario/list";
    }
    
}
