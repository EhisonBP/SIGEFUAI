package ve.co.bsc.sigai.domain;

import java.lang.SuppressWarnings;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import ve.co.bsc.sigai.domain.Observacion;

privileged aspect Recomendacion_Roo_Finder {
    
    @SuppressWarnings("unchecked")
    public static Query Recomendacion.findRecomendacionsByObservacion(Observacion observacion) {
        if (observacion == null) throw new IllegalArgumentException("The observacion argument is required");
        EntityManager em = Recomendacion.entityManager();
        Query q = em.createQuery("SELECT Recomendacion FROM Recomendacion AS recomendacion WHERE recomendacion.observacion = :observacion");
        q.setParameter("observacion", observacion);
        return q;
    }
    
}
