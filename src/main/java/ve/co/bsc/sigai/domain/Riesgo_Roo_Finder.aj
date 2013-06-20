package ve.co.bsc.sigai.domain;

import java.lang.SuppressWarnings;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import ve.co.bsc.sigai.domain.Proceso;

privileged aspect Riesgo_Roo_Finder {
    
    @SuppressWarnings("unchecked")
    public static Query Riesgo.findRiesgoesByProceso(Proceso proceso) {
        if (proceso == null) throw new IllegalArgumentException("The proceso argument is required");
        EntityManager em = Riesgo.entityManager();
        Query q = em.createQuery("SELECT Riesgo FROM Riesgo AS riesgo WHERE riesgo.proceso = :proceso");
        q.setParameter("proceso", proceso);
        return q;
    }
    
}
