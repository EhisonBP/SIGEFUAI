package ve.co.bsc.sigai.domain;

import java.lang.Boolean;
import java.lang.String;
import java.lang.SuppressWarnings;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import ve.co.bsc.sigai.domain.OrganismoEnte;

privileged aspect EstructuraOrganizativa_Roo_Finder {
    
    @SuppressWarnings("unchecked")
    public static Query EstructuraOrganizativa.findEstructuraOrganizativasByNombreUnidadEquals(String nombreUnidad) {
        if (nombreUnidad == null || nombreUnidad.length() == 0) throw new IllegalArgumentException("The nombreUnidad argument is required");
        EntityManager em = EstructuraOrganizativa.entityManager();
        Query q = em.createQuery("SELECT EstructuraOrganizativa FROM EstructuraOrganizativa AS estructuraorganizativa WHERE estructuraorganizativa.nombreUnidad = :nombreUnidad");
        q.setParameter("nombreUnidad", nombreUnidad);
        return q;
    }
    
    @SuppressWarnings("unchecked")
    public static Query EstructuraOrganizativa.findEstructuraOrganizativasByRif(OrganismoEnte rif) {
        if (rif == null) throw new IllegalArgumentException("The rif argument is required");
        EntityManager em = EstructuraOrganizativa.entityManager();
        Query q = em.createQuery("SELECT EstructuraOrganizativa FROM EstructuraOrganizativa AS estructuraorganizativa WHERE estructuraorganizativa.rif = :rif");
        q.setParameter("rif", rif);
        return q;
    }
    
    @SuppressWarnings("unchecked")
    public static Query EstructuraOrganizativa.findEstructuraOrganizativasByVerificarUnidad(Boolean verificarUnidad) {
        if (verificarUnidad == null) throw new IllegalArgumentException("The verificarUnidad argument is required");
        EntityManager em = EstructuraOrganizativa.entityManager();
        Query q = em.createQuery("SELECT EstructuraOrganizativa FROM EstructuraOrganizativa AS estructuraorganizativa WHERE estructuraorganizativa.verificarUnidad = :verificarUnidad");
        q.setParameter("verificarUnidad", verificarUnidad);
        return q;
    }
    
}
