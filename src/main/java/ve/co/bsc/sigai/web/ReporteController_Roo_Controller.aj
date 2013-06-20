package ve.co.bsc.sigai.web;

import java.lang.Long;
import java.lang.String;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.Reporte;

privileged aspect ReporteController_Roo_Controller {
    
    @RequestMapping(value = "/reporte/{id}", method = RequestMethod.DELETE)
    public String ReporteController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        Reporte.findReporte(id).remove();
        return "redirect:/reporte?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
    @RequestMapping(value = "find/ByReporte/form", method = RequestMethod.GET)
    public String ReporteController.findReportesByReporteForm(ModelMap modelMap) {
        modelMap.addAttribute("reportes", Reporte.findAllReportes());
        return "reporte/findReportesByReporte";
    }
    
    @RequestMapping(value = "find/ByReporte", method = RequestMethod.GET)
    public String ReporteController.findReportesByReporte(@RequestParam("reporte") Reporte reporte, ModelMap modelMap) {
        if (reporte == null) throw new IllegalArgumentException("A Reporte is required.");
        modelMap.addAttribute("reportes", Reporte.findReportesByReporte(reporte).getResultList());
        return "reporte/list";
    }
    
    @RequestMapping(value = "find/ByClave/form", method = RequestMethod.GET)
    public String ReporteController.findReportesByClaveForm(ModelMap modelMap) {
        return "reporte/findReportesByClave";
    }
    
    @RequestMapping(value = "find/ByClave", method = RequestMethod.GET)
    public String ReporteController.findReportesByClave(@RequestParam("clave") String clave, ModelMap modelMap) {
        if (clave == null || clave.length() == 0) throw new IllegalArgumentException("A Clave is required.");
        modelMap.addAttribute("reportes", Reporte.findReportesByClave(clave).getResultList());
        return "reporte/list";
    }
    
}
