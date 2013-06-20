package ve.co.bsc.sigai.web;

import java.lang.Long;
import java.lang.String;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.TipoUnidad;

privileged aspect TipoUnidadController_Roo_Controller {
    
    @RequestMapping(value = "/tipounidad/form", method = RequestMethod.GET)
    public String TipoUnidadController.createForm(ModelMap modelMap) {
        modelMap.addAttribute("tipoUnidad", new TipoUnidad());
        return "tipounidad/create";
    }
    
    @RequestMapping(value = "/tipounidad/{id}", method = RequestMethod.GET)
    public String TipoUnidadController.show(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("tipoUnidad", TipoUnidad.findTipoUnidad(id));
        return "tipounidad/show";
    }
    
    @RequestMapping(value = "/tipounidad", method = RequestMethod.GET)
    public String TipoUnidadController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("tipounidads", TipoUnidad.findTipoUnidadEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) TipoUnidad.countTipoUnidads() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("tipounidads", TipoUnidad.findAllTipoUnidads());
        }
        return "tipounidad/list";
    }
    
    @RequestMapping(value = "/tipounidad/{id}/form", method = RequestMethod.GET)
    public String TipoUnidadController.updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("tipoUnidad", TipoUnidad.findTipoUnidad(id));
        return "tipounidad/update";
    }
    
    @RequestMapping(value = "/tipounidad/{id}", method = RequestMethod.DELETE)
    public String TipoUnidadController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        TipoUnidad.findTipoUnidad(id).remove();
        return "redirect:/tipounidad?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
}
