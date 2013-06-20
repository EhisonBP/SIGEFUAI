package ve.co.bsc.sigai.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import ve.co.bsc.sigai.domain.EstatusRequerimiento;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMethod;
import ve.co.bsc.util.Util;

@RooWebScaffold(path = "estatusrequerimiento", automaticallyMaintainView = true, formBackingObject = EstatusRequerimiento.class)
@RequestMapping("/estatusrequerimiento/**")
@Controller
public class EstatusRequerimientoController {

    @RequestMapping(value = "/estatusrequerimiento", method = RequestMethod.POST)
    public String create(@Valid EstatusRequerimiento estatusRequerimiento, BindingResult result, ModelMap modelMap,
            HttpServletRequest request) {
        if (estatusRequerimiento == null) throw new IllegalArgumentException("A estatusRequerimiento is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("estatusRequerimiento", estatusRequerimiento);
            return "estatusrequerimiento/create";
        }
        estatusRequerimiento.persist();
        Util.registrarEntradaEnBitacora(estatusRequerimiento, "Se creó el estatus de requerimiento ", request.getRemoteAddr());
        return "redirect:/estatusrequerimiento/" + estatusRequerimiento.getId();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String update(@Valid EstatusRequerimiento estatusRequerimiento, BindingResult result, ModelMap modelMap,
            HttpServletRequest request) {
        if (estatusRequerimiento == null) throw new IllegalArgumentException("A estatusRequerimiento is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("estatusRequerimiento", estatusRequerimiento);
            return "estatusrequerimiento/update";
        }
        estatusRequerimiento.merge();
        Util.registrarEntradaEnBitacora(estatusRequerimiento, "Se modificó el estatus de requerimiento ", request.getRemoteAddr());
        return "redirect:/estatusrequerimiento/" + estatusRequerimiento.getId();
    }
}
