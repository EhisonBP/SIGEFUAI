package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;

/**
 * Representa una prueba asociada a una auditoría
 * @author carlosalvite
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity(finders = { "findPruebasByCodigoActuacion" })
public class Prueba extends ActividadActuacion implements ObjetoNombreClase, Serializable {

    @NotNull
    @Lob
    @Size(min = 0, max = 10000)
    @Column(length = 10000)
    private String objetivo;

    @NotNull
    @Lob
    @Size(min = 0, max =10000)
    @Column(length = 10000)
    private String procedimiento;

    @Lob
    @Size(min = 0, max = 10000)
    @Column(length = 10000)
    private String resultado;

    private Integer porcentajeCompletitud;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Requerimiento> requerimientos = new HashSet<Requerimiento>();

    public String getType() {
        return "p";
    }

    public String toStringExtendido() {
        StringBuilder sb = new StringBuilder();
        sb.append("Codigo: ").append(getCodigoCompleto()).append(", ");
        sb.append("Actuacion: ").append(getCodigoActuacion() == null ? "n/a" : getCodigoActuacion().toStringLimitado()).append(", ");
        sb.append("Descripcion: ").append(getDescripcion() == null ? "n/a" : getDescripcion()).append(", ");
        sb.append("EstadoActividadActuacion: ").append(getEstadoActividadActuacion() == null ? "n/a" : getEstadoActividadActuacion().toStringLimitado()).append(", ");
        sb.append("Padre: ").append(getActividadActuacion() == null ? "n/a" : getActividadActuacion().toStringLimitado()).append(", ");
        sb.append("ObjetivoAMitigar: ").append(getObjetivoAMitigar() == null ? "n/a" : getObjetivoAMitigar().toStringLimitado()).append(", ");
        sb.append("Objetivo: ").append(getObjetivo() == null ? "n/a" : getObjetivo()).append(", ");
        sb.append("Procedimiento: ").append(getProcedimiento() == null ? "n/a" : getProcedimiento()).append(", ");
        sb.append("Resultado: ").append(getResultado() == null ? "n/a" : getResultado()).append(", ");
        sb.append("PorcentajeCompletitud: ").append(getPorcentajeCompletitud() == null ? "n/a" : getPorcentajeCompletitud()).append(", ");
        return sb.toString();
    }

    @Override
    public String getNombreClase()
    {
        return "Prueba";
    }
}
