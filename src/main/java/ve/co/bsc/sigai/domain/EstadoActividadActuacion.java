package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Lob;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.entity.RooEntity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Representa un estado posible de una actividad parte de una actuacion
 * (activa, anulada, verificada, aprobada, etc). El estado de una actividad
 * altera y es alterado por las reglas del flujo de trabajo.
 * @author jdeoliveira
 */
@Entity
@RooJavaBean
//@RooToString
@RooEntity
public class EstadoActividadActuacion implements ObjetoNombreClase, Serializable {

    @NotNull
    @Size(min = 2)
    private String nombre;

    @NotNull
    @Lob
    @Size(min=2, max=1000)
    private String descripcion;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getNombre()).append(" ");
        return sb.toString();
    }

    public String toStringExtendido() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nombre: ").append(getNombre()).append(", ");
        sb.append("Descripcion: ").append(getDescripcion()).append(" ");
        return sb.toString();
    }

    public String toStringLimitado() {
        StringBuilder sb = new StringBuilder();
        sb.append(getNombre()).append(" ");
        return sb.toString();
    }

    @Override
    public String getNombreClase()
    {
        return "Estado de Actividad";
    }
}
