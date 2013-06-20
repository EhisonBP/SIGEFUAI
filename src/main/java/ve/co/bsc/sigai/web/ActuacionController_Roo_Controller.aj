package ve.co.bsc.sigai.web;

import java.lang.Long;
import java.lang.String;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ve.co.bsc.sigai.domain.Actuacion;
import ve.co.bsc.sigai.domain.OrganismoEnte;

privileged aspect ActuacionController_Roo_Controller {
    
    @RequestMapping(value = "/actuacion/{id}", method = RequestMethod.DELETE)
    public String ActuacionController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        Actuacion.findActuacion(id).remove();
        return "redirect:/actuacion?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
    @RequestMapping(value = "find/ByActuacion/form", method = RequestMethod.GET)
    public String ActuacionController.findActuacionsByActuacionForm(ModelMap modelMap) {
        modelMap.addAttribute("actuacions", Actuacion.findAllActuacions());
        return "actuacion/findActuacionsByActuacion";
    }
    
    @RequestMapping(value = "find/ByActuacion", method = RequestMethod.GET)
    public String ActuacionController.findActuacionsByActuacion(@RequestParam("actuacion") Actuacion actuacion, ModelMap modelMap) {
        if (actuacion == null) throw new IllegalArgumentException("A Actuacion is required.");
        modelMap.addAttribute("actuacions", Actuacion.findActuacionsByActuacion(actuacion).getResultList());
        return "actuacion/list";
    }
    
    @RequestMapping(value = "find/ByCodigo/form", method = RequestMethod.GET)
    public String ActuacionController.findActuacionsByCodigoForm(ModelMap modelMap) {
        return "actuacion/findActuacionsByCodigo";
    }
    
    @RequestMapping(value = "find/ByCodigo", method = RequestMethod.GET)
    public String ActuacionController.findActuacionsByCodigo(@RequestParam("codigo") String codigo, ModelMap modelMap) {
        if (codigo == null || codigo.length() == 0) throw new IllegalArgumentException("A Codigo is required.");
        modelMap.addAttribute("actuacions", Actuacion.findActuacionsByCodigo(codigo).getResultList());
        return "actuacion/list";
    }
    
    @RequestMapping(value = "find/ByRif/form", method = RequestMethod.GET)
    public String ActuacionController.findActuacionsByRifForm(ModelMap modelMap) {
        modelMap.addAttribute("organismoentes", OrganismoEnte.findAllOrganismoEntes());
        return "actuacion/findActuacionsByRif";
    }
    
    @RequestMapping(value = "find/ByRif", method = RequestMethod.GET)
    public String ActuacionController.findActuacionsByRif(@RequestParam("rif") OrganismoEnte rif, ModelMap modelMap) {
        if (rif == null) throw new IllegalArgumentException("A Rif is required.");
        modelMap.addAttribute("actuacions", Actuacion.findActuacionsByRif(rif).getResultList());
        return "actuacion/list";
    }
    
}
