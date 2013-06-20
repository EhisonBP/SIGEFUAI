package ve.co.bsc.sigai.domain;

import java.lang.Boolean;
import java.lang.SuppressWarnings;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import ve.co.bsc.sigai.domain.Actuacion;

privileged aspect Cuestionario_Roo_Finder {
    
    @SuppressWarnings("unchecked")
    public static Query Cuestionario.findCuestionariosByActuacion(Actuacion actuacion) {
        if (actuacion == null) throw new IllegalArgumentException("The actuacion argument is required");
        EntityManager em = Cuestionario.entityManager();
        Query q = em.createQuery("SELECT Cuestionario FROM Cuestionario AS cuestionario WHERE cuestionario.actuacion = :actuacion");
        q.setParameter("actuacion", actuacion);
        return q;
    }
    
    @SuppressWarnings("unchecked")
    public static Query Cuestionario.findCuestionariosByRespondido(Boolean respondido) {
        if (respondido == null) throw new IllegalArgumentException("The respondido argument is required");
        EntityManager em = Cuestionario.entityManager();
        Query q = em.createQuery("SELECT Cuestionario FROM Cuestionario AS cuestionario WHERE cuestionario.respondido = :respondido");
        q.setParameter("respondido", respondido);
        return q;
    }
    
}
