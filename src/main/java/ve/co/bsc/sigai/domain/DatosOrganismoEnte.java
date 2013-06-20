package ve.co.bsc.sigai.domain;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@Entity
@RooJavaBean
@RooToString
@RooEntity(finders = { "findDatosOrganismoEntesByRifEquals" })
public class DatosOrganismoEnte {

    private String Rif;

    private String pagina_web;

    @Size(max = 1000)
    private String direcion;

    private String estado;

    private String ciudad;

    private String municipios;

    private Integer telefono_master;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date fechaCreacion;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date fechaModificacion;

    @ManyToOne(targetEntity = EstadoAuditor.class)
    @JoinColumn
    private EstadoAuditor Estatus;

    @ManyToOne(targetEntity = ve.co.bsc.sigai.domain.CodigoArea.class)
    @JoinColumn
    private ve.co.bsc.sigai.domain.CodigoArea CodigoArea;

    public String NombreCiudad() {
        String print = DatosOrganismoEnte.findDatosOrganismoEnte(this.getId()).getCiudad();
        return print;
    }
}
