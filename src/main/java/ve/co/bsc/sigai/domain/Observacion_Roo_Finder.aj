package ve.co.bsc.sigai.domain;

import java.lang.SuppressWarnings;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import ve.co.bsc.sigai.domain.Prueba;

privileged aspect Observacion_Roo_Finder {
    
    @SuppressWarnings("unchecked")
    public static Query Observacion.findObservacionsByPrueba(Prueba prueba) {
        if (prueba == null) throw new IllegalArgumentException("The prueba argument is required");
        EntityManager em = Observacion.entityManager();
        Query q = em.createQuery("SELECT Observacion FROM Observacion AS observacion WHERE observacion.prueba = :prueba");
        q.setParameter("prueba", prueba);
        return q;
    }
    
}
