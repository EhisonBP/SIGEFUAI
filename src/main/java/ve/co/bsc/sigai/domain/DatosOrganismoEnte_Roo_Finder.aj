package ve.co.bsc.sigai.domain;

import java.lang.String;
import java.lang.SuppressWarnings;
import javax.persistence.EntityManager;
import javax.persistence.Query;

privileged aspect DatosOrganismoEnte_Roo_Finder {
    
    @SuppressWarnings("unchecked")
    public static Query DatosOrganismoEnte.findDatosOrganismoEntesByRifEquals(String Rif) {
        if (Rif == null || Rif.length() == 0) throw new IllegalArgumentException("The Rif argument is required");
        EntityManager em = DatosOrganismoEnte.entityManager();
        Query q = em.createQuery("SELECT DatosOrganismoEnte FROM DatosOrganismoEnte AS datosorganismoente WHERE datosorganismoente.Rif = :Rif");
        q.setParameter("Rif", Rif);
        return q;
    }
    
}
