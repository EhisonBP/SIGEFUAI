package ve.co.bsc.sigai.domain;

import java.lang.Long;
import java.util.List;
import ve.co.bsc.sigai.domain.Actuacion;

privileged aspect Actuacion_Roo_Entity {
    
    public static long Actuacion.countActuacions() {
        return (Long) entityManager().createQuery("select count(o) from Actuacion o").getSingleResult();
    }
    
    public static List<Actuacion> Actuacion.findAllActuacions() {
        return entityManager().createQuery("select o from Actuacion o").getResultList();
    }
    
    public static Actuacion Actuacion.findActuacion(Long id) {
        if (id == null) return null;
        return entityManager().find(Actuacion.class, id);
    }
    
    public static List<Actuacion> Actuacion.findActuacionEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from Actuacion o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
