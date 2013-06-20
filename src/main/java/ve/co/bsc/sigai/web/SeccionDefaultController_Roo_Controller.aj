package ve.co.bsc.sigai.web;

import java.lang.Long;
import java.lang.String;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.Biblioteca;
import ve.co.bsc.sigai.domain.SeccionDefault;

privileged aspect SeccionDefaultController_Roo_Controller {
    
    @RequestMapping(value = "/secciondefault/{id}", method = RequestMethod.DELETE)
    public String SeccionDefaultController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        SeccionDefault.findSeccionDefault(id).remove();
        return "redirect:/secciondefault?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
    @RequestMapping(value = "find/BySeccionDefault/form", method = RequestMethod.GET)
    public String SeccionDefaultController.findSeccionDefaultsBySeccionDefaultForm(ModelMap modelMap) {
        modelMap.addAttribute("secciondefaults", SeccionDefault.findAllSeccionDefaults());
        return "secciondefault/findSeccionDefaultsBySeccionDefault";
    }
    
    @RequestMapping(value = "find/BySeccionDefault", method = RequestMethod.GET)
    public String SeccionDefaultController.findSeccionDefaultsBySeccionDefault(@RequestParam("seccionDefault") SeccionDefault seccionDefault, ModelMap modelMap) {
        if (seccionDefault == null) throw new IllegalArgumentException("A SeccionDefault is required.");
        modelMap.addAttribute("secciondefaults", SeccionDefault.findSeccionDefaultsBySeccionDefault(seccionDefault).getResultList());
        return "secciondefault/list";
    }
    
    @RequestMapping(value = "find/ByBiblioteca/form", method = RequestMethod.GET)
    public String SeccionDefaultController.findSeccionDefaultsByBibliotecaForm(ModelMap modelMap) {
        modelMap.addAttribute("bibliotecas", Biblioteca.findAllBibliotecas());
        return "secciondefault/findSeccionDefaultsByBiblioteca";
    }
    
    @RequestMapping(value = "find/ByBiblioteca", method = RequestMethod.GET)
    public String SeccionDefaultController.findSeccionDefaultsByBiblioteca(@RequestParam("biblioteca") Biblioteca biblioteca, ModelMap modelMap) {
        if (biblioteca == null) throw new IllegalArgumentException("A Biblioteca is required.");
        modelMap.addAttribute("secciondefaults", SeccionDefault.findSeccionDefaultsByBiblioteca(biblioteca).getResultList());
        return "secciondefault/list";
    }
    
}
