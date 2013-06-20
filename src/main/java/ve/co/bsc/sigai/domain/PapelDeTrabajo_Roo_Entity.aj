package ve.co.bsc.sigai.domain;

import java.lang.Long;
import java.util.List;
import ve.co.bsc.sigai.domain.PapelDeTrabajo;

privileged aspect PapelDeTrabajo_Roo_Entity {
    
    public static long PapelDeTrabajo.countPapelDeTrabajoes() {
        return (Long) entityManager().createQuery("select count(o) from PapelDeTrabajo o").getSingleResult();
    }
    
    public static List<PapelDeTrabajo> PapelDeTrabajo.findAllPapelDeTrabajoes() {
        return entityManager().createQuery("select o from PapelDeTrabajo o").getResultList();
    }
    
    public static PapelDeTrabajo PapelDeTrabajo.findPapelDeTrabajo(Long id) {
        if (id == null) return null;
        return entityManager().find(PapelDeTrabajo.class, id);
    }
    
    public static List<PapelDeTrabajo> PapelDeTrabajo.findPapelDeTrabajoEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from PapelDeTrabajo o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
