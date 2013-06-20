package ve.co.bsc.sigai.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import ve.co.bsc.sigai.domain.UnidadDeMedida;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMethod;
import ve.co.bsc.util.Util;

@RooWebScaffold(path = "unidaddemedida", automaticallyMaintainView = true, formBackingObject = UnidadDeMedida.class)
@RequestMapping("/unidaddemedida/**")
@Controller
public class UnidadDeMedidaController {
    @RequestMapping(value = "/unidaddemedida", method = RequestMethod.POST)
    public String create(
            @Valid UnidadDeMedida unidadDeMedida,
            BindingResult result,
            ModelMap modelMap,
            HttpServletRequest request
            ) {
        
        if (unidadDeMedida == null) throw new IllegalArgumentException("A unidadDeMedida is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("unidadDeMedida", unidadDeMedida);
            return "unidaddemedida/create";
        }
        unidadDeMedida.persist();
        Util.registrarEntradaEnBitacora(unidadDeMedida, "Se creo la unidad de medida", request.getRemoteAddr());
        return "redirect:/unidaddemedida";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String update(
            @Valid UnidadDeMedida unidadDeMedida,
            BindingResult result,
            ModelMap modelMap,
            HttpServletRequest request
            ) {

        if (unidadDeMedida == null) throw new IllegalArgumentException("A unidadDeMedida is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("unidadDeMedida", unidadDeMedida);
            return "unidaddemedida/update";
        }
        unidadDeMedida.merge();
        Util.registrarEntradaEnBitacora(unidadDeMedida, "Se modificó la unidad de medida ", request.getRemoteAddr());
        return "redirect:/unidaddemedida";
    }
}
