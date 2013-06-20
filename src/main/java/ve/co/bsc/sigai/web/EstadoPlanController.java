package ve.co.bsc.sigai.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import ve.co.bsc.sigai.domain.EstadoPlan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMethod;
import ve.co.bsc.util.Util;

/*
 * Controlador para estado plan (CRUD) generado por ROO.
 * Recibe todas las solicitudes a URLs con el patron
 * "/estadoplan/**"
 */
@RooWebScaffold(path = "estadoplan", automaticallyMaintainView = true, formBackingObject = EstadoPlan.class)
@RequestMapping("/estadoplan/**")
@Controller
public class EstadoPlanController {


    @RequestMapping(value = "/estadoplan", method = RequestMethod.POST)
    public String create(
            @Valid EstadoPlan estadoPlan,
            BindingResult result,
            ModelMap modelMap,
            HttpServletRequest request) {
        if (estadoPlan == null) throw new IllegalArgumentException("A estadoPlan is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("estadoPlan", estadoPlan);
            return "estadoplan/create";
        }
        estadoPlan.persist();
        Util.registrarEntradaEnBitacora(estadoPlan, "Se creó el Estatus de Plan Anual ", request.getRemoteAddr());
        return "redirect:/estadoplan";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String update(
            @Valid EstadoPlan estadoPlan,
            BindingResult result,
            ModelMap modelMap,
            HttpServletRequest request) {
        if (estadoPlan == null) throw new IllegalArgumentException("A estadoPlan is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("estadoPlan", estadoPlan);
            return "estadoplan/update";
        }
        estadoPlan.merge();
        Util.registrarEntradaEnBitacora(estadoPlan, "Se modificó el Estatus de Plan Anual ", request.getRemoteAddr());
        return "redirect:/estadoplan" ;
    }
}
