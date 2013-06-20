  package ve.co.bsc.sigai.web;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import ve.co.bsc.sigai.domain.Riesgo;
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
import ve.co.bsc.sigai.domain.ClasificacionRiesgo;
import ve.co.bsc.sigai.domain.FrecuenciaOcurrenciaRiesgo;
import ve.co.bsc.sigai.domain.Proceso;
import ve.co.bsc.sigai.domain.TecnicaDeControl;
import ve.co.bsc.util.Util;

@RooWebScaffold(path = "riesgo", automaticallyMaintainView = true, formBackingObject = Riesgo.class)
@RequestMapping("/riesgo/**")
@SessionAttributes({"riesgo","nombreProceso","pruebaORproceso", "idPrueba", "idProceso"})
@Controller
public class RiesgoController {

     Logger logger = Logger.getLogger(RiesgoController.class);

    @RequestMapping(value = "/riesgo", method = RequestMethod.POST)
    public String create(
            @Valid @ModelAttribute("riesgo") Riesgo riesgo,
            @Valid @ModelAttribute("idProceso") long idProceso,
            BindingResult result,
            ModelMap modelMap,
            SessionStatus status,
            HttpServletRequest request
            )
    {
        if (riesgo == null) throw new IllegalArgumentException("A riesgo is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("riesgo", riesgo);
            modelMap.addAttribute("clasificacionriesgoes", ClasificacionRiesgo.findAllClasificacionRiesgoes());
            modelMap.addAttribute("frecuenciaocurrenciariesgos", FrecuenciaOcurrenciaRiesgo.findAllFrecuenciaOcurrenciaRiesgoes());
            return "riesgo/create";
        }
        riesgo.setProceso(Proceso.findProceso(idProceso));
        riesgo.persist();
        Util.registrarEntradaEnBitacora(riesgo, "Se creó el Riesgo", request.getRemoteAddr());
        status.setComplete();
        return "redirect:/proceso/"+idProceso;
    }

