package ve.co.bsc.sigai.domain;

import java.lang.String;
import java.lang.SuppressWarnings;
import javax.persistence.EntityManager;
import javax.persistence.Query;

privileged aspect Personalizacion_Roo_Finder {
    
    @SuppressWarnings("unchecked")
    public static Query Personalizacion.findPersonalizacionsByRifEquals(String Rif) {
        if (Rif == null || Rif.length() == 0) throw new IllegalArgumentException("The Rif argument is required");
        EntityManager em = Personalizacion.entityManager();
        Query q = em.createQuery("SELECT Personalizacion FROM Personalizacion AS personalizacion WHERE personalizacion.Rif = :Rif");
        q.setParameter("Rif", Rif);
        return q;
    }
    
}
