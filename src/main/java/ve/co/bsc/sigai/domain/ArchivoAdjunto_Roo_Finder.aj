package ve.co.bsc.sigai.domain;

import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import ve.co.bsc.sigai.domain.ActividadActuacion;
import ve.co.bsc.sigai.domain.Alegato;
import ve.co.bsc.sigai.domain.AvanceActuacion;
import ve.co.bsc.sigai.domain.Observacion;
import ve.co.bsc.sigai.domain.Personalizacion;
import ve.co.bsc.sigai.domain.Requerimiento;
import ve.co.bsc.sigai.domain.Respuesta;
import ve.co.bsc.sigai.domain.SeccionDefault;

privileged aspect ArchivoAdjunto_Roo_Finder {
    
    @SuppressWarnings("unchecked")
    public static Query ArchivoAdjunto.findArchivoAdjuntoesByActividadActuacion(Set<ActividadActuacion> actividadActuacion) {
        if (actividadActuacion == null) throw new IllegalArgumentException("The actividadActuacion argument is required");
        EntityManager em = ArchivoAdjunto.entityManager();
        StringBuilder queryBuilder = new StringBuilder("SELECT ArchivoAdjunto FROM ArchivoAdjunto AS archivoadjunto WHERE");
        for (int i = 0; i < actividadActuacion.size(); i++) {
            if (i > 0) queryBuilder.append(" AND");
            queryBuilder.append(" :actividadActuacion_item").append(i).append(" MEMBER OF archivoadjunto.actividadActuacion");
        }
        Query q = em.createQuery(queryBuilder.toString());
        int actividadActuacionIndex = 0;
        for (ActividadActuacion _actividadactuacion: actividadActuacion) {
            q.setParameter("actividadActuacion_item" + actividadActuacionIndex++, _actividadactuacion);
        }
        return q;
    }
    
    @SuppressWarnings("unchecked")
    public static Query ArchivoAdjunto.findArchivoAdjuntoesByLoginUsuario(String loginUsuario) {
        if (loginUsuario == null || loginUsuario.length() == 0) throw new IllegalArgumentException("The loginUsuario argument is required");
        EntityManager em = ArchivoAdjunto.entityManager();
        Query q = em.createQuery("SELECT ArchivoAdjunto FROM ArchivoAdjunto AS archivoadjunto WHERE archivoadjunto.loginUsuario = :loginUsuario");
        q.setParameter("loginUsuario", loginUsuario);
        return q;
    }
    
    @SuppressWarnings("unchecked")
    public static Query ArchivoAdjunto.findArchivoAdjuntoesByAlegato(Alegato alegato) {
        if (alegato == null) throw new IllegalArgumentException("The alegato argument is required");
        EntityManager em = ArchivoAdjunto.entityManager();
        Query q = em.createQuery("SELECT ArchivoAdjunto FROM ArchivoAdjunto AS archivoadjunto WHERE archivoadjunto.alegato = :alegato");
        q.setParameter("alegato", alegato);
        return q;
    }
    
    @SuppressWarnings("unchecked")
    public static Query ArchivoAdjunto.findArchivoAdjuntoesByHallazgo(Observacion hallazgo) {
        if (hallazgo == null) throw new IllegalArgumentException("The hallazgo argument is required");
        EntityManager em = ArchivoAdjunto.entityManager();
        Query q = em.createQuery("SELECT ArchivoAdjunto FROM ArchivoAdjunto AS archivoadjunto WHERE archivoadjunto.hallazgo = :hallazgo");
        q.setParameter("hallazgo", hallazgo);
        return q;
    }
    
    @SuppressWarnings("unchecked")
    public static Query ArchivoAdjunto.findArchivoAdjuntoesByRespuesta(Respuesta respuesta) {
        if (respuesta == null) throw new IllegalArgumentException("The respuesta argument is required");
        EntityManager em = ArchivoAdjunto.entityManager();
        Query q = em.createQuery("SELECT ArchivoAdjunto FROM ArchivoAdjunto AS archivoadjunto WHERE archivoadjunto.respuesta = :respuesta");
        q.setParameter("respuesta", respuesta);
        return q;
    }
    
    @SuppressWarnings("unchecked")
    public static Query ArchivoAdjunto.findArchivoAdjuntoesBySeccionDefault(SeccionDefault seccionDefault) {
        if (seccionDefault == null) throw new IllegalArgumentException("The seccionDefault argument is required");
        EntityManager em = ArchivoAdjunto.entityManager();
        Query q = em.createQuery("SELECT ArchivoAdjunto FROM ArchivoAdjunto AS archivoadjunto WHERE archivoadjunto.seccionDefault = :seccionDefault");
        q.setParameter("seccionDefault", seccionDefault);
        return q;
    }
    
    @SuppressWarnings("unchecked")
    public static Query ArchivoAdjunto.findArchivoAdjuntoesByAvanceActuacion(AvanceActuacion avanceActuacion) {
        if (avanceActuacion == null) throw new IllegalArgumentException("The avanceActuacion argument is required");
        EntityManager em = ArchivoAdjunto.entityManager();
        Query q = em.createQuery("SELECT ArchivoAdjunto FROM ArchivoAdjunto AS archivoadjunto WHERE archivoadjunto.avanceActuacion = :avanceActuacion");
        q.setParameter("avanceActuacion", avanceActuacion);
        return q;
    }
    
    @SuppressWarnings("unchecked")
    public static Query ArchivoAdjunto.findArchivoAdjuntoesByRequerimiento(Requerimiento requerimiento) {
        if (requerimiento == null) throw new IllegalArgumentException("The requerimiento argument is required");
        EntityManager em = ArchivoAdjunto.entityManager();
        Query q = em.createQuery("SELECT ArchivoAdjunto FROM ArchivoAdjunto AS archivoadjunto WHERE archivoadjunto.requerimiento = :requerimiento");
        q.setParameter("requerimiento", requerimiento);
        return q;
    }
    
    @SuppressWarnings("unchecked")
    public static Query ArchivoAdjunto.findArchivoAdjuntoesByPersonalizacion(Personalizacion personalizacion) {
        if (personalizacion == null) throw new IllegalArgumentException("The personalizacion argument is required");
        EntityManager em = ArchivoAdjunto.entityManager();
        Query q = em.createQuery("SELECT ArchivoAdjunto FROM ArchivoAdjunto AS archivoadjunto WHERE archivoadjunto.personalizacion = :personalizacion");
        q.setParameter("personalizacion", personalizacion);
        return q;
    }
    
}
