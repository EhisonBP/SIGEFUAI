package ve.co.bsc.sigai.web;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import ve.co.bsc.sigai.domain.Personalizacion;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import ve.co.bsc.sigai.domain.ArchivoAdjunto;
import ve.co.bsc.sigai.domain.CondicionAuditorInterno;
import ve.co.bsc.sigai.domain.Estado;
import ve.co.bsc.sigai.domain.TipoOrganismo;

@RooWebScaffold(path = "personalizacion", automaticallyMaintainView = true, formBackingObject = Personalizacion.class)
@RequestMapping("/personalizacion/**")
@SessionAttributes({"personalizacion"})
@Controller
public class PersonalizacionController {

    private static Logger logger = Logger.getLogger(PersonalizacionController.class);

    @RequestMapping(value = "/personalizacion/form", method = RequestMethod.GET)
    public String createForm(
            ModelMap modelMap)
    {
        modelMap.addAttribute("personalizacion", new Personalizacion());
        modelMap.addAttribute("tipoorganismoes", TipoOrganismo.findAllTipoOrganismoes());
        modelMap.addAttribute("estadoes", Estado.findAllEstadoes());
        modelMap.addAttribute("condicionauditorinternoes", CondicionAuditorInterno.findAllCondicionAuditorInternoes());

        return "personalizacion/create";
    }

    @RequestMapping(value = "/personalizacion", method = RequestMethod.POST)
    public String create(
            @Valid Personalizacion personalizacion,
            BindingResult result,
            MultipartHttpServletRequest request,
            ModelMap modelMap
            )
    {
        if (personalizacion == null) throw new IllegalArgumentException("A personalizacion is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("personalizacion", personalizacion);
            modelMap.addAttribute("tipoorganismoes", TipoOrganismo.findAllTipoOrganismoes());
            modelMap.addAttribute("estadoes", Estado.findAllEstadoes());
            modelMap.addAttribute("condicionauditorinternoes", CondicionAuditorInterno.findAllCondicionAuditorInternoes());
            return "personalizacion/create";
        }

        try {
            personalizacion.setArchivoImagen(request.getFile("filedata").getBytes());
            personalizacion.persist();
        } catch (Exception e) {
            //logger.error("imposible guardar adjunto", e);
            //modelMap.addAttribute("imagen", imagen);
            //result.addError(new ObjectError("imagen", "No fue posible almacenar el archivo seleccionado"));
            return "personalizacion/create";
        }

        
        return "redirect:/personalizacion/" + personalizacion.getId();
    }

    @RequestMapping(value = "/personalizacion/{id}/form", method = RequestMethod.GET)
    public String updateForm(
            @PathVariable("id") Long id,
            ModelMap modelMap
            ) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("personalizacion", Personalizacion.findPersonalizacion(id));
        modelMap.addAttribute("tipoorganismoes", TipoOrganismo.findAllTipoOrganismoes());
        modelMap.addAttribute("estadoes", Estado.findAllEstadoes());
        modelMap.addAttribute("condicionauditorinternoes", CondicionAuditorInterno.findAllCondicionAuditorInternoes());
        return "personalizacion/update";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String update(
            @Valid Personalizacion personalizacion,
            BindingResult result,
            //MultipartHttpServletRequest request,
            ModelMap modelMap
            ) {
        if (personalizacion == null) throw new IllegalArgumentException("A personalizacion is required");
        if (result.hasErrors()) {        
            modelMap.addAttribute("personalizacion", personalizacion);
            modelMap.addAttribute("tipoorganismoes", TipoOrganismo.findAllTipoOrganismoes());
            modelMap.addAttribute("estadoes", Estado.findAllEstadoes());
            modelMap.addAttribute("condicionauditorinternoes", CondicionAuditorInterno.findAllCondicionAuditorInternoes());
           return "personalizacion/update";
        }

        /*try {
            logger.debug("getSize() ****** ***** **** : " + request.getFile(null).getSize());
            personalizacion.setArchivoImagen(request.getFile("filedata").getBytes());
            personalizacion.merge();
        } catch (Exception e) {
            //logger.error("imposible guardar adjunto", e);
            //modelMap.addAttribute("imagen", imagen);
            //result.addError(new ObjectError("imagen", "No fue posible almacenar el archivo seleccionado"));
            return "personalizacion/update";
        }*/
        
        personalizacion.merge();
        return "redirect:/personalizacion/" + personalizacion.getId();
    }

