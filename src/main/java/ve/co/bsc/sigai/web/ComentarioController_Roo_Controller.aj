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
import ve.co.bsc.sigai.domain.Comentario;

privileged aspect ComentarioController_Roo_Controller {
    
    @RequestMapping(value = "/comentario/form", method = RequestMethod.GET)
    public String ComentarioController.createForm(ModelMap modelMap) {
        modelMap.addAttribute("comentario", new Comentario());
        return "comentario/create";
    }
    
    @RequestMapping(value = "/comentario/{_id}", method = RequestMethod.GET)
    public String ComentarioController.show(@PathVariable("_id") Long _id, ModelMap modelMap) {
        if (_id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("comentario", Comentario.findComentario(_id));
        return "comentario/show";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public String ComentarioController.update(@Valid Comentario comentario, BindingResult result, ModelMap modelMap) {
        if (comentario == null) throw new IllegalArgumentException("A comentario is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("comentario", comentario);
            return "comentario/update";
        }
        comentario.merge();
        return "redirect:/comentario/" + comentario.get_id();
    }
    
    @RequestMapping(value = "/comentario/{_id}/form", method = RequestMethod.GET)
    public String ComentarioController.updateForm(@PathVariable("_id") Long _id, ModelMap modelMap) {
        if (_id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("comentario", Comentario.findComentario(_id));
        return "comentario/update";
    }
    
    @RequestMapping(value = "/comentario/{_id}", method = RequestMethod.DELETE)
    public String ComentarioController.delete(@PathVariable("_id") Long _id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (_id == null) throw new IllegalArgumentException("An Identifier is required");
        Comentario.findComentario(_id).remove();
        return "redirect:/comentario?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
    @RequestMapping(value = "find/ByTipoAndId/form", method = RequestMethod.GET)
    public String ComentarioController.findComentariosByTipoAndIdForm(ModelMap modelMap) {
        return "comentario/findComentariosByTipoAndId";
    }
    
    @RequestMapping(value = "find/ByTipoAndId", method = RequestMethod.GET)
    public String ComentarioController.findComentariosByTipoAndId(@RequestParam("tipo") String tipo, @RequestParam("id") Long id, ModelMap modelMap) {
        if (tipo == null || tipo.length() == 0) throw new IllegalArgumentException("A Tipo is required.");
        if (id == null) throw new IllegalArgumentException("A Id is required.");
        modelMap.addAttribute("comentarios", Comentario.findComentariosByTipoAndId(tipo, id).getResultList());
        return "comentario/list";
    }
    
}
