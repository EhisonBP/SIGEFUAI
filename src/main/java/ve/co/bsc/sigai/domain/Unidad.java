package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * Representa una dependencia sujeta a control
 * 
 * @author carlosalvite
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity(finders = { "findUnidadsByNombreEquals", "findUnidadsByRif" })
public class Unidad implements ObjetoNombreClase, Serializable {

    @NotNull
    @Size(min = 2)
    private String nombre;

    @ManyToOne(targetEntity = OrganismoEnte.class)
    @JoinColumn
    private OrganismoEnte rif;

    @Lob
    @Size(min = 0, max = 10000)
    @Column(length = 10000)
    private String descripcion;

    @ManyToOne(targetEntity = ve.co.bsc.sigai.domain.Unidad.class)
    @JoinColumn
    private ve.co.bsc.sigai.domain.Unidad auditado;

    @ManyToOne(targetEntity = TipoUnidad.class)
    @JoinColumn
    private TipoUnidad tipoUnidad;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<PlanDeAccion> planesDeAccion = new HashSet<PlanDeAccion>();

    @Lob
    @Size(min = 0, max = 10000)
    @Column(length = 10000)
    private String mision;

    @Lob
    @Size(min = 0, max = 10000)
    @Column(length = 10000)
    private String vision;

    @Lob
    @Size(min = 0, max = 10000)
    @Column(length = 10000)
    private String funciones;

    @ManyToOne(targetEntity = Auditado.class)
    @JoinColumn
    private Auditado responsable;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getNombre()).append(" ");
        return sb.toString();
    }

    public String toStringExtendido() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nombre: ").append(getNombre()).append(", ");
        sb.append("Descripcion: ").append(getDescripcion());
        sb.append("Auditado: ").append(getAuditado() == null ? "n/a" : getAuditado().toStringLimitado());
        sb.append("TipoUnidad: ").append(getTipoUnidad() == null ? "n/a" : getTipoUnidad().toStringLimitado());
        return sb.toString();
    }

    public String toStringLimitado() {
        StringBuilder sb = new StringBuilder();
        sb.append(getNombre()).append(", ");
        return sb.toString();
    }

    @Override
    public String getNombreClase() {
        return "Unidad";
    }
}
