package ve.co.bsc.sigai.web;

import java.lang.Long;
import java.lang.String;
import javax.validation.Valid;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.Actuacion;
import ve.co.bsc.sigai.domain.ItemPlanificacionActuacion;
import ve.co.bsc.sigai.domain.OtraActividad;
import ve.co.bsc.sigai.domain.PlanAnual;

privileged aspect ItemPlanificacionActuacionController_Roo_Controller {
    
    @RequestMapping(value = "/itemplanificacionactuacion", method = RequestMethod.POST)
    public String ItemPlanificacionActuacionController.create(@Valid ItemPlanificacionActuacion itemPlanificacionActuacion, BindingResult result, ModelMap modelMap) {
        if (itemPlanificacionActuacion == null) throw new IllegalArgumentException("A itemPlanificacionActuacion is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("itemPlanificacionActuacion", itemPlanificacionActuacion);
            modelMap.addAttribute("actuacions", Actuacion.findAllActuacions());
            modelMap.addAttribute("otraactividads", OtraActividad.findAllOtraActividads());
            modelMap.addAttribute("plananuals", PlanAnual.findAllPlanAnuals());
            modelMap.addAttribute("itemPlanificacionActuacion_fechaInicioEstimada_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
            modelMap.addAttribute("itemPlanificacionActuacion_finReal_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
            modelMap.addAttribute("itemPlanificacionActuacion_inicioReal_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
            modelMap.addAttribute("itemPlanificacionActuacion_fechaFinEstimada_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
            return "itemplanificacionactuacion/create";
        }
        itemPlanificacionActuacion.persist();
        return "redirect:/itemplanificacionactuacion/" + itemPlanificacionActuacion.getId();
    }
    
    @RequestMapping(value = "/itemplanificacionactuacion/form", method = RequestMethod.GET)
    public String ItemPlanificacionActuacionController.createForm(ModelMap modelMap) {
        modelMap.addAttribute("itemPlanificacionActuacion", new ItemPlanificacionActuacion());
        modelMap.addAttribute("actuacions", Actuacion.findAllActuacions());
        modelMap.addAttribute("otraactividads", OtraActividad.findAllOtraActividads());
        modelMap.addAttribute("plananuals", PlanAnual.findAllPlanAnuals());
        modelMap.addAttribute("itemPlanificacionActuacion_fechaInicioEstimada_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("itemPlanificacionActuacion_finReal_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("itemPlanificacionActuacion_inicioReal_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("itemPlanificacionActuacion_fechaFinEstimada_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        return "itemplanificacionactuacion/create";
    }
    
    @RequestMapping(value = "/itemplanificacionactuacion/{id}", method = RequestMethod.GET)
    public String ItemPlanificacionActuacionController.show(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("itemPlanificacionActuacion_fechaInicioEstimada_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("itemPlanificacionActuacion_finReal_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("itemPlanificacionActuacion_inicioReal_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("itemPlanificacionActuacion_fechaFinEstimada_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("itemPlanificacionActuacion", ItemPlanificacionActuacion.findItemPlanificacionActuacion(id));
        return "itemplanificacionactuacion/show";
    }
    
    @RequestMapping(value = "/itemplanificacionactuacion", method = RequestMethod.GET)
    public String ItemPlanificacionActuacionController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("itemplanificacionactuacions", ItemPlanificacionActuacion.findItemPlanificacionActuacionEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) ItemPlanificacionActuacion.countItemPlanificacionActuacions() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("itemplanificacionactuacions", ItemPlanificacionActuacion.findAllItemPlanificacionActuacions());
        }
        modelMap.addAttribute("itemPlanificacionActuacion_fechaInicioEstimada_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("itemPlanificacionActuacion_finReal_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("itemPlanificacionActuacion_inicioReal_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("itemPlanificacionActuacion_fechaFinEstimada_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        return "itemplanificacionactuacion/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public String ItemPlanificacionActuacionController.update(@Valid ItemPlanificacionActuacion itemPlanificacionActuacion, BindingResult result, ModelMap modelMap) {
        if (itemPlanificacionActuacion == null) throw new IllegalArgumentException("A itemPlanificacionActuacion is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("itemPlanificacionActuacion", itemPlanificacionActuacion);
            modelMap.addAttribute("actuacions", Actuacion.findAllActuacions());
            modelMap.addAttribute("otraactividads", OtraActividad.findAllOtraActividads());
            modelMap.addAttribute("plananuals", PlanAnual.findAllPlanAnuals());
            modelMap.addAttribute("itemPlanificacionActuacion_fechaInicioEstimada_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
            modelMap.addAttribute("itemPlanificacionActuacion_finReal_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
            modelMap.addAttribute("itemPlanificacionActuacion_inicioReal_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
            modelMap.addAttribute("itemPlanificacionActuacion_fechaFinEstimada_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
            return "itemplanificacionactuacion/update";
        }
        itemPlanificacionActuacion.merge();
        return "redirect:/itemplanificacionactuacion/" + itemPlanificacionActuacion.getId();
    }
    
    @RequestMapping(value = "/itemplanificacionactuacion/{id}/form", method = RequestMethod.GET)
    public String ItemPlanificacionActuacionController.updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("itemPlanificacionActuacion", ItemPlanificacionActuacion.findItemPlanificacionActuacion(id));
        modelMap.addAttribute("actuacions", Actuacion.findAllActuacions());
        modelMap.addAttribute("otraactividads", OtraActividad.findAllOtraActividads());
        modelMap.addAttribute("plananuals", PlanAnual.findAllPlanAnuals());
        modelMap.addAttribute("itemPlanificacionActuacion_fechaInicioEstimada_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("itemPlanificacionActuacion_finReal_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("itemPlanificacionActuacion_inicioReal_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("itemPlanificacionActuacion_fechaFinEstimada_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        return "itemplanificacionactuacion/update";
    }
    
    @RequestMapping(value = "/itemplanificacionactuacion/{id}", method = RequestMethod.DELETE)
    public String ItemPlanificacionActuacionController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        ItemPlanificacionActuacion.findItemPlanificacionActuacion(id).remove();
        return "redirect:/itemplanificacionactuacion?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
    @RequestMapping(value = "find/ByPlanAnual/form", method = RequestMethod.GET)
    public String ItemPlanificacionActuacionController.findItemPlanificacionActuacionsByPlanAnualForm(ModelMap modelMap) {
        modelMap.addAttribute("plananuals", PlanAnual.findAllPlanAnuals());
        return "itemplanificacionactuacion/findItemPlanificacionActuacionsByPlanAnual";
    }
    
    @RequestMapping(value = "find/ByPlanAnual", method = RequestMethod.GET)
    public String ItemPlanificacionActuacionController.findItemPlanificacionActuacionsByPlanAnual(@RequestParam("planAnual") PlanAnual planAnual, ModelMap modelMap) {
        if (planAnual == null) throw new IllegalArgumentException("A PlanAnual is required.");
        modelMap.addAttribute("itemplanificacionactuacions", ItemPlanificacionActuacion.findItemPlanificacionActuacionsByPlanAnual(planAnual).getResultList());
        return "itemplanificacionactuacion/list";
    }
    
    @RequestMapping(value = "find/ByActuacion/form", method = RequestMethod.GET)
    public String ItemPlanificacionActuacionController.findItemPlanificacionActuacionsByActuacionForm(ModelMap modelMap) {
        modelMap.addAttribute("actuacions", Actuacion.findAllActuacions());
        return "itemplanificacionactuacion/findItemPlanificacionActuacionsByActuacion";
    }
    
    @RequestMapping(value = "find/ByActuacion", method = RequestMethod.GET)
    public String ItemPlanificacionActuacionController.findItemPlanificacionActuacionsByActuacion(@RequestParam("actuacion") Actuacion actuacion, ModelMap modelMap) {
        if (actuacion == null) throw new IllegalArgumentException("A Actuacion is required.");
        modelMap.addAttribute("itemplanificacionactuacions", ItemPlanificacionActuacion.findItemPlanificacionActuacionsByActuacion(actuacion).getResultList());
        return "itemplanificacionactuacion/list";
    }
    
    @RequestMapping(value = "find/ByOtraActividad/form", method = RequestMethod.GET)
    public String ItemPlanificacionActuacionController.findItemPlanificacionActuacionsByOtraActividadForm(ModelMap modelMap) {
        modelMap.addAttribute("otraactividads", OtraActividad.findAllOtraActividads());
        return "itemplanificacionactuacion/findItemPlanificacionActuacionsByOtraActividad";
    }
    
    @RequestMapping(value = "find/ByOtraActividad", method = RequestMethod.GET)
    public String ItemPlanificacionActuacionController.findItemPlanificacionActuacionsByOtraActividad(@RequestParam("otraActividad") OtraActividad otraActividad, ModelMap modelMap) {
        if (otraActividad == null) throw new IllegalArgumentException("A OtraActividad is required.");
        modelMap.addAttribute("itemplanificacionactuacions", ItemPlanificacionActuacion.findItemPlanificacionActuacionsByOtraActividad(otraActividad).getResultList());
        return "itemplanificacionactuacion/list";
    }
    
}
