package ve.co.bsc.sigai.web;

import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.EstadoAuditor;
import ve.co.bsc.sigai.domain.InstruirPlan;

privileged aspect InstruirPlanController_Roo_Controller {
    
    @RequestMapping(value = "/instruirplan/{id}", method = RequestMethod.GET)
    public String InstruirPlanController.show(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("instruirPlan_fechaFin_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("instruirPlan_fechaInicio_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("instruirPlan", InstruirPlan.findInstruirPlan(id));
        return "instruirplan/show";
    }
    
    @RequestMapping(value = "/instruirplan", method = RequestMethod.GET)
    public String InstruirPlanController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("instruirplans", InstruirPlan.findInstruirPlanEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) InstruirPlan.countInstruirPlans() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("instruirplans", InstruirPlan.findAllInstruirPlans());
        }
        modelMap.addAttribute("instruirPlan_fechaFin_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("instruirPlan_fechaInicio_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        return "instruirplan/list";
    }
    
    @RequestMapping(value = "/instruirplan/{id}", method = RequestMethod.DELETE)
    public String InstruirPlanController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        InstruirPlan.findInstruirPlan(id).remove();
        return "redirect:/instruirplan?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
    @RequestMapping(value = "find/ByAnoEquals/form", method = RequestMethod.GET)
    public String InstruirPlanController.findInstruirPlansByAnoEqualsForm(ModelMap modelMap) {
        return "instruirplan/findInstruirPlansByAnoEquals";
    }
    
    @RequestMapping(value = "find/ByAnoEquals", method = RequestMethod.GET)
    public String InstruirPlanController.findInstruirPlansByAnoEquals(@RequestParam("ano") Integer ano, ModelMap modelMap) {
        if (ano == null) throw new IllegalArgumentException("A Ano is required.");
        modelMap.addAttribute("instruirplans", InstruirPlan.findInstruirPlansByAnoEquals(ano).getResultList());
        return "instruirplan/list";
    }
    
    @RequestMapping(value = "find/ByEstatus/form", method = RequestMethod.GET)
    public String InstruirPlanController.findInstruirPlansByEstatusForm(ModelMap modelMap) {
        modelMap.addAttribute("estadoauditors", EstadoAuditor.findAllEstadoAuditors());
        return "instruirplan/findInstruirPlansByEstatus";
    }
    
    @RequestMapping(value = "find/ByEstatus", method = RequestMethod.GET)
    public String InstruirPlanController.findInstruirPlansByEstatus(@RequestParam("estatus") EstadoAuditor estatus, ModelMap modelMap) {
        if (estatus == null) throw new IllegalArgumentException("A Estatus is required.");
        modelMap.addAttribute("instruirplans", InstruirPlan.findInstruirPlansByEstatus(estatus).getResultList());
        return "instruirplan/list";
    }
    
}
