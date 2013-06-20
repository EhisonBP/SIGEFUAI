package ve.co.bsc.sigai.web;

import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import ve.co.bsc.sigai.domain.TecnicaDeControl;
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
import ve.co.bsc.sigai.domain.EfectividadTecnicaControl;
import ve.co.bsc.sigai.domain.Prueba;
import ve.co.bsc.sigai.domain.Riesgo;
import ve.co.bsc.util.Util;

@RooWebScaffold(path = "tecnicadecontrol", automaticallyMaintainView = true, formBackingObject = TecnicaDeControl.class)
@RequestMapping("/tecnicadecontrol/**")
@SessionAttributes({"tecnicaDeControl","idRiesgo","descripcionRiesgo","codigoCompletoPrueba","idPrueba"})
@Controller
public class TecnicaDeControlController {

    Logger logger = Logger.getLogger(RiesgoController.class);

    @RequestMapping(value = "/tecnicadecontrol", method = RequestMethod.POST)
    public String create(
            @Valid @ModelAttribute("tecnicaDeControl") TecnicaDeControl tecnicaDeControl,
            @Valid @ModelAttribute("idRiesgo") long idRiesgo,
            BindingResult result,
            ModelMap modelMap,
            SessionStatus status,
            HttpServletRequest request
            ) {
        if (tecnicaDeControl == null) throw new IllegalArgumentException("A tecnicaDeControl is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("tecnicaDeControl", tecnicaDeControl);
            modelMap.addAttribute("efectividadtecnicacontrols", EfectividadTecnicaControl.findAllEfectividadTecnicaControls());
            return "tecnicadecontrol/create";
        }
       tecnicaDeControl.setRiesgo(Riesgo.findRiesgo(idRiesgo));
       tecnicaDeControl.persist();
       Util.registrarEntradaEnBitacora(tecnicaDeControl, "Se creó la Técnica de Control", request.getRemoteAddr());
       status.setComplete();
       return "redirect:/riesgo/" + tecnicaDeControl.getRiesgo().getId();
    }

    @RequestMapping(value = "/tecnicadecontrol/form", method = RequestMethod.GET)
    public String createForm(
            @RequestParam("idRiesgo") long idRiesgo,
            HttpServletRequest hsr,
            ModelMap modelMap
            )
    {
        TecnicaDeControl miTecnica = new TecnicaDeControl();
        if (!hsr.getParameter("idPrueba").equals(""))
        {
            Prueba miPrueba = Prueba.findPrueba(Long.parseLong(hsr.getParameter("idPrueba")));
            modelMap.addAttribute("codigoCompletoPrueba",  miPrueba.getCodigoCompleto());
            modelMap.addAttribute("idPrueba",miPrueba.getId());
        }
        Riesgo miRiesgo = Riesgo.findRiesgo(idRiesgo);
        String descripcionRiesgo = miRiesgo.getDescripcion();
        if (descripcionRiesgo.length() > 29){
            modelMap.addAttribute("descripcionRiesgo",  descripcionRiesgo.substring(0, 29));
        }
        else{
            modelMap.addAttribute("descripcionRiesgo",  descripcionRiesgo);
        }

        //List<TecnicaDeControl> tecnicas = TecnicaDeControl.findTecnicaDeControlsByRiesgo(miRiesgo).getResultList();

        List<TecnicaDeControl> tecnicas = TecnicaDeControl.findAllTecnicaDeControls();
        
        int mayor = 0;

        for (int i = 0; i < tecnicas.size(); i++)
        {
            TecnicaDeControl laTecnica = tecnicas.get(i);
            if (laTecnica.getReferencia() > mayor)
            {
                mayor = laTecnica.getReferencia();
            }
        }

        miTecnica.setReferencia(mayor + 1);

        modelMap.addAttribute("tecnicaDeControl", miTecnica);
        modelMap.addAttribute("idRiesgo", idRiesgo);
        modelMap.addAttribute("efectividadtecnicacontrols", EfectividadTecnicaControl.findAllEfectividadTecnicaControls());
        return "tecnicadecontrol/create";
    }



    @RequestMapping(method = RequestMethod.PUT)
    public String update(
        @Valid @ModelAttribute("tecnicaDeControl") TecnicaDeControl tecnicaDeControl,
        BindingResult result,
        ModelMap modelMap,
        SessionStatus status,
            HttpServletRequest request
    )
   {
        if (tecnicaDeControl == null) throw new IllegalArgumentException("A tecnicaDeControl is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("tecnicaDeControl", tecnicaDeControl);
            modelMap.addAttribute("efectividadtecnicacontrols", EfectividadTecnicaControl.findAllEfectividadTecnicaControls());
            
            return "tecnicadecontrol/update";
        }
        tecnicaDeControl.merge();
        Util.registrarEntradaEnBitacora(tecnicaDeControl, "Se modificó la Técnica de Control", request.getRemoteAddr());
        status.setComplete();
        return "redirect:/riesgo/" + tecnicaDeControl.getRiesgo().getId();
    }

