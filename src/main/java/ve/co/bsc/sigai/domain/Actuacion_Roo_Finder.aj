package ve.co.bsc.sigai.domain;

import java.lang.String;
import java.lang.SuppressWarnings;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import ve.co.bsc.sigai.domain.Actuacion;
import ve.co.bsc.sigai.domain.OrganismoEnte;

privileged aspect Actuacion_Roo_Finder {
    
    @SuppressWarnings("unchecked")
    public static Query Actuacion.findActuacionsByActuacion(Actuacion actuacion) {
        if (actuacion == null) throw new IllegalArgumentException("The actuacion argument is required");
        EntityManager em = Actuacion.entityManager();
        Query q = em.createQuery("SELECT Actuacion FROM Actuacion AS actuacion WHERE actuacion.actuacion = :actuacion");
        q.setParameter("actuacion", actuacion);
        return q;
    }
    
    @SuppressWarnings("unchecked")
    public static Query Actuacion.findActuacionsByCodigo(String codigo) {
        if (codigo == null || codigo.length() == 0) throw new IllegalArgumentException("The codigo argument is required");
        EntityManager em = Actuacion.entityManager();
        Query q = em.createQuery("SELECT Actuacion FROM Actuacion AS actuacion WHERE actuacion.codigo = :codigo");
        q.setParameter("codigo", codigo);
        return q;
    }
    
    @SuppressWarnings("unchecked")
    public static Query Actuacion.findActuacionsByRif(OrganismoEnte rif) {
        if (rif == null) throw new IllegalArgumentException("The rif argument is required");
        EntityManager em = Actuacion.entityManager();
        Query q = em.createQuery("SELECT Actuacion FROM Actuacion AS actuacion WHERE actuacion.rif = :rif");
        q.setParameter("rif", rif);
        return q;
    }
    
}
