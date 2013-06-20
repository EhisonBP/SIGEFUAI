package ve.co.bsc.sigai.web;

import java.lang.Long;
import java.lang.String;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.AutoridadMaxima;
import ve.co.bsc.sigai.domain.OrganismoEnte;

privileged aspect AutoridadMaximaController_Roo_Controller {
    
    @RequestMapping(value = "/autoridadmaxima/{id}", method = RequestMethod.GET)
    public String AutoridadMaximaController.show(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("autoridadMaxima", AutoridadMaxima.findAutoridadMaxima(id));
        return "autoridadmaxima/show";
    }
    
    @RequestMapping(value = "/autoridadmaxima/{id}", method = RequestMethod.DELETE)
    public String AutoridadMaximaController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        AutoridadMaxima.findAutoridadMaxima(id).remove();
        return "redirect:/autoridadmaxima?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
    @RequestMapping(value = "find/ByRif/form", method = RequestMethod.GET)
    public String AutoridadMaximaController.findAutoridadMaximasByRifForm(ModelMap modelMap) {
        modelMap.addAttribute("organismoentes", OrganismoEnte.findAllOrganismoEntes());
        return "autoridadmaxima/findAutoridadMaximasByRif";
    }
    
    @RequestMapping(value = "find/ByRif", method = RequestMethod.GET)
    public String AutoridadMaximaController.findAutoridadMaximasByRif(@RequestParam("rif") OrganismoEnte rif, ModelMap modelMap) {
        if (rif == null) throw new IllegalArgumentException("A Rif is required.");
        modelMap.addAttribute("autoridadmaximas", AutoridadMaxima.findAutoridadMaximasByRif(rif).getResultList());
        return "autoridadmaxima/list";
    }
    
}
