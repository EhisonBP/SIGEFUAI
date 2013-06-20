package ve.co.bsc.sigai.domain;

import java.lang.SuppressWarnings;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import ve.co.bsc.sigai.domain.ActividadAuditor;
import ve.co.bsc.sigai.domain.Auditor;
import ve.co.bsc.sigai.domain.PlanAnual;

privileged aspect OcupacionAuditor_Roo_Finder {
    
    @SuppressWarnings("unchecked")
    public static Query OcupacionAuditor.findOcupacionAuditorsByAuditor(Auditor auditor) {
        if (auditor == null) throw new IllegalArgumentException("The auditor argument is required");
        EntityManager em = OcupacionAuditor.entityManager();
        Query q = em.createQuery("SELECT OcupacionAuditor FROM OcupacionAuditor AS ocupacionauditor WHERE ocupacionauditor.auditor = :auditor");
        q.setParameter("auditor", auditor);
        return q;
    }
    
    @SuppressWarnings("unchecked")
    public static Query OcupacionAuditor.findOcupacionAuditorsByActividadAuditor(ActividadAuditor actividadAuditor) {
        if (actividadAuditor == null) throw new IllegalArgumentException("The actividadAuditor argument is required");
        EntityManager em = OcupacionAuditor.entityManager();
        Query q = em.createQuery("SELECT OcupacionAuditor FROM OcupacionAuditor AS ocupacionauditor WHERE ocupacionauditor.actividadAuditor = :actividadAuditor");
        q.setParameter("actividadAuditor", actividadAuditor);
        return q;
    }
    
    @SuppressWarnings("unchecked")
    public static Query OcupacionAuditor.findOcupacionAuditorsByAuditorAndPlanAnual(Auditor auditor, PlanAnual planAnual) {
        if (auditor == null) throw new IllegalArgumentException("The auditor argument is required");
        if (planAnual == null) throw new IllegalArgumentException("The planAnual argument is required");
        EntityManager em = OcupacionAuditor.entityManager();
        Query q = em.createQuery("SELECT OcupacionAuditor FROM OcupacionAuditor AS ocupacionauditor WHERE ocupacionauditor.auditor = :auditor AND ocupacionauditor.planAnual = :planAnual");
        q.setParameter("auditor", auditor);
        q.setParameter("planAnual", planAnual);
        return q;
    }
    
}
