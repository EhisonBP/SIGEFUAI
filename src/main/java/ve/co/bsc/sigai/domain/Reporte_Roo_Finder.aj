package ve.co.bsc.sigai.domain;

import java.lang.String;
import java.lang.SuppressWarnings;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import ve.co.bsc.sigai.domain.Reporte;

privileged aspect Reporte_Roo_Finder {
    
    @SuppressWarnings("unchecked")
    public static Query Reporte.findReportesByReporte(Reporte reporte) {
        if (reporte == null) throw new IllegalArgumentException("The reporte argument is required");
        EntityManager em = Reporte.entityManager();
        Query q = em.createQuery("SELECT Reporte FROM Reporte AS reporte WHERE reporte.reporte = :reporte");
        q.setParameter("reporte", reporte);
        return q;
    }
    
    @SuppressWarnings("unchecked")
    public static Query Reporte.findReportesByClave(String clave) {
        if (clave == null || clave.length() == 0) throw new IllegalArgumentException("The clave argument is required");
        EntityManager em = Reporte.entityManager();
        Query q = em.createQuery("SELECT Reporte FROM Reporte AS reporte WHERE reporte.clave = :clave");
        q.setParameter("clave", clave);
        return q;
    }
    
}
