package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;

/**
 * Representa el código fuente de una vista implementada de forma dinámica en base de datos (scripting)
 * @author carlosalvite
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity
public class TipoSeccion implements Serializable {

    @Size(min = 0, max = 10000)
    @Column(length = 10000)
    private String codigo;

    private String vista;

    public String toStringExtendido() {
        StringBuilder sb = new StringBuilder();
        sb.append("Codigo: ").append(getCodigo()).append(", ");
        sb.append("Vista: ").append(getVista());
        return sb.toString();
    }

    public String toStringLimitado() {
        StringBuilder sb = new StringBuilder();
        sb.append(getCodigo()).append(", ");
        return sb.toString();
    }
}
