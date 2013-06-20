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
import ve.co.bsc.sigai.domain.Ciudad;
import ve.co.bsc.sigai.domain.EstadoAuditor;
import ve.co.bsc.sigai.domain.Municipios;

privileged aspect MunicipiosController_Roo_Controller {
    
    @RequestMapping(value = "/municipios", method = RequestMethod.POST)
    public String MunicipiosController.create(@Valid Municipios municipios, BindingResult result, ModelMap modelMap) {
        if (municipios == null) throw new IllegalArgumentException("A municipios is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("municipios", municipios);
            modelMap.addAttribute("ciudads", Ciudad.findAllCiudads());
            modelMap.addAttribute("estadoauditors", EstadoAuditor.findAllEstadoAuditors());
            return "municipios/create";
        }
        municipios.persist();
        return "redirect:/municipios/" + municipios.getId();
    }
    
    @RequestMapping(value = "/municipios/form", method = RequestMethod.GET)
    public String MunicipiosController.createForm(ModelMap modelMap) {
        modelMap.addAttribute("municipios", new Municipios());
        modelMap.addAttribute("ciudads", Ciudad.findAllCiudads());
        modelMap.addAttribute("estadoauditors", EstadoAuditor.findAllEstadoAuditors());
        return "municipios/create";
    }
    
    @RequestMapping(value = "/municipios/{id}", method = RequestMethod.GET)
    public String MunicipiosController.show(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("municipios", Municipios.findMunicipios(id));
        return "municipios/show";
    }
    
    @RequestMapping(value = "/municipios", method = RequestMethod.GET)
    public String MunicipiosController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("municipioses", Municipios.findMunicipiosEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) Municipios.countMunicipioses() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("municipioses", Municipios.findAllMunicipioses());
        }
        return "municipios/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public String MunicipiosController.update(@Valid Municipios municipios, BindingResult result, ModelMap modelMap) {
        if (municipios == null) throw new IllegalArgumentException("A municipios is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("municipios", municipios);
            modelMap.addAttribute("ciudads", Ciudad.findAllCiudads());
            modelMap.addAttribute("estadoauditors", EstadoAuditor.findAllEstadoAuditors());
            return "municipios/update";
        }
        municipios.merge();
        return "redirect:/municipios/" + municipios.getId();
    }
    
    @RequestMapping(value = "/municipios/{id}/form", method = RequestMethod.GET)
    public String MunicipiosController.updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("municipios", Municipios.findMunicipios(id));
        modelMap.addAttribute("ciudads", Ciudad.findAllCiudads());
        modelMap.addAttribute("estadoauditors", EstadoAuditor.findAllEstadoAuditors());
        return "municipios/update";
    }
    
    @RequestMapping(value = "/municipios/{id}", method = RequestMethod.DELETE)
    public String MunicipiosController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        Municipios.findMunicipios(id).remove();
        return "redirect:/municipios?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
}
