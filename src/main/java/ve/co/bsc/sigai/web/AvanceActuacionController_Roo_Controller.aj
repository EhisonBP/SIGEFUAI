package ve.co.bsc.sigai.web;

import java.lang.String;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.ActividadActuacion;
import ve.co.bsc.sigai.domain.AvanceActuacion;

privileged aspect AvanceActuacionController_Roo_Controller {
    
    @RequestMapping(value = "/avanceactuacion", method = RequestMethod.GET)
    public String AvanceActuacionController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("avanceactuacions", AvanceActuacion.findAvanceActuacionEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) AvanceActuacion.countAvanceActuacions() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("avanceactuacions", AvanceActuacion.findAllAvanceActuacions());
        }
        modelMap.addAttribute("avanceActuacion_fechaInicioEstimada_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("avanceActuacion_fechaFinReal_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("avanceActuacion_fechaInicioReal_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("avanceActuacion_fechaFinEstimada_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        return "avanceactuacion/list";
    }
    
    @RequestMapping(value = "find/ByCodigoActividad/form", method = RequestMethod.GET)
    public String AvanceActuacionController.findAvanceActuacionsByCodigoActividadForm(ModelMap modelMap) {
        modelMap.addAttribute("actividadactuacions", ActividadActuacion.findAllActividadActuacions());
        return "avanceactuacion/findAvanceActuacionsByCodigoActividad";
    }
    
    @RequestMapping(value = "find/ByCodigoActividad", method = RequestMethod.GET)
    public String AvanceActuacionController.findAvanceActuacionsByCodigoActividad(@RequestParam("codigoActividad") ActividadActuacion codigoActividad, ModelMap modelMap) {
        if (codigoActividad == null) throw new IllegalArgumentException("A CodigoActividad is required.");
        modelMap.addAttribute("avanceactuacions", AvanceActuacion.findAvanceActuacionsByCodigoActividad(codigoActividad).getResultList());
        return "avanceactuacion/list";
    }
    
}
