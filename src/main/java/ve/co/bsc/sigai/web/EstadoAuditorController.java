package ve.co.bsc.sigai.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import ve.co.bsc.sigai.domain.EstadoAuditor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMethod;
import ve.co.bsc.util.Util;

@RooWebScaffold(path = "estadoauditor", automaticallyMaintainView = true, formBackingObject = EstadoAuditor.class)
@RequestMapping("/estadoauditor/**")
@Controller
public class EstadoAuditorController {

    @RequestMapping(value = "/estadoauditor", method = RequestMethod.POST)
    public String create(
            @Valid EstadoAuditor estadoAuditor,
            BindingResult result,
            ModelMap modelMap,
            HttpServletRequest request
            ) {
        if (estadoAuditor == null) throw new IllegalArgumentException("A estadoAuditor is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("estadoAuditor", estadoAuditor);
            return "estadoauditor/create";
        }
        estadoAuditor.persist();
        Util.registrarEntradaEnBitacora(estadoAuditor, "Se creó el Estatus de Auditor ", request.getRemoteAddr());
        return "redirect:/estadoauditor";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String update(
            @Valid EstadoAuditor estadoAuditor,
            BindingResult result,
            ModelMap modelMap,
            HttpServletRequest request
            ) {
        if (estadoAuditor == null) throw new IllegalArgumentException("A estadoAuditor is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("estadoAuditor", estadoAuditor);
            return "estadoauditor/update";
        }
        estadoAuditor.merge();
        Util.registrarEntradaEnBitacora(estadoAuditor, "Se modificó el Estatus de Auditor ", request.getRemoteAddr());
        return "redirect:/estadoauditor";
    }
}
