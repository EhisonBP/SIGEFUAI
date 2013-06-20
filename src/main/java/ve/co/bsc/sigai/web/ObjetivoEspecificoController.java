package ve.co.bsc.sigai.web;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import javax.validation.Valid;

import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import ve.co.bsc.sigai.domain.ObjetivoEspecifico;
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
import ve.co.bsc.sigai.domain.Actuacion;
import ve.co.bsc.util.Util;

@RooWebScaffold(path = "objetivoespecifico", automaticallyMaintainView = true, formBackingObject = ObjetivoEspecifico.class)
@RequestMapping("/objetivoespecifico/**")
@SessionAttributes({"objetivoEspecifico"})
@Controller
public class ObjetivoEspecificoController {

    @RequestMapping(value = "/objetivoespecifico", method = RequestMethod.POST)
    public String create(
            @Valid @ModelAttribute("objetivoEspecifico") ObjetivoEspecifico objetivoEspecifico,
            BindingResult result,
            ModelMap modelMap,
            SessionStatus status,
            HttpServletRequest request
            ) {
        if (objetivoEspecifico == null) throw new IllegalArgumentException("A objetivoEspecifico is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("objetivoEspecifico", objetivoEspecifico);
            modelMap.addAttribute("actuacions", Actuacion.findAllActuacions());
            return "objetivoespecifico/create";
        }
        
        objetivoEspecifico.persist();
        Util.registrarEntradaEnBitacora(objetivoEspecifico, "Se creó el Objetivo Específico ", request.getRemoteAddr());
        status.setComplete();
        //return "redirect:/objetivoespecifico/" + objetivoEspecifico.getId();
        return "redirect:/actuacion/" + objetivoEspecifico.getActuacion().getId();
    }

    @RequestMapping(value = "/objetivoespecifico/form", method = RequestMethod.GET)
    public String createForm(
            @RequestParam("idActuacion") long idActuacion,
            ModelMap modelMap
            ) {
        ObjetivoEspecifico miObjetivo = new ObjetivoEspecifico();
        Actuacion laActuacion = Actuacion.findActuacion(idActuacion);
        miObjetivo.setActuacion(laActuacion);


        Query queryObjetivos = ObjetivoEspecifico.findObjetivoEspecificoesByActuacion(laActuacion);
        List<ObjetivoEspecifico> objetivos = queryObjetivos.getResultList();       

        int mayor = 0;

        for (int i=0; i<objetivos.size(); i++)
        {
            ObjetivoEspecifico elObjetivo = (ObjetivoEspecifico) objetivos.get(i);

            if (elObjetivo.getReferencia()>mayor)
            {
                mayor = elObjetivo.getReferencia();
            }
        }

        miObjetivo.setReferencia(mayor+1);
        modelMap.addAttribute("objetivoEspecifico", miObjetivo);
        modelMap.addAttribute("actuacion", laActuacion);
        return "objetivoespecifico/create";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String update(
            @Valid @ModelAttribute("objetivoEspecifico") ObjetivoEspecifico objetivoEspecifico,
            BindingResult result,
            ModelMap modelMap,
            SessionStatus status,
            HttpServletRequest request
            ) {

        if (objetivoEspecifico == null) throw new IllegalArgumentException("A objetivoEspecifico is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("objetivoEspecifico", objetivoEspecifico);
            modelMap.addAttribute("actuacions", Actuacion.findAllActuacions());
            return "objetivoespecifico/update";
        }
        
        objetivoEspecifico.merge();
        Util.registrarEntradaEnBitacora(objetivoEspecifico, "Se modificó el Objetivo Específico ", request.getRemoteAddr());
        return "redirect:/actuacion/" + objetivoEspecifico.getActuacion().getId();
        //return "redirect:/objetivoespecifico/" + objetivoEspecifico.getId();
    }

    @RequestMapping(value = "/objetivoespecifico/{id}/form", method = RequestMethod.GET)
    public String updateForm(
            @PathVariable("id") Long id,
            ModelMap modelMap
            ) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");

        ObjetivoEspecifico miObjetivo = ObjetivoEspecifico.findObjetivoEspecifico(id);
        
        modelMap.addAttribute("objetivoEspecifico", miObjetivo);
        modelMap.addAttribute("actuacion", miObjetivo.getActuacion());
        return "objetivoespecifico/update";
    }

    @RequestMapping(value = "/objetivoespecifico/{id}", method = RequestMethod.GET)
    public String show(
            @PathVariable("id") Long id,
            ModelMap modelMap
            ) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("objetivoEspecifico", ObjetivoEspecifico.findObjetivoEspecifico(id));
        return "objetivoespecifico/show";
    }

    @RequestMapping(value = "/objetivoespecifico/{id}", method = RequestMethod.DELETE)
    public String delete(
            @PathVariable("id") Long id,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size
            ) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        ObjetivoEspecifico miObjetivo = ObjetivoEspecifico.findObjetivoEspecifico(id);
        String url = "redirect:/actuacion/" + miObjetivo.getActuacion().getId();
        miObjetivo.remove();
        return url;
        //return "redirect:/objetivoespecifico?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }

}
