package ve.co.bsc.sigai.domain;

import java.lang.SuppressWarnings;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import ve.co.bsc.sigai.domain.Actuacion;
import ve.co.bsc.sigai.domain.OtraActividad;
import ve.co.bsc.sigai.domain.PlanAnual;

privileged aspect ItemPlanificacionActuacion_Roo_Finder {
    
    @SuppressWarnings("unchecked")
    public static Query ItemPlanificacionActuacion.findItemPlanificacionActuacionsByPlanAnual(PlanAnual planAnual) {
        if (planAnual == null) throw new IllegalArgumentException("The planAnual argument is required");
        EntityManager em = ItemPlanificacionActuacion.entityManager();
        Query q = em.createQuery("SELECT ItemPlanificacionActuacion FROM ItemPlanificacionActuacion AS itemplanificacionactuacion WHERE itemplanificacionactuacion.planAnual = :planAnual");
        q.setParameter("planAnual", planAnual);
        return q;
    }
    
    @SuppressWarnings("unchecked")
    public static Query ItemPlanificacionActuacion.findItemPlanificacionActuacionsByActuacion(Actuacion actuacion) {
        if (actuacion == null) throw new IllegalArgumentException("The actuacion argument is required");
        EntityManager em = ItemPlanificacionActuacion.entityManager();
        Query q = em.createQuery("SELECT ItemPlanificacionActuacion FROM ItemPlanificacionActuacion AS itemplanificacionactuacion WHERE itemplanificacionactuacion.actuacion = :actuacion");
        q.setParameter("actuacion", actuacion);
        return q;
    }
    
    @SuppressWarnings("unchecked")
    public static Query ItemPlanificacionActuacion.findItemPlanificacionActuacionsByOtraActividad(OtraActividad otraActividad) {
        if (otraActividad == null) throw new IllegalArgumentException("The otraActividad argument is required");
        EntityManager em = ItemPlanificacionActuacion.entityManager();
        Query q = em.createQuery("SELECT ItemPlanificacionActuacion FROM ItemPlanificacionActuacion AS itemplanificacionactuacion WHERE itemplanificacionactuacion.otraActividad = :otraActividad");
        q.setParameter("otraActividad", otraActividad);
        return q;
    }
    
}
