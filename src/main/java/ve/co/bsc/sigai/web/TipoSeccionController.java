package ve.co.bsc.sigai.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import ve.co.bsc.sigai.domain.TipoSeccion;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMethod;
import ve.co.bsc.util.Util;

@RooWebScaffold(path = "tiposeccion", automaticallyMaintainView = true, formBackingObject = TipoSeccion.class)
@RequestMapping("/tiposeccion/**")
@Controller
public class TipoSeccionController {

    @RequestMapping(value = "/tiposeccion", method = RequestMethod.POST)
    public String create(@Valid TipoSeccion tipoSeccion, BindingResult result, ModelMap modelMap,
            HttpServletRequest request) {
        if (tipoSeccion == null) throw new IllegalArgumentException("A tipoSeccion is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("tipoSeccion", tipoSeccion);
            return "tiposeccion/create";
        }
        tipoSeccion.persist();
        Util.registrarEntradaEnBitacora(tipoSeccion, "Se creó el tipo de sección ", request.getRemoteAddr());
        return "redirect:/tiposeccion/" + tipoSeccion.getId();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String update(@Valid TipoSeccion tipoSeccion, BindingResult result, ModelMap modelMap,
            HttpServletRequest request) {
        if (tipoSeccion == null) throw new IllegalArgumentException("A tipoSeccion is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("tipoSeccion", tipoSeccion);
            return "tiposeccion/update";
        }
        tipoSeccion.merge();
        Util.registrarEntradaEnBitacora(tipoSeccion, "Se modificó el tipo de sección ", request.getRemoteAddr());
        return "redirect:/tiposeccion/" + tipoSeccion.getId();
    }
}
