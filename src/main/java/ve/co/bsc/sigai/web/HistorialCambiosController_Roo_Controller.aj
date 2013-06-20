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
import ve.co.bsc.sigai.domain.ArchivoAdjunto;
import ve.co.bsc.sigai.domain.HistorialCambios;

privileged aspect HistorialCambiosController_Roo_Controller {
    
    @RequestMapping(value = "/historialcambios", method = RequestMethod.POST)
    public String HistorialCambiosController.create(@Valid HistorialCambios historialCambios, BindingResult result, ModelMap modelMap) {
        if (historialCambios == null) throw new IllegalArgumentException("A historialCambios is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("historialCambios", historialCambios);
            modelMap.addAttribute("archivoadjuntoes", ArchivoAdjunto.findAllArchivoAdjuntoes());
            modelMap.addAttribute("historialCambios_fecha_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("FM", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
            return "historialcambios/create";
        }
        historialCambios.persist();
        return "redirect:/historialcambios/" + historialCambios.getId();
    }
    
    @RequestMapping(value = "/historialcambios/form", method = RequestMethod.GET)
    public String HistorialCambiosController.createForm(ModelMap modelMap) {
        modelMap.addAttribute("historialCambios", new HistorialCambios());
        modelMap.addAttribute("archivoadjuntoes", ArchivoAdjunto.findAllArchivoAdjuntoes());
        modelMap.addAttribute("historialCambios_fecha_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("FM", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        return "historialcambios/create";
    }
    
    @RequestMapping(value = "/historialcambios/{id}", method = RequestMethod.GET)
    public String HistorialCambiosController.show(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("historialCambios_fecha_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("FM", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("historialCambios", HistorialCambios.findHistorialCambios(id));
        return "historialcambios/show";
    }
    
    @RequestMapping(value = "/historialcambios", method = RequestMethod.GET)
    public String HistorialCambiosController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("historialcambioses", HistorialCambios.findHistorialCambiosEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) HistorialCambios.countHistorialCambioses() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("historialcambioses", HistorialCambios.findAllHistorialCambioses());
        }
        modelMap.addAttribute("historialCambios_fecha_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("FM", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        return "historialcambios/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public String HistorialCambiosController.update(@Valid HistorialCambios historialCambios, BindingResult result, ModelMap modelMap) {
        if (historialCambios == null) throw new IllegalArgumentException("A historialCambios is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("historialCambios", historialCambios);
            modelMap.addAttribute("archivoadjuntoes", ArchivoAdjunto.findAllArchivoAdjuntoes());
            modelMap.addAttribute("historialCambios_fecha_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("FM", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
            return "historialcambios/update";
        }
        historialCambios.merge();
        return "redirect:/historialcambios/" + historialCambios.getId();
    }
    
    @RequestMapping(value = "/historialcambios/{id}/form", method = RequestMethod.GET)
    public String HistorialCambiosController.updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("historialCambios", HistorialCambios.findHistorialCambios(id));
        modelMap.addAttribute("archivoadjuntoes", ArchivoAdjunto.findAllArchivoAdjuntoes());
        modelMap.addAttribute("historialCambios_fecha_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("FM", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        return "historialcambios/update";
    }
    
    @RequestMapping(value = "/historialcambios/{id}", method = RequestMethod.DELETE)
    public String HistorialCambiosController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        HistorialCambios.findHistorialCambios(id).remove();
        return "redirect:/historialcambios?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
    @RequestMapping(value = "find/ByArchivoAdjunto/form", method = RequestMethod.GET)
    public String HistorialCambiosController.findHistorialCambiosesByArchivoAdjuntoForm(ModelMap modelMap) {
        modelMap.addAttribute("archivoadjuntoes", ArchivoAdjunto.findAllArchivoAdjuntoes());
        return "historialcambios/findHistorialCambiosesByArchivoAdjunto";
    }
    
    @RequestMapping(value = "find/ByArchivoAdjunto", method = RequestMethod.GET)
    public String HistorialCambiosController.findHistorialCambiosesByArchivoAdjunto(@RequestParam("archivoAdjunto") ArchivoAdjunto archivoAdjunto, ModelMap modelMap) {
        if (archivoAdjunto == null) throw new IllegalArgumentException("A ArchivoAdjunto is required.");
        modelMap.addAttribute("historialcambioses", HistorialCambios.findHistorialCambiosesByArchivoAdjunto(archivoAdjunto).getResultList());
        return "historialcambios/list";
    }
    
}
