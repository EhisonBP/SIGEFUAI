package ve.co.bsc.sigai.domain;

import java.lang.SuppressWarnings;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import ve.co.bsc.sigai.domain.Observacion;

privileged aspect Alegato_Roo_Finder {
    
    @SuppressWarnings("unchecked")
    public static Query Alegato.findAlegatoesByObservacion(Observacion observacion) {
        if (observacion == null) throw new IllegalArgumentException("The observacion argument is required");
        EntityManager em = Alegato.entityManager();
        Query q = em.createQuery("SELECT Alegato FROM Alegato AS alegato WHERE alegato.observacion = :observacion");
        q.setParameter("observacion", observacion);
        return q;
    }
    
}
