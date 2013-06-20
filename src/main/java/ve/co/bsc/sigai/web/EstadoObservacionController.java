package ve.co.bsc.sigai.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import ve.co.bsc.sigai.domain.EstadoObservacion;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMethod;
import ve.co.bsc.util.Util;

@RooWebScaffold(path = "estadoobservacion", automaticallyMaintainView = true, formBackingObject = EstadoObservacion.class)
@RequestMapping("/estadoobservacion/**")
@Controller
public class EstadoObservacionController {

    @RequestMapping(value = "/estadoobservacion", method = RequestMethod.POST)
    public String create(
            @Valid EstadoObservacion estadoObservacion,
            BindingResult result,
            ModelMap modelMap,
            HttpServletRequest request) {
        if (estadoObservacion == null) throw new IllegalArgumentException("A estadoObservacion is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("estadoObservacion", estadoObservacion);
            return "estadoobservacion/create";
        }
        estadoObservacion.persist();
        Util.registrarEntradaEnBitacora(estadoObservacion, "Se creó el Estatus de Hallazgo ", request.getRemoteAddr());
        return "redirect:/estadoobservacion" ;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String update(
            @Valid EstadoObservacion estadoObservacion,
            BindingResult result,
            ModelMap modelMap,
            HttpServletRequest request) {
        if (estadoObservacion == null) throw new IllegalArgumentException("A estadoObservacion is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("estadoObservacion", estadoObservacion);
            return "estadoobservacion/update";
        }
        estadoObservacion.merge();
        Util.registrarEntradaEnBitacora(estadoObservacion, "Se modificó el Estatus de Hallazgo ", request.getRemoteAddr());
        return "redirect:/estadoobservacion" ;
    }
}
