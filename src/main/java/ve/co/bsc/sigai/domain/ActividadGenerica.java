package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;
import javax.validation.constraints.Size;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.persistence.Column;

/**
 * Representa una actividad que será asignada a un auditor
 * Las actividades que no son tomadas como una actuación, se representan como una actividad genérica.
 * Ejm: vacaciones, cursos, etc.
 * @author adernersissian
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity
public class ActividadGenerica extends ActividadAuditor implements ObjetoComentable, ObjetoNombreClase, Serializable {

    @NotNull
    @Lob
    @Size(min = 5, max = 10000)
    @Column(length = 10000)
    private String descripcion;

    private String descripcionCorta;

    public String toStringExtendido() {
        StringBuilder sb = new StringBuilder();
        sb.append("DescripcionGeneral: ").append(getDescripcionGeneral()).append(", ");
        sb.append("Descripcion: ").append(getDescripcion());
        return sb.toString();
    }

    @Override
    public String getNombreClase() {
        return "Actividad Generica";
    }
}
