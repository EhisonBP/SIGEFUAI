package ve.co.bsc.sigai.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import ve.co.bsc.sigai.domain.SeccionDefault;
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
import ve.co.bsc.sigai.domain.Biblioteca;
import ve.co.bsc.sigai.domain.TipoSeccion;
import ve.co.bsc.util.Util;

@RooWebScaffold(path = "secciondefault", automaticallyMaintainView = true, formBackingObject = SeccionDefault.class)
@RequestMapping("/secciondefault/**")
@SessionAttributes({"seccionDefault","idReturn","returnURL"})
@Controller
public class SeccionDefaultController {

    Logger logger = Logger.getLogger(SeccionDefaultController.class);

    @RequestMapping(value = "/secciondefault", method = RequestMethod.POST)
    public String create(
            @Valid @ModelAttribute("seccionDefault") SeccionDefault seccionDefault,
            @Valid @ModelAttribute("idReturn") long idReturn,
            @Valid @ModelAttribute("returnURL") String returnURL,
            BindingResult result,
            ModelMap modelMap,
            SessionStatus status,
            HttpServletRequest request
            ) {
        
        if (seccionDefault == null) throw new IllegalArgumentException("A seccionDefault is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("seccionDefault", seccionDefault);
            modelMap.addAttribute("tipoSeccions", TipoSeccion.findAllTipoSeccions());
            modelMap.addAttribute("idReturn", idReturn);
            modelMap.addAttribute("returnURL", returnURL);
            return "secciondefault/create";
        }        

        seccionDefault.persist();
        Util.registrarEntradaEnBitacora(seccionDefault, "Se creó la sección", request.getRemoteAddr() );
        status.setComplete();
        return "redirect:/" + returnURL + "/" + idReturn;
        //return "redirect:/secciondefault/";
    }

    @RequestMapping(value = "/secciondefault/form", method = RequestMethod.GET)
    public String createForm(
            @RequestParam("idBiblioteca") long idBiblioteca,
            @RequestParam("idReturn") long idReturn,
            @RequestParam("returnURL") String returnURL,
            ModelMap modelMap,
            HttpServletRequest hsr
            ) {
        SeccionDefault miSeccion = new SeccionDefault();
        miSeccion.setBiblioteca(Biblioteca.findBiblioteca(idBiblioteca));

        if (hsr.getParameter("idSeccionDefault") != null)
        {
            //Entra aqui si tiene un padre
            SeccionDefault seccionDefaultPadre = SeccionDefault.findSeccionDefault(new Long(hsr.getParameter("idSeccionDefault")));
            miSeccion.setSeccionDefault(seccionDefaultPadre);

        }

        modelMap.addAttribute("seccionDefault", miSeccion);
        modelMap.addAttribute("tipoSeccions", TipoSeccion.findAllTipoSeccions());
        modelMap.addAttribute("idReturn",idReturn);
        modelMap.addAttribute("returnURL",returnURL);
        return "secciondefault/create";
    }
    
    @RequestMapping(value = "/secciondefault/{id}", method = RequestMethod.GET)
    public String show(
            @PathVariable("id") Long id, 
            ModelMap modelMap
            ) {
        
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        SeccionDefault seccion = SeccionDefault.findSeccionDefault(id);
        modelMap.addAttribute("seccionDefault", seccion);
        modelMap.addAttribute("seccionDefaults", SeccionDefault.findSeccionDefaultsBySeccionDefault(seccion).getResultList());        
        modelMap.addAttribute("archivosAdjuntos", ArchivoAdjunto.findArchivoAdjuntoesBySeccionDefault(seccion).getResultList());
        return "secciondefault/show";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String update(
            @Valid SeccionDefault seccionDefault,
            @Valid @ModelAttribute("idReturn") long idReturn,
            @Valid @ModelAttribute("returnURL") String returnURL,
            BindingResult result,
            ModelMap modelMap,
            SessionStatus status,
            HttpServletRequest request
            ) {

        if (seccionDefault == null) throw new IllegalArgumentException("A seccionDefault is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("seccionDefault", seccionDefault);
            modelMap.addAttribute("tipoSeccions", TipoSeccion.findAllTipoSeccions());
            return "secciondefault/update";
        }       

        seccionDefault.merge();
        Util.registrarEntradaEnBitacora(seccionDefault, "Se modificó la sección predefinida ", request.getRemoteAddr());
        status.setComplete();

        return "redirect:/" + returnURL + "/" + idReturn;
        //return "redirect:/secciondefault/";
    }

    @RequestMapping(value = "/secciondefault/{id}/form", method = RequestMethod.GET)
    public String updateForm(
            @PathVariable("id") Long id,
            @RequestParam("idReturn") long idReturn,
            @RequestParam("returnURL") String returnURL,
            ModelMap modelMap
            ) {

        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("seccionDefault", SeccionDefault.findSeccionDefault(id));
        modelMap.addAttribute("tipoSeccions", TipoSeccion.findAllTipoSeccions());
        modelMap.addAttribute("idReturn",idReturn);
        modelMap.addAttribute("returnURL",returnURL);
        return "secciondefault/update";
    }

    @RequestMapping(value = "/secciondefault", method = RequestMethod.GET)
    public String list(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            ModelMap modelMap
            ) {

        /*if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("secciondefaults", SeccionDefault.findSeccionDefaultEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) SeccionDefault.countSeccionDefaults() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {*/
            List<SeccionDefault> seccionDefaults = new ArrayList<SeccionDefault>();
            List<SeccionDefault> seccionesDefaultTodo = SeccionDefault.findAllSeccionDefaults();
            List<SeccionDefault> seccionesDefaultPadres = new ArrayList<SeccionDefault>();

            for (int i = 0; i < seccionesDefaultTodo.size(); i++){
                if (seccionesDefaultTodo.get(i).getSeccionDefault() == null){
                    seccionesDefaultPadres.add(seccionesDefaultTodo.get(i));
                }
            }

            if (seccionesDefaultPadres.size() > 0){
                Collections.sort(seccionesDefaultPadres, SeccionDefault.compararSeccionesPorId);

                for (int j = 0; j < seccionesDefaultPadres.size(); j++){
                    //agrego el padre
                    seccionDefaults.add(seccionesDefaultPadres.get(j));
                    //busco los hijos
                    seccionDefaults.addAll(bucarSeccionHijos(seccionesDefaultPadres.get(j), seccionesDefaultTodo));
                }
            }

            modelMap.addAttribute("secciondefaults", seccionDefaults);
        //}
        return "secciondefault/list";
    }

    private List<SeccionDefault> bucarSeccionHijos(SeccionDefault padre, List<SeccionDefault> todos){
        List<SeccionDefault> result = new ArrayList<SeccionDefault>();
        for (int i = 0; i < todos.size(); i++){
            SeccionDefault seccion = todos.get(i);
            SeccionDefault seccionPadre = seccion.getSeccionDefault();
            if (seccionPadre != null){
                if (seccionPadre.getId() == padre.getId()){
                    result.add(todos.get(i));
                    result.addAll(bucarSeccionHijos(todos.get(i), todos));
                }
            }
        }

        return result;
    }
}
