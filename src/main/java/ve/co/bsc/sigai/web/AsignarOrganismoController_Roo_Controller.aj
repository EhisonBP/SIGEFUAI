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
import ve.co.bsc.sigai.domain.AsignarOrganismo;
import ve.co.bsc.sigai.domain.Auditor;
import ve.co.bsc.sigai.domain.OrganismoEnte;

privileged aspect AsignarOrganismoController_Roo_Controller {
    
    @RequestMapping(value = "/asignarorganismo/{id}", method = RequestMethod.GET)
    public String AsignarOrganismoController.show(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("asignarOrganismo", AsignarOrganismo.findAsignarOrganismo(id));
        return "asignarorganismo/show";
    }
    
    @RequestMapping(value = "/asignarorganismo", method = RequestMethod.GET)
    public String AsignarOrganismoController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("asignarorganismoes", AsignarOrganismo.findAsignarOrganismoEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) AsignarOrganismo.countAsignarOrganismoes() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("asignarorganismoes", AsignarOrganismo.findAllAsignarOrganismoes());
        }
        return "asignarorganismo/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public String AsignarOrganismoController.update(@Valid AsignarOrganismo asignarOrganismo, BindingResult result, ModelMap modelMap) {
        if (asignarOrganismo == null) throw new IllegalArgumentException("A asignarOrganismo is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("asignarOrganismo", asignarOrganismo);
            modelMap.addAttribute("auditors", Auditor.findAllAuditors());
            modelMap.addAttribute("organismoentes", OrganismoEnte.findAllOrganismoEntes());
            return "asignarorganismo/update";
        }
        asignarOrganismo.merge();
        return "redirect:/asignarorganismo/" + asignarOrganismo.getId();
    }
    
    @RequestMapping(value = "/asignarorganismo/{id}/form", method = RequestMethod.GET)
    public String AsignarOrganismoController.updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("asignarOrganismo", AsignarOrganismo.findAsignarOrganismo(id));
        modelMap.addAttribute("auditors", Auditor.findAllAuditors());
        modelMap.addAttribute("organismoentes", OrganismoEnte.findAllOrganismoEntes());
        return "asignarorganismo/update";
    }
    
    @RequestMapping(value = "/asignarorganismo/{id}", method = RequestMethod.DELETE)
    public String AsignarOrganismoController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        AsignarOrganismo.findAsignarOrganismo(id).remove();
        return "redirect:/asignarorganismo?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
}
