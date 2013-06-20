package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

/**
 * Representa un plan de acción asociado a un hallazgo(observación)
 * @author carlosalvite
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity(finders = { "findPlanDeAccionsByObservacion" })
public class PlanDeAccion implements ObjetoComentable,  ObjetoNombreClase, Serializable {

    @NotNull
    private String descripcion;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date fechaCierre;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Unidad> unidad = new HashSet<Unidad>();

    @ManyToOne(targetEntity = EstadoPlanDeAccion.class)
    @JoinColumn
    private EstadoPlanDeAccion estadoPlanDeAccion;

    @ManyToOne(targetEntity = Observacion.class)
    @JoinColumn
    private Observacion observacion;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date fechaCierre2;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date fechaSeguimiento;

    public String toStringExtendido() {
        StringBuilder sb = new StringBuilder();
        sb.append("Descripcion: ").append(getDescripcion()).append(", ");
        sb.append("FechaCierre: ").append(getFechaCierre()).append(", ");
        sb.append("EstadoPlanDeAccion: ").append(getEstadoPlanDeAccion() == null ? "n/a" : getEstadoPlanDeAccion().toStringLimitado()).append(", ");
        sb.append("Hallazgo: ").append(getObservacion() == null ? "n/a" : getObservacion().toStringLimitado()).append(", ");
        sb.append("FechaCierre2: ").append(getFechaCierre2()).append(", ");
        sb.append("FechaSeguimiento: ").append(getFechaSeguimiento());
        return sb.toString();
    }

    public String toStringLimitado() {
        StringBuilder sb = new StringBuilder();
        sb.append(getDescripcion()).append(", ");
        return sb.toString();
    }

    @Override
    public String getNombreClase()
    {
        return "Plan de Accion";
    }
}
