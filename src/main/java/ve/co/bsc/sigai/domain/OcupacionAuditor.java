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
 * Representa la cantidad de tiempo (horas, fecha incio y fin) 
 * en la que un auditor estará ocupado con una actividad auditor.
 * La OcupacionAuditor debe estar relacionada con un plan anual y un cargo auditor  
 *
 * @author adernersissian
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity(finders = { "findOcupacionAuditorsByAuditor", "findOcupacionAuditorsByActividadAuditor", "findOcupacionAuditorsByAuditorAndPlanAnual" })
public class OcupacionAuditor implements ObjetoComentable, ObjetoNombreClase, Serializable {

    private Double horas;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date inicio;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date fin;

    @ManyToOne(targetEntity = CargoAuditor.class)
    @JoinColumn
    private CargoAuditor cargoAuditor;

    @ManyToOne(targetEntity = Auditor.class)
    @JoinColumn
    private Auditor auditor;

    @ManyToOne(targetEntity = PlanAnual.class)
    @JoinColumn
    private PlanAnual planAnual;

    @ManyToOne(targetEntity = ActividadAuditor.class)
    @JoinColumn
    private ActividadAuditor actividadAuditor;

    private String observaciones;

    private Integer porcentajeCompletado;

    private String unidadDeMedida;

    public String toStringExtendido() {
        StringBuilder sb = new StringBuilder();
        sb.append("Horas: ").append(getHoras()).append(", ");
        sb.append("Inicio: ").append(getInicio()).append(", ");
        sb.append("Fin: ").append(getFin()).append(", ");
        sb.append("CargoAuditor: ").append(getCargoAuditor() == null ? "n/a" : getCargoAuditor().toStringLimitado()).append(", ");
        sb.append("PlanAnual: ").append(getPlanAnual() == null ? "n/a" : getPlanAnual().toStringLimitado()).append(", ");
        sb.append("Observaciones: ").append(getObservaciones());
        return sb.toString();
    }

    public String toStringLimitado() {
        StringBuilder sb = new StringBuilder();
        sb.append("Inicio: ").append(getInicio()).append(", ");
        sb.append("Fin: ").append(getFin()).append(", ");
        return sb.toString();
    }

    @Override
    public String getNombreClase() {
        return "Ocupacion Auditor";
    }
}
