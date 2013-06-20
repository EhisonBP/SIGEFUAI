package ve.co.bsc.sigai.web;

import java.lang.String;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.Observacion;
import ve.co.bsc.sigai.domain.PlanDeAccion;

privileged aspect PlanDeAccionController_Roo_Controller {
    
    @RequestMapping(value = "/plandeaccion", method = RequestMethod.GET)
    public String PlanDeAccionController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("plandeaccions", PlanDeAccion.findPlanDeAccionEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) PlanDeAccion.countPlanDeAccions() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("plandeaccions", PlanDeAccion.findAllPlanDeAccions());
        }
        modelMap.addAttribute("planDeAccion_fechaCierre2_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("planDeAccion_fechaCierre_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("planDeAccion_fechaSeguimiento_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        return "plandeaccion/list";
    }
    
    @RequestMapping(value = "find/ByObservacion/form", method = RequestMethod.GET)
    public String PlanDeAccionController.findPlanDeAccionsByObservacionForm(ModelMap modelMap) {
        modelMap.addAttribute("observacions", Observacion.findAllObservacions());
        return "plandeaccion/findPlanDeAccionsByObservacion";
    }
    
    @RequestMapping(value = "find/ByObservacion", method = RequestMethod.GET)
    public String PlanDeAccionController.findPlanDeAccionsByObservacion(@RequestParam("observacion") Observacion observacion, ModelMap modelMap) {
        if (observacion == null) throw new IllegalArgumentException("A Observacion is required.");
        modelMap.addAttribute("plandeaccions", PlanDeAccion.findPlanDeAccionsByObservacion(observacion).getResultList());
        return "plandeaccion/list";
    }
    
}
