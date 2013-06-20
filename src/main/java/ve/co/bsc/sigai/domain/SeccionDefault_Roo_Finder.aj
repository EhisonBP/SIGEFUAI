package ve.co.bsc.sigai.domain;

import java.lang.SuppressWarnings;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import ve.co.bsc.sigai.domain.Biblioteca;
import ve.co.bsc.sigai.domain.SeccionDefault;

privileged aspect SeccionDefault_Roo_Finder {
    
    @SuppressWarnings("unchecked")
    public static Query SeccionDefault.findSeccionDefaultsBySeccionDefault(SeccionDefault seccionDefault) {
        if (seccionDefault == null) throw new IllegalArgumentException("The seccionDefault argument is required");
        EntityManager em = SeccionDefault.entityManager();
        Query q = em.createQuery("SELECT SeccionDefault FROM SeccionDefault AS secciondefault WHERE secciondefault.seccionDefault = :seccionDefault");
        q.setParameter("seccionDefault", seccionDefault);
        return q;
    }
    
    @SuppressWarnings("unchecked")
    public static Query SeccionDefault.findSeccionDefaultsByBiblioteca(Biblioteca biblioteca) {
        if (biblioteca == null) throw new IllegalArgumentException("The biblioteca argument is required");
        EntityManager em = SeccionDefault.entityManager();
        Query q = em.createQuery("SELECT SeccionDefault FROM SeccionDefault AS secciondefault WHERE secciondefault.biblioteca = :biblioteca");
        q.setParameter("biblioteca", biblioteca);
        return q;
    }
    
}
