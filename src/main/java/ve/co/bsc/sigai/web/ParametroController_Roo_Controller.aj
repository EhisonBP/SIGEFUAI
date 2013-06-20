package ve.co.bsc.sigai.web;

import java.lang.Long;
import java.lang.String;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.Parametro;
import ve.co.bsc.sigai.domain.Reporte;

privileged aspect ParametroController_Roo_Controller {
    
    @RequestMapping(value = "/parametro/{id}", method = RequestMethod.GET)
    public String ParametroController.show(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("parametro", Parametro.findParametro(id));
        return "parametro/show";
    }
    
    @RequestMapping(value = "/parametro", method = RequestMethod.GET)
    public String ParametroController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("parametroes", Parametro.findParametroEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) Parametro.countParametroes() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("parametroes", Parametro.findAllParametroes());
        }
        return "parametro/list";
    }
    
    @RequestMapping(value = "find/ByReporte/form", method = RequestMethod.GET)
    public String ParametroController.findParametroesByReporteForm(ModelMap modelMap) {
        modelMap.addAttribute("reportes", Reporte.findAllReportes());
        return "parametro/findParametroesByReporte";
    }
    
    @RequestMapping(value = "find/ByReporte", method = RequestMethod.GET)
    public String ParametroController.findParametroesByReporte(@RequestParam("reporte") Reporte reporte, ModelMap modelMap) {
        if (reporte == null) throw new IllegalArgumentException("A Reporte is required.");
        modelMap.addAttribute("parametroes", Parametro.findParametroesByReporte(reporte).getResultList());
        return "parametro/list";
    }
    
}
