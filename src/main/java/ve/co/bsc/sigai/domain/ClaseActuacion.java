package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Lob;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Representa una clase de actuacion (clasificacion de actuaciones por tipo)
 * @author carlosalvite
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity
public class ClaseActuacion  implements ObjetoNombreClase, Serializable {

    @NotNull
    @Size(min = 2)
    private String nombre;

    @NotNull
    @Lob
    @Size(min=2, max=1000)
    private String descripcion;

    public String toStringExtendido() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nombre: ").append(getNombre()).append(", ");
        sb.append("Descripcion: ").append(getDescripcion());
        return sb.toString();
    }

    public String toStringLimitado() {
        StringBuilder sb = new StringBuilder();
        sb.append(getNombre()).append(", ");
        return sb.toString();
    }

    public String getNombreClase()
    {
        return "Clase de Actuacion";
    }
}
