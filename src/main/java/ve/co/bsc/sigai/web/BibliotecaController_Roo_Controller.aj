package ve.co.bsc.sigai.web;

import java.lang.Long;
import java.lang.String;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.Biblioteca;

privileged aspect BibliotecaController_Roo_Controller {
    
    @RequestMapping(value = "/biblioteca/form", method = RequestMethod.GET)
    public String BibliotecaController.createForm(ModelMap modelMap) {
        modelMap.addAttribute("biblioteca", new Biblioteca());
        return "biblioteca/create";
    }
    
    @RequestMapping(value = "/biblioteca", method = RequestMethod.GET)
    public String BibliotecaController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("bibliotecas", Biblioteca.findBibliotecaEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) Biblioteca.countBibliotecas() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("bibliotecas", Biblioteca.findAllBibliotecas());
        }
        return "biblioteca/list";
    }
    
    @RequestMapping(value = "/biblioteca/{id}/form", method = RequestMethod.GET)
    public String BibliotecaController.updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("biblioteca", Biblioteca.findBiblioteca(id));
        return "biblioteca/update";
    }
    
    @RequestMapping(value = "/biblioteca/{id}", method = RequestMethod.DELETE)
    public String BibliotecaController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        Biblioteca.findBiblioteca(id).remove();
        return "redirect:/biblioteca?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
}
