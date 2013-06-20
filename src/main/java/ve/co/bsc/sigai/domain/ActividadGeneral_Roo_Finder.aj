package ve.co.bsc.sigai.domain;

import java.lang.SuppressWarnings;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import ve.co.bsc.sigai.domain.Actuacion;

privileged aspect ActividadGeneral_Roo_Finder {
    
    @SuppressWarnings("unchecked")
    public static Query ActividadGeneral.findActividadGeneralsByCodigoActuacion(Actuacion codigoActuacion) {
        if (codigoActuacion == null) throw new IllegalArgumentException("The codigoActuacion argument is required");
        EntityManager em = ActividadGeneral.entityManager();
        Query q = em.createQuery("SELECT ActividadGeneral FROM ActividadGeneral AS actividadgeneral WHERE actividadgeneral.codigoActuacion = :codigoActuacion");
        q.setParameter("codigoActuacion", codigoActuacion);
        return q;
    }
    
}
