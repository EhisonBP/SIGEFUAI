package ve.co.bsc.sigai.web;

import java.lang.Long;
import java.lang.String;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.Personalizacion;

privileged aspect PersonalizacionController_Roo_Controller {
    
    @RequestMapping(value = "/personalizacion", method = RequestMethod.GET)
    public String PersonalizacionController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("personalizacions", Personalizacion.findPersonalizacionEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) Personalizacion.countPersonalizacions() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("personalizacions", Personalizacion.findAllPersonalizacions());
        }
        return "personalizacion/list";
    }
    
    @RequestMapping(value = "/personalizacion/{id}", method = RequestMethod.DELETE)
    public String PersonalizacionController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        Personalizacion.findPersonalizacion(id).remove();
        return "redirect:/personalizacion?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
    @RequestMapping(value = "find/ByRifEquals/form", method = RequestMethod.GET)
    public String PersonalizacionController.findPersonalizacionsByRifEqualsForm(ModelMap modelMap) {
        return "personalizacion/findPersonalizacionsByRifEquals";
    }
    
    @RequestMapping(value = "find/ByRifEquals", method = RequestMethod.GET)
    public String PersonalizacionController.findPersonalizacionsByRifEquals(@RequestParam("rif") String Rif, ModelMap modelMap) {
        if (Rif == null || Rif.length() == 0) throw new IllegalArgumentException("A Rif is required.");
        modelMap.addAttribute("personalizacions", Personalizacion.findPersonalizacionsByRifEquals(Rif).getResultList());
        return "personalizacion/list";
    }
    
}
