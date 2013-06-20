package ve.co.bsc.sigai.domain;

import java.lang.SuppressWarnings;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import ve.co.bsc.sigai.domain.Actuacion;
import ve.co.bsc.sigai.domain.Auditado;
import ve.co.bsc.sigai.domain.EstatusRequerimiento;

privileged aspect Requerimiento_Roo_Finder {
    
    @SuppressWarnings("unchecked")
    public static Query Requerimiento.findRequerimientoesByActuacion(Actuacion actuacion) {
        if (actuacion == null) throw new IllegalArgumentException("The actuacion argument is required");
        EntityManager em = Requerimiento.entityManager();
        Query q = em.createQuery("SELECT Requerimiento FROM Requerimiento AS requerimiento WHERE requerimiento.actuacion = :actuacion");
        q.setParameter("actuacion", actuacion);
        return q;
    }
    
    @SuppressWarnings("unchecked")
    public static Query Requerimiento.findRequerimientoesByAuditado(Auditado auditado) {
        if (auditado == null) throw new IllegalArgumentException("The auditado argument is required");
        EntityManager em = Requerimiento.entityManager();
        Query q = em.createQuery("SELECT Requerimiento FROM Requerimiento AS requerimiento WHERE requerimiento.auditado = :auditado");
        q.setParameter("auditado", auditado);
        return q;
    }
    
    @SuppressWarnings("unchecked")
    public static Query Requerimiento.findRequerimientoesByEstadoRequerimientoAndAuditado(EstatusRequerimiento estadoRequerimiento, Auditado auditado) {
        if (estadoRequerimiento == null) throw new IllegalArgumentException("The estadoRequerimiento argument is required");
        if (auditado == null) throw new IllegalArgumentException("The auditado argument is required");
        EntityManager em = Requerimiento.entityManager();
        Query q = em.createQuery("SELECT Requerimiento FROM Requerimiento AS requerimiento WHERE requerimiento.estadoRequerimiento = :estadoRequerimiento AND requerimiento.auditado = :auditado");
        q.setParameter("estadoRequerimiento", estadoRequerimiento);
        q.setParameter("auditado", auditado);
        return q;
    }
    
}