    @RequestMapping(value = "/tecnicadecontrol/{id}/form", method = RequestMethod.GET)
    public String updateForm(
            @PathVariable("id") Long id,
            HttpServletRequest hsr,
            ModelMap modelMap
            ) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        if (!hsr.getParameter("idPrueba").equals(""))
        {
            Prueba miPrueba = Prueba.findPrueba(Long.parseLong(hsr.getParameter("idPrueba")));
            modelMap.addAttribute("codigoCompletoPrueba",  miPrueba.getCodigoCompleto());
            modelMap.addAttribute("idPrueba",miPrueba.getId());
        }

        TecnicaDeControl laTecnica = TecnicaDeControl.findTecnicaDeControl(id);
        Riesgo miRiesgo = laTecnica.getRiesgo();
        String descripcionRiesgo = miRiesgo.getDescripcion();
        if (descripcionRiesgo.length() > 29){
            modelMap.addAttribute("descripcionRiesgo",  descripcionRiesgo.substring(0, 29));
        }
        else{
            modelMap.addAttribute("descripcionRiesgo",  descripcionRiesgo);
        }
        modelMap.addAttribute("tecnicaDeControl", laTecnica);
        modelMap.addAttribute("idRiesgo", miRiesgo.getId());
        modelMap.addAttribute("efectividadtecnicacontrols", EfectividadTecnicaControl.findAllEfectividadTecnicaControls());
        return "tecnicadecontrol/update";
    }

    @RequestMapping(value = "/tecnicadecontrol/{id}", method = RequestMethod.GET)
    public String show(
            @PathVariable("id") Long id,
            HttpServletRequest hsr,
            ModelMap modelMap,
            SessionStatus status) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");

        TecnicaDeControl laTecnica = TecnicaDeControl.findTecnicaDeControl(id);
        Riesgo miRiesgo = laTecnica.getRiesgo();
        String descripcionRiesgo = miRiesgo.getDescripcion();
        if (descripcionRiesgo.length() > 29){
            modelMap.addAttribute("descripcionRiesgo",  descripcionRiesgo.substring(0, 29));
        }
        else{
            modelMap.addAttribute("descripcionRiesgo",  descripcionRiesgo);
        }

        if (!hsr.getParameter("idPrueba").equals(""))
        {
            Prueba miPrueba = Prueba.findPrueba(Long.parseLong(hsr.getParameter("idPrueba")));
            modelMap.addAttribute("codigoCompletoPrueba",  miPrueba.getCodigoCompleto());
            modelMap.addAttribute("idPrueba", miPrueba.getId());
        }

        modelMap.addAttribute("idRiesgo", miRiesgo.getId());
        modelMap.addAttribute("tecnicaDeControl",TecnicaDeControl.findTecnicaDeControl(id));
        return "tecnicadecontrol/show";
    }

//    @RequestMapping(value = "/tecnicadecontrol/{id}", method = RequestMethod.DELETE)
//    public String delete(
//            @PathVariable("id") Long id,
//            @RequestParam(value = "page", required = false) Integer page,
//            @RequestParam(value = "size", required = false) Integer size,
//            HttpServletRequest hsr)
//    {
//        if (id == null) throw new IllegalArgumentException("An Identifier is required");
//
//        TecnicaDeControl.findTecnicaDeControl(id).remove();
//
////        //Estoy eliminando desde la consulta de riesgo
////        if ((hsr.getParameter("idPrueba")!=null) && (hsr.getParameter("idRiesgo")!=null))
////        {
////            long idPrueba = Long.parseLong(hsr.getParameter("idPrueba"));
////            long idRiesgo = Long.parseLong(hsr.getParameter("idRiesgo"));
////            return "redirect:/riesgo/"+idRiesgo+"?idPrueba="+idPrueba ;
////        }
////        //Estoy eliminando desde la consulta de prueba
////        else
////        {
////            long idPrueba = Long.parseLong(hsr.getParameter("idPrueba"));
////            return "redirect:/prueba/" + idPrueba;
////        }
//
//
//
//
//    }

    //Este método elimina la relacion entre una tecnica de control y una prueba
    @RequestMapping(value = "/tecnicadecontrol/deleteTecnicaDeControlPrueba/{idPrueba}/{idTecnica}", method = RequestMethod.DELETE)
    public String deleteTecnicaDeControlPrueba(
            @PathVariable("idPrueba") Long idPrueba,
            @PathVariable("idTecnica") Long idTecnica,
            HttpServletRequest request
            ) {

        Prueba miPrueba = Prueba.findPrueba(idPrueba);
        TecnicaDeControl miTecnica = TecnicaDeControl.findTecnicaDeControl(idTecnica);

        Set setPrueba = miTecnica.getPrueba();
        setPrueba.remove(miPrueba);

        miTecnica.setPrueba(setPrueba);
        miTecnica.merge();
        Util.registrarEntradaEnBitacora(miTecnica, "Se elminó de la prueba " + miPrueba.toStringLimitado() +" la t�cnica de control ", request.getRemoteAddr());

        String url = "redirect:/prueba/" + idPrueba;

        return url;
    }
}
