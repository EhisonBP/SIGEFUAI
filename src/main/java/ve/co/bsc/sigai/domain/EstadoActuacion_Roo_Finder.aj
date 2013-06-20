package ve.co.bsc.sigai.domain;

import java.lang.String;
import java.lang.SuppressWarnings;
import javax.persistence.EntityManager;
import javax.persistence.Query;

privileged aspect EstadoActuacion_Roo_Finder {
    
    @SuppressWarnings("unchecked")
    public static Query EstadoActuacion.findEstadoActuacionsByNombre(String nombre) {
        if (nombre == null || nombre.length() == 0) throw new IllegalArgumentException("The nombre argument is required");
        EntityManager em = EstadoActuacion.entityManager();
        Query q = em.createQuery("SELECT EstadoActuacion FROM EstadoActuacion AS estadoactuacion WHERE estadoactuacion.nombre = :nombre");
        q.setParameter("nombre", nombre);
        return q;
    }
    
}
