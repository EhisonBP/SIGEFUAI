package ve.co.bsc.sigai.web;

import javax.validation.Valid;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import ve.co.bsc.sigai.domain.Parametro;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import ve.co.bsc.sigai.domain.Reporte;

@RooWebScaffold(path = "parametro", automaticallyMaintainView = true, formBackingObject = Parametro.class)
@RequestMapping("/parametro/**")
@SessionAttributes({"parametro","idReporte"})
@Controller
public class ParametroController {

    @RequestMapping(value = "/parametro", method = RequestMethod.POST)
    public String create(
            @Valid Parametro parametro,
            @Valid @ModelAttribute("idReporte") long idReporte,
            BindingResult result,
            ModelMap modelMap,
            SessionStatus status) {
        if (parametro == null) throw new IllegalArgumentException("A parametro is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("parametro", parametro);
            modelMap.addAttribute("idReporte", idReporte);
            return "parametro/create";
        }
        parametro.setReporte(Reporte.findReporte(idReporte));
        parametro.persist();
        status.setComplete();
        return "redirect:/reporte/" + idReporte;
    }

    @RequestMapping(value = "/parametro/form", method = RequestMethod.GET)
    public String createForm(
            @RequestParam("idReporte") long idReporte,
            ModelMap modelMap
            )
    {
        modelMap.addAttribute("idReporte", idReporte);
        modelMap.addAttribute("parametro", new Parametro());
        return "parametro/create";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String update(@Valid Parametro parametro, BindingResult result, ModelMap modelMap) {
        if (parametro == null) throw new IllegalArgumentException("A parametro is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("parametro", parametro);
            
            return "parametro/update";
        }
        parametro.merge();
        return "redirect:/reporte/" + (parametro.getReporte()).getId();
    }

    @RequestMapping(value = "/parametro/{id}/form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("parametro", Parametro.findParametro(id));
        modelMap.addAttribute("reportes", Reporte.findAllReportes());
        return "parametro/update";
    }

    @RequestMapping(value = "/parametro/delete/{id}", method = RequestMethod.DELETE)
    public String delete(
            @PathVariable("id") Long id)
    {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        Parametro parametro = Parametro.findParametro(id);
        Reporte elReporte = parametro.getReporte();

        parametro.remove();
        return "redirect:/reporte/"+elReporte.getId();
    }
}
