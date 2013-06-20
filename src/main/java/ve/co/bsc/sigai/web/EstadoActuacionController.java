package ve.co.bsc.sigai.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import ve.co.bsc.sigai.domain.EstadoActuacion;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMethod;
import ve.co.bsc.util.Util;

/*
 * Controlador para estado actuacion (CRUD) generado por ROO.
 * Recibe todas las solicitudes a URLs con el patron
 * "/estadoactuacion/**"
 */
@RooWebScaffold(path = "estadoactuacion", automaticallyMaintainView = true, formBackingObject = EstadoActuacion.class)
@RequestMapping("/estadoactuacion/**")
@Controller
public class EstadoActuacionController {

    @RequestMapping(value = "/estadoactuacion", method = RequestMethod.POST)
    public String create(
            @Valid EstadoActuacion estadoActuacion,
            BindingResult result,
            ModelMap modelMap,
            HttpServletRequest request
            ) {
        if (estadoActuacion == null) throw new IllegalArgumentException("A estadoActuacion is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("estadoActuacion", estadoActuacion);
            return "estadoactuacion/create";
        }
        estadoActuacion.persist();
        Util.registrarEntradaEnBitacora(estadoActuacion, "Se creó el Estatus Actuación ", request.getRemoteAddr());
        return "redirect:/estadoactuacion" ;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String update(
            @Valid EstadoActuacion estadoActuacion,
            BindingResult result,
            ModelMap modelMap,
            HttpServletRequest request) {
        if (estadoActuacion == null) throw new IllegalArgumentException("A estadoActuacion is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("estadoActuacion", estadoActuacion);
            return "estadoactuacion/update";
        }
        estadoActuacion.merge();
        Util.registrarEntradaEnBitacora(estadoActuacion, "Se modificó el Estatus Actuación ", request.getRemoteAddr());
        return "redirect:/estadoactuacion" ;
    }
}
