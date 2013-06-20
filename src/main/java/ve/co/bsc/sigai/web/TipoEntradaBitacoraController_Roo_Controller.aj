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
import ve.co.bsc.sigai.domain.TipoEntradaBitacora;

privileged aspect TipoEntradaBitacoraController_Roo_Controller {
    
    @RequestMapping(value = "/tipoentradabitacora", method = RequestMethod.POST)
    public String TipoEntradaBitacoraController.create(@Valid TipoEntradaBitacora tipoEntradaBitacora, BindingResult result, ModelMap modelMap) {
        if (tipoEntradaBitacora == null) throw new IllegalArgumentException("A tipoEntradaBitacora is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("tipoEntradaBitacora", tipoEntradaBitacora);
            return "tipoentradabitacora/create";
        }
        tipoEntradaBitacora.persist();
        return "redirect:/tipoentradabitacora/" + tipoEntradaBitacora.getId();
    }
    
    @RequestMapping(value = "/tipoentradabitacora/form", method = RequestMethod.GET)
    public String TipoEntradaBitacoraController.createForm(ModelMap modelMap) {
        modelMap.addAttribute("tipoEntradaBitacora", new TipoEntradaBitacora());
        return "tipoentradabitacora/create";
    }
    
    @RequestMapping(value = "/tipoentradabitacora/{id}", method = RequestMethod.GET)
    public String TipoEntradaBitacoraController.show(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("tipoEntradaBitacora", TipoEntradaBitacora.findTipoEntradaBitacora(id));
        return "tipoentradabitacora/show";
    }
    
    @RequestMapping(value = "/tipoentradabitacora", method = RequestMethod.GET)
    public String TipoEntradaBitacoraController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("tipoentradabitacoras", TipoEntradaBitacora.findTipoEntradaBitacoraEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) TipoEntradaBitacora.countTipoEntradaBitacoras() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("tipoentradabitacoras", TipoEntradaBitacora.findAllTipoEntradaBitacoras());
        }
        return "tipoentradabitacora/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public String TipoEntradaBitacoraController.update(@Valid TipoEntradaBitacora tipoEntradaBitacora, BindingResult result, ModelMap modelMap) {
        if (tipoEntradaBitacora == null) throw new IllegalArgumentException("A tipoEntradaBitacora is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("tipoEntradaBitacora", tipoEntradaBitacora);
            return "tipoentradabitacora/update";
        }
        tipoEntradaBitacora.merge();
        return "redirect:/tipoentradabitacora/" + tipoEntradaBitacora.getId();
    }
    
    @RequestMapping(value = "/tipoentradabitacora/{id}/form", method = RequestMethod.GET)
    public String TipoEntradaBitacoraController.updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("tipoEntradaBitacora", TipoEntradaBitacora.findTipoEntradaBitacora(id));
        return "tipoentradabitacora/update";
    }
    
    @RequestMapping(value = "/tipoentradabitacora/{id}", method = RequestMethod.DELETE)
    public String TipoEntradaBitacoraController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        TipoEntradaBitacora.findTipoEntradaBitacora(id).remove();
        return "redirect:/tipoentradabitacora?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
    @RequestMapping(value = "find/ByClase/form", method = RequestMethod.GET)
    public String TipoEntradaBitacoraController.findTipoEntradaBitacorasByClaseForm(ModelMap modelMap) {
        return "tipoentradabitacora/findTipoEntradaBitacorasByClase";
    }
    
    @RequestMapping(value = "find/ByClase", method = RequestMethod.GET)
    public String TipoEntradaBitacoraController.findTipoEntradaBitacorasByClase(@RequestParam("clase") String clase, ModelMap modelMap) {
        if (clase == null || clase.length() == 0) throw new IllegalArgumentException("A Clase is required.");
        modelMap.addAttribute("tipoentradabitacoras", TipoEntradaBitacora.findTipoEntradaBitacorasByClase(clase).getResultList());
        return "tipoentradabitacora/list";
    }
    
}
