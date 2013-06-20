package ve.co.bsc.sigai.domain;

import java.lang.Integer;
import java.lang.SuppressWarnings;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import ve.co.bsc.sigai.domain.EstadoAuditor;

privileged aspect InstruirPlan_Roo_Finder {
    
    @SuppressWarnings("unchecked")
    public static Query InstruirPlan.findInstruirPlansByAnoEquals(Integer ano) {
        if (ano == null) throw new IllegalArgumentException("The ano argument is required");
        EntityManager em = InstruirPlan.entityManager();
        Query q = em.createQuery("SELECT InstruirPlan FROM InstruirPlan AS instruirplan WHERE instruirplan.ano = :ano");
        q.setParameter("ano", ano);
        return q;
    }
    
    @SuppressWarnings("unchecked")
    public static Query InstruirPlan.findInstruirPlansByEstatus(EstadoAuditor estatus) {
        if (estatus == null) throw new IllegalArgumentException("The estatus argument is required");
        EntityManager em = InstruirPlan.entityManager();
        Query q = em.createQuery("SELECT InstruirPlan FROM InstruirPlan AS instruirplan WHERE instruirplan.estatus = :estatus");
        q.setParameter("estatus", estatus);
        return q;
    }
    
}