    @RequestMapping(value = "/riesgo/form", method = RequestMethod.GET)
    public String createForm(
            @RequestParam("idProceso") long idProceso,
            ModelMap modelMap
            )
    {
        Riesgo miRiesgo = new Riesgo();
        Proceso miProceso = Proceso.findProceso(idProceso);
        String descripcion = miProceso.getDescripcion();
        if (descripcion.length() > 29){
            modelMap.addAttribute("nombreProceso",  descripcion.substring(0, 29));
        }
        else{
            modelMap.addAttribute("nombreProceso",  descripcion);
        }

        List<Riesgo> riesgos = Riesgo.findRiesgoesByProceso(miProceso).getResultList();
        int mayor = 0;

        for (int i = 0; i < riesgos.size(); i++)
        {
            Riesgo elRiesgo = riesgos.get(i);
            if (elRiesgo.getReferencia() > mayor)
            {
                mayor = elRiesgo.getReferencia();
            }
        }

        miRiesgo.setReferencia(mayor+1);

        modelMap.addAttribute("idProceso", idProceso);
        modelMap.addAttribute("riesgo", miRiesgo);
        modelMap.addAttribute("clasificacionriesgoes", ClasificacionRiesgo.findAllClasificacionRiesgoes());
        modelMap.addAttribute("frecuenciaocurrenciariesgos", FrecuenciaOcurrenciaRiesgo.findAllFrecuenciaOcurrenciaRiesgoes());

        return "riesgo/create";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String update(
            @Valid @ModelAttribute("riesgo") Riesgo riesgo,
            BindingResult result,
            ModelMap modelMap,
            SessionStatus status,
            HttpServletRequest request
            ) {
        if (riesgo == null) throw new IllegalArgumentException("A riesgo is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("riesgo", riesgo);
            modelMap.addAttribute("clasificacionriesgoes", ClasificacionRiesgo.findAllClasificacionRiesgoes());
            modelMap.addAttribute("frecuenciaocurrenciariesgos", FrecuenciaOcurrenciaRiesgo.findAllFrecuenciaOcurrenciaRiesgoes());
            return "riesgo/update";
        }
        riesgo.merge();
        Util.registrarEntradaEnBitacora(riesgo, "Se modificó el Riesgo", request.getRemoteAddr());
        status.setComplete();
        return "redirect:/proceso/"+riesgo.getProceso().getId();

    }

    @RequestMapping(value = "/riesgo/{id}/form", method = RequestMethod.GET)
    public String updateForm(
            @PathVariable("id") Long id,
            ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");

        Riesgo elRiesgo = Riesgo.findRiesgo(id);
        Proceso miProceso = elRiesgo.getProceso();
        String descripcion = miProceso.getDescripcion();
        if (descripcion.length() > 29){
            modelMap.addAttribute("nombreProceso",  descripcion.substring(0, 29));
        }
        else{
            modelMap.addAttribute("nombreProceso",  descripcion);
        }
        modelMap.addAttribute("idProceso", miProceso.getId());
        modelMap.addAttribute("riesgo",elRiesgo);
        modelMap.addAttribute("clasificacionriesgoes", ClasificacionRiesgo.findAllClasificacionRiesgoes());
        modelMap.addAttribute("frecuenciaocurrenciariesgos", FrecuenciaOcurrenciaRiesgo.findAllFrecuenciaOcurrenciaRiesgoes());
        return "riesgo/update";
    }

    @RequestMapping(value = "/riesgo/{id}", method = RequestMethod.GET)
    public String show(
            @PathVariable("id") Long id,
            HttpServletRequest hsr,
            ModelMap modelMap
            ) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");

        Riesgo riesgo = Riesgo.findRiesgo(id);
        modelMap.addAttribute("riesgo", riesgo);

        /*if (!hsr.getParameter("idPrueba").equals(""))
        {
            Prueba miPrueba = Prueba.findPrueba(Long.parseLong(hsr.getParameter("idPrueba")));
            modelMap.addAttribute("codigoCompletoPrueba",  miPrueba.getCodigoCompleto());
            modelMap.addAttribute("idPrueba",miPrueba.getId());
        }*/

        //Busco cual es mi proceso para mostrar el link. Un riesgo siempre va a existir gracias a un proceso
        Proceso miProceso = riesgo.getProceso();
        String descripcion = miProceso.getDescripcion();
        if (descripcion.length() > 39){
            modelMap.addAttribute("codigoCompletoProceso",  descripcion.substring(0, 39));
        }
        else{
            modelMap.addAttribute("codigoCompletoProceso",  descripcion);
        }
        modelMap.addAttribute("idProceso",miProceso.getId());
        modelMap.addAttribute("tecnicasByRiesgo",TecnicaDeControl.findTecnicaDeControlsByRiesgo(riesgo).getResultList());
        return "riesgo/show";
    }

    //TODO: EVALUAR CON YUSEPIN COMO VA A SER EL ELIMINAR
//    //Esta funcion es llamada desde un proceso, debe
//    @RequestMapping(value = "/riesgo/{id}", method = RequestMethod.DELETE)
//    public String delete(
//            @PathVariable("id") Long id,
//            HttpServletRequest hsr
//            ) {
//
//        if (id == null) throw new IllegalArgumentException("An Identifier is required");
//
//
//
//        if (Long.parseLong(hsr.getParameter("idProceso")) != 0){
//            returnUrl = "redirect:/proceso/" + hsr.getParameter("idProceso");
//        }
//
//
//        Riesgo.findRiesgo(id).remove();
//
//
//        return returnUrl;
//    }
    
    //Este método elimina la relacion entre un riesgo y una prueba
    @RequestMapping(value = "/riesgo/deleteRiesgoPrueba/", method = RequestMethod.DELETE)
    public String deleteRiesgoPrueba(
            @RequestParam("idProceso") Long idProceso,
            @RequestParam("idRiesgo") Long idRiesgo,
            HttpServletRequest request
            ) {

        //Prueba miPrueba = Prueba.findPrueba(idPrueba);
        Riesgo miRiesgo = Riesgo.findRiesgo(idRiesgo);

       // Set setPrueba = miRiesgo.getPrueba();
       // setPrueba.remove(miPrueba);

       // miRiesgo.setPrueba(setPrueba);
        miRiesgo.setInactivo(true);
        miRiesgo.merge();
        Util.registrarEntradaEnBitacora(miRiesgo, "Se eliminó el riesgo ", request.getRemoteAddr());

        String url = "redirect:/proceso/" + idProceso;

        return url;
    }
}
