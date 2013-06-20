package ve.co.bsc.sigai.domain;

import java.lang.SuppressWarnings;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import ve.co.bsc.sigai.domain.Actuacion;

privileged aspect ObjetivoEspecifico_Roo_Finder {
    
    @SuppressWarnings("unchecked")
    public static Query ObjetivoEspecifico.findObjetivoEspecificoesByActuacion(Actuacion actuacion) {
        if (actuacion == null) throw new IllegalArgumentException("The actuacion argument is required");
        EntityManager em = ObjetivoEspecifico.entityManager();
        Query q = em.createQuery("SELECT ObjetivoEspecifico FROM ObjetivoEspecifico AS objetivoespecifico WHERE objetivoespecifico.actuacion = :actuacion");
        q.setParameter("actuacion", actuacion);
        return q;
    }
    
}
