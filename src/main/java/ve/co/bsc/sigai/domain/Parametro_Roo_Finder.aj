package ve.co.bsc.sigai.domain;

import java.lang.SuppressWarnings;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import ve.co.bsc.sigai.domain.Reporte;

privileged aspect Parametro_Roo_Finder {
    
    @SuppressWarnings("unchecked")
    public static Query Parametro.findParametroesByReporte(Reporte reporte) {
        if (reporte == null) throw new IllegalArgumentException("The reporte argument is required");
        EntityManager em = Parametro.entityManager();
        Query q = em.createQuery("SELECT Parametro FROM Parametro AS parametro WHERE parametro.reporte = :reporte");
        q.setParameter("reporte", reporte);
        return q;
    }
    
}
