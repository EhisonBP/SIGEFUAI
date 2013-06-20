package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

/**
 * Representa un historial de cambios asociados a un archivo adjunto
 * @author carlosalvite
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity(finders = { "findHistorialCambiosesByArchivoAdjunto" })
public class HistorialCambios implements Serializable {

    private String accion;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "FM")
    private Date fecha;

    private String usuario;

   
    @ManyToOne(targetEntity = ArchivoAdjunto.class)
    @JoinColumn
    private ArchivoAdjunto archivoAdjunto;

    
}
