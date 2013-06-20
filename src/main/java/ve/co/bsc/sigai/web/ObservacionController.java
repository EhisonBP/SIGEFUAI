package ve.co.bsc.sigai.web;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ve.co.bsc.sigai.domain.Observacion;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import ve.co.bsc.sigai.domain.Alegato;
import ve.co.bsc.sigai.domain.ArchivoAdjunto;
import ve.co.bsc.sigai.domain.EstadoObservacion;
import ve.co.bsc.sigai.domain.PlanDeAccion;
import ve.co.bsc.sigai.domain.Prueba;
import ve.co.bsc.sigai.domain.Recomendacion;
import ve.co.bsc.sigai.service.JbpmService;
import ve.co.bsc.util.Util;

@RooWebScaffold(path = "observacion", automaticallyMaintainView = true, formBackingObject = Observacion.class)
@RequestMapping("/observacion/**")
@SessionAttributes({"observacion"})
@Controller
public class ObservacionController {

    Logger logger = Logger.getLogger(ObservacionController.class);

    @Autowired
    private JbpmService jbpmService;

    @RequestMapping(value = "/observacion/redaccion", method = RequestMethod.POST )
    public String saveRedaccion(
            @Valid @ModelAttribute("observacion") Observacion observacion,
            BindingResult result,
            ModelMap modelMap,
            SessionStatus status,
            HttpServletRequest request
            )
    {
        if (observacion == null) throw new IllegalArgumentException("A observacion is required");
               
        if (result.hasErrors()) {            
                      
            Query queryRecomendaciones = Recomendacion.findRecomendacionsByObservacion(observacion);
            List<Recomendacion> recomendaciones = queryRecomendaciones.getResultList();

            Query queryAlegatos = Alegato.findAlegatoesByObservacion(observacion);
            List<Alegato> alegatos = queryAlegatos.getResultList();

            Query queryArchivosAdjuntosByHallazgo = ArchivoAdjunto.findArchivoAdjuntoesByHallazgo(observacion);
            List<ArchivoAdjunto> archivosAdjuntosByHallazgo = queryArchivosAdjuntosByHallazgo.getResultList();

            Query queryPlanDeAccion = PlanDeAccion.findPlanDeAccionsByObservacion(observacion);
            List<PlanDeAccion> planesDeAccion = queryPlanDeAccion.getResultList();

            modelMap.addAttribute("estadoobservacions", EstadoObservacion.findAllEstadoObservacions());
            modelMap.addAttribute("archivosAdjuntosByHallazgo", archivosAdjuntosByHallazgo);
            modelMap.addAttribute("alegatos", alegatos);
            modelMap.addAttribute("recomendaciones", recomendaciones);
            modelMap.addAttribute("observacion", observacion);
            modelMap.addAttribute("planesDeAccion", planesDeAccion);

            modelMap.addAttribute("objetoComentable", observacion);

            logger.debug("solicitando revision de observacion en motor de reglas");
            jbpmService.executeRulesStateless(observacion);
            logger.debug("revision de observacion en motor de reglas listo");
            
            return "observacion/show";
        }

        observacion.merge();
        Util.registrarEntradaEnBitacora(observacion, "Se modificó la redacción del hallazgo", request.getRemoteAddr());

        status.setComplete();

        return "redirect:/observacion/"+observacion.getId();
    }

    @RequestMapping(value = "/observacion/conclusion", method = RequestMethod.POST )
    public String saveConclusion(
            @Valid @ModelAttribute("observacion") Observacion observacion,
            BindingResult result,
            ModelMap modelMap,
            SessionStatus status,
            HttpServletRequest request
            )
    {
        if (observacion == null) throw new IllegalArgumentException("A observacion is required");
        if (result.hasErrors()) {
            
            Query queryRecomendaciones = Recomendacion.findRecomendacionsByObservacion(observacion);
            List<Recomendacion> recomendaciones = queryRecomendaciones.getResultList();

            Query queryAlegatos = Alegato.findAlegatoesByObservacion(observacion);
            List<Alegato> alegatos = queryAlegatos.getResultList();

            Query queryArchivosAdjuntosByHallazgo = ArchivoAdjunto.findArchivoAdjuntoesByHallazgo(observacion);
            List<ArchivoAdjunto> archivosAdjuntosByHallazgo = queryArchivosAdjuntosByHallazgo.getResultList();

            Query queryPlanDeAccion = PlanDeAccion.findPlanDeAccionsByObservacion(observacion);
            List<PlanDeAccion> planesDeAccion = queryPlanDeAccion.getResultList();

            modelMap.addAttribute("estadoobservacions", EstadoObservacion.findAllEstadoObservacions());
            modelMap.addAttribute("archivosAdjuntosByHallazgo", archivosAdjuntosByHallazgo);
            modelMap.addAttribute("alegatos", alegatos);
            modelMap.addAttribute("recomendaciones", recomendaciones);
            modelMap.addAttribute("observacion", observacion);
            modelMap.addAttribute("planesDeAccion", planesDeAccion);

            modelMap.addAttribute("objetoComentable", observacion);

            logger.debug("solicitando revision de observacion en motor de reglas");
            jbpmService.executeRulesStateless(observacion);
            logger.debug("revision de observacion en motor de reglas listo");
            
            return "observacion/show";
        }

        observacion.merge();
        Util.registrarEntradaEnBitacora(observacion, "Se modificó la conclusión del hallazgo ", request.getRemoteAddr());

        status.setComplete();

        return "redirect:/observacion/"+observacion.getId();
    }
    
