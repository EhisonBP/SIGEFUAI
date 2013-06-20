package ve.co.bsc.sigai.web;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import ve.co.bsc.sigai.domain.ItemCuestionario;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import ve.co.bsc.sigai.domain.Cuestionario;

@RooWebScaffold(path = "itemcuestionario", automaticallyMaintainView = true, formBackingObject = ItemCuestionario.class)
@RequestMapping("/itemcuestionario/**")
@SessionAttributes({"itemCuestionario", "idCuestionario", "idPapelDeTrabajo"})
@Controller
public class ItemCuestionarioController {

    Logger logger = Logger.getLogger(ActividadGeneralController.class);

    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
    }

    @RequestMapping(value = "/itemcuestionario/form", method = RequestMethod.GET)
    public String createForm(
            @RequestParam("idCuestionario") long idCuestionario,
            @RequestParam("idPapelDeTrabajo") long idPapelDeTrabajo,
            HttpServletRequest hsr,
            ModelMap modelMap
            )
    {
        Cuestionario miCuestionario = Cuestionario.findCuestionario(idCuestionario);
        ItemCuestionario miItemCuestionario = new ItemCuestionario();
        miItemCuestionario.setCuestionario(miCuestionario);

        modelMap.addAttribute("itemCuestionario", miItemCuestionario);        
        modelMap.addAttribute("idCuestionario", idCuestionario);
        modelMap.addAttribute("idPapelDeTrabajo", idPapelDeTrabajo);
        
        return "itemcuestionario/create";
    }

    @RequestMapping(value = "/itemcuestionario", method = RequestMethod.POST)
    public String create(
            @Valid @ModelAttribute("itemCuestionario") ItemCuestionario itemCuestionario,            
            @Valid @ModelAttribute("idCuestionario") long idCuestionario,
            @Valid @ModelAttribute("idPapelDeTrabajo") long idPapelDeTrabajo,
            BindingResult result,
            ModelMap modelMap,
            SessionStatus status
            )
    {
        if (itemCuestionario == null) throw new IllegalArgumentException("A itemCuestionario is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("itemCuestionario", itemCuestionario);            
            return "itemcuestionario/create";
        }
        
        itemCuestionario.persist();
        //TODO
        //Util.registrarEntradaEnBitacora(estadoPlanDeAccion, "Se creó el Estatus de Plan de Acción ");
        status.setComplete();
        return "redirect:/cuestionario/" + itemCuestionario.getCuestionario().getId() + "?idPapelDeTrabajo=" + idPapelDeTrabajo;
    }    

    @RequestMapping(value = "/itemcuestionario/{id}", method = RequestMethod.DELETE)
    public String delete(
            @PathVariable("id") Long id,
            @RequestParam("idPapelDeTrabajo") long idPapelDeTrabajo,
            ModelMap modelMap)
    {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        ItemCuestionario itemCuestionario = ItemCuestionario.findItemCuestionario(id);
        long idCuestionario = itemCuestionario.getCuestionario().getId();
        itemCuestionario.remove();
        
        return "redirect:/cuestionario/" + idCuestionario + "?idPapelDeTrabajo=" + idPapelDeTrabajo;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String update(
            @Valid @ModelAttribute("itemCuestionario") ItemCuestionario itemCuestionario,            
            @Valid @ModelAttribute("idCuestionario") long idCuestionario,
            @Valid @ModelAttribute("idPapelDeTrabajo") long idPapelDeTrabajo,
            BindingResult result,
            ModelMap modelMap,            
            SessionStatus status
            )
    {
        if (itemCuestionario == null) throw new IllegalArgumentException("A itemCuestionario is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("itemCuestionario", itemCuestionario);
            return "itemcuestionario/update";
        }        
        itemCuestionario.merge();
        //TODO
        //Util.registrarEntradaEnBitacora(estadoPlanDeAccion, "Se creó el Estatus de Plan de Acción ");
        status.setComplete();
        return "redirect:/cuestionario/" + itemCuestionario.getCuestionario().getId() + "?idPapelDeTrabajo=" + idPapelDeTrabajo;
    }

    @RequestMapping(value = "/itemcuestionario/{id}/form", method = RequestMethod.GET)
    public String updateForm(
            @PathVariable("id") Long id,            
            @RequestParam("idCuestionario") long idCuestionario,
            @RequestParam("idPapelDeTrabajo") long idPapelDeTrabajo,
            ModelMap modelMap
            )
    {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");

        ItemCuestionario miItemCuestionario = ItemCuestionario.findItemCuestionario(id);        

        modelMap.addAttribute("itemCuestionario", miItemCuestionario);
        modelMap.addAttribute("idCuestionario", idCuestionario);
        modelMap.addAttribute("idPapelDeTrabajo", idPapelDeTrabajo);

        return "itemcuestionario/update";
    }

    @RequestMapping(value = "/itemcuestionario/responder/{id}/form", method = RequestMethod.GET)
    public String responderForm(
            @PathVariable("id") Long idCuestionario,
            //@RequestParam("idPapelDeTrabajo") long idPapelDeTrabajo,
            HttpServletRequest hsr,
            ModelMap modelMap
            )
    {
        if (idCuestionario == null) throw new IllegalArgumentException("An Identifier is required");

        long idPapelDeTrabajo;
        if (hsr.getParameter("idPapelDeTrabajo") != null)
        {
            idPapelDeTrabajo = Long.parseLong(hsr.getParameter("idPapelDeTrabajo"));
        }
        else{
            idPapelDeTrabajo = 0;
        }

        Cuestionario cuestionario = Cuestionario.findCuestionario(idCuestionario);
        List<ItemCuestionario> items = ItemCuestionario.findItemCuestionariosByCuestionario(cuestionario).getResultList();

        long idItemActual = 0;
        int preguntaNum = 0;
        for (int i = 0; i < items.size(); i++){
            preguntaNum++;
            if (items.get(i).getRespuesta() == null){
                idItemActual = items.get(i).getId();
                break;
            }
        }

        ItemCuestionario miItemCuestionario = ItemCuestionario.findItemCuestionario(idItemActual);

        if (preguntaNum == items.size()){
            modelMap.addAttribute("ultimaPregunta", "true");
        }
        else{
            modelMap.addAttribute("ultimaPregunta", "false");
        }
        
        modelMap.addAttribute("itemCuestionario", miItemCuestionario);
        modelMap.addAttribute("idCuestionario", idCuestionario);
        modelMap.addAttribute("cuestionario", Cuestionario.findCuestionario(idCuestionario));
        modelMap.addAttribute("idPapelDeTrabajo", idPapelDeTrabajo);
        
        return "itemcuestionario/responder";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String responder(
            @Valid @ModelAttribute("itemCuestionario") ItemCuestionario itemCuestionario,
            @Valid @ModelAttribute("idCuestionario") long idCuestionario,
            @Valid @ModelAttribute("idPapelDeTrabajo") long idPapelDeTrabajo,
            BindingResult result,
            MultipartHttpServletRequest request,
            ModelMap modelMap,
            SessionStatus status
            )
    {
        if (itemCuestionario == null) throw new IllegalArgumentException("A itemCuestionario is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("itemCuestionario", itemCuestionario);
            modelMap.addAttribute("idCuestionario", idCuestionario);
            modelMap.addAttribute("cuestionario", Cuestionario.findCuestionario(idCuestionario));
            modelMap.addAttribute("idPapelDeTrabajo", idPapelDeTrabajo);
            return "itemcuestionario/responder";
        }
        //Validamos si hay archivo adjunto
        if (!request.getFile("filedata").isEmpty()){
            String originalFilename = request.getFile("filedata").getOriginalFilename();

            String[] originalFileNameArray = originalFilename.split("\\.");
            itemCuestionario.setFileName(originalFileNameArray[0]);
            itemCuestionario.setFileName(itemCuestionario.getFileName().replace(' ', '_'));

            itemCuestionario.setFileExtension(originalFileNameArray[1]);
        }
        itemCuestionario.merge();
        //TODO
        //Util.registrarEntradaEnBitacora(estadoPlanDeAccion, "Se creó el Estatus de Plan de Acción ");
        
        //Verifico si esta es la última pregunta
        Cuestionario cuestionario = Cuestionario.findCuestionario(idCuestionario);
        List<ItemCuestionario> items = ItemCuestionario.findItemCuestionariosByCuestionario(cuestionario).getResultList();

        int preguntaNum = 0;
        for (int i = 0; i < items.size(); i++){            
            if (items.get(i).getRespuesta() == null){
                break;
            }
            preguntaNum++;
        }
        
        if (preguntaNum == items.size()){
            cuestionario.setRespondido(true);
            cuestionario.merge();
            
            status.setComplete();

            //Veo si el que repondió el cuestionario fue el auditado
            if (idPapelDeTrabajo == 0){
                //return "redirect:/auditado/actividades";
                return "redirect:/cuestionario/completo/" + itemCuestionario.getCuestionario().getId() + "?idPapelDeTrabajo=" + idPapelDeTrabajo;
            }
            else{
                return "redirect:/cuestionario/" + itemCuestionario.getCuestionario().getId() + "?idPapelDeTrabajo=" + idPapelDeTrabajo;
            }
        }
        else{
            status.setComplete();
            return "redirect:/itemcuestionario/responder/" + cuestionario.getId() + "/form";
        }
    }
}
