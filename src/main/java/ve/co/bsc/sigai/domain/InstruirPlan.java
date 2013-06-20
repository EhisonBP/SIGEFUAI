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
@RooEntity(finders = { "findInstruirPlansByAnoEquals", "findInstruirPlansByEstatus" })
public class InstruirPlan implements Cloneable, ObjetoComentable, ObjetoNombreClase, Serializable {

    @NotNull
    private Integer ano;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date fechaInicio;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date fechaFin;

    @NotNull
    @Size(max = 250)
    private String instruccion;

    @NotNull
    @ManyToOne(targetEntity = EstadoAuditor.class)
    @JoinColumn
    private EstadoAuditor estatus;

    @Override
    public Object clone() {
        InstruirPlan obj = new InstruirPlan();
        obj.setAno(this.ano);
        obj.setEstatus(this.estatus);
        obj.setFechaFin(this.fechaFin);
        obj.setFechaInicio(this.fechaInicio);
        obj.setInstruccion(this.instruccion);
        return obj;
    }

    public String getUrl() {
        return "/instruirplan/" + this.getId();
    }

    public String getEstadoInstruccion() {
        String out = this.getEstatus().getNombre();
        return out;
    }

    @Override
    public String getNombreClase() {
        return "Instruir Plan";
    }
}
