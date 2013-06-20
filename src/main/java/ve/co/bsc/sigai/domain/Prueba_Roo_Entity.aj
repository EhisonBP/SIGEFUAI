package ve.co.bsc.sigai.domain;

import java.lang.Long;
import java.util.List;
import ve.co.bsc.sigai.domain.Prueba;

privileged aspect Prueba_Roo_Entity {
    
    public static long Prueba.countPruebas() {
        return (Long) entityManager().createQuery("select count(o) from Prueba o").getSingleResult();
    }
    
    public static List<Prueba> Prueba.findAllPruebas() {
        return entityManager().createQuery("select o from Prueba o").getResultList();
    }
    
    public static Prueba Prueba.findPrueba(Long id) {
        if (id == null) return null;
        return entityManager().find(Prueba.class, id);
    }
    
    public static List<Prueba> Prueba.findPruebaEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from Prueba o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
