package ve.co.bsc.sigai.domain;

import java.lang.SuppressWarnings;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import ve.co.bsc.sigai.domain.ActividadActuacion;

privileged aspect AvanceActuacion_Roo_Finder {
    
    @SuppressWarnings("unchecked")
    public static Query AvanceActuacion.findAvanceActuacionsByCodigoActividad(ActividadActuacion codigoActividad) {
        if (codigoActividad == null) throw new IllegalArgumentException("The codigoActividad argument is required");
        EntityManager em = AvanceActuacion.entityManager();
        Query q = em.createQuery("SELECT AvanceActuacion FROM AvanceActuacion AS avanceactuacion WHERE avanceactuacion.codigoActividad = :codigoActividad");
        q.setParameter("codigoActividad", codigoActividad);
        return q;
    }
    
}
