package ve.co.bsc.sigai.web;

import java.lang.String;
import java.util.Set;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.ActividadActuacion;
import ve.co.bsc.sigai.domain.Alegato;
import ve.co.bsc.sigai.domain.ArchivoAdjunto;
import ve.co.bsc.sigai.domain.AvanceActuacion;
import ve.co.bsc.sigai.domain.Observacion;
import ve.co.bsc.sigai.domain.Personalizacion;
import ve.co.bsc.sigai.domain.Requerimiento;
import ve.co.bsc.sigai.domain.Respuesta;
import ve.co.bsc.sigai.domain.SeccionDefault;

privileged aspect ArchivoAdjuntoController_Roo_Controller {
    
    @RequestMapping(value = "find/ByActividadActuacion/form", method = RequestMethod.GET)
    public String ArchivoAdjuntoController.findArchivoAdjuntoesByActividadActuacionForm(ModelMap modelMap) {
        modelMap.addAttribute("actividadactuacions", ActividadActuacion.findAllActividadActuacions());
        return "archivoadjunto/findArchivoAdjuntoesByActividadActuacion";
    }
    
    @RequestMapping(value = "find/ByActividadActuacion", method = RequestMethod.GET)
    public String ArchivoAdjuntoController.findArchivoAdjuntoesByActividadActuacion(@RequestParam("actividadActuacion") Set<ActividadActuacion> actividadActuacion, ModelMap modelMap) {
        if (actividadActuacion == null) throw new IllegalArgumentException("A ActividadActuacion is required.");
        modelMap.addAttribute("archivoadjuntoes", ArchivoAdjunto.findArchivoAdjuntoesByActividadActuacion(actividadActuacion).getResultList());
        return "archivoadjunto/list";
    }
    
    @RequestMapping(value = "find/ByLoginUsuario/form", method = RequestMethod.GET)
    public String ArchivoAdjuntoController.findArchivoAdjuntoesByLoginUsuarioForm(ModelMap modelMap) {
        return "archivoadjunto/findArchivoAdjuntoesByLoginUsuario";
    }
    
    @RequestMapping(value = "find/ByLoginUsuario", method = RequestMethod.GET)
    public String ArchivoAdjuntoController.findArchivoAdjuntoesByLoginUsuario(@RequestParam("loginUsuario") String loginUsuario, ModelMap modelMap) {
        if (loginUsuario == null || loginUsuario.length() == 0) throw new IllegalArgumentException("A LoginUsuario is required.");
        modelMap.addAttribute("archivoadjuntoes", ArchivoAdjunto.findArchivoAdjuntoesByLoginUsuario(loginUsuario).getResultList());
        return "archivoadjunto/list";
    }
    
    @RequestMapping(value = "find/ByAlegato/form", method = RequestMethod.GET)
    public String ArchivoAdjuntoController.findArchivoAdjuntoesByAlegatoForm(ModelMap modelMap) {
        modelMap.addAttribute("alegatoes", Alegato.findAllAlegatoes());
        return "archivoadjunto/findArchivoAdjuntoesByAlegato";
    }
    
    @RequestMapping(value = "find/ByAlegato", method = RequestMethod.GET)
    public String ArchivoAdjuntoController.findArchivoAdjuntoesByAlegato(@RequestParam("alegato") Alegato alegato, ModelMap modelMap) {
        if (alegato == null) throw new IllegalArgumentException("A Alegato is required.");
        modelMap.addAttribute("archivoadjuntoes", ArchivoAdjunto.findArchivoAdjuntoesByAlegato(alegato).getResultList());
        return "archivoadjunto/list";
    }
    
    @RequestMapping(value = "find/ByHallazgo/form", method = RequestMethod.GET)
    public String ArchivoAdjuntoController.findArchivoAdjuntoesByHallazgoForm(ModelMap modelMap) {
        modelMap.addAttribute("observacions", Observacion.findAllObservacions());
        return "archivoadjunto/findArchivoAdjuntoesByHallazgo";
    }
    
    @RequestMapping(value = "find/ByHallazgo", method = RequestMethod.GET)
    public String ArchivoAdjuntoController.findArchivoAdjuntoesByHallazgo(@RequestParam("hallazgo") Observacion hallazgo, ModelMap modelMap) {
        if (hallazgo == null) throw new IllegalArgumentException("A Hallazgo is required.");
        modelMap.addAttribute("archivoadjuntoes", ArchivoAdjunto.findArchivoAdjuntoesByHallazgo(hallazgo).getResultList());
        return "archivoadjunto/list";
    }
    
    @RequestMapping(value = "find/ByRespuesta/form", method = RequestMethod.GET)
    public String ArchivoAdjuntoController.findArchivoAdjuntoesByRespuestaForm(ModelMap modelMap) {
        modelMap.addAttribute("respuestas", Respuesta.findAllRespuestas());
        return "archivoadjunto/findArchivoAdjuntoesByRespuesta";
    }
    
    @RequestMapping(value = "find/ByRespuesta", method = RequestMethod.GET)
    public String ArchivoAdjuntoController.findArchivoAdjuntoesByRespuesta(@RequestParam("respuesta") Respuesta respuesta, ModelMap modelMap) {
        if (respuesta == null) throw new IllegalArgumentException("A Respuesta is required.");
        modelMap.addAttribute("archivoadjuntoes", ArchivoAdjunto.findArchivoAdjuntoesByRespuesta(respuesta).getResultList());
        return "archivoadjunto/list";
    }
    
    @RequestMapping(value = "find/BySeccionDefault/form", method = RequestMethod.GET)
    public String ArchivoAdjuntoController.findArchivoAdjuntoesBySeccionDefaultForm(ModelMap modelMap) {
        modelMap.addAttribute("secciondefaults", SeccionDefault.findAllSeccionDefaults());
        return "archivoadjunto/findArchivoAdjuntoesBySeccionDefault";
    }
    
    @RequestMapping(value = "find/BySeccionDefault", method = RequestMethod.GET)
    public String ArchivoAdjuntoController.findArchivoAdjuntoesBySeccionDefault(@RequestParam("seccionDefault") SeccionDefault seccionDefault, ModelMap modelMap) {
        if (seccionDefault == null) throw new IllegalArgumentException("A SeccionDefault is required.");
        modelMap.addAttribute("archivoadjuntoes", ArchivoAdjunto.findArchivoAdjuntoesBySeccionDefault(seccionDefault).getResultList());
        return "archivoadjunto/list";
    }
    
    @RequestMapping(value = "find/ByAvanceActuacion/form", method = RequestMethod.GET)
    public String ArchivoAdjuntoController.findArchivoAdjuntoesByAvanceActuacionForm(ModelMap modelMap) {
        modelMap.addAttribute("avanceactuacions", AvanceActuacion.findAllAvanceActuacions());
        return "archivoadjunto/findArchivoAdjuntoesByAvanceActuacion";
    }
    
    @RequestMapping(value = "find/ByAvanceActuacion", method = RequestMethod.GET)
    public String ArchivoAdjuntoController.findArchivoAdjuntoesByAvanceActuacion(@RequestParam("avanceActuacion") AvanceActuacion avanceActuacion, ModelMap modelMap) {
        if (avanceActuacion == null) throw new IllegalArgumentException("A AvanceActuacion is required.");
        modelMap.addAttribute("archivoadjuntoes", ArchivoAdjunto.findArchivoAdjuntoesByAvanceActuacion(avanceActuacion).getResultList());
        return "archivoadjunto/list";
    }
    
    @RequestMapping(value = "find/ByRequerimiento/form", method = RequestMethod.GET)
    public String ArchivoAdjuntoController.findArchivoAdjuntoesByRequerimientoForm(ModelMap modelMap) {
        modelMap.addAttribute("requerimientoes", Requerimiento.findAllRequerimientoes());
        return "archivoadjunto/findArchivoAdjuntoesByRequerimiento";
    }
    
    @RequestMapping(value = "find/ByRequerimiento", method = RequestMethod.GET)
    public String ArchivoAdjuntoController.findArchivoAdjuntoesByRequerimiento(@RequestParam("requerimiento") Requerimiento requerimiento, ModelMap modelMap) {
        if (requerimiento == null) throw new IllegalArgumentException("A Requerimiento is required.");
        modelMap.addAttribute("archivoadjuntoes", ArchivoAdjunto.findArchivoAdjuntoesByRequerimiento(requerimiento).getResultList());
        return "archivoadjunto/list";
    }
    
    @RequestMapping(value = "find/ByPersonalizacion/form", method = RequestMethod.GET)
    public String ArchivoAdjuntoController.findArchivoAdjuntoesByPersonalizacionForm(ModelMap modelMap) {
        modelMap.addAttribute("personalizacions", Personalizacion.findAllPersonalizacions());
        return "archivoadjunto/findArchivoAdjuntoesByPersonalizacion";
    }
    
    @RequestMapping(value = "find/ByPersonalizacion", method = RequestMethod.GET)
    public String ArchivoAdjuntoController.findArchivoAdjuntoesByPersonalizacion(@RequestParam("personalizacion") Personalizacion personalizacion, ModelMap modelMap) {
        if (personalizacion == null) throw new IllegalArgumentException("A Personalizacion is required.");
        modelMap.addAttribute("archivoadjuntoes", ArchivoAdjunto.findArchivoAdjuntoesByPersonalizacion(personalizacion).getResultList());
        return "archivoadjunto/list";
    }
    
}
