package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;
import ve.co.bsc.sigai.domain.Estado;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import ve.co.bsc.sigai.domain.Ciudad;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@RooJavaBean
@RooToString
@RooEntity
public class CodigoArea implements Cloneable, Serializable {

    private Integer Codigo;

    private String Nombre;

    @ManyToOne(targetEntity = Estado.class)
    @JoinColumn
    private Estado estado;

    @ManyToOne(targetEntity = Ciudad.class)
    @JoinColumn
    private Ciudad ciudad;

    private String TipoTelefono;

    //@NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date fechaCreacion;

    //@NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date fechaModificacion;
}
