package ve.co.bsc.sigai.domain;

import java.lang.String;
import java.lang.SuppressWarnings;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import ve.co.bsc.sigai.domain.OrganismoEnte;

privileged aspect EstructuraCargos_Roo_Finder {
    
    @SuppressWarnings("unchecked")
    public static Query EstructuraCargos.findEstructuraCargosesByNombreCargoEquals(String nombreCargo) {
        if (nombreCargo == null || nombreCargo.length() == 0) throw new IllegalArgumentException("The nombreCargo argument is required");
        EntityManager em = EstructuraCargos.entityManager();
        Query q = em.createQuery("SELECT EstructuraCargos FROM EstructuraCargos AS estructuracargos WHERE estructuracargos.nombreCargo = :nombreCargo");
        q.setParameter("nombreCargo", nombreCargo);
        return q;
    }
    
    @SuppressWarnings("unchecked")
    public static Query EstructuraCargos.findEstructuraCargosesByRif(OrganismoEnte rif) {
        if (rif == null) throw new IllegalArgumentException("The rif argument is required");
        EntityManager em = EstructuraCargos.entityManager();
        Query q = em.createQuery("SELECT EstructuraCargos FROM EstructuraCargos AS estructuracargos WHERE estructuracargos.rif = :rif");
        q.setParameter("rif", rif);
        return q;
    }
    
}
