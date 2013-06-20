package ve.co.bsc.sigai.web;

import java.lang.Long;
import java.lang.String;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.EstatusRequerimiento;

privileged aspect EstatusRequerimientoController_Roo_Controller {
    
    @RequestMapping(value = "/estatusrequerimiento/form", method = RequestMethod.GET)
    public String EstatusRequerimientoController.createForm(ModelMap modelMap) {
        modelMap.addAttribute("estatusRequerimiento", new EstatusRequerimiento());
        return "estatusrequerimiento/create";
    }
    
    @RequestMapping(value = "/estatusrequerimiento/{id}", method = RequestMethod.GET)
    public String EstatusRequerimientoController.show(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("estatusRequerimiento", EstatusRequerimiento.findEstatusRequerimiento(id));
        return "estatusrequerimiento/show";
    }
    
    @RequestMapping(value = "/estatusrequerimiento", method = RequestMethod.GET)
    public String EstatusRequerimientoController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("estatusrequerimientoes", EstatusRequerimiento.findEstatusRequerimientoEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) EstatusRequerimiento.countEstatusRequerimientoes() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("estatusrequerimientoes", EstatusRequerimiento.findAllEstatusRequerimientoes());
        }
        return "estatusrequerimiento/list";
    }
    
    @RequestMapping(value = "/estatusrequerimiento/{id}/form", method = RequestMethod.GET)
    public String EstatusRequerimientoController.updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("estatusRequerimiento", EstatusRequerimiento.findEstatusRequerimiento(id));
        return "estatusrequerimiento/update";
    }
    
    @RequestMapping(value = "/estatusrequerimiento/{id}", method = RequestMethod.DELETE)
    public String EstatusRequerimientoController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        EstatusRequerimiento.findEstatusRequerimiento(id).remove();
        return "redirect:/estatusrequerimiento?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
}
