package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;
import javax.validation.constraints.Size;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import javax.persistence.Lob;

/**
 * Representa un alegato perteneciente a una observación
 * @author jdeoliveira
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity(finders = { "findAlegatoesByObservacion" })
public class Alegato implements ObjetoComentable ,ObjetoNombreClase, Serializable {

    @Lob
    @Size(min = 1, max = 10000)
    @Column(length = 10000)
    private String descripcion;

    @ManyToOne(targetEntity = Observacion.class)
    @JoinColumn
    private Observacion observacion;

    public String toStringExtendido() {
        StringBuilder sb = new StringBuilder();
        sb.append("Descripcion: ").append(getDescripcion()).append(", ");
        sb.append("Observacion: ").append(getObservacion()==null ? "n/a" : getObservacion().toStringLimitado());
        return sb.toString();
    }

    public String toStringLimitado() {
        StringBuilder sb = new StringBuilder();
        sb.append(getDescripcion()).append(", ");
        return sb.toString();
    }

    @Override
    public String getNombreClase()
    {
        return "Alegato";
    }
}
