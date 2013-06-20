package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;
import javax.validation.constraints.NotNull;
import javax.persistence.Lob;
import javax.persistence.Column;
import javax.validation.constraints.Size;

/**
 * Representa un estatus de un requerimiento
 * @author carlosalvite
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity
public class EstatusRequerimiento implements Serializable {

    @NotNull
    private String nombre;

    @Lob
    @Size(min = 0, max = 10000)
    @Column(length = 10000)
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
}
