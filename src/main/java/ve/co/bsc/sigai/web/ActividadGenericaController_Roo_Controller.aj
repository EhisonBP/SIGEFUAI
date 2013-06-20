package ve.co.bsc.sigai.web;

import java.lang.Long;
import java.lang.String;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.ActividadGenerica;

privileged aspect ActividadGenericaController_Roo_Controller {
    
    @RequestMapping(value = "/actividadgenerica/form", method = RequestMethod.GET)
    public String ActividadGenericaController.createForm(ModelMap modelMap) {
        modelMap.addAttribute("actividadGenerica", new ActividadGenerica());
        return "actividadgenerica/create";
    }
    
    @RequestMapping(value = "/actividadgenerica", method = RequestMethod.GET)
    public String ActividadGenericaController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("actividadgenericas", ActividadGenerica.findActividadGenericaEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) ActividadGenerica.countActividadGenericas() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("actividadgenericas", ActividadGenerica.findAllActividadGenericas());
        }
        return "actividadgenerica/list";
    }
    
    @RequestMapping(value = "/actividadgenerica/{id}/form", method = RequestMethod.GET)
    public String ActividadGenericaController.updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("actividadGenerica", ActividadGenerica.findActividadGenerica(id));
        return "actividadgenerica/update";
    }
    
    @RequestMapping(value = "/actividadgenerica/{id}", method = RequestMethod.DELETE)
    public String ActividadGenericaController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        ActividadGenerica.findActividadGenerica(id).remove();
        return "redirect:/actividadgenerica?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
}
