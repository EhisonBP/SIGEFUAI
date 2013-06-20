package ve.co.bsc.sigai.domain;

import java.lang.SuppressWarnings;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import ve.co.bsc.sigai.domain.Observacion;

privileged aspect PlanDeAccion_Roo_Finder {
    
    @SuppressWarnings("unchecked")
    public static Query PlanDeAccion.findPlanDeAccionsByObservacion(Observacion observacion) {
        if (observacion == null) throw new IllegalArgumentException("The observacion argument is required");
        EntityManager em = PlanDeAccion.entityManager();
        Query q = em.createQuery("SELECT PlanDeAccion FROM PlanDeAccion AS plandeaccion WHERE plandeaccion.observacion = :observacion");
        q.setParameter("observacion", observacion);
        return q;
    }
    
}
