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

/**
 * Representa un estado de una observación
 * @author carlosalvite
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity
public class EstadoObservacion implements ObjetoNombreClase, Serializable {

    @NotNull
    @Size(min = 2)
    private String nombre;

    @Lob
    @Size(min = 0, max = 10000)
    @Column(length = 10000)
    private String descripcion;

    @NotNull
    @Size(min = 2)
    private String descripcionCorta;

    public String toStringExtendido() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nombre: ").append(getNombre()).append(", ");
        sb.append("Descripcion: ").append(getDescripcion()).append(", ");
        sb.append("DescripcionCorta: ").append(getDescripcionCorta());
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
        return "Estado de Observacion";
    }
}
