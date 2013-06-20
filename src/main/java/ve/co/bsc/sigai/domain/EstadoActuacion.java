package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Lob;
import javax.persistence.Query;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Representa un estado posible de una actuacion
 * (activa, anulada, verificada, aprobada, etc). El estado de una actuacion
 * altera y es alterado por las reglas del flujo de trabajo.
 * @author jdeoliveira
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity(finders = { "findEstadoActuacionsByNombre" })
public class EstadoActuacion implements ObjetoNombreClase, Serializable {

    @NotNull
    @Size(min = 2)
    private String nombre;

    @NotNull
    @Lob
    @Size(min = 2, max = 1000)
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

    @SuppressWarnings("unchecked")
    public static EstadoActuacion findEstadoActuacionByNombre(String nombre) {
        if (nombre == null || nombre.length() == 0) throw new IllegalArgumentException("The nombre argument is required");
        EntityManager em = EstadoActuacion.entityManager();
        Query q = em.createQuery("SELECT EstadoActuacion FROM EstadoActuacion AS estadoactuacion WHERE estadoactuacion.nombre = :nombre");
        q.setParameter("nombre", nombre);        
        return (EstadoActuacion)q.getSingleResult();
    }

    @Override
    public String getNombreClase()
    {
        return "Estado de Actuacion";
    }
}
