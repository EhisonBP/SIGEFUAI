package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NoResultException;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.Type;

/**
 * Representa un archivo adjunto 
 * @author carlosalvite
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity(finders = { "findArchivoAdjuntoesByActividadActuacion", "findArchivoAdjuntoesByLoginUsuario", "findArchivoAdjuntoesByAlegato", "findArchivoAdjuntoesByHallazgo", "findArchivoAdjuntoesByRespuesta", "findArchivoAdjuntoesBySeccionDefault", "findArchivoAdjuntoesByAvanceActuacion", "findArchivoAdjuntoesByRequerimiento", "findArchivoAdjuntoesByPersonalizacion" })
public class ArchivoAdjunto extends Documento implements Serializable {

    private static Logger logger = Logger.getLogger(ArchivoAdjunto.class);

    @NotNull
    @Size(min = 2, max = 90)
    private String codigo;

    
    @Type(type="binary")
    @Column(length = 104857600)
    @Basic(fetch = FetchType.LAZY)
    private byte[] filedata;

    @Size(min = 2)
    private String extension;

    private Long filesize;

    private String loginUsuario;

    @ManyToOne(targetEntity = Respuesta.class)
    @JoinColumn
    private Respuesta respuesta;

    @ManyToOne(targetEntity = Requerimiento.class)
    @JoinColumn
    private Requerimiento requerimiento;

    @ManyToOne(targetEntity = Alegato.class)
    @JoinColumn
    private Alegato alegato;

    @ManyToOne(targetEntity = Observacion.class)
    @JoinColumn
    private Observacion hallazgo;

    @ManyToOne(targetEntity = SeccionDefault.class)
    @JoinColumn
    private SeccionDefault seccionDefault;

    @ManyToOne(targetEntity = AvanceActuacion.class)
    @JoinColumn
    private AvanceActuacion avanceActuacion;

    @ManyToOne(targetEntity = Personalizacion.class)
    @JoinColumn
    private Personalizacion personalizacion;

    public String getType() {
        return "a";
    }
    
    public static ArchivoAdjunto findArchivoAdjuntoCurrentlyLoggedIn() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logger.debug("[principal] es: " + principal);
        try {
            return (ArchivoAdjunto) ArchivoAdjunto.findArchivoAdjuntoesByLoginUsuario(((User) principal).getUsername()).getSingleResult();
        } catch (NoResultException e) {
            logger.error("No se encontro archivo adjunto con login " + ((User) principal).getUsername());
            return null;
        }
    }
    
//    public static EntityManager getEm(){
//        return entityManager();
//    }
}
