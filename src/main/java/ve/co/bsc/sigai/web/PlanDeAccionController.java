package ve.co.bsc.sigai.web;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import ve.co.bsc.sigai.domain.PlanDeAccion;
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
import ve.co.bsc.sigai.domain.EstadoPlanDeAccion;
import ve.co.bsc.sigai.domain.Observacion;
import ve.co.bsc.sigai.domain.PapelDeTrabajo;
import ve.co.bsc.sigai.domain.Prueba;
import ve.co.bsc.sigai.domain.Unidad;
import ve.co.bsc.util.Util;

@RooWebScaffold(path = "plandeaccion", automaticallyMaintainView = true, formBackingObject = PlanDeAccion.class)
@RequestMapping("/plandeaccion/**")
@SessionAttributes({"planDeAccion", "idObservacion", "idPapelDeTrabajo"})
@Controller
public class PlanDeAccionController {

    Logger logger = Logger.getLogger(PlanDeAccionController.class);

    @RequestMapping(value = "/plandeaccion", method = RequestMethod.POST)
    public String create(
            @Valid @ModelAttribute("planDeAccion") PlanDeAccion planDeAccion,
            @Valid @ModelAttribute("idObservacion") Long idObservacion,
            @Valid @ModelAttribute("idPapelDeTrabajo") Long idPapelDeTrabajo,
            BindingResult result,
            ModelMap modelMap,
            SessionStatus status,
            HttpServletRequest request
            ) {
        logger.debug("Entro al create baby!!");
        if (planDeAccion == null) throw new IllegalArgumentException("A planDeAccion is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("idObservacion", idObservacion);
            modelMap.addAttribute("planDeAccion", planDeAccion);
            modelMap.addAttribute("estadoplandeaccions", EstadoPlanDeAccion.findAllEstadoPlanDeAccions());            
            modelMap.addAttribute("unidads", Unidad.findAllUnidads());
            modelMap.addAttribute("planDeAccion_fechaCierre_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
            modelMap.addAttribute("planDeAccion_fechaCierre2_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
            modelMap.addAttribute("planDeAccion_fechaSeguimiento_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
            return "plandeaccion/create";
        }
        //logger.debug("el idObservacion es " + idObservacion);
        if (idObservacion!=0){
            Observacion laObservacion = Observacion.findObservacion(idObservacion);
            planDeAccion.setObservacion(laObservacion);
        }
        planDeAccion.persist();
        Util.registrarEntradaEnBitacora(planDeAccion, "Se creó el Plan de Acción ", request.getRemoteAddr());
        status.setComplete();
        //return "redirect:/plandeaccion/" + planDeAccion.getId();
        if (idObservacion!=0){
            //Regreso al hallazgo
            return "redirect:/observacion/" + planDeAccion.getObservacion().getId();
        }
        else
        {
            //Regreso al resumen de acciones correctivas
            return "redirect:/papeldetrabajo/" + idPapelDeTrabajo;
        }
        
    }

    @RequestMapping(value = "/plandeaccion/form", method = RequestMethod.GET)
    public String createForm(
            HttpServletRequest hsr,
            ModelMap modelMap
            ) {
        PlanDeAccion miPlan = new PlanDeAccion();
        //Verificamos si la accion correctiva se esta agregando desde un hallazgo o no
        //Si es desde un hallazgo, seteamos de una vez
        logger.debug("hsr" + hsr.getParameter("idObservacion"));
        if (hsr.getParameter("idObservacion")!=null)
        {
            modelMap.addAttribute("idPapelDeTrabajo",Long.parseLong("0"));
            modelMap.addAttribute("idObservacion",Long.parseLong(hsr.getParameter("idObservacion")));
            Observacion laObservacion = Observacion.findObservacion(Long.parseLong(hsr.getParameter("idObservacion")));
            miPlan.setObservacion(laObservacion);
        }
        //Sino, permitimos al usuario seleccionar el hallazgo QUE SEA DE LA ACTUACION Y QUE NO TENGA ASIGNADO NINGUNA ACCION CORRECTIVA  al cual va ir asociado el plan de accion
        else
        {
            Long idPapelDeTrabajo = Long.parseLong(hsr.getParameter("idPapelDeTrabajo"));
            
            PapelDeTrabajo elPapelDeTrabajo = PapelDeTrabajo.findPapelDeTrabajo(idPapelDeTrabajo);
            Long idActuacion = elPapelDeTrabajo.getCodigoActuacion().getId();
            
            //Buscamos todas las pruebas que pertenecen a la actuacion
            List<Prueba> pruebasDeLaActuacion = Prueba.findPruebasByCodigoActuacion(elPapelDeTrabajo.getCodigoActuacion()).getResultList();
            logger.debug("pruebas de la actuacion " + pruebasDeLaActuacion );
            //De cada prueba, obtenemos los hallazgos
            List<Observacion> observacionesSinPlan = new ArrayList();
            for (int i=0; i<pruebasDeLaActuacion.size(); i++)
            {
                //Llamo al query que recibe una prueba y retorna las observaciones de ella que no estan en plan de accion
                Query queryObservaciones = Observacion.findObservacionesByPruebaSinPlanDeAccion(pruebasDeLaActuacion.get(i));
                List <Observacion> observaciones = queryObservaciones.getResultList();
                logger.debug("prueba " + pruebasDeLaActuacion.get(i).getId() + "observaciones sin plan " + observaciones);
                observacionesSinPlan.addAll(observaciones);
            }

            logger.debug("observaciones sin plan " +observacionesSinPlan);
            modelMap.addAttribute("observacions", observacionesSinPlan);
            modelMap.addAttribute("idPapelDeTrabajo",idPapelDeTrabajo);
            modelMap.addAttribute("idObservacion",Long.parseLong("0"));
        }
        modelMap.addAttribute("planDeAccion", miPlan);
        modelMap.addAttribute("estadoplandeaccions", EstadoPlanDeAccion.findAllEstadoPlanDeAccions());        
        modelMap.addAttribute("unidads", Unidad.findAllUnidads());
        modelMap.addAttribute("planDeAccion_fechaCierre_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("planDeAccion_fechaCierre2_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("planDeAccion_fechaSeguimiento_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        return "plandeaccion/create";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String update(
            @Valid @ModelAttribute("planDeAccion") PlanDeAccion planDeAccion,
            BindingResult result,
            ModelMap modelMap,
            SessionStatus status,
            HttpServletRequest request
            ) {
        if (planDeAccion == null) throw new IllegalArgumentException("A planDeAccion is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("planDeAccion", planDeAccion);
            modelMap.addAttribute("estadoplandeaccions", EstadoPlanDeAccion.findAllEstadoPlanDeAccions());            
            modelMap.addAttribute("unidads", Unidad.findAllUnidads());
            modelMap.addAttribute("planDeAccion_fechaCierre_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
            modelMap.addAttribute("planDeAccion_fechaCierre2_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
            modelMap.addAttribute("planDeAccion_fechaSeguimiento_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
            return "plandeaccion/update";
        }
        planDeAccion.merge();
        Util.registrarEntradaEnBitacora(planDeAccion, "Se modificó el Plan de Acción ", request.getRemoteAddr());
        status.setComplete();
        //return "redirect:/plandeaccion/" + planDeAccion.getId();
        return "redirect:/observacion/" + planDeAccion.getObservacion().getId();
    }

    @RequestMapping(value = "/plandeaccion/{id}/form", method = RequestMethod.GET)
    public String updateForm(
            @PathVariable("id") Long id,
            ModelMap modelMap
            ) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("planDeAccion", PlanDeAccion.findPlanDeAccion(id));
        modelMap.addAttribute("estadoplandeaccions", EstadoPlanDeAccion.findAllEstadoPlanDeAccions());        
        modelMap.addAttribute("unidads", Unidad.findAllUnidads());
        modelMap.addAttribute("planDeAccion_fechaCierre_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("planDeAccion_fechaCierre2_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("planDeAccion_fechaSeguimiento_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        return "plandeaccion/update";
    }

    @RequestMapping(value = "/plandeaccion/{id}", method = RequestMethod.DELETE)
    public String delete(
            @PathVariable("id") Long id,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size
            ) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        PlanDeAccion planDeAccion = PlanDeAccion.findPlanDeAccion(id);
        String urlReturn = "/observacion/" + planDeAccion.getObservacion().getId();
        planDeAccion.setUnidad(null);
        planDeAccion.remove();

        return "redirect:" + urlReturn;
        //return "redirect:/plandeaccion?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }

    @RequestMapping(value = "/plandeaccion/{id}", method = RequestMethod.GET)
    public String show(
            @PathVariable("id") Long id,
            ModelMap modelMap
            ) {

        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        
        PlanDeAccion planDeAccion = PlanDeAccion.findPlanDeAccion(id);
        modelMap.addAttribute("planDeAccion_fechaCierre2_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("planDeAccion_fechaCierre_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("planDeAccion_fechaSeguimiento_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", org.springframework.context.i18n.LocaleContextHolder.getLocale()));
        modelMap.addAttribute("planDeAccion", planDeAccion);
        modelMap.addAttribute("objetoComentable", planDeAccion);

        return "plandeaccion/show";
    }
}
