package ve.co.bsc.sigai.web;

import java.lang.String;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.Actuacion;
import ve.co.bsc.sigai.domain.Auditado;
import ve.co.bsc.sigai.domain.EstatusRequerimiento;
import ve.co.bsc.sigai.domain.Requerimiento;

privileged aspect RequerimientoController_Roo_Controller {
    
    @RequestMapping(value = "/requerimiento", method = RequestMethod.GET)
    public String RequerimientoController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("requerimientoes", Requerimiento.findRequerimientoEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) Requerimiento.countRequerimientoes() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("requerimientoes", Requerimiento.findAllRequerimientoes());
        }
        modelMap.addAttribute("requerimiento_fechaRecibidoTotalmente_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("requerimiento_fechaSolicitud_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        return "requerimiento/list";
    }
    
    @RequestMapping(value = "find/ByActuacion/form", method = RequestMethod.GET)
    public String RequerimientoController.findRequerimientoesByActuacionForm(ModelMap modelMap) {
        modelMap.addAttribute("actuacions", Actuacion.findAllActuacions());
        return "requerimiento/findRequerimientoesByActuacion";
    }
    
    @RequestMapping(value = "find/ByActuacion", method = RequestMethod.GET)
    public String RequerimientoController.findRequerimientoesByActuacion(@RequestParam("actuacion") Actuacion actuacion, ModelMap modelMap) {
        if (actuacion == null) throw new IllegalArgumentException("A Actuacion is required.");
        modelMap.addAttribute("requerimientoes", Requerimiento.findRequerimientoesByActuacion(actuacion).getResultList());
        return "requerimiento/list";
    }
    
    @RequestMapping(value = "find/ByAuditado/form", method = RequestMethod.GET)
    public String RequerimientoController.findRequerimientoesByAuditadoForm(ModelMap modelMap) {
        modelMap.addAttribute("auditadoes", Auditado.findAllAuditadoes());
        return "requerimiento/findRequerimientoesByAuditado";
    }
    
    @RequestMapping(value = "find/ByAuditado", method = RequestMethod.GET)
    public String RequerimientoController.findRequerimientoesByAuditado(@RequestParam("auditado") Auditado auditado, ModelMap modelMap) {
        if (auditado == null) throw new IllegalArgumentException("A Auditado is required.");
        modelMap.addAttribute("requerimientoes", Requerimiento.findRequerimientoesByAuditado(auditado).getResultList());
        return "requerimiento/list";
    }
    
    @RequestMapping(value = "find/ByEstadoRequerimientoAndAuditado/form", method = RequestMethod.GET)
    public String RequerimientoController.findRequerimientoesByEstadoRequerimientoAndAuditadoForm(ModelMap modelMap) {
        modelMap.addAttribute("estatusrequerimientoes", EstatusRequerimiento.findAllEstatusRequerimientoes());
        modelMap.addAttribute("auditadoes", Auditado.findAllAuditadoes());
        return "requerimiento/findRequerimientoesByEstadoRequerimientoAndAuditado";
    }
    
    @RequestMapping(value = "find/ByEstadoRequerimientoAndAuditado", method = RequestMethod.GET)
    public String RequerimientoController.findRequerimientoesByEstadoRequerimientoAndAuditado(@RequestParam("estadoRequerimiento") EstatusRequerimiento estadoRequerimiento, @RequestParam("auditado") Auditado auditado, ModelMap modelMap) {
        if (estadoRequerimiento == null) throw new IllegalArgumentException("A EstadoRequerimiento is required.");
        if (auditado == null) throw new IllegalArgumentException("A Auditado is required.");
        modelMap.addAttribute("requerimientoes", Requerimiento.findRequerimientoesByEstadoRequerimientoAndAuditado(estadoRequerimiento, auditado).getResultList());
        return "requerimiento/list";
    }
    
}
