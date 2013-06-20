package ve.co.bsc.sigai.web;

import java.lang.Long;
import java.lang.String;
import java.util.Set;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.Prueba;
import ve.co.bsc.sigai.domain.Riesgo;
import ve.co.bsc.sigai.domain.TecnicaDeControl;

privileged aspect TecnicaDeControlController_Roo_Controller {
    
    @RequestMapping(value = "/tecnicadecontrol", method = RequestMethod.GET)
    public String TecnicaDeControlController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("tecnicadecontrols", TecnicaDeControl.findTecnicaDeControlEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) TecnicaDeControl.countTecnicaDeControls() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("tecnicadecontrols", TecnicaDeControl.findAllTecnicaDeControls());
        }
        return "tecnicadecontrol/list";
    }
    
    @RequestMapping(value = "/tecnicadecontrol/{id}", method = RequestMethod.DELETE)
    public String TecnicaDeControlController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        TecnicaDeControl.findTecnicaDeControl(id).remove();
        return "redirect:/tecnicadecontrol?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
    @RequestMapping(value = "find/ByRiesgo/form", method = RequestMethod.GET)
    public String TecnicaDeControlController.findTecnicaDeControlsByRiesgoForm(ModelMap modelMap) {
        modelMap.addAttribute("riesgoes", Riesgo.findAllRiesgoes());
        return "tecnicadecontrol/findTecnicaDeControlsByRiesgo";
    }
    
    @RequestMapping(value = "find/ByRiesgo", method = RequestMethod.GET)
    public String TecnicaDeControlController.findTecnicaDeControlsByRiesgo(@RequestParam("riesgo") Riesgo riesgo, ModelMap modelMap) {
        if (riesgo == null) throw new IllegalArgumentException("A Riesgo is required.");
        modelMap.addAttribute("tecnicadecontrols", TecnicaDeControl.findTecnicaDeControlsByRiesgo(riesgo).getResultList());
        return "tecnicadecontrol/list";
    }
    
    @RequestMapping(value = "find/ByPrueba/form", method = RequestMethod.GET)
    public String TecnicaDeControlController.findTecnicaDeControlsByPruebaForm(ModelMap modelMap) {
        modelMap.addAttribute("pruebas", Prueba.findAllPruebas());
        return "tecnicadecontrol/findTecnicaDeControlsByPrueba";
    }
    
    @RequestMapping(value = "find/ByPrueba", method = RequestMethod.GET)
    public String TecnicaDeControlController.findTecnicaDeControlsByPrueba(@RequestParam("prueba") Set<Prueba> prueba, ModelMap modelMap) {
        if (prueba == null) throw new IllegalArgumentException("A Prueba is required.");
        modelMap.addAttribute("tecnicadecontrols", TecnicaDeControl.findTecnicaDeControlsByPrueba(prueba).getResultList());
        return "tecnicadecontrol/list";
    }
    
}
