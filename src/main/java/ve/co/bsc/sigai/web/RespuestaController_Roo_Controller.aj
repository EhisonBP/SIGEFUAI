package ve.co.bsc.sigai.web;

import java.lang.String;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.Requerimiento;
import ve.co.bsc.sigai.domain.Respuesta;

privileged aspect RespuestaController_Roo_Controller {
    
    @RequestMapping(value = "/respuesta", method = RequestMethod.GET)
    public String RespuestaController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("respuestas", Respuesta.findRespuestaEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) Respuesta.countRespuestas() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("respuestas", Respuesta.findAllRespuestas());
        }
        modelMap.addAttribute("respuesta_fecha_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        return "respuesta/list";
    }
    
    @RequestMapping(value = "find/ByRequerimiento/form", method = RequestMethod.GET)
    public String RespuestaController.findRespuestasByRequerimientoForm(ModelMap modelMap) {
        modelMap.addAttribute("requerimientoes", Requerimiento.findAllRequerimientoes());
        return "respuesta/findRespuestasByRequerimiento";
    }
    
    @RequestMapping(value = "find/ByRequerimiento", method = RequestMethod.GET)
    public String RespuestaController.findRespuestasByRequerimiento(@RequestParam("requerimiento") Requerimiento requerimiento, ModelMap modelMap) {
        if (requerimiento == null) throw new IllegalArgumentException("A Requerimiento is required.");
        modelMap.addAttribute("respuestas", Respuesta.findRespuestasByRequerimiento(requerimiento).getResultList());
        return "respuesta/list";
    }
    
}
