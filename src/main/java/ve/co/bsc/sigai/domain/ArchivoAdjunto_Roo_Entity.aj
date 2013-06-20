package ve.co.bsc.sigai.domain;

import java.lang.Long;
import java.util.List;
import ve.co.bsc.sigai.domain.ArchivoAdjunto;

privileged aspect ArchivoAdjunto_Roo_Entity {
    
    public static long ArchivoAdjunto.countArchivoAdjuntoes() {
        return (Long) entityManager().createQuery("select count(o) from ArchivoAdjunto o").getSingleResult();
    }
    
    public static List<ArchivoAdjunto> ArchivoAdjunto.findAllArchivoAdjuntoes() {
        return entityManager().createQuery("select o from ArchivoAdjunto o").getResultList();
    }
    
    public static ArchivoAdjunto ArchivoAdjunto.findArchivoAdjunto(Long id) {
        if (id == null) return null;
        return entityManager().find(ArchivoAdjunto.class, id);
    }
    
    public static List<ArchivoAdjunto> ArchivoAdjunto.findArchivoAdjuntoEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from ArchivoAdjunto o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
