package ve.co.bsc.sigai.domain;

import java.lang.String;
import java.lang.SuppressWarnings;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import ve.co.bsc.sigai.domain.OrganismoEnte;

privileged aspect Auditor_Roo_Finder {
    
    @SuppressWarnings("unchecked")
    public static Query Auditor.findAuditorsByCedulaEquals(String cedula) {
        if (cedula == null || cedula.length() == 0) throw new IllegalArgumentException("The cedula argument is required");
        EntityManager em = Auditor.entityManager();
        Query q = em.createQuery("SELECT Auditor FROM Auditor AS auditor WHERE auditor.cedula = :cedula");
        q.setParameter("cedula", cedula);
        return q;
    }
    
    @SuppressWarnings("unchecked")
    public static Query Auditor.findAuditorsByLoginEquals(String login) {
        if (login == null || login.length() == 0) throw new IllegalArgumentException("The login argument is required");
        EntityManager em = Auditor.entityManager();
        Query q = em.createQuery("SELECT Auditor FROM Auditor AS auditor WHERE auditor.login = :login");
        q.setParameter("login", login);
        return q;
    }
    
    @SuppressWarnings("unchecked")
    public static Query Auditor.findAuditorsById_OrganismoEnte(OrganismoEnte id_OrganismoEnte) {
        if (id_OrganismoEnte == null) throw new IllegalArgumentException("The id_OrganismoEnte argument is required");
        EntityManager em = Auditor.entityManager();
        Query q = em.createQuery("SELECT Auditor FROM Auditor AS auditor WHERE auditor.id_OrganismoEnte = :id_OrganismoEnte");
        q.setParameter("id_OrganismoEnte", id_OrganismoEnte);
        return q;
    }
    
}
