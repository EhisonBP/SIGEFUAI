package ve.co.bsc.sigai.web;

import java.lang.Long;
import java.lang.String;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.ActividadGeneral;
import ve.co.bsc.sigai.domain.Actuacion;

privileged aspect ActividadGeneralController_Roo_Controller {
    
    @RequestMapping(value = "/actividadgeneral", method = RequestMethod.GET)
    public String ActividadGeneralController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("actividadgenerals", ActividadGeneral.findActividadGeneralEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) ActividadGeneral.countActividadGenerals() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("actividadgenerals", ActividadGeneral.findAllActividadGenerals());
        }
        modelMap.addAttribute("actividadGeneral_fechaEstimadaDeInicio_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("actividadGeneral_fechaEstimadaDeFin_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        return "actividadgeneral/list";
    }
    
    @RequestMapping(value = "/actividadgeneral/{id}", method = RequestMethod.DELETE)
    public String ActividadGeneralController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        ActividadGeneral.findActividadGeneral(id).remove();
        return "redirect:/actividadgeneral?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
    @RequestMapping(value = "find/ByCodigoActuacion/form", method = RequestMethod.GET)
    public String ActividadGeneralController.findActividadGeneralsByCodigoActuacionForm(ModelMap modelMap) {
        modelMap.addAttribute("actuacions", Actuacion.findAllActuacions());
        return "actividadgeneral/findActividadGeneralsByCodigoActuacion";
    }
    
    @RequestMapping(value = "find/ByCodigoActuacion", method = RequestMethod.GET)
    public String ActividadGeneralController.findActividadGeneralsByCodigoActuacion(@RequestParam("codigoActuacion") Actuacion codigoActuacion, ModelMap modelMap) {
        if (codigoActuacion == null) throw new IllegalArgumentException("A CodigoActuacion is required.");
        modelMap.addAttribute("actividadgenerals", ActividadGeneral.findActividadGeneralsByCodigoActuacion(codigoActuacion).getResultList());
        return "actividadgeneral/list";
    }
    
}
