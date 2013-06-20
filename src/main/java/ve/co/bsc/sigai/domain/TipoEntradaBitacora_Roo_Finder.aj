package ve.co.bsc.sigai.domain;

import java.lang.String;
import java.lang.SuppressWarnings;
import javax.persistence.EntityManager;
import javax.persistence.Query;

privileged aspect TipoEntradaBitacora_Roo_Finder {
    
    @SuppressWarnings("unchecked")
    public static Query TipoEntradaBitacora.findTipoEntradaBitacorasByClase(String clase) {
        if (clase == null || clase.length() == 0) throw new IllegalArgumentException("The clase argument is required");
        EntityManager em = TipoEntradaBitacora.entityManager();
        Query q = em.createQuery("SELECT TipoEntradaBitacora FROM TipoEntradaBitacora AS tipoentradabitacora WHERE tipoentradabitacora.clase = :clase");
        q.setParameter("clase", clase);
        return q;
    }
    
}
