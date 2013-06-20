package ve.co.bsc.sigai.domain;

import java.lang.SuppressWarnings;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import ve.co.bsc.sigai.domain.Proceso;

privileged aspect Proceso_Roo_Finder {
    
    @SuppressWarnings("unchecked")
    public static Query Proceso.findProcesoesByProceso(Proceso proceso) {
        if (proceso == null) throw new IllegalArgumentException("The proceso argument is required");
        EntityManager em = Proceso.entityManager();
        Query q = em.createQuery("SELECT Proceso FROM Proceso AS proceso WHERE proceso.proceso = :proceso");
        q.setParameter("proceso", proceso);
        return q;
    }
    
}
