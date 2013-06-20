package ve.co.bsc.sigai.web;

import java.lang.Long;
import java.lang.String;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.UnidadDeMedida;

privileged aspect UnidadDeMedidaController_Roo_Controller {
    
    @RequestMapping(value = "/unidaddemedida/form", method = RequestMethod.GET)
    public String UnidadDeMedidaController.createForm(ModelMap modelMap) {
        modelMap.addAttribute("unidadDeMedida", new UnidadDeMedida());
        return "unidaddemedida/create";
    }
    
    @RequestMapping(value = "/unidaddemedida/{id}", method = RequestMethod.GET)
    public String UnidadDeMedidaController.show(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("unidadDeMedida", UnidadDeMedida.findUnidadDeMedida(id));
        return "unidaddemedida/show";
    }
    
    @RequestMapping(value = "/unidaddemedida", method = RequestMethod.GET)
    public String UnidadDeMedidaController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("unidaddemedidas", UnidadDeMedida.findUnidadDeMedidaEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) UnidadDeMedida.countUnidadDeMedidas() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("unidaddemedidas", UnidadDeMedida.findAllUnidadDeMedidas());
        }
        return "unidaddemedida/list";
    }
    
    @RequestMapping(value = "/unidaddemedida/{id}/form", method = RequestMethod.GET)
    public String UnidadDeMedidaController.updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("unidadDeMedida", UnidadDeMedida.findUnidadDeMedida(id));
        return "unidaddemedida/update";
    }
    
    @RequestMapping(value = "/unidaddemedida/{id}", method = RequestMethod.DELETE)
    public String UnidadDeMedidaController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        UnidadDeMedida.findUnidadDeMedida(id).remove();
        return "redirect:/unidaddemedida?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
}
