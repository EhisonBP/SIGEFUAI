package ve.co.bsc.sigai.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import ve.co.bsc.sigai.domain.EstadoPlanDeAccion;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMethod;
import ve.co.bsc.util.Util;

@RooWebScaffold(path = "estadoplandeaccion", automaticallyMaintainView = true, formBackingObject = EstadoPlanDeAccion.class)
@RequestMapping("/estadoplandeaccion/**")
@Controller
public class EstadoPlanDeAccionController {

    @RequestMapping(value = "/estadoplandeaccion", method = RequestMethod.POST)
    public String create(
            @Valid EstadoPlanDeAccion estadoPlanDeAccion,
            BindingResult result,
            ModelMap modelMap,
            HttpServletRequest request
            ) {
        if (estadoPlanDeAccion == null) throw new IllegalArgumentException("A estadoPlanDeAccion is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("estadoPlanDeAccion", estadoPlanDeAccion);
            return "estadoplandeaccion/create";
        }
        estadoPlanDeAccion.persist();
        Util.registrarEntradaEnBitacora(estadoPlanDeAccion, "Se creó el Estatus de Plan de Acción ", request.getRemoteAddr());
        return "redirect:/estadoplandeaccion";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String update(
            @Valid EstadoPlanDeAccion estadoPlanDeAccion,
            BindingResult result,
            ModelMap modelMap,
            HttpServletRequest request
            ) {
        if (estadoPlanDeAccion == null) throw new IllegalArgumentException("A estadoPlanDeAccion is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("estadoPlanDeAccion", estadoPlanDeAccion);
            return "estadoplandeaccion/update";
        }
        estadoPlanDeAccion.merge();
        Util.registrarEntradaEnBitacora(estadoPlanDeAccion, "Se modificó el Estatus de Plan de Acción ", request.getRemoteAddr());
        return "redirect:/estadoplandeaccion";
    }
}
