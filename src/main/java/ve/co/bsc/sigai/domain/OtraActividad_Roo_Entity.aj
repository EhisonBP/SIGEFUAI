package ve.co.bsc.sigai.domain;

import java.lang.Long;
import java.util.List;
import ve.co.bsc.sigai.domain.OtraActividad;

privileged aspect OtraActividad_Roo_Entity {
    
    public static long OtraActividad.countOtraActividads() {
        return (Long) entityManager().createQuery("select count(o) from OtraActividad o").getSingleResult();
    }
    
    public static List<OtraActividad> OtraActividad.findAllOtraActividads() {
        return entityManager().createQuery("select o from OtraActividad o").getResultList();
    }
    
    public static OtraActividad OtraActividad.findOtraActividad(Long id) {
        if (id == null) return null;
        return entityManager().find(OtraActividad.class, id);
    }
    
    public static List<OtraActividad> OtraActividad.findOtraActividadEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from OtraActividad o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
