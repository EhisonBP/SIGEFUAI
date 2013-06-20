package ve.co.bsc.sigai.domain;

import java.lang.SuppressWarnings;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import ve.co.bsc.sigai.domain.Requerimiento;

privileged aspect Respuesta_Roo_Finder {
    
    @SuppressWarnings("unchecked")
    public static Query Respuesta.findRespuestasByRequerimiento(Requerimiento requerimiento) {
        if (requerimiento == null) throw new IllegalArgumentException("The requerimiento argument is required");
        EntityManager em = Respuesta.entityManager();
        Query q = em.createQuery("SELECT Respuesta FROM Respuesta AS respuesta WHERE respuesta.requerimiento = :requerimiento");
        q.setParameter("requerimiento", requerimiento);
        return q;
    }
    
}
