package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;
import javax.validation.constraints.Size;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.Query;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;

/**
 * Representa un proceso de una auditoría por procesos
 * @author carlosalvite
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity(finders = { "findProcesoesByProceso" })
public class Proceso implements ObjetoNombreClase, Serializable {

    @Lob
    @Size(min = 1, max = 10000)
    @Column(length = 10000)
    private String descripcion;

    @ManyToOne(targetEntity = ve.co.bsc.sigai.domain.Proceso.class)
    @JoinColumn
    private ve.co.bsc.sigai.domain.Proceso proceso;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Unidad> unidades = new HashSet<Unidad>();

    public String toString() {
        StringBuilder sb = new StringBuilder();
        String desc = getDescripcion();
        String subDesc = desc;
        if (desc.length() > 40) subDesc = desc.substring(0, 40);
        sb.append(subDesc).append(" ");
        return sb.toString();
    }

    /*@SuppressWarnings("unchecked")
    public static Query findProcesoesByUnidadPadres(Unidad unidad) {
        if (unidad == null) throw new IllegalArgumentException("The unidad argument is required");
        EntityManager em = Proceso.entityManager();
        Query q = em.createQuery("SELECT Proceso FROM Proceso AS proceso WHERE proceso.unidad = :unidad AND proceso.proceso = NULL");
        q.setParameter("unidad", unidad);
        return q;
    }*/

    @SuppressWarnings("unchecked")
    public static Query findAllProcesoesPadres() {
        EntityManager em = Proceso.entityManager();
        Query q = em.createQuery("SELECT Proceso FROM Proceso AS proceso WHERE proceso.proceso = NULL");
        return q;
    }
    public String toStringExtendido() {
        StringBuilder sb = new StringBuilder();
        sb.append("Descripcion: ").append(getDescripcion()).append(", ");
        //sb.append("Unidad: ").append(getUnidad()==null ? "n/a" : getUnidad().toStringLimitado()).append(", ");
        sb.append("ProcesoPadre: ").append(getProceso()==null ? "n/a" : getProceso().toStringLimitado());
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
        return "Proceso";
    }
}
