package ve.co.bsc.sigai.domain;

import java.lang.String;
import java.lang.SuppressWarnings;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import ve.co.bsc.sigai.domain.OrganismoEnte;

privileged aspect Auditado_Roo_Finder {
    
    @SuppressWarnings("unchecked")
    public static Query Auditado.findAuditadoesByLogin(String login) {
        if (login == null || login.length() == 0) throw new IllegalArgumentException("The login argument is required");
        EntityManager em = Auditado.entityManager();
        Query q = em.createQuery("SELECT Auditado FROM Auditado AS auditado WHERE auditado.login = :login");
        q.setParameter("login", login);
        return q;
    }
    
    @SuppressWarnings("unchecked")
    public static Query Auditado.findAuditadoesByCedulaEquals(String cedula) {
        if (cedula == null || cedula.length() == 0) throw new IllegalArgumentException("The cedula argument is required");
        EntityManager em = Auditado.entityManager();
        Query q = em.createQuery("SELECT Auditado FROM Auditado AS auditado WHERE auditado.cedula = :cedula");
        q.setParameter("cedula", cedula);
        return q;
    }
    
    @SuppressWarnings("unchecked")
    public static Query Auditado.findAuditadoesByIdOrganismoEnte(OrganismoEnte idOrganismoEnte) {
        if (idOrganismoEnte == null) throw new IllegalArgumentException("The idOrganismoEnte argument is required");
        EntityManager em = Auditado.entityManager();
        Query q = em.createQuery("SELECT Auditado FROM Auditado AS auditado WHERE auditado.idOrganismoEnte = :idOrganismoEnte");
        q.setParameter("idOrganismoEnte", idOrganismoEnte);
        return q;
    }
    
}
