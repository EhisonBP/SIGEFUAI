package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;
import javax.validation.constraints.NotNull;

/**
 * Representa una unidad de medida de una auditoría o actividad genérica
 * @author carlosalvite
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity
public class UnidadDeMedida implements ObjetoNombreClase, Serializable {

    @NotNull
    private String nombre;

    private String descripcion;

    

    public String toStringExtendido() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nombre: ").append(getNombre()).append(", ");
        sb.append("Descripcion: ").append(getDescripcion());
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
        return "Unidad de Medida";
    }
}
