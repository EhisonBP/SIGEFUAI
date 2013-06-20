package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Query;
import org.hibernate.annotations.Type;
/**
 * Representa un reporte
 * @author carlosalvite
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity(finders = { "findReportesByReporte", "findReportesByClave" })
public class Reporte implements Serializable {

    private String nombre;

    @Lob
    @Size(min = 0, max = 10000)
    @Column(length = 10000)
    private String descripcion;

    @Type(type="binary")
    @Column(length = 16777216)
    @Basic(fetch = FetchType.LAZY)
    private byte[] filedata;

    @NotNull
    private String extension;

    @ManyToOne(targetEntity = ve.co.bsc.sigai.domain.Reporte.class)
    @JoinColumn
    private ve.co.bsc.sigai.domain.Reporte reporte;

    private String clave;

    @SuppressWarnings("unchecked")
    public static Query findReportesByReporteNull() {
        EntityManager em = Reporte.entityManager();
        Query q = em.createQuery("SELECT Reporte FROM Reporte AS reporte WHERE reporte.reporte = NULL");
        return q;
    }

    @SuppressWarnings("unchecked")
    public static Query findReportesPorClave(String clave) {
        if (clave == null || clave.length() == 0) throw new IllegalArgumentException("The clave argument is required");
        EntityManager em = Reporte.entityManager();
        Query q = em.createQuery("SELECT Reporte FROM Reporte AS reporte WHERE reporte.clave LIKE :clave");
        q.setParameter("clave","%" + clave + "%");
        return q;
    }
}
