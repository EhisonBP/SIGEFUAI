package ve.co.bsc.sigai.domain;

import java.lang.String;
import java.lang.SuppressWarnings;
import javax.persistence.EntityManager;
import javax.persistence.Query;

privileged aspect OrganismoEnte_Roo_Finder {
    
    @SuppressWarnings("unchecked")
    public static Query OrganismoEnte.findOrganismoEntesByRifEquals(String Rif) {
        if (Rif == null || Rif.length() == 0) throw new IllegalArgumentException("The Rif argument is required");
        EntityManager em = OrganismoEnte.entityManager();
        Query q = em.createQuery("SELECT OrganismoEnte FROM OrganismoEnte AS organismoente WHERE organismoente.Rif = :Rif");
        q.setParameter("Rif", Rif);
        return q;
    }
    
}
