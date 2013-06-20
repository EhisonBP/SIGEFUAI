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
import ve.co.bsc.sigai.domain.EntradaBitacora;
import ve.co.bsc.sigai.domain.TipoEntradaBitacora;

privileged aspect EntradaBitacoraController_Roo_Controller {
    
    @RequestMapping(value = "/entradabitacora", method = RequestMethod.POST)
    public String EntradaBitacoraController.create(@Valid EntradaBitacora entradaBitacora, BindingResult result, ModelMap modelMap) {
        if (entradaBitacora == null) throw new IllegalArgumentException("A entradaBitacora is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("entradaBitacora", entradaBitacora);
            modelMap.addAttribute("tipoentradabitacoras", TipoEntradaBitacora.findAllTipoEntradaBitacoras());
            modelMap.addAttribute("entradaBitacora_timeStamp_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("SM", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
            return "entradabitacora/create";
        }
        entradaBitacora.persist();
        return "redirect:/entradabitacora/" + entradaBitacora.getId();
    }
    
    @RequestMapping(value = "/entradabitacora/form", method = RequestMethod.GET)
    public String EntradaBitacoraController.createForm(ModelMap modelMap) {
        modelMap.addAttribute("entradaBitacora", new EntradaBitacora());
        modelMap.addAttribute("tipoentradabitacoras", TipoEntradaBitacora.findAllTipoEntradaBitacoras());
        modelMap.addAttribute("entradaBitacora_timeStamp_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("SM", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        return "entradabitacora/create";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public String EntradaBitacoraController.update(@Valid EntradaBitacora entradaBitacora, BindingResult result, ModelMap modelMap) {
        if (entradaBitacora == null) throw new IllegalArgumentException("A entradaBitacora is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("entradaBitacora", entradaBitacora);
            modelMap.addAttribute("tipoentradabitacoras", TipoEntradaBitacora.findAllTipoEntradaBitacoras());
            modelMap.addAttribute("entradaBitacora_timeStamp_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("SM", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
            return "entradabitacora/update";
        }
        entradaBitacora.merge();
        return "redirect:/entradabitacora/" + entradaBitacora.getId();
    }
    
    @RequestMapping(value = "/entradabitacora/{id}/form", method = RequestMethod.GET)
    public String EntradaBitacoraController.updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("entradaBitacora", EntradaBitacora.findEntradaBitacora(id));
        modelMap.addAttribute("tipoentradabitacoras", TipoEntradaBitacora.findAllTipoEntradaBitacoras());
        modelMap.addAttribute("entradaBitacora_timeStamp_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("SM", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        return "entradabitacora/update";
    }
    
    @RequestMapping(value = "/entradabitacora/{id}", method = RequestMethod.DELETE)
    public String EntradaBitacoraController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        EntradaBitacora.findEntradaBitacora(id).remove();
        return "redirect:/entradabitacora?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
}
