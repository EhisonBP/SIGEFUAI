package ve.co.bsc.sigai.web;

import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import ve.co.bsc.sigai.domain.Respuesta;
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
import ve.co.bsc.sigai.domain.ArchivoAdjunto;
import ve.co.bsc.sigai.domain.Requerimiento;
import ve.co.bsc.util.Util;

@RooWebScaffold(path = "respuesta", automaticallyMaintainView = true, formBackingObject = Respuesta.class)
@RequestMapping("/respuesta/**")
@SessionAttributes({"respuesta"})
@Controller
public class RespuestaController {
    
    @RequestMapping(value = "/respuesta", method = RequestMethod.POST)
    public String create(
            @Valid @ModelAttribute("respuesta") Respuesta respuesta,
            BindingResult result,
            ModelMap modelMap,
            SessionStatus status,
            HttpServletRequest request
            ) {
        if (respuesta == null) throw new IllegalArgumentException("A respuesta is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("respuesta", respuesta);
            modelMap.addAttribute("requerimientoes", Requerimiento.findAllRequerimientoes());
            modelMap.addAttribute("respuesta_fecha_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
            return "respuesta/create";
        }
        respuesta.persist();
        Util.registrarEntradaEnBitacora(respuesta, "Se creó la Respuesta a Requerimiento", request.getRemoteAddr());
        status.setComplete();
        
        return "redirect:/requerimiento/" + respuesta.getRequerimiento().getId();
        //return "redirect:/respuesta/" + respuesta.getId();
    }

    @RequestMapping(value = "/respuesta/form", method = RequestMethod.GET)
    public String createForm(
            @RequestParam("idRequerimiento") long idRequerimiento,
            ModelMap modelMap
            ) {
        Date fechaDeHoy = new Date();
        Requerimiento miRequerimiento = Requerimiento.findRequerimiento(idRequerimiento);
        Respuesta miRespuesta = new Respuesta();
        miRespuesta.setRequerimiento(miRequerimiento);
        miRespuesta.setFecha(fechaDeHoy);

        modelMap.addAttribute("respuesta", miRespuesta);
        modelMap.addAttribute("requerimientoes", Requerimiento.findAllRequerimientoes());
        modelMap.addAttribute("respuesta_fecha_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        return "respuesta/create";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String update(
            @Valid @ModelAttribute("respuesta") Respuesta respuesta,
            BindingResult result,
            ModelMap modelMap,
            SessionStatus status,
            HttpServletRequest request
            ) {
        if (respuesta == null) throw new IllegalArgumentException("A respuesta is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("respuesta", respuesta);
            modelMap.addAttribute("requerimientoes", Requerimiento.findAllRequerimientoes());
            modelMap.addAttribute("respuesta_fecha_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
            return "respuesta/update";
        }
        respuesta.merge();
        Util.registrarEntradaEnBitacora(respuesta, "Se modificó la Respuesta a Requerimiento", request.getRemoteAddr());
        status.setComplete();

        return "redirect:/requerimiento/" + respuesta.getRequerimiento().getId();
        //return "redirect:/respuesta/" + respuesta.getId();
    }

    @RequestMapping(value = "/respuesta/{id}/form", method = RequestMethod.GET)
    public String updateForm(
            @PathVariable("id") Long id,
            ModelMap modelMap
            ) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("respuesta", Respuesta.findRespuesta(id));
        modelMap.addAttribute("requerimientoes", Requerimiento.findAllRequerimientoes());
        modelMap.addAttribute("respuesta_fecha_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        return "respuesta/update";
    }

    @RequestMapping(value = "/respuesta/{id}", method = RequestMethod.GET)
    public String show(
            @PathVariable("id") Long id,
            ModelMap modelMap
            )
    {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        Respuesta laRespuesta = Respuesta.findRespuesta(id);
        Query queryArchivosAdjuntosByRespuesta = ArchivoAdjunto.findArchivoAdjuntoesByRespuesta(laRespuesta);
        List<ArchivoAdjunto> archivosAdjuntosByRespuesta = queryArchivosAdjuntosByRespuesta.getResultList();
        modelMap.addAttribute("archivosAdjuntosByRespuesta", archivosAdjuntosByRespuesta);
        modelMap.addAttribute("respuesta_fecha_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("respuesta", Respuesta.findRespuesta(id));
        return "respuesta/show";
    }

    @RequestMapping(value = "/respuesta/{id}", method = RequestMethod.DELETE)
    public String delete(
            @PathVariable("id") Long id,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size
            ) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        
        Respuesta respuesta = Respuesta.findRespuesta(id);
        long idRequerimiento = respuesta.getRequerimiento().getId();
        respuesta.remove();
        
        return "redirect:/requerimiento/" + idRequerimiento;
        //return "redirect:/respuesta?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
}
