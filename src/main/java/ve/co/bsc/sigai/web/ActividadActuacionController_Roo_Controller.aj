package ve.co.bsc.sigai.web;

import java.lang.Long;
import java.lang.String;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.ActividadActuacion;
import ve.co.bsc.sigai.domain.Actuacion;
import ve.co.bsc.sigai.domain.Auditor;
import ve.co.bsc.sigai.domain.Documento;
import ve.co.bsc.sigai.domain.EstadoActividadActuacion;
import ve.co.bsc.sigai.domain.ObjetivoEspecifico;

privileged aspect ActividadActuacionController_Roo_Controller {
    
    @RequestMapping(value = "/actividadactuacion/form", method = RequestMethod.GET)
    public String ActividadActuacionController.createForm(ModelMap modelMap) {
        modelMap.addAttribute("actividadActuacion", new ActividadActuacion());
        modelMap.addAttribute("actividadactuacions", ActividadActuacion.findAllActividadActuacions());
        modelMap.addAttribute("actuacions", Actuacion.findAllActuacions());
        modelMap.addAttribute("auditors", Auditor.findAllAuditors());
        modelMap.addAttribute("documentoes", Documento.findAllDocumentoes());
        modelMap.addAttribute("estadoactividadactuacions", EstadoActividadActuacion.findAllEstadoActividadActuacions());
        modelMap.addAttribute("objetivoespecificoes", ObjetivoEspecifico.findAllObjetivoEspecificoes());
        return "actividadactuacion/create";
    }
    
    @RequestMapping(value = "/actividadactuacion", method = RequestMethod.GET)
    public String ActividadActuacionController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("actividadactuacions", ActividadActuacion.findActividadActuacionEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) ActividadActuacion.countActividadActuacions() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("actividadactuacions", ActividadActuacion.findAllActividadActuacions());
        }
        return "actividadactuacion/list";
    }
    
    @RequestMapping(value = "/actividadactuacion/{id}", method = RequestMethod.DELETE)
    public String ActividadActuacionController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        ActividadActuacion.findActividadActuacion(id).remove();
        return "redirect:/actividadactuacion?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
    @RequestMapping(value = "find/ByCodigoActuacion/form", method = RequestMethod.GET)
    public String ActividadActuacionController.findActividadActuacionsByCodigoActuacionForm(ModelMap modelMap) {
        modelMap.addAttribute("actuacions", Actuacion.findAllActuacions());
        return "actividadactuacion/findActividadActuacionsByCodigoActuacion";
    }
    
    @RequestMapping(value = "find/ByCodigoActuacion", method = RequestMethod.GET)
    public String ActividadActuacionController.findActividadActuacionsByCodigoActuacion(@RequestParam("codigoActuacion") Actuacion codigoActuacion, ModelMap modelMap) {
        if (codigoActuacion == null) throw new IllegalArgumentException("A CodigoActuacion is required.");
        modelMap.addAttribute("actividadactuacions", ActividadActuacion.findActividadActuacionsByCodigoActuacion(codigoActuacion).getResultList());
        return "actividadactuacion/list";
    }
    
    @RequestMapping(value = "find/ByActividadActuacion/form", method = RequestMethod.GET)
    public String ActividadActuacionController.findActividadActuacionsByActividadActuacionForm(ModelMap modelMap) {
        modelMap.addAttribute("actividadactuacions", ActividadActuacion.findAllActividadActuacions());
        return "actividadactuacion/findActividadActuacionsByActividadActuacion";
    }
    
    @RequestMapping(value = "find/ByActividadActuacion", method = RequestMethod.GET)
    public String ActividadActuacionController.findActividadActuacionsByActividadActuacion(@RequestParam("actividadActuacion") ActividadActuacion actividadActuacion, ModelMap modelMap) {
        if (actividadActuacion == null) throw new IllegalArgumentException("A ActividadActuacion is required.");
        modelMap.addAttribute("actividadactuacions", ActividadActuacion.findActividadActuacionsByActividadActuacion(actividadActuacion).getResultList());
        return "actividadactuacion/list";
    }
    
}