    @RequestMapping(value = "/personalizacion/updatelogo/form", method = RequestMethod.GET)
    public String updatelogoForm(ModelMap modelMap) {
        //if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("personalizacion", Personalizacion.findPersonalizacion(new Long(1)));
        return "personalizacion/updatelogo";
    }

    @RequestMapping(value = "/personalizacion/updatelogo/", method = RequestMethod.POST)
    public String updatelogo(
            @Valid @ModelAttribute("personalizacion") Personalizacion personalizacion,
            BindingResult result,
            MultipartHttpServletRequest request,
            ModelMap modelMap
            )
    {
        if (personalizacion == null) throw new IllegalArgumentException("A personalizacion is required");
        
        if (result.hasErrors()) {
            modelMap.addAttribute("personalizacion", personalizacion);
            return "personalizacion/create";
        }

        try {
            personalizacion.setArchivoImagen(request.getFile("filedata").getBytes());
            personalizacion.merge();
        } catch (Exception e) {
            //logger.error("imposible guardar adjunto", e);
            //modelMap.addAttribute("imagen", imagen);
            //result.addError(new ObjectError("imagen", "No fue posible almacenar el archivo seleccionado"));
            return "personalizacion/create";
        }


        return "redirect:/personalizacion/" + personalizacion.getId();
    }

    @RequestMapping(value = "/personalizacion/{id}", method = RequestMethod.GET)
    public String show(
            @PathVariable("id") Long id,
            ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        Personalizacion laPersonalizacion = Personalizacion.findPersonalizacion(id);
        modelMap.addAttribute("personalizacion", laPersonalizacion);
        if (laPersonalizacion.getArchivoImagen() != null){
            String s = new sun.misc.BASE64Encoder().encode(laPersonalizacion.getArchivoImagen());
            modelMap.addAttribute("imagenBase64",s);
        }

        int cantServidoresPublicos = 0;

        int cantAuditorInterno = 0;
        int cantDirectivos = 0;
        int cantCoordinadores = 0;
        int cantProfesionales = 0;
        int cantTecnicos = 0;
        int cantNoProfesionales = 0;

        if ((laPersonalizacion.getCantAuditorInterno() != null) && (laPersonalizacion.getCantAuditorInterno() > 0)){
            cantAuditorInterno = laPersonalizacion.getCantAuditorInterno();
        }
        if ((laPersonalizacion.getCantDirectivos() != null) && (laPersonalizacion.getCantDirectivos() > 0)){
            cantDirectivos = laPersonalizacion.getCantDirectivos();
        }
        if ((laPersonalizacion.getCantCoordinadores() != null) && (laPersonalizacion.getCantCoordinadores() > 0)){
            cantCoordinadores = laPersonalizacion.getCantCoordinadores();
        }
        if ((laPersonalizacion.getCantProfesionales() != null) && (laPersonalizacion.getCantProfesionales() > 0)){
            cantProfesionales = laPersonalizacion.getCantProfesionales();
        }
        if ((laPersonalizacion.getCantTecnicos() != null) && (laPersonalizacion.getCantTecnicos() > 0)){
            cantTecnicos = laPersonalizacion.getCantTecnicos();
        }
        if ((laPersonalizacion.getCantNoProfesionales() != null) && (laPersonalizacion.getCantNoProfesionales() > 0)){
            cantNoProfesionales = laPersonalizacion.getCantNoProfesionales();
        }

        cantServidoresPublicos = cantAuditorInterno + cantDirectivos + cantCoordinadores + cantProfesionales + cantTecnicos + cantNoProfesionales;
        modelMap.addAttribute("total", cantServidoresPublicos);

        List<ArchivoAdjunto> archivosAdjuntosByPersonalizacion = ArchivoAdjunto.findArchivoAdjuntoesByPersonalizacion(laPersonalizacion).getResultList();
        modelMap.addAttribute("archivosAdjuntosByPersonalizacion", archivosAdjuntosByPersonalizacion);

        return "personalizacion/show";
    }

    @RequestMapping(value = "/personalizacion/logo/", method = RequestMethod.GET)
    public void render(
            ModelMap modelMap,
            HttpServletRequest request,
            HttpServletResponse response
            )
    {
        Personalizacion personalizacion = Personalizacion.findPersonalizacion(new Long(1));
        try
        {
            response.setContentType("");
            //response.setHeader("Content-Disposition","attachment; filename="+ archivo.getNombre() + "."+ archivo.getExtension());
            if (personalizacion.getArchivoImagen() != null){
                response.getOutputStream().write(personalizacion.getArchivoImagen());
            }
        } catch (IOException e) {
            logger.error("Error renderizando archivo ", e);
        }
    }

}
