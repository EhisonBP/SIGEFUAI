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
import ve.co.bsc.sigai.domain.Ciudad;
import ve.co.bsc.sigai.domain.CodigoArea;
import ve.co.bsc.sigai.domain.Estado;

privileged aspect CodigoAreaController_Roo_Controller {
    
    @RequestMapping(value = "/codigoarea/{id}", method = RequestMethod.GET)
    public String CodigoAreaController.show(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("codigoArea_fechaCreacion_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("codigoArea_fechaModificacion_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("codigoArea", CodigoArea.findCodigoArea(id));
        return "codigoarea/show";
    }
    
    @RequestMapping(value = "/codigoarea", method = RequestMethod.GET)
    public String CodigoAreaController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("codigoareas", CodigoArea.findCodigoAreaEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) CodigoArea.countCodigoAreas() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("codigoareas", CodigoArea.findAllCodigoAreas());
        }
        modelMap.addAttribute("codigoArea_fechaCreacion_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("codigoArea_fechaModificacion_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        return "codigoarea/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public String CodigoAreaController.update(@Valid CodigoArea codigoArea, BindingResult result, ModelMap modelMap) {
        if (codigoArea == null) throw new IllegalArgumentException("A codigoArea is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("codigoArea", codigoArea);
            modelMap.addAttribute("ciudads", Ciudad.findAllCiudads());
            modelMap.addAttribute("estadoes", Estado.findAllEstadoes());
            modelMap.addAttribute("codigoArea_fechaCreacion_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
            modelMap.addAttribute("codigoArea_fechaModificacion_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
            return "codigoarea/update";
        }
        codigoArea.merge();
        return "redirect:/codigoarea/" + codigoArea.getId();
    }
    
    @RequestMapping(value = "/codigoarea/{id}/form", method = RequestMethod.GET)
    public String CodigoAreaController.updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("codigoArea", CodigoArea.findCodigoArea(id));
        modelMap.addAttribute("ciudads", Ciudad.findAllCiudads());
        modelMap.addAttribute("estadoes", Estado.findAllEstadoes());
        modelMap.addAttribute("codigoArea_fechaCreacion_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("codigoArea_fechaModificacion_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        return "codigoarea/update";
    }
    
    @RequestMapping(value = "/codigoarea/{id}", method = RequestMethod.DELETE)
    public String CodigoAreaController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        CodigoArea.findCodigoArea(id).remove();
        return "redirect:/codigoarea?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
}
