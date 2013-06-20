package ve.co.bsc.sigai.domain;

import java.lang.SuppressWarnings;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import ve.co.bsc.sigai.domain.Prueba;
import ve.co.bsc.sigai.domain.Riesgo;

privileged aspect TecnicaDeControl_Roo_Finder {
    
    @SuppressWarnings("unchecked")
    public static Query TecnicaDeControl.findTecnicaDeControlsByRiesgo(Riesgo riesgo) {
        if (riesgo == null) throw new IllegalArgumentException("The riesgo argument is required");
        EntityManager em = TecnicaDeControl.entityManager();
        Query q = em.createQuery("SELECT TecnicaDeControl FROM TecnicaDeControl AS tecnicadecontrol WHERE tecnicadecontrol.riesgo = :riesgo");
        q.setParameter("riesgo", riesgo);
        return q;
    }
    
    @SuppressWarnings("unchecked")
    public static Query TecnicaDeControl.findTecnicaDeControlsByPrueba(Set<Prueba> prueba) {
        if (prueba == null) throw new IllegalArgumentException("The prueba argument is required");
        EntityManager em = TecnicaDeControl.entityManager();
        StringBuilder queryBuilder = new StringBuilder("SELECT TecnicaDeControl FROM TecnicaDeControl AS tecnicadecontrol WHERE");
        for (int i = 0; i < prueba.size(); i++) {
            if (i > 0) queryBuilder.append(" AND");
            queryBuilder.append(" :prueba_item").append(i).append(" MEMBER OF tecnicadecontrol.prueba");
        }
        Query q = em.createQuery(queryBuilder.toString());
        int pruebaIndex = 0;
        for (Prueba _prueba: prueba) {
            q.setParameter("prueba_item" + pruebaIndex++, _prueba);
        }
        return q;
    }
    
}
