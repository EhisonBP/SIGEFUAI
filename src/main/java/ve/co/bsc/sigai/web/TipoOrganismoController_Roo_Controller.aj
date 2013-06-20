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
import ve.co.bsc.sigai.domain.TipoOrganismo;

privileged aspect TipoOrganismoController_Roo_Controller {
    
    @RequestMapping(value = "/tipoorganismo", method = RequestMethod.POST)
    public String TipoOrganismoController.create(@Valid TipoOrganismo tipoOrganismo, BindingResult result, ModelMap modelMap) {
        if (tipoOrganismo == null) throw new IllegalArgumentException("A tipoOrganismo is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("tipoOrganismo", tipoOrganismo);
            return "tipoorganismo/create";
        }
        tipoOrganismo.persist();
        return "redirect:/tipoorganismo/" + tipoOrganismo.getId();
    }
    
    @RequestMapping(value = "/tipoorganismo/form", method = RequestMethod.GET)
    public String TipoOrganismoController.createForm(ModelMap modelMap) {
        modelMap.addAttribute("tipoOrganismo", new TipoOrganismo());
        return "tipoorganismo/create";
    }
    
    @RequestMapping(value = "/tipoorganismo/{id}", method = RequestMethod.GET)
    public String TipoOrganismoController.show(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("tipoOrganismo", TipoOrganismo.findTipoOrganismo(id));
        return "tipoorganismo/show";
    }
    
    @RequestMapping(value = "/tipoorganismo", method = RequestMethod.GET)
    public String TipoOrganismoController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("tipoorganismoes", TipoOrganismo.findTipoOrganismoEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) TipoOrganismo.countTipoOrganismoes() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("tipoorganismoes", TipoOrganismo.findAllTipoOrganismoes());
        }
        return "tipoorganismo/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public String TipoOrganismoController.update(@Valid TipoOrganismo tipoOrganismo, BindingResult result, ModelMap modelMap) {
        if (tipoOrganismo == null) throw new IllegalArgumentException("A tipoOrganismo is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("tipoOrganismo", tipoOrganismo);
            return "tipoorganismo/update";
        }
        tipoOrganismo.merge();
        return "redirect:/tipoorganismo/" + tipoOrganismo.getId();
    }
    
    @RequestMapping(value = "/tipoorganismo/{id}/form", method = RequestMethod.GET)
    public String TipoOrganismoController.updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("tipoOrganismo", TipoOrganismo.findTipoOrganismo(id));
        return "tipoorganismo/update";
    }
    
    @RequestMapping(value = "/tipoorganismo/{id}", method = RequestMethod.DELETE)
    public String TipoOrganismoController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        TipoOrganismo.findTipoOrganismo(id).remove();
        return "redirect:/tipoorganismo?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
}
