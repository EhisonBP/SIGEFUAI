package ve.co.bsc.sigai.domain;

import java.lang.SuppressWarnings;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import ve.co.bsc.sigai.domain.OrganismoEnte;

privileged aspect PlanAnual_Roo_Finder {
    
    @SuppressWarnings("unchecked")
    public static Query PlanAnual.findPlanAnualsByRif(OrganismoEnte rif) {
        if (rif == null) throw new IllegalArgumentException("The rif argument is required");
        EntityManager em = PlanAnual.entityManager();
        Query q = em.createQuery("SELECT PlanAnual FROM PlanAnual AS plananual WHERE plananual.rif = :rif");
        q.setParameter("rif", rif);
        return q;
    }
    
}
