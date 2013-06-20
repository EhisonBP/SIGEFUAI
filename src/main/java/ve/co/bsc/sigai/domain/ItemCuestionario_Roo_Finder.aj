package ve.co.bsc.sigai.domain;

import java.lang.SuppressWarnings;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import ve.co.bsc.sigai.domain.Cuestionario;

privileged aspect ItemCuestionario_Roo_Finder {
    
    @SuppressWarnings("unchecked")
    public static Query ItemCuestionario.findItemCuestionariosByCuestionario(Cuestionario cuestionario) {
        if (cuestionario == null) throw new IllegalArgumentException("The cuestionario argument is required");
        EntityManager em = ItemCuestionario.entityManager();
        Query q = em.createQuery("SELECT ItemCuestionario FROM ItemCuestionario AS itemcuestionario WHERE itemcuestionario.cuestionario = :cuestionario");
        q.setParameter("cuestionario", cuestionario);
        return q;
    }
    
}
