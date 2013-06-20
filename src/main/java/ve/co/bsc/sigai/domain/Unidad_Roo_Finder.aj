package ve.co.bsc.sigai.domain;

import java.lang.String;
import java.lang.SuppressWarnings;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import ve.co.bsc.sigai.domain.OrganismoEnte;

privileged aspect Unidad_Roo_Finder {
    
    @SuppressWarnings("unchecked")
    public static Query Unidad.findUnidadsByNombreEquals(String nombre) {
        if (nombre == null || nombre.length() == 0) throw new IllegalArgumentException("The nombre argument is required");
        EntityManager em = Unidad.entityManager();
        Query q = em.createQuery("SELECT Unidad FROM Unidad AS unidad WHERE unidad.nombre = :nombre");
        q.setParameter("nombre", nombre);
        return q;
    }
    
    @SuppressWarnings("unchecked")
    public static Query Unidad.findUnidadsByRif(OrganismoEnte rif) {
        if (rif == null) throw new IllegalArgumentException("The rif argument is required");
        EntityManager em = Unidad.entityManager();
        Query q = em.createQuery("SELECT Unidad FROM Unidad AS unidad WHERE unidad.rif = :rif");
        q.setParameter("rif", rif);
        return q;
    }
    
}
