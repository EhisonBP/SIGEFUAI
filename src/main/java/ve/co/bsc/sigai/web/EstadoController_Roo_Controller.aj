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
import ve.co.bsc.sigai.domain.Estado;
import ve.co.bsc.sigai.domain.EstadoAuditor;

privileged aspect EstadoController_Roo_Controller {
    
    @RequestMapping(value = "/estado", method = RequestMethod.POST)
    public String EstadoController.create(@Valid Estado estado, BindingResult result, ModelMap modelMap) {
        if (estado == null) throw new IllegalArgumentException("A estado is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("estado", estado);
            modelMap.addAttribute("estadoauditors", EstadoAuditor.findAllEstadoAuditors());
            return "estado/create";
        }
        estado.persist();
        return "redirect:/estado/" + estado.getId();
    }
    
    @RequestMapping(value = "/estado/form", method = RequestMethod.GET)
    public String EstadoController.createForm(ModelMap modelMap) {
        modelMap.addAttribute("estado", new Estado());
        modelMap.addAttribute("estadoauditors", EstadoAuditor.findAllEstadoAuditors());
        return "estado/create";
    }
    
    @RequestMapping(value = "/estado/{id}", method = RequestMethod.GET)
    public String EstadoController.show(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("estado", Estado.findEstado(id));
        return "estado/show";
    }
    
    @RequestMapping(value = "/estado", method = RequestMethod.GET)
    public String EstadoController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("estadoes", Estado.findEstadoEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) Estado.countEstadoes() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("estadoes", Estado.findAllEstadoes());
        }
        return "estado/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public String EstadoController.update(@Valid Estado estado, BindingResult result, ModelMap modelMap) {
        if (estado == null) throw new IllegalArgumentException("A estado is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("estado", estado);
            modelMap.addAttribute("estadoauditors", EstadoAuditor.findAllEstadoAuditors());
            return "estado/update";
        }
        estado.merge();
        return "redirect:/estado/" + estado.getId();
    }
    
    @RequestMapping(value = "/estado/{id}/form", method = RequestMethod.GET)
    public String EstadoController.updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("estado", Estado.findEstado(id));
        modelMap.addAttribute("estadoauditors", EstadoAuditor.findAllEstadoAuditors());
        return "estado/update";
    }
    
    @RequestMapping(value = "/estado/{id}", method = RequestMethod.DELETE)
    public String EstadoController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        Estado.findEstado(id).remove();
        return "redirect:/estado?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
}
