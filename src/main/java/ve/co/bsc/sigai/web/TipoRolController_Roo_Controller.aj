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
import ve.co.bsc.sigai.domain.TipoRol;

privileged aspect TipoRolController_Roo_Controller {
    
    @RequestMapping(value = "/tiporol", method = RequestMethod.POST)
    public String TipoRolController.create(@Valid TipoRol tipoRol, BindingResult result, ModelMap modelMap) {
        if (tipoRol == null) throw new IllegalArgumentException("A tipoRol is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("tipoRol", tipoRol);
            return "tiporol/create";
        }
        tipoRol.persist();
        return "redirect:/tiporol/" + tipoRol.getId();
    }
    
    @RequestMapping(value = "/tiporol/form", method = RequestMethod.GET)
    public String TipoRolController.createForm(ModelMap modelMap) {
        modelMap.addAttribute("tipoRol", new TipoRol());
        return "tiporol/create";
    }
    
    @RequestMapping(value = "/tiporol/{id}", method = RequestMethod.GET)
    public String TipoRolController.show(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("tipoRol", TipoRol.findTipoRol(id));
        return "tiporol/show";
    }
    
    @RequestMapping(value = "/tiporol", method = RequestMethod.GET)
    public String TipoRolController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("tiporols", TipoRol.findTipoRolEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) TipoRol.countTipoRols() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("tiporols", TipoRol.findAllTipoRols());
        }
        return "tiporol/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public String TipoRolController.update(@Valid TipoRol tipoRol, BindingResult result, ModelMap modelMap) {
        if (tipoRol == null) throw new IllegalArgumentException("A tipoRol is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("tipoRol", tipoRol);
            return "tiporol/update";
        }
        tipoRol.merge();
        return "redirect:/tiporol/" + tipoRol.getId();
    }
    
    @RequestMapping(value = "/tiporol/{id}/form", method = RequestMethod.GET)
    public String TipoRolController.updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("tipoRol", TipoRol.findTipoRol(id));
        return "tiporol/update";
    }
    
    @RequestMapping(value = "/tiporol/{id}", method = RequestMethod.DELETE)
    public String TipoRolController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        TipoRol.findTipoRol(id).remove();
        return "redirect:/tiporol?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
}
