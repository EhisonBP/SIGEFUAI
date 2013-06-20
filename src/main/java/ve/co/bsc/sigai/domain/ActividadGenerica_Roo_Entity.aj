package ve.co.bsc.sigai.domain;

import java.lang.Long;
import java.util.List;
import ve.co.bsc.sigai.domain.ActividadGenerica;

privileged aspect ActividadGenerica_Roo_Entity {
    
    public static long ActividadGenerica.countActividadGenericas() {
        return (Long) entityManager().createQuery("select count(o) from ActividadGenerica o").getSingleResult();
    }
    
    public static List<ActividadGenerica> ActividadGenerica.findAllActividadGenericas() {
        return entityManager().createQuery("select o from ActividadGenerica o").getResultList();
    }
    
    public static ActividadGenerica ActividadGenerica.findActividadGenerica(Long id) {
        if (id == null) return null;
        return entityManager().find(ActividadGenerica.class, id);
    }
    
    public static List<ActividadGenerica> ActividadGenerica.findActividadGenericaEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from ActividadGenerica o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
