package ve.co.bsc.sigai.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ve.co.bsc.sigai.domain.Actuacion;
import ve.co.bsc.sigai.domain.ItemPlanificacionActuacion;
import javax.persistence.Query;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.bind.annotation.ModelAttribute;
import ve.co.bsc.sigai.domain.PlanAnual;
import ve.co.bsc.util.Util;


@RequestMapping("/planificadoractuacion/**")
@Controller
public class PlanificadorActuacion {

    static Logger logger = Logger.getLogger(PlanificadorActuacion.class);

    @RequestMapping
    public void get(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
    }

    /*@RequestMapping(method = RequestMethod.POST, value = "{id}")
    public void post(@PathVariable Long id, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
    }*/

    @RequestMapping(method = RequestMethod.GET, value = "index")
    public String index(
            ModelMap modelMap,
            @RequestParam("idPlan") long idPlan
            )
    {
        PlanAnual miPlan = PlanAnual.findPlanAnual(idPlan);
        logger.debug("miPlan.descripcion: " + miPlan.getDescripcion());
        modelMap.addAttribute("plan", miPlan);
        modelMap.addAttribute("idPlan", idPlan);
      
        return "planificadoractuacion/index";
    }

    @RequestMapping(value = "/planificadoractuacion/crear", method = RequestMethod.POST)
    public void crear(                    
                    @RequestParam ("inicio") String inicio,
                    @RequestParam ("fin") String fin,
                    @RequestParam ("idPlan") long idPlan,
                    @RequestParam ("idActuacion") long idActuacion,
                    ModelMap modelMap,
                    HttpServletRequest request,
                    HttpServletResponse response
                    ) throws ParseException {
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        ItemPlanificacionActuacion miItem = new ItemPlanificacionActuacion();
        
        String arregloI[] = inicio.split("_");
        String fechaInicialS = "01/" + arregloI[1] + "/2010";                
        Date fechaInicial = null;
        fechaInicial = sdf.parse(fechaInicialS);
        //miItem.setInicioEstimado(fechaInicial);
                
        String arregloF[] = fin.split("_");
        String fechaFinalS = "30/" + arregloF[1] + "/2010";                
        Date fechaFinal = null;
        fechaFinal = sdf.parse(fechaFinalS);
        //miItem.setFinEstimado(fechaFinal);

        miItem.setPlanAnual(PlanAnual.findPlanAnual(idPlan));

        Actuacion actuacion = Actuacion.findActuacion(idActuacion);
        miItem.setActuacion(actuacion);

        miItem.persist();
        Util.registrarEntradaEnBitacora(miItem, "Para la actuacion "+ actuacion.toStringLimitado() + " se creó la planificación ", request.getRemoteAddr());
        
    }

    @RequestMapping(value="/planificadoractuacion/getitems", method=RequestMethod.GET)
    public @ResponseBody String getItemPlanificacionActuacions(@RequestParam("idPlan") long idPlan) {        
        PlanAnual miPlanAnual = PlanAnual.findPlanAnual(idPlan);
        Query queryItems = ItemPlanificacionActuacion.findItemPlanificacionActuacionsByPlanAnual(miPlanAnual);
        List<ItemPlanificacionActuacion> items = queryItems.getResultList();        
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try{
            json = mapper.writeValueAsString(items);
        }
        catch(Exception e){
            logger.error("error fatal: " + e);
        }

        return json;
    }

    @RequestMapping(value="/planificadoractuacion/getactuaciones", method=RequestMethod.GET)
    public @ResponseBody String getActuaciones() {

        List<Actuacion> actuaciones = Actuacion.findAllActuacions();
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try{
            json = mapper.writeValueAsString(actuaciones);
        }
        catch(Exception e){
            logger.error("error fatal: " + e);
        }

        return json;
    }

}
