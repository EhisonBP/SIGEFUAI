package ve.co.bsc.sigai.domain;

import java.lang.SuppressWarnings;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import ve.co.bsc.sigai.domain.OrganismoEnte;

privileged aspect OtraActividad_Roo_Finder {
    
    @SuppressWarnings("unchecked")
    public static Query OtraActividad.findOtraActividadsByRif(OrganismoEnte rif) {
        if (rif == null) throw new IllegalArgumentException("The rif argument is required");
        EntityManager em = OtraActividad.entityManager();
        Query q = em.createQuery("SELECT OtraActividad FROM OtraActividad AS otraactividad WHERE otraactividad.rif = :rif");
        q.setParameter("rif", rif);
        return q;
    }
    
}
