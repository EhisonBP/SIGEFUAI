package ve.co.bsc.sigai.domain;

import java.lang.SuppressWarnings;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import ve.co.bsc.sigai.domain.Actuacion;

privileged aspect Prueba_Roo_Finder {
    
    @SuppressWarnings("unchecked")
    public static Query Prueba.findPruebasByCodigoActuacion(Actuacion codigoActuacion) {
        if (codigoActuacion == null) throw new IllegalArgumentException("The codigoActuacion argument is required");
        EntityManager em = Prueba.entityManager();
        Query q = em.createQuery("SELECT Prueba FROM Prueba AS prueba WHERE prueba.codigoActuacion = :codigoActuacion");
        q.setParameter("codigoActuacion", codigoActuacion);
        return q;
    }
    
}
