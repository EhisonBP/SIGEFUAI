package ve.co.bsc.sigai.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import ve.co.bsc.sigai.domain.Unidad;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import ve.co.bsc.sigai.domain.Auditado;
import ve.co.bsc.util.Util;
import ve.co.bsc.sigai.domain.TipoUnidad;

@RooWebScaffold(path = "unidad", automaticallyMaintainView = true, formBackingObject = Unidad.class)
@RequestMapping("/unidad/**")
@Controller
public class UnidadController {

    @RequestMapping(value = "/unidad/form", method = RequestMethod.GET)
    public String createForm(
            ModelMap modelMap
            ) {

        modelMap.addAttribute("unidad", new Unidad());
        modelMap.addAttribute("tipounidads", TipoUnidad.findAllTipoUnidads());
        modelMap.addAttribute("unidads", Unidad.findAllUnidads());
        modelMap.addAttribute("auditadoes", Auditado.findAllAuditadoes());

        return "unidad/create";
    }

    @RequestMapping(value = "/unidad", method = RequestMethod.POST)
    public String create(
            @Valid Unidad unidad,
            BindingResult result,
            ModelMap modelMap,
            HttpServletRequest request) {
        if (unidad == null) throw new IllegalArgumentException("A unidad is required");

        String descripcion = unidad.getDescripcion();
        if (descripcion.length() < 40){
            result.addError(new FieldError(result.getObjectName(), "descripcion", "El campo descripción es obligatorio para crear una Dependencia Sujeta a Control"));
        }
        if (result.hasErrors()) {
            modelMap.addAttribute("unidad", unidad);
            modelMap.addAttribute("tipounidads", TipoUnidad.findAllTipoUnidads());
            modelMap.addAttribute("auditadoes", Auditado.findAllAuditadoes());
            return "unidad/create";
        }
        unidad.persist();
        Util.registrarEntradaEnBitacora(unidad, "Se creó la Dependencia Sujeta a Control", request.getRemoteAddr());
        return "redirect:/unidad";
    }

    @RequestMapping(value = "/unidad/{id}/form", method = RequestMethod.GET)
    public String updateForm(
            @PathVariable("id") Long id,
            ModelMap modelMap
            ) {
        
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("unidad", Unidad.findUnidad(id));
        modelMap.addAttribute("tipounidads", TipoUnidad.findAllTipoUnidads());
        modelMap.addAttribute("unidads", Unidad.findAllUnidads());
        modelMap.addAttribute("auditadoes", Auditado.findAllAuditadoes());
        return "unidad/update";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String update(
            @Valid Unidad unidad,
            BindingResult result,
            ModelMap modelMap,
            HttpServletRequest request
            ) {
        if (unidad == null) throw new IllegalArgumentException("A unidad is required");

        String descripcion = unidad.getDescripcion();
        if (descripcion.length() < 40){
            result.addError(new FieldError(result.getObjectName(), "descripcion", "El campo descripción es obligatorio para crear una Dependencia Sujeta a Control"));
        }
        if (result.hasErrors()) {
            modelMap.addAttribute("unidad", unidad);
            modelMap.addAttribute("tipounidads", TipoUnidad.findAllTipoUnidads());
            modelMap.addAttribute("auditadoes", Auditado.findAllAuditadoes());
            return "unidad/update";
        }
        unidad.merge();
        Util.registrarEntradaEnBitacora(unidad, "Se modificó la Dependencia Sujeta a Control", request.getRemoteAddr());
        return "redirect:/unidad";
    }

    @RequestMapping(value = "/unidad/{id}", method = RequestMethod.GET)
    public String show(
            @PathVariable("id") Long id,
            ModelMap modelMap
            ) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        Unidad unidad = Unidad.findUnidad(id);       
        modelMap.addAttribute("unidad", unidad);
        return "unidad/show";
    }

}
