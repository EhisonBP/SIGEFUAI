package ve.co.bsc.sigai.domain;

import java.lang.SuppressWarnings;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import ve.co.bsc.sigai.domain.ActividadActuacion;
import ve.co.bsc.sigai.domain.Actuacion;

privileged aspect ActividadActuacion_Roo_Finder {
    
    @SuppressWarnings("unchecked")
    public static Query ActividadActuacion.findActividadActuacionsByCodigoActuacion(Actuacion codigoActuacion) {
        if (codigoActuacion == null) throw new IllegalArgumentException("The codigoActuacion argument is required");
        EntityManager em = ActividadActuacion.entityManager();
        Query q = em.createQuery("SELECT ActividadActuacion FROM ActividadActuacion AS actividadactuacion WHERE actividadactuacion.codigoActuacion = :codigoActuacion");
        q.setParameter("codigoActuacion", codigoActuacion);
        return q;
    }
    
    @SuppressWarnings("unchecked")
    public static Query ActividadActuacion.findActividadActuacionsByActividadActuacion(ActividadActuacion actividadActuacion) {
        if (actividadActuacion == null) throw new IllegalArgumentException("The actividadActuacion argument is required");
        EntityManager em = ActividadActuacion.entityManager();
        Query q = em.createQuery("SELECT ActividadActuacion FROM ActividadActuacion AS actividadactuacion WHERE actividadactuacion.actividadActuacion = :actividadActuacion");
        q.setParameter("actividadActuacion", actividadActuacion);
        return q;
    }
    
}
