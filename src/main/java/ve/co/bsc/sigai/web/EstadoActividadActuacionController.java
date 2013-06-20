package ve.co.bsc.sigai.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import ve.co.bsc.sigai.domain.EstadoActividadActuacion;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMethod;
import ve.co.bsc.util.Util;

/*
 * Controlador para estado actividad actuacion (CRUD) generado por ROO.
 * Recibe todas las solicitudes a URLs con el patron
 * "/estadoactividadactuacion/**"
 */
@RooWebScaffold(path = "estadoactividadactuacion", automaticallyMaintainView = true, formBackingObject = EstadoActividadActuacion.class)
@RequestMapping("/estadoactividadactuacion/**")
@Controller
public class EstadoActividadActuacionController {

    @RequestMapping(value = "/estadoactividadactuacion", method = RequestMethod.POST)
    public String create(
            @Valid EstadoActividadActuacion estadoActividadActuacion,
            BindingResult result,
            ModelMap modelMap,
            HttpServletRequest request
            ) {
        if (estadoActividadActuacion == null) throw new IllegalArgumentException("A estadoActividadActuacion is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("estadoActividadActuacion", estadoActividadActuacion);
            return "estadoactividadactuacion/create";
        }
        estadoActividadActuacion.persist();
        Util.registrarEntradaEnBitacora(estadoActividadActuacion, "Se creó el Estatus Actividad(Actuación) ", request.getRemoteAddr());
        return "redirect:/estadoactividadactuacion" ;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String update(
            @Valid EstadoActividadActuacion estadoActividadActuacion,
            BindingResult result,
            ModelMap modelMap,
            HttpServletRequest request) {
        if (estadoActividadActuacion == null) throw new IllegalArgumentException("A estadoActividadActuacion is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("estadoActividadActuacion", estadoActividadActuacion);
            return "estadoactividadactuacion/update";
        }
        estadoActividadActuacion.merge();
        Util.registrarEntradaEnBitacora(estadoActividadActuacion, "Se modificó el Estatus Actividad(Actuación) ", request.getRemoteAddr());
        return "redirect:/estadoactividadactuacion";
    }
}
