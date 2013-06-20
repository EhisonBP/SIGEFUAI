package ve.co.bsc.sigai.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import ve.co.bsc.sigai.domain.Recomendacion;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import ve.co.bsc.sigai.domain.Observacion;
import ve.co.bsc.util.Util;

@RooWebScaffold(path = "recomendacion", automaticallyMaintainView = true, formBackingObject = Recomendacion.class)
@RequestMapping("/recomendacion/**")
@SessionAttributes({"recomendacion"})
@Controller
public class RecomendacionController {

    @RequestMapping(value = "/recomendacion", method = RequestMethod.POST)
    public String create(
            @Valid @ModelAttribute("recomendacion") Recomendacion recomendacion,
            BindingResult result,
            ModelMap modelMap,
            SessionStatus status,
            HttpServletRequest request
            ) {
        if (recomendacion == null) throw new IllegalArgumentException("A recomendacion is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("recomendacion", recomendacion);
            
            return "recomendacion/create";
        }
        
        recomendacion.persist();
        Util.registrarEntradaEnBitacora(recomendacion, "Se creó la Recomendación ", request.getRemoteAddr());
        status.setComplete();
        //return "redirect:/recomendacion/" + recomendacion.getId();
        return "redirect:/observacion/" + recomendacion.getObservacion().getId();
    }

    @RequestMapping(value = "/recomendacion/form", method = RequestMethod.GET)
    public String createForm(
            @RequestParam("idObservacion") long idObservacion,
            ModelMap modelMap
            ) {
        Recomendacion miRecomendacion = new Recomendacion();

        Observacion laObservacion = Observacion.findObservacion(idObservacion);
        miRecomendacion.setObservacion(laObservacion);
        
        modelMap.addAttribute("recomendacion", miRecomendacion);
        
        return "recomendacion/create";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String update(
            @Valid @ModelAttribute("recomendacion") Recomendacion recomendacion,
            BindingResult result,
            ModelMap modelMap,
            SessionStatus status,
            HttpServletRequest request
            ) {
        if (recomendacion == null) throw new IllegalArgumentException("A recomendacion is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("recomendacion", recomendacion);
            
            return "recomendacion/update";
        }
        
        recomendacion.merge();
        Util.registrarEntradaEnBitacora(recomendacion, "Se modificó la Recomendación ", request.getRemoteAddr());
        status.setComplete();
        //return "redirect:/recomendacion/" + recomendacion.getId();
        return "redirect:/observacion/" + recomendacion.getObservacion().getId();
    }

    @RequestMapping(value = "/recomendacion/{id}/form", method = RequestMethod.GET)
    public String updateForm(
            @PathVariable("id") Long id,
            ModelMap modelMap
            ) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        
        Recomendacion miRecomendacion = Recomendacion.findRecomendacion(id);        
        modelMap.addAttribute("recomendacion", miRecomendacion);
        
        return "recomendacion/update";
    }

    @RequestMapping(value = "/recomendacion/{id}", method = RequestMethod.DELETE)
    public String delete(
            @PathVariable("id") Long id,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size
            ) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        Recomendacion miRecomendacion = Recomendacion.findRecomendacion(id);
        Observacion laObservacion = miRecomendacion.getObservacion();
        miRecomendacion.remove();
        //return "redirect:/recomendacion?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
        return "redirect:/observacion/" + laObservacion.getId();
    }

    @RequestMapping(value = "/recomendacion/{id}", method = RequestMethod.GET)
    public String show(
            @PathVariable("id") Long id,
            ModelMap modelMap
            ) {

        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        Recomendacion recomendacion = Recomendacion.findRecomendacion(id);
        modelMap.addAttribute("recomendacion", recomendacion);
        modelMap.addAttribute("objetoComentable", recomendacion);

        return "recomendacion/show";
    }
}
