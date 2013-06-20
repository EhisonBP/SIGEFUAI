package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.Query;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;
import org.apache.log4j.Logger;

/**
 * Representa un hallazgo relacionado a una prueba
 * @author carlosalvite
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity(finders = { "findObservacionsByPrueba" })
public class Observacion implements ObjetoComentable ,ObjetoNombreClase, Serializable {

    private static Logger logger = Logger.getLogger(Observacion.class);

    @Lob
    @Size(min = 0, max = 10000)
    @Column(length = 10000)
    private String criterio;

    @Lob
    @Size(min = 0, max = 10000)
    @Column(length = 10000)
    private String condicion;

    @Lob
    @Size(min = 0, max = 10000)
    @Column(length = 10000)
    private String causa;

    @Lob
    @Size(min = 0, max = 10000)
    @Column(length = 10000)
    private String efecto;

    @ManyToOne(targetEntity = Prueba.class)
    @JoinColumn
    private Prueba prueba;

    @ManyToOne(targetEntity = EstadoObservacion.class)
    @JoinColumn
    private EstadoObservacion estadoObservacion;

    @NotNull
    private String referencia;

    @NotNull
    @Size(min = 2, max = 50)
    private String descripcionCorta;

    @Lob
    @Size(min = 0, max = 10000)
    @Column(length = 10000)
    private String redaccion;

    @Lob
    @Size(min = 0, max = 10000)
    @Column(length = 10000)
    private String comentarios;

    @Lob
    @Size(min = 0, max = 10000)
    @Column(length = 10000)
    private String conclusion;


    @Transient
    private String[] accionesPermitidas;


    public void setAccionesPermitidasFromString(String commaSeparadtedStringWithActionsList) {
        logger.debug("Setting acciones permitidas desde string con: " + commaSeparadtedStringWithActionsList);

        this.setAccionesPermitidas(commaSeparadtedStringWithActionsList.split(","));

    }


    public String toStringExtendido() {
        StringBuilder sb = new StringBuilder();
        sb.append("Criterio: ").append(getCriterio()).append(", ");
        sb.append("Condicion: ").append(getCondicion()).append(", ");
        sb.append("Causa: ").append(getCausa()).append(", ");
        sb.append("Efecto: ").append(getEfecto()).append(", ");
        sb.append("Prueba: ").append(getPrueba() == null ? "n/a" : getPrueba().toStringLimitado()).append(", ");
        sb.append("EstadoObservacion: ").append(getEstadoObservacion() == null ? "n/a" : getEstadoObservacion().toStringLimitado()).append(", ");
        sb.append("Referencia: ").append(getReferencia()).append(", ");
        sb.append("DescripcionCorta: ").append(getDescripcionCorta()).append(", ");
        sb.append("Redaccion: ").append(getRedaccion()).append(", ");
        sb.append("Comentarios: ").append(getComentarios()).append(", ");
        sb.append("Conclusion: ").append(getConclusion());
        return sb.toString();
    }

    public String toStringLimitado() {
        StringBuilder sb = new StringBuilder();
        sb.append(getDescripcionCorta()).append(" ");
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Referencia: ").append(getReferencia()).append(", ");
        sb.append("DescripcionCorta: ").append(getDescripcionCorta()).append(", ");
        //sb.append("Conclusion: ").append(getConclusion());
        return sb.toString();
    }

    @SuppressWarnings("unchecked")
    public static Query findObservacionesByPruebaSinPlanDeAccion(Prueba prueba) {
        if (prueba == null) throw new IllegalArgumentException("The prueba argument is required");
        EntityManager em = Observacion.entityManager();
        Query q = em.createQuery("SELECT Observacion FROM Observacion AS observacion WHERE (observacion.prueba = :prueba AND observacion NOT IN (SELECT p.observacion FROM PlanDeAccion AS P))");
        q.setParameter("prueba", prueba);
        return q;
    }

    @Override
    public String getNombreClase()
    {
        return "Hallazgo";
    }
}
