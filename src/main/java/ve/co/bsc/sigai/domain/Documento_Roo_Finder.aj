package ve.co.bsc.sigai.domain;

import java.lang.SuppressWarnings;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import ve.co.bsc.sigai.domain.ActividadActuacion;

privileged aspect Documento_Roo_Finder {
    
    @SuppressWarnings("unchecked")
    public static Query Documento.findDocumentoesByActividadActuacion(Set<ActividadActuacion> actividadActuacion) {
        if (actividadActuacion == null) throw new IllegalArgumentException("The actividadActuacion argument is required");
        EntityManager em = Documento.entityManager();
        StringBuilder queryBuilder = new StringBuilder("SELECT Documento FROM Documento AS documento WHERE");
        for (int i = 0; i < actividadActuacion.size(); i++) {
            if (i > 0) queryBuilder.append(" AND");
            queryBuilder.append(" :actividadActuacion_item").append(i).append(" MEMBER OF documento.actividadActuacion");
        }
        Query q = em.createQuery(queryBuilder.toString());
        int actividadActuacionIndex = 0;
        for (ActividadActuacion _actividadactuacion: actividadActuacion) {
            q.setParameter("actividadActuacion_item" + actividadActuacionIndex++, _actividadactuacion);
        }
        return q;
    }
    
}
