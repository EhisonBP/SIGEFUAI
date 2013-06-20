package ve.co.bsc.sigai.web;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import ve.co.bsc.sigai.domain.Proceso;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import ve.co.bsc.sigai.domain.Riesgo;
import ve.co.bsc.sigai.domain.Unidad;
import ve.co.bsc.util.Util;

@RooWebScaffold(path = "proceso", automaticallyMaintainView = true, formBackingObject = Proceso.class)
@RequestMapping("/proceso/**")
@SessionAttributes({"proceso"})
@Controller
public class ProcesoController {

    @RequestMapping(value = "/proceso", method = RequestMethod.POST)
    public String create(
            @Valid @ModelAttribute("proceso") Proceso proceso,
            BindingResult result,
            ModelMap modelMap,
            SessionStatus status,
            HttpServletRequest request
            ) {
        if (proceso == null) throw new IllegalArgumentException("A proceso is required");

//        if (((String)proceso.getDescripcion()).length() < 1)
//        {
//            result.addError(new FieldError(result.getObjectName(), "descripcion", "El campo descripción es obligatorio"));
//        }
//        if (((String)proceso.getDescripcion()).length() > 5000)
//        {
//            result.addError(new FieldError(result.getObjectName(), "descripcion", "El campo descripción debe tener max 5000 caracteres"));
//        }


        if (result.hasErrors()) {
            modelMap.addAttribute("proceso", proceso);
            modelMap.addAttribute("unidads", Unidad.findAllUnidads());
            return "proceso/create";
        }
        proceso.persist();
        Util.registrarEntradaEnBitacora(proceso, "Se creó el Proceso ", request.getRemoteAddr());
        status.setComplete();
        return "redirect:/proceso/" + proceso.getId();       
    }

    @RequestMapping(value = "/proceso/form", method = RequestMethod.GET)
    public String createForm(            
            ModelMap modelMap,
            HttpServletRequest hsr
            ) {
        Proceso proceso = new Proceso();        
        if (hsr.getParameter("idProceso") != null)
        {
            //Entra aqui si tiene un padre
            Proceso procesoPadre = Proceso.findProceso(new Long(hsr.getParameter("idProceso")));
            proceso.setProceso(procesoPadre);
        }        
        modelMap.addAttribute("proceso", proceso);
        modelMap.addAttribute("unidads", Unidad.findAllUnidads());
        return "proceso/create";
    }

    @RequestMapping(value = "/proceso/{id}", method = RequestMethod.GET)
    public String show(
            @PathVariable("id") Long id,
            ModelMap modelMap
            ) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        Proceso proceso = Proceso.findProceso(id);
        List<Riesgo> riesgoes = Riesgo.findRiesgoesByProceso(proceso).getResultList();
        modelMap.addAttribute("riesgoes", riesgoes);
        modelMap.addAttribute("proceso", proceso);
        modelMap.addAttribute("procesoes", Proceso.findProcesoesByProceso(proceso).getResultList());
        return "proceso/show";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String update(
            @Valid Proceso proceso,
            BindingResult result,
            ModelMap modelMap,
            SessionStatus status,
            HttpServletRequest request) {

        if (proceso == null) throw new IllegalArgumentException("A proceso is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("proceso", proceso);
            modelMap.addAttribute("unidads", Unidad.findAllUnidads());
            return "proceso/update";
        }
        proceso.merge();
        Util.registrarEntradaEnBitacora(proceso, "Se modificó el Proceso ", request.getRemoteAddr());
        status.setComplete();
        return "redirect:/proceso/" + proceso.getId();        
    }

    @RequestMapping(value = "/proceso/{id}/form", method = RequestMethod.GET)
    public String updateForm(
            @PathVariable("id") Long id,
            ModelMap modelMap
            ) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        Proceso proceso = Proceso.findProceso(id);
        modelMap.addAttribute("proceso", proceso);
        modelMap.addAttribute("unidads", Unidad.findAllUnidads());
        return "proceso/update";
    }

    @RequestMapping(value = "/proceso/{id}", method = RequestMethod.DELETE)
    public String delete(
            @PathVariable("id") Long id,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size)
    {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        Proceso proceso = Proceso.findProceso(id);        
        proceso.remove();
        return "redirect:/proceso?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());        
    }

    @RequestMapping(value = "/proceso", method = RequestMethod.GET)
    public String list(
            ModelMap modelMap
            ) {
        
        modelMap.addAttribute("procesoes", Proceso.findAllProcesoesPadres().getResultList());        
        return "proceso/list";
    }
}
