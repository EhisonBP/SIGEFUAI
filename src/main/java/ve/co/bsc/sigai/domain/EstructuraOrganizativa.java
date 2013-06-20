package ve.co.bsc.sigai.domain;

import java.io.Serializable;
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

@Entity
@RooJavaBean
@RooEntity(finders = { "findEstructuraOrganizativasByNombreUnidadEquals", "findEstructuraOrganizativasByRif", "findEstructuraOrganizativasByVerificarUnidad" })
public class EstructuraOrganizativa implements ObjetoNombreClase, Serializable {

    @ManyToOne(targetEntity = OrganismoEnte.class)
    @JoinColumn
    private OrganismoEnte rif;

    private String nombreUnidad;

    @Size(max = 1000)
    private String descripcionUnidad;

    @ManyToOne(targetEntity = TipoUnidad.class)
    @JoinColumn
    private TipoUnidad tipoUnidad;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date fechaCreacion;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date fechaModificacion;

    @ManyToOne(targetEntity = EstadoAuditor.class)
    @JoinColumn
    private EstadoAuditor Estatus;

    @ManyToOne(targetEntity = ve.co.bsc.sigai.domain.EstructuraOrganizativa.class)
    @JoinColumn
    private ve.co.bsc.sigai.domain.EstructuraOrganizativa idEstructuraPadre;

    private Boolean verificarUnidad;

    public String getNombreClase() {
        return "Estructura Organizativa";
    }
}
