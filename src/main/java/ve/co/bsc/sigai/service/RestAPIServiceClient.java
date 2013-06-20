/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ve.co.bsc.sigai.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 *
 * @author jdeoliveira
 */
@Service
public class RestAPIServiceClient implements InitializingBean {

    private static final Logger logger = Logger.getLogger(RestAPIServiceClient.class);
    
    
    // singleton, outside spring
    private static RestAPIServiceClient me;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        RestAPIServiceClient.me = this;
    }

    public static RestAPIServiceClient instance() {
        return me;
    }

    public void updatePlanAnualEstado(Long idPlan, String estado) {
        WebClient client = null;
        try {
            logger.debug("updatePlanAnualEstado entering");
            client = WebClient.create(url);
            client.type("application/x-www-form-urlencoded");
            client.path("sigefuai/rest/api/updatePlanAnualEstado").post("idPlanAnual=" + idPlan + "&estado=" + estado);
            logger.debug("updatePlanAnualEstado done");
        } catch (Exception e) {
            logger.error("No se puede invocar api rest", e);
            throw new RuntimeException(e);
        } finally {
            
        }
    }
    
    
    public void updateActuacionEstado(Long idActuacion, String estado) {
        WebClient client = null;
        try {
            logger.debug("updateActuacionEstado entering");
            client = WebClient.create(url);
            client.type("application/x-www-form-urlencoded");
            client.path("sigefuai/rest/api/updateActuacionEstado").post("idActuacion=" + idActuacion + "&estado=" + estado);
            logger.debug("updateActuacionEstado done");
        } catch (Exception e) {
            logger.error("No se puede invocar api rest", e);
            throw new RuntimeException(e);
        } finally {
            
        }
    }
    
    public void updateActuacionFechaDesdeReal(Long idActuacion, Date fecha) {
        WebClient client = null;
        try {
            logger.debug("updateActuacionFechaDesdeReal entering");
            
            Integer mes = new Integer((new SimpleDateFormat("MM")).format(fecha));
            
            client = WebClient.create(url);
            client.type("application/x-www-form-urlencoded");
            client.path("sigefuai/rest/api/updateActuacionMesDesdeReal").post("idActuacion=" + idActuacion + "&mes=" + mes);
            logger.debug("updateActuacionFechaDesdeReal done");
        } catch (Exception e) {
            logger.error("No se puede invocar api rest", e);
            throw new RuntimeException(e);
        } finally {
            
        }
    }
    
    public void updateActuacionFechaHastaReal(Long idActuacion, Date fecha) {
        WebClient client = null;
        try {
            logger.debug("updateActuacionFechaHastaReal entering");
            
            Integer mes = new Integer((new SimpleDateFormat("MM")).format(fecha));
            
            client = WebClient.create(url);
            client.type("application/x-www-form-urlencoded");
            client.path("sigefuai/rest/api/updateActuacionMesHastaReal").post("idActuacion=" + idActuacion + "&mes=" + mes);
            logger.debug("updateActuacionFechaHastaReal done");
        } catch (Exception e) {
            logger.error("No se puede invocar api rest", e);
            throw new RuntimeException(e);
        } finally {
            
        }
    }
    
}
