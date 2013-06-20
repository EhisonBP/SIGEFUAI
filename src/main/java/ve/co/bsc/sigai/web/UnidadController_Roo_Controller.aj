package ve.co.bsc.sigai.web;

import java.lang.Long;
import java.lang.String;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.OrganismoEnte;
import ve.co.bsc.sigai.domain.Unidad;

privileged aspect UnidadController_Roo_Controller {
    
    @RequestMapping(value = "/unidad", method = RequestMethod.GET)
    public String UnidadController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("unidads", Unidad.findUnidadEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) Unidad.countUnidads() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("unidads", Unidad.findAllUnidads());
        }
        return "unidad/list";
    }
    
    @RequestMapping(value = "/unidad/{id}", method = RequestMethod.DELETE)
    public String UnidadController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        Unidad.findUnidad(id).remove();
        return "redirect:/unidad?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
    @RequestMapping(value = "find/ByNombreEquals/form", method = RequestMethod.GET)
    public String UnidadController.findUnidadsByNombreEqualsForm(ModelMap modelMap) {
        return "unidad/findUnidadsByNombreEquals";
    }
    
    @RequestMapping(value = "find/ByNombreEquals", method = RequestMethod.GET)
    public String UnidadController.findUnidadsByNombreEquals(@RequestParam("nombre") String nombre, ModelMap modelMap) {
        if (nombre == null || nombre.length() == 0) throw new IllegalArgumentException("A Nombre is required.");
        modelMap.addAttribute("unidads", Unidad.findUnidadsByNombreEquals(nombre).getResultList());
        return "unidad/list";
    }
    
    @RequestMapping(value = "find/ByRif/form", method = RequestMethod.GET)
    public String UnidadController.findUnidadsByRifForm(ModelMap modelMap) {
        modelMap.addAttribute("organismoentes", OrganismoEnte.findAllOrganismoEntes());
        return "unidad/findUnidadsByRif";
    }
    
    @RequestMapping(value = "find/ByRif", method = RequestMethod.GET)
    public String UnidadController.findUnidadsByRif(@RequestParam("rif") OrganismoEnte rif, ModelMap modelMap) {
        if (rif == null) throw new IllegalArgumentException("A Rif is required.");
        modelMap.addAttribute("unidads", Unidad.findUnidadsByRif(rif).getResultList());
        return "unidad/list";
    }
    
}
