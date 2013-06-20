package ve.co.bsc.sigai.domain;

import java.lang.SuppressWarnings;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import ve.co.bsc.sigai.domain.ArchivoAdjunto;

privileged aspect HistorialCambios_Roo_Finder {
    
    @SuppressWarnings("unchecked")
    public static Query HistorialCambios.findHistorialCambiosesByArchivoAdjunto(ArchivoAdjunto archivoAdjunto) {
        if (archivoAdjunto == null) throw new IllegalArgumentException("The archivoAdjunto argument is required");
        EntityManager em = HistorialCambios.entityManager();
        Query q = em.createQuery("SELECT HistorialCambios FROM HistorialCambios AS historialcambios WHERE historialcambios.archivoAdjunto = :archivoAdjunto");
        q.setParameter("archivoAdjunto", archivoAdjunto);
        return q;
    }
    
}
