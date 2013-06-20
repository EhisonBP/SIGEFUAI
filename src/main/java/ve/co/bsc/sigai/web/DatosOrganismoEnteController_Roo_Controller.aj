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
import ve.co.bsc.sigai.domain.CodigoArea;
import ve.co.bsc.sigai.domain.DatosOrganismoEnte;
import ve.co.bsc.sigai.domain.EstadoAuditor;

privileged aspect DatosOrganismoEnteController_Roo_Controller {
    
    @RequestMapping(value = "/datosorganismoente", method = RequestMethod.POST)
    public String DatosOrganismoEnteController.create(@Valid DatosOrganismoEnte datosOrganismoEnte, BindingResult result, ModelMap modelMap) {
        if (datosOrganismoEnte == null) throw new IllegalArgumentException("A datosOrganismoEnte is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("datosOrganismoEnte", datosOrganismoEnte);
            modelMap.addAttribute("codigoareas", CodigoArea.findAllCodigoAreas());
            modelMap.addAttribute("estadoauditors", EstadoAuditor.findAllEstadoAuditors());
            modelMap.addAttribute("datosOrganismoEnte_fechaCreacion_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
            modelMap.addAttribute("datosOrganismoEnte_fechaModificacion_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
            return "datosorganismoente/create";
        }
        datosOrganismoEnte.persist();
        return "redirect:/datosorganismoente/" + datosOrganismoEnte.getId();
    }
    
    @RequestMapping(value = "/datosorganismoente/form", method = RequestMethod.GET)
    public String DatosOrganismoEnteController.createForm(ModelMap modelMap) {
        modelMap.addAttribute("datosOrganismoEnte", new DatosOrganismoEnte());
        modelMap.addAttribute("codigoareas", CodigoArea.findAllCodigoAreas());
        modelMap.addAttribute("estadoauditors", EstadoAuditor.findAllEstadoAuditors());
        modelMap.addAttribute("datosOrganismoEnte_fechaCreacion_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("datosOrganismoEnte_fechaModificacion_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        return "datosorganismoente/create";
    }
    
    @RequestMapping(value = "/datosorganismoente/{id}", method = RequestMethod.GET)
    public String DatosOrganismoEnteController.show(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("datosOrganismoEnte_fechaCreacion_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("datosOrganismoEnte_fechaModificacion_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("datosOrganismoEnte", DatosOrganismoEnte.findDatosOrganismoEnte(id));
        return "datosorganismoente/show";
    }
    
    @RequestMapping(value = "/datosorganismoente", method = RequestMethod.GET)
    public String DatosOrganismoEnteController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("datosorganismoentes", DatosOrganismoEnte.findDatosOrganismoEnteEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) DatosOrganismoEnte.countDatosOrganismoEntes() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("datosorganismoentes", DatosOrganismoEnte.findAllDatosOrganismoEntes());
        }
        modelMap.addAttribute("datosOrganismoEnte_fechaCreacion_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("datosOrganismoEnte_fechaModificacion_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        return "datosorganismoente/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public String DatosOrganismoEnteController.update(@Valid DatosOrganismoEnte datosOrganismoEnte, BindingResult result, ModelMap modelMap) {
        if (datosOrganismoEnte == null) throw new IllegalArgumentException("A datosOrganismoEnte is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("datosOrganismoEnte", datosOrganismoEnte);
            modelMap.addAttribute("codigoareas", CodigoArea.findAllCodigoAreas());
            modelMap.addAttribute("estadoauditors", EstadoAuditor.findAllEstadoAuditors());
            modelMap.addAttribute("datosOrganismoEnte_fechaCreacion_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
            modelMap.addAttribute("datosOrganismoEnte_fechaModificacion_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
            return "datosorganismoente/update";
        }
        datosOrganismoEnte.merge();
        return "redirect:/datosorganismoente/" + datosOrganismoEnte.getId();
    }
    
    @RequestMapping(value = "/datosorganismoente/{id}/form", method = RequestMethod.GET)
    public String DatosOrganismoEnteController.updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("datosOrganismoEnte", DatosOrganismoEnte.findDatosOrganismoEnte(id));
        modelMap.addAttribute("codigoareas", CodigoArea.findAllCodigoAreas());
        modelMap.addAttribute("estadoauditors", EstadoAuditor.findAllEstadoAuditors());
        modelMap.addAttribute("datosOrganismoEnte_fechaCreacion_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("datosOrganismoEnte_fechaModificacion_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        return "datosorganismoente/update";
    }
    
    @RequestMapping(value = "/datosorganismoente/{id}", method = RequestMethod.DELETE)
    public String DatosOrganismoEnteController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        DatosOrganismoEnte.findDatosOrganismoEnte(id).remove();
        return "redirect:/datosorganismoente?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
    @RequestMapping(value = "find/ByRifEquals/form", method = RequestMethod.GET)
    public String DatosOrganismoEnteController.findDatosOrganismoEntesByRifEqualsForm(ModelMap modelMap) {
        return "datosorganismoente/findDatosOrganismoEntesByRifEquals";
    }
    
    @RequestMapping(value = "find/ByRifEquals", method = RequestMethod.GET)
    public String DatosOrganismoEnteController.findDatosOrganismoEntesByRifEquals(@RequestParam("rif") String Rif, ModelMap modelMap) {
        if (Rif == null || Rif.length() == 0) throw new IllegalArgumentException("A Rif is required.");
        modelMap.addAttribute("datosorganismoentes", DatosOrganismoEnte.findDatosOrganismoEntesByRifEquals(Rif).getResultList());
        return "datosorganismoente/list";
    }
    
}
