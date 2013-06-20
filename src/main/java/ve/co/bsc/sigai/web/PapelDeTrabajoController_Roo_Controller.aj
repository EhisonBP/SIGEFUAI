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
import ve.co.bsc.sigai.domain.PapelDeTrabajo;

privileged aspect PapelDeTrabajoController_Roo_Controller {
    
    @RequestMapping(value = "/papeldetrabajo", method = RequestMethod.GET)
    public String PapelDeTrabajoController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("papeldetrabajoes", PapelDeTrabajo.findPapelDeTrabajoEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) PapelDeTrabajo.countPapelDeTrabajoes() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("papeldetrabajoes", PapelDeTrabajo.findAllPapelDeTrabajoes());
        }
        return "papeldetrabajo/list";
    }
    
    @RequestMapping(value = "/papeldetrabajo/{id}", method = RequestMethod.DELETE)
    public String PapelDeTrabajoController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        PapelDeTrabajo.findPapelDeTrabajo(id).remove();
        return "redirect:/papeldetrabajo?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
    @RequestMapping(value = "find/ByCodigoActuacion/form", method = RequestMethod.GET)
    public String PapelDeTrabajoController.findPapelDeTrabajoesByCodigoActuacionForm(ModelMap modelMap) {
        modelMap.addAttribute("actuacions", Actuacion.findAllActuacions());
        return "papeldetrabajo/findPapelDeTrabajoesByCodigoActuacion";
    }
    
    @RequestMapping(value = "find/ByCodigoActuacion", method = RequestMethod.GET)
    public String PapelDeTrabajoController.findPapelDeTrabajoesByCodigoActuacion(@RequestParam("codigoActuacion") Actuacion codigoActuacion, ModelMap modelMap) {
        if (codigoActuacion == null) throw new IllegalArgumentException("A CodigoActuacion is required.");
        modelMap.addAttribute("papeldetrabajoes", PapelDeTrabajo.findPapelDeTrabajoesByCodigoActuacion(codigoActuacion).getResultList());
        return "papeldetrabajo/list";
    }
    
    @RequestMapping(value = "find/ByActividadActuacion/form", method = RequestMethod.GET)
    public String PapelDeTrabajoController.findPapelDeTrabajoesByActividadActuacionForm(ModelMap modelMap) {
        modelMap.addAttribute("actividadactuacions", ActividadActuacion.findAllActividadActuacions());
        return "papeldetrabajo/findPapelDeTrabajoesByActividadActuacion";
    }
    
    @RequestMapping(value = "find/ByActividadActuacion", method = RequestMethod.GET)
    public String PapelDeTrabajoController.findPapelDeTrabajoesByActividadActuacion(@RequestParam("actividadActuacion") ActividadActuacion actividadActuacion, ModelMap modelMap) {
        if (actividadActuacion == null) throw new IllegalArgumentException("A ActividadActuacion is required.");
        modelMap.addAttribute("papeldetrabajoes", PapelDeTrabajo.findPapelDeTrabajoesByActividadActuacion(actividadActuacion).getResultList());
        return "papeldetrabajo/list";
    }
    
    @RequestMapping(value = "find/ByCodigoEquals/form", method = RequestMethod.GET)
    public String PapelDeTrabajoController.findPapelDeTrabajoesByCodigoEqualsForm(ModelMap modelMap) {
        return "papeldetrabajo/findPapelDeTrabajoesByCodigoEquals";
    }
    
    @RequestMapping(value = "find/ByCodigoEquals", method = RequestMethod.GET)
    public String PapelDeTrabajoController.findPapelDeTrabajoesByCodigoEquals(@RequestParam("codigo") String codigo, ModelMap modelMap) {
        if (codigo == null || codigo.length() == 0) throw new IllegalArgumentException("A Codigo is required.");
        modelMap.addAttribute("papeldetrabajoes", PapelDeTrabajo.findPapelDeTrabajoesByCodigoEquals(codigo).getResultList());
        return "papeldetrabajo/list";
    }
    
}
