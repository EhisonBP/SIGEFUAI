package ve.co.bsc.sigai.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import ve.co.bsc.sigai.domain.TipoUnidad;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMethod;
import ve.co.bsc.util.Util;

@RooWebScaffold(path = "tipounidad", automaticallyMaintainView = true, formBackingObject = TipoUnidad.class)
@RequestMapping("/tipounidad/**")
@Controller
public class TipoUnidadController {

    @RequestMapping(value = "/tipounidad", method = RequestMethod.POST)
    public String create(
            @Valid TipoUnidad tipoUnidad,
            BindingResult result,
            ModelMap modelMap,
            HttpServletRequest request
            ) {
        if (tipoUnidad == null) throw new IllegalArgumentException("A tipoUnidad is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("tipoUnidad", tipoUnidad);
            return "tipounidad/create";
        }
        tipoUnidad.persist();
        Util.registrarEntradaEnBitacora(tipoUnidad, "Se creó el tipo de dependencia sujeta a control ", request.getRemoteAddr());
        return "redirect:/tipounidad";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String update(
            @Valid TipoUnidad tipoUnidad,
            BindingResult result,
            ModelMap modelMap,
            HttpServletRequest request
            ) {

        if (tipoUnidad == null) throw new IllegalArgumentException("A tipoUnidad is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("tipoUnidad", tipoUnidad);
            return "tipounidad/update";
        }
        tipoUnidad.merge();
        Util.registrarEntradaEnBitacora(tipoUnidad, "Se modificó el tipo de dependencia sujeta a control ", request.getRemoteAddr());
        return "redirect:/tipounidad";
    }

}
