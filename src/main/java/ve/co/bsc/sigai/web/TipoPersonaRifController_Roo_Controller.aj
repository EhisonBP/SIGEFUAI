package ve.co.bsc.sigai.web;

import java.lang.Long;
import java.lang.String;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.TipoPersonaRif;

privileged aspect TipoPersonaRifController_Roo_Controller {
    
    @RequestMapping(value = "/tipopersonarif/{id}", method = RequestMethod.GET)
    public String TipoPersonaRifController.show(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("tipoPersonaRif_fechaCreacion_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("tipoPersonaRif_fechaModificacion_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("tipoPersonaRif", TipoPersonaRif.findTipoPersonaRif(id));
        return "tipopersonarif/show";
    }
    
    @RequestMapping(value = "/tipopersonarif", method = RequestMethod.GET)
    public String TipoPersonaRifController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("tipopersonarifs", TipoPersonaRif.findTipoPersonaRifEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) TipoPersonaRif.countTipoPersonaRifs() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("tipopersonarifs", TipoPersonaRif.findAllTipoPersonaRifs());
        }
        modelMap.addAttribute("tipoPersonaRif_fechaCreacion_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("tipoPersonaRif_fechaModificacion_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        return "tipopersonarif/list";
    }
    
    @RequestMapping(value = "/tipopersonarif/{id}", method = RequestMethod.DELETE)
    public String TipoPersonaRifController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        TipoPersonaRif.findTipoPersonaRif(id).remove();
        return "redirect:/tipopersonarif?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
}
