package ve.co.bsc.sigai.domain;

import java.lang.SuppressWarnings;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import ve.co.bsc.sigai.domain.OrganismoEnte;

privileged aspect AutoridadMaxima_Roo_Finder {
    
    @SuppressWarnings("unchecked")
    public static Query AutoridadMaxima.findAutoridadMaximasByRif(OrganismoEnte rif) {
        if (rif == null) throw new IllegalArgumentException("The rif argument is required");
        EntityManager em = AutoridadMaxima.entityManager();
        Query q = em.createQuery("SELECT AutoridadMaxima FROM AutoridadMaxima AS autoridadmaxima WHERE autoridadmaxima.rif = :rif");
        q.setParameter("rif", rif);
        return q;
    }
    
}
