package ve.co.bsc.sigai.web;

import java.lang.Long;
import java.lang.String;
import javax.validation.Valid;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.ClasificacionRiesgo;

privileged aspect ClasificacionRiesgoController_Roo_Controller {
    
    @RequestMapping(value = "/clasificacionriesgo", method = RequestMethod.POST)
    public String ClasificacionRiesgoController.create(@Valid ClasificacionRiesgo clasificacionRiesgo, BindingResult result, ModelMap modelMap) {
        if (clasificacionRiesgo == null) throw new IllegalArgumentException("A clasificacionRiesgo is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("clasificacionRiesgo", clasificacionRiesgo);
            return "clasificacionriesgo/create";
        }
        clasificacionRiesgo.persist();
        return "redirect:/clasificacionriesgo/" + clasificacionRiesgo.getId();
    }
    
    @RequestMapping(value = "/clasificacionriesgo/form", method = RequestMethod.GET)
    public String ClasificacionRiesgoController.createForm(ModelMap modelMap) {
        modelMap.addAttribute("clasificacionRiesgo", new ClasificacionRiesgo());
        return "clasificacionriesgo/create";
    }
    
    @RequestMapping(value = "/clasificacionriesgo/{id}", method = RequestMethod.GET)
    public String ClasificacionRiesgoController.show(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("clasificacionRiesgo", ClasificacionRiesgo.findClasificacionRiesgo(id));
        return "clasificacionriesgo/show";
    }
    
    @RequestMapping(value = "/clasificacionriesgo", method = RequestMethod.GET)
    public String ClasificacionRiesgoController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("clasificacionriesgoes", ClasificacionRiesgo.findClasificacionRiesgoEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) ClasificacionRiesgo.countClasificacionRiesgoes() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("clasificacionriesgoes", ClasificacionRiesgo.findAllClasificacionRiesgoes());
        }
        return "clasificacionriesgo/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public String ClasificacionRiesgoController.update(@Valid ClasificacionRiesgo clasificacionRiesgo, BindingResult result, ModelMap modelMap) {
        if (clasificacionRiesgo == null) throw new IllegalArgumentException("A clasificacionRiesgo is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("clasificacionRiesgo", clasificacionRiesgo);
            return "clasificacionriesgo/update";
        }
        clasificacionRiesgo.merge();
        return "redirect:/clasificacionriesgo/" + clasificacionRiesgo.getId();
    }
    
    @RequestMapping(value = "/clasificacionriesgo/{id}/form", method = RequestMethod.GET)
    public String ClasificacionRiesgoController.updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("clasificacionRiesgo", ClasificacionRiesgo.findClasificacionRiesgo(id));
        return "clasificacionriesgo/update";
    }
    
    @RequestMapping(value = "/clasificacionriesgo/{id}", method = RequestMethod.DELETE)
    public String ClasificacionRiesgoController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        ClasificacionRiesgo.findClasificacionRiesgo(id).remove();
        return "redirect:/clasificacionriesgo?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
}