    @RequestMapping(value = "/observacion", method = RequestMethod.POST )
    public String create(
            @Valid @ModelAttribute("observacion") Observacion observacion,
            BindingResult result,
            ModelMap modelMap,
            SessionStatus status,
            HttpServletRequest request
            )
    {
        if (observacion == null) throw new IllegalArgumentException("A observacion is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("observacion", observacion);
            modelMap.addAttribute("estadoobservacions", EstadoObservacion.findAllEstadoObservacions());
            return "observacion/create";
        }

        String condicion = observacion.getCondicion();
        String criterio = observacion.getCriterio();
        String causa = observacion.getCausa();
        String efecto = observacion.getEfecto();


        if ((condicion != null) && (criterio != null) && (causa != null) && (efecto != null)){
            if ((condicion.length() > 0) && (criterio.length() > 0) && (causa.length() > 0) && (efecto.length() > 0)){
                String redaccion = condicion + criterio + causa + efecto;
                observacion.setRedaccion(redaccion);
            }
        }

        observacion.persist();
        Util.registrarEntradaEnBitacora(observacion, "Se creó el Hallazgo ", request.getRemoteAddr());
        status.setComplete();        
        return "redirect:/observacion/"+observacion.getId();
    }

    @RequestMapping(value = "/observacion/form", method = RequestMethod.GET)
    public String createForm(
            @RequestParam("idPrueba") long idPrueba,
            ModelMap modelMap
            ) {

            Observacion laObservacion = new Observacion();
            Prueba laPrueba = Prueba.findPrueba(idPrueba);
            laObservacion.setPrueba(laPrueba);


            List<EstadoObservacion> estadoObservacions = EstadoObservacion.findAllEstadoObservacions();

            ArrayList estados = new ArrayList();
            for (int i = 0; i < estadoObservacions.size(); i++){
                estados.add(estadoObservacions.get(i).getDescripcionCorta());
            }

            //Buscamos todas las observaciones relacionadas con mi actuacion
            List<Prueba> misPruebas = Prueba.findPruebasByCodigoActuacion(laPrueba.getCodigoActuacion()).getResultList();
            List<Observacion> misObservaciones = new ArrayList();
            for (int i=0; i<misPruebas.size(); i++) {
                List<Observacion> misObservacionesByPrueba = ve.co.bsc.sigai.domain.Observacion.findObservacionsByPrueba((ve.co.bsc.sigai.domain.Prueba)misPruebas.get(i)).getResultList();
                misObservaciones.addAll(misObservacionesByPrueba);
            }

            int mayor = 0;
            //De las observaciones relacionadas con la prueba, busco el código mayor
            for (int i=0; i<misObservaciones.size(); i++)
            {
                Observacion observacion = (Observacion) misObservaciones.get(i);

                if (Integer.parseInt(observacion.getReferencia()) > mayor)
                {
                    mayor = Integer.parseInt(observacion.getReferencia());
                }
            }

            //Seteamos la nueva referencia a la observacion
            laObservacion.setReferencia(String.valueOf(mayor+1));

            modelMap.addAttribute("estados", estados);
            modelMap.addAttribute("observacion",laObservacion);
            modelMap.addAttribute("estadoobservacions", estadoObservacions);
            return "observacion/create";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String update(
            @Valid @ModelAttribute("observacion") Observacion observacion,
            BindingResult result,
            ModelMap modelMap,
            SessionStatus status,
            HttpServletRequest request
            )
    {
        if (observacion == null) throw new IllegalArgumentException("A observacion is required");
        if (result.hasErrors()) {
            
            modelMap.addAttribute("observacion", observacion);
            
            modelMap.addAttribute("estadoobservacions", EstadoObservacion.findAllEstadoObservacions());
            return "observacion/update";
        }

       
        observacion.merge();
        Util.registrarEntradaEnBitacora(observacion, "Se modificó el Hallazgo ", request.getRemoteAddr());
        status.setComplete();
        return "redirect:/observacion/"+observacion.getId();
        
    }

    @RequestMapping(value = "/observacion/{id}/form", method = RequestMethod.GET)
    public String updateForm(
            @PathVariable("id") Long id,
            ModelMap modelMap
            )
    {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");

        Observacion observacion = Observacion.findObservacion(id);

        List<EstadoObservacion> estadoObservacions = EstadoObservacion.findAllEstadoObservacions();
        ArrayList estados = new ArrayList();
        for (int i = 0; i < estadoObservacions.size(); i++){
            estados.add(estadoObservacions.get(i).getDescripcionCorta());
        }

        modelMap.addAttribute("estados", estados);
        modelMap.addAttribute("observacion", observacion);
        modelMap.addAttribute("estadoobservacions", estadoObservacions);

        modelMap.addAttribute("estadoSelected", observacion.getEstadoObservacion().getId());
        
        return "observacion/update";
    }

    @RequestMapping(value = "/observacion/{id}", method = RequestMethod.DELETE)
    public String delete(
            @PathVariable("id") Long id,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size
            )
    {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");

        Observacion miObservacion = Observacion.findObservacion(id);

        Prueba laPrueba = miObservacion.getPrueba();

        String response = "redirect:/prueba/"+laPrueba.getId();
        
        miObservacion.remove();

        return response;
        //return "redirect:/observacion?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }

    @RequestMapping(value = "/observacion/{id}", method = RequestMethod.GET)
    public String show(
            @PathVariable("id") Long id, ModelMap modelMap
            ) {
        
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        
        Observacion miObservacion = Observacion.findObservacion(id);

        Query queryRecomendaciones = Recomendacion.findRecomendacionsByObservacion(miObservacion);
        List<Recomendacion> recomendaciones = queryRecomendaciones.getResultList();

        Query queryAlegatos = Alegato.findAlegatoesByObservacion(miObservacion);
        List<Alegato> alegatos = queryAlegatos.getResultList();

        Query queryArchivosAdjuntosByHallazgo = ArchivoAdjunto.findArchivoAdjuntoesByHallazgo(miObservacion);
        List<ArchivoAdjunto> archivosAdjuntosByHallazgo = queryArchivosAdjuntosByHallazgo.getResultList();
        
        Query queryPlanDeAccion = PlanDeAccion.findPlanDeAccionsByObservacion(miObservacion);
        List<PlanDeAccion> planesDeAccion = queryPlanDeAccion.getResultList();

        modelMap.addAttribute("archivosAdjuntosByHallazgo", archivosAdjuntosByHallazgo);
        modelMap.addAttribute("alegatos", alegatos);
        modelMap.addAttribute("recomendaciones", recomendaciones);
        modelMap.addAttribute("observacion", miObservacion);
        modelMap.addAttribute("planesDeAccion", planesDeAccion);

        modelMap.addAttribute("objetoComentable", miObservacion);

        logger.debug("solicitando revision de observacion en motor de reglas");
        jbpmService.executeRulesStateless(miObservacion);
        logger.debug("revision de observacion en motor de reglas listo");

        return "observacion/show";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String agregarRedaccion(
            @Valid @ModelAttribute("observacion") Observacion observacion,
            BindingResult result,
            ModelMap modelMap,
            SessionStatus status,
            HttpServletRequest request
            )
    {
        if (observacion == null) throw new IllegalArgumentException("A observacion is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("observacion", observacion);
            return "observacion/agregarRedaccion";
        }

        observacion.merge();
        Util.registrarEntradaEnBitacora(observacion, "Se modificó la redaccion del hallazgo ", request.getRemoteAddr());
        status.setComplete();
        return "redirect:/observacion/"+observacion.getId();

    }

    @RequestMapping(value = "/observacion/agregarRedaccion/{id}/form", method = RequestMethod.GET)
    public String agregarRedaccionForm(
            @PathVariable("id") Long id,
            ModelMap modelMap
            )
    {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");

        Observacion observacion = Observacion.findObservacion(id);

        String redaccion = observacion.getCondicion() + observacion.getCriterio() + observacion.getCausa() + observacion.getEfecto();
        observacion.setRedaccion(redaccion);
        modelMap.addAttribute("observacion", observacion);

        return "observacion/agregarRedaccion";
    }

    @RequestMapping(value = "/observacion/recargarRedaccion/{idHallazgo}", method = RequestMethod.GET )
    public String recargarConclusion(
            @PathVariable("idHallazgo") Long idHallazgo,
            ModelMap modelMap,
            HttpServletRequest request
            )
    {
        Observacion observacion = Observacion.findObservacion(idHallazgo);
        String redaccion = observacion.getCondicion() + observacion.getCriterio() + observacion.getCausa() + observacion.getEfecto();
        observacion.setRedaccion(redaccion);
        observacion.persist();

        Util.registrarEntradaEnBitacora(observacion, "Se recargó la redacción del hallazgo ", request.getRemoteAddr());

        return "redirect:/observacion/" + idHallazgo;
    }
}
