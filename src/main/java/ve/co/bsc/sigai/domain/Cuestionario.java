package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;

/**
 * Representa un cuestionario
 * @author carlosalvite
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity(finders = { "findCuestionariosByActuacion", "findCuestionariosByRespondido" })
public class Cuestionario implements ObjetoNombreClase, Serializable {

    @NotNull
    private String nombre;

    private String descripcion;

    @ManyToOne(targetEntity = Actuacion.class)
    @JoinColumn
    private Actuacion actuacion;

    private Integer correlativo;

    private Boolean respondido;

    @ManyToOne(targetEntity = Auditado.class)
    @JoinColumn
    private Auditado auditado;
    
    @ManyToOne(targetEntity = Unidad.class)
    @JoinColumn
    private Unidad unidad;

    public String toStringExtendido() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nombre: ").append(getNombre()).append(", ");
        sb.append("Descripcion: ").append(getDescripcion()).append(", ");
        return sb.toString();
    }

    public String toStringLimitado() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nombre: ").append(getNombre()).append(", ");
        return sb.toString();
    }

    @SuppressWarnings("unchecked")
    public static Query findCuestionariosByActuacionAndRespondidoTrue(Actuacion actuacion) {
        if (actuacion == null) throw new IllegalArgumentException("The actuacion argument is required");
        EntityManager em = Cuestionario.entityManager();
        Query q = em.createQuery("SELECT Cuestionario FROM Cuestionario AS cuestionario WHERE cuestionario.actuacion = :actuacion AND cuestionario.respondido = true");
        q.setParameter("actuacion", actuacion);
        return q;
    }

    @SuppressWarnings("unchecked")
    public static Query findCuestionariosByAuditadoAndNotAnswered(Auditado auditado) {
        if (auditado == null) throw new IllegalArgumentException("The auditado argument is required");
        EntityManager em = Cuestionario.entityManager();
        Query q = em.createQuery("SELECT Cuestionario FROM Cuestionario AS cuestionario WHERE cuestionario.auditado = :auditado AND cuestionario.respondido = FALSE");
        q.setParameter("auditado", auditado);
        return q;
    }

    @Override
    public String getNombreClase() {
        return "Cuestionario";
    }
}
