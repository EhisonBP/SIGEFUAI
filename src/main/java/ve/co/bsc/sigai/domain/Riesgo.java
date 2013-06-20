package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;
import javax.validation.constraints.Size;
import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotNull;

/**
 * Representa un riesgo de una auditoría de procesos
 * @author carlosalvite
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity(finders = { "findRiesgoesByProceso" })
public class Riesgo implements ObjetoNombreClase, Serializable {

    @Lob
    @Size(min = 1, max = 10000)
    @Column(length = 10000)
    private String descripcion;

    @ManyToOne(targetEntity = Proceso.class)
    @JoinColumn
    private Proceso proceso;

    private Boolean inactivo;

    @NotNull
    private Integer referencia;

    @ManyToOne(targetEntity = ClasificacionRiesgo.class)
    @JoinColumn
    private ClasificacionRiesgo clasificacionRiesgo;

    @ManyToOne(targetEntity = FrecuenciaOcurrenciaRiesgo.class)
    @JoinColumn
    private FrecuenciaOcurrenciaRiesgo frecuenciaOcurrenciaRiesgo;

    public String toStringExtendido() {
        StringBuilder sb = new StringBuilder();
        sb.append("Descripcion: ").append(getDescripcion()).append(", ");
        sb.append("Proceso: ").append(getProceso() == null ? "n/a" : getProceso().toStringLimitado());
        return sb.toString();
    }

    public String toStringLimitado() {
        StringBuilder sb = new StringBuilder();
        sb.append(getDescripcion()).append(" ");
        return sb.toString();
    }

    @Override
    public String getNombreClase() {
        return "Riesgo";
    }
}
