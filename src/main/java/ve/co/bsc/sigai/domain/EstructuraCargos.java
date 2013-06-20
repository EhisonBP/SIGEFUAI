package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@Entity
@RooJavaBean
@RooToString
@RooEntity(finders = { "findEstructuraCargosesByNombreCargoEquals", "findEstructuraCargosesByRif" })
public class EstructuraCargos implements Cloneable, ObjetoComentable, ObjetoNombreClase, Serializable {

    @NotNull
    private String nombreCargo;

    @NotNull
    @Size(max = 250)
    private String descripcionCargo;

    @NotNull
    @Size(max = 250)
    private String funcionesCargo;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date fechaCreacion;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date fechaModificacion;

    @NotNull
    @ManyToOne(targetEntity = EstadoAuditor.class)
    @JoinColumn
    private EstadoAuditor estado;

    @NotNull
    @ManyToOne(targetEntity = EstructuraOrganizativa.class)
    @JoinColumn
    private EstructuraOrganizativa idEstructura;

    @ManyToOne(targetEntity = OrganismoEnte.class)
    @JoinColumn
    private OrganismoEnte rif;

    public Object clone() {
        EstructuraCargos obj = new EstructuraCargos();
        obj.setDescripcionCargo(this.descripcionCargo);
        obj.setEstado(this.estado);
        obj.setFechaCreacion(this.fechaCreacion);
        obj.setFechaModificacion(this.fechaModificacion);
        obj.setFuncionesCargo(this.funcionesCargo);
        obj.setIdEstructura(this.idEstructura);
        obj.setNombreCargo(this.nombreCargo);
        return obj;
    }

    public String getNombreClase() {
        return "Estructura Cargos";
    }
}
