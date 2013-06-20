package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;
import javax.persistence.Query;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

/**
 * Representa un elemento de agrupación para los papeles de trabajo, actividades generales y pruebas
 * @author carlosalvite
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity(finders = { "findPapelDeTrabajoesByCodigoActuacion", "findPapelDeTrabajoesByActividadActuacion", "findPapelDeTrabajoesByCodigoEquals" })
public class PapelDeTrabajo extends ActividadActuacion implements ObjetoNombreClase, Serializable {

    @ManyToOne(targetEntity = TipoSeccion.class)
    @JoinColumn
    private TipoSeccion tipoSeccion;

    @Lob
    @Size(min = 0, max = 10000)
    @Column(length = 10000)
    private String contenido;


    @Transient
    private String[] accionesPermitidas;


    public void setAccionesPermitidasFromString(String commaSeparadtedStringWithActionsList) {
        //logger.debug("Setting acciones permitidas desde string con: " + commaSeparadtedStringWithActionsList);

        this.setAccionesPermitidas(commaSeparadtedStringWithActionsList.split(","));

    }
    public String getType() {
        return "pt";
    }

    public int getNivel() {
        if (this.getActividadActuacion() != null) {
            if (this.getActividadActuacion() instanceof PapelDeTrabajo) {
                return ((PapelDeTrabajo) this.getActividadActuacion()).getNivel() + 1;
            } else {
                return 0;
            }
        } else {
            return 1;
        }
    }

    @SuppressWarnings("unchecked")
    public static Query findPapelDeTrabajoesByCodigoEqualsAndActuacion(String codigo, Actuacion actuacion) {
        if (codigo == null || codigo.length() == 0) {
            throw new IllegalArgumentException("The codigo argument is required");
        }
        EntityManager em = PapelDeTrabajo.entityManager();
        Query q = em.createQuery("SELECT PapelDeTrabajo FROM PapelDeTrabajo AS papeldetrabajo WHERE papeldetrabajo.codigo = :codigo AND papeldetrabajo.codigoActuacion = :actuacion");
        q.setParameter("codigo", codigo);
        q.setParameter("actuacion", actuacion);
        return q;
    }

    public String toStringExtendido() {
        StringBuilder sb = new StringBuilder();
        sb.append("Codigo: ").append(getCodigoCompleto()).append(", ");
        sb.append("Actuacion: ").append(getCodigoActuacion() == null ? "n/a" : getCodigoActuacion().toStringLimitado()).append(", ");
        sb.append("Descripcion: ").append(getDescripcion() == null ? "n/a" : getDescripcion()).append(", ");
        sb.append("Padre: ").append(getActividadActuacion() == null ? "n/a" : getActividadActuacion().toStringLimitado()).append(", ");
        sb.append("ObjetivoAMitigar: ").append(getObjetivoAMitigar() == null ? "n/a" : getObjetivoAMitigar().toStringLimitado()).append(", ");
        sb.append("TipoSeccion: ").append(getTipoSeccion() == null ? "n/a" : getTipoSeccion().toStringLimitado()).append(", ");
        sb.append("Contenido: ").append(getContenido() == null ? "n/a" : getContenido());
        return sb.toString();
    }

    @Override
    public String getNombreClase()
    {
        return "Seccion";
    }
}
