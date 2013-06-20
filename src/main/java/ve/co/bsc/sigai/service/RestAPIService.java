/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ve.co.bsc.sigai.service;

import java.util.Date;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import org.apache.log4j.Logger;
import ve.co.bsc.sigai.domain.Actuacion;
import ve.co.bsc.sigai.domain.EstadoActuacion;
import ve.co.bsc.sigai.domain.EstadoPlan;
import ve.co.bsc.sigai.domain.PlanAnual;
/**
 *
 * @author jdeoliveira
 */

@WebService
@Path("/api")
@Produces("application/xml")
public class RestAPIService {

    private Logger logger = Logger.getLogger(RestAPIService.class);
    
    @POST
    @Path("/updatePlanAnualEstado")
    @Consumes("application/x-www-form-urlencoded")
    public void updatePlanAnualEstado(@FormParam("idPlanAnual") Long idPlanAnual, @FormParam("estado") String estado, @Context HttpServletRequest request) {
        try {
            if (!request.getRemoteAddr().equals("127.0.0.1")) {
                logger.fatal("INTENTO DE ACCEDER A SERVICIOS REST REMOTAMENTE DESDE " + request.getRemoteAddr());
                throw new UnsupportedOperationException("INTENTO DE ACCEDER A SERVICIOS REST REMOTAMENTE DESDE " + request.getRemoteAddr());
            }
            logger.debug("updatePlanAnualEstado " + idPlanAnual + " " + estado);
            PlanAnual x = PlanAnual.findPlanAnual(idPlanAnual);
            x.setEstadoPlan(EstadoPlan.findByNombre(estado));
            x.merge();
            logger.debug("updatePlanAnualEstado " + idPlanAnual + " " + estado + " HECHO");
        } catch (Exception e) {
            logger.error("No se pudo actualizar estado del plan " + idPlanAnual + " " + estado , e);
            throw new RuntimeException(e);
        }
    }
    
    
    @POST
    @Path("/updateActuacionEstado")
    @Consumes("application/x-www-form-urlencoded")
    public void updateActuacionEstado(@FormParam("idActuacion") Long idActuacion, @FormParam("estado") String estado, @Context HttpServletRequest request) {
        try {
            if (!request.getRemoteAddr().equals("127.0.0.1")) {
                logger.fatal("INTENTO DE ACCEDER A SERVICIOS REST REMOTAMENTE DESDE " + request.getRemoteAddr());
                throw new UnsupportedOperationException("INTENTO DE ACCEDER A SERVICIOS REST REMOTAMENTE DESDE " + request.getRemoteAddr());
            }
            logger.debug("updateActuacionEstado " + idActuacion + " " + estado);
            Actuacion x = Actuacion.findActuacion(idActuacion);
            x.setEstadoActuacion(EstadoActuacion.findEstadoActuacionByNombre(estado));
            x.merge();
            logger.debug("updateActuacionEstado " + idActuacion + " " + estado + " HECHO");
        } catch (Exception e) {
            logger.error("No se pudo actualizar estado de actuacion " + idActuacion + " " + estado , e);
            throw new RuntimeException(e);
        }
    }
    
    @POST
    @Path("/updateActuacionMesDesdeReal")
    @Consumes("application/x-www-form-urlencoded")
    public void updateActuacionMesDesdeReal(@FormParam("idActuacion") Long idActuacion, @FormParam("mes") Integer mes, @Context HttpServletRequest request) {
        try {
            if (!request.getRemoteAddr().equals("127.0.0.1")) {
                logger.fatal("INTENTO DE ACCEDER A SERVICIOS REST REMOTAMENTE DESDE " + request.getRemoteAddr());
                throw new UnsupportedOperationException("INTENTO DE ACCEDER A SERVICIOS REST REMOTAMENTE DESDE " + request.getRemoteAddr());
            }
            logger.debug("updateActuacionFechaDesdeReal " + idActuacion + " " + mes);
            Actuacion x = Actuacion.findActuacion(idActuacion);
            x.setMesDesdeReal(mes);
            x.merge();
            logger.debug("updateActuacionMesDesdeReal " + idActuacion + " " + mes + " HECHO");
        } catch (Exception e) {
            logger.error("No se pudo actualizar mes desde real de actuacion " + idActuacion + " " + mes , e);
            throw new RuntimeException(e);
        }
    }
    
    @POST
    @Path("/updateActuacionMesHastaReal")
    @Consumes("application/x-www-form-urlencoded")
    public void updateActuacionMesHastaReal(@FormParam("idActuacion") Long idActuacion, @FormParam("mes") Integer mes, @Context HttpServletRequest request) {
        try {
            if (!request.getRemoteAddr().equals("127.0.0.1")) {
                logger.fatal("INTENTO DE ACCEDER A SERVICIOS REST REMOTAMENTE DESDE " + request.getRemoteAddr());
                throw new UnsupportedOperationException("INTENTO DE ACCEDER A SERVICIOS REST REMOTAMENTE DESDE " + request.getRemoteAddr());
            }
            logger.debug("updateActuacionFechaDesdeReal " + idActuacion + " " + mes);
            Actuacion x = Actuacion.findActuacion(idActuacion);
            x.setMesHastaReal(mes);
            x.merge();
            logger.debug("updateActuacionMesHastaReal " + idActuacion + " " + mes + " HECHO");
        } catch (Exception e) {
            logger.error("No se pudo actualizar mes hasta real de actuacion " + idActuacion + " " + mes , e);
            throw new RuntimeException(e);
        }
    }
}
