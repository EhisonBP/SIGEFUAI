package ve.co.bsc.sigai.web;

import java.lang.Long;
import java.lang.String;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.Cuestionario;
import ve.co.bsc.sigai.domain.ItemCuestionario;

privileged aspect ItemCuestionarioController_Roo_Controller {
    
    @RequestMapping(value = "/itemcuestionario/{id}", method = RequestMethod.GET)
    public String ItemCuestionarioController.show(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("itemCuestionario", ItemCuestionario.findItemCuestionario(id));
        return "itemcuestionario/show";
    }
    
    @RequestMapping(value = "/itemcuestionario", method = RequestMethod.GET)
    public String ItemCuestionarioController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("itemcuestionarios", ItemCuestionario.findItemCuestionarioEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) ItemCuestionario.countItemCuestionarios() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("itemcuestionarios", ItemCuestionario.findAllItemCuestionarios());
        }
        return "itemcuestionario/list";
    }
    
    @RequestMapping(value = "find/ByCuestionario/form", method = RequestMethod.GET)
    public String ItemCuestionarioController.findItemCuestionariosByCuestionarioForm(ModelMap modelMap) {
        modelMap.addAttribute("cuestionarios", Cuestionario.findAllCuestionarios());
        return "itemcuestionario/findItemCuestionariosByCuestionario";
    }
    
    @RequestMapping(value = "find/ByCuestionario", method = RequestMethod.GET)
    public String ItemCuestionarioController.findItemCuestionariosByCuestionario(@RequestParam("cuestionario") Cuestionario cuestionario, ModelMap modelMap) {
        if (cuestionario == null) throw new IllegalArgumentException("A Cuestionario is required.");
        modelMap.addAttribute("itemcuestionarios", ItemCuestionario.findItemCuestionariosByCuestionario(cuestionario).getResultList());
        return "itemcuestionario/list";
    }
    
}
