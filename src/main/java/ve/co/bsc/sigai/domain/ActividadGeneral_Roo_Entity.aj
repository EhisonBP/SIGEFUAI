package ve.co.bsc.sigai.domain;

import java.lang.Long;
import java.util.List;
import ve.co.bsc.sigai.domain.ActividadGeneral;

privileged aspect ActividadGeneral_Roo_Entity {
    
    public static long ActividadGeneral.countActividadGenerals() {
        return (Long) entityManager().createQuery("select count(o) from ActividadGeneral o").getSingleResult();
    }
    
    public static List<ActividadGeneral> ActividadGeneral.findAllActividadGenerals() {
        return entityManager().createQuery("select o from ActividadGeneral o").getResultList();
    }
    
    public static ActividadGeneral ActividadGeneral.findActividadGeneral(Long id) {
        if (id == null) return null;
        return entityManager().find(ActividadGeneral.class, id);
    }
    
    public static List<ActividadGeneral> ActividadGeneral.findActividadGeneralEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from ActividadGeneral o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
