package ve.co.bsc.sigai.domain;

import java.lang.String;
import java.lang.SuppressWarnings;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import ve.co.bsc.sigai.domain.ActividadActuacion;
import ve.co.bsc.sigai.domain.Actuacion;

privileged aspect PapelDeTrabajo_Roo_Finder {
    
    @SuppressWarnings("unchecked")
    public static Query PapelDeTrabajo.findPapelDeTrabajoesByCodigoActuacion(Actuacion codigoActuacion) {
        if (codigoActuacion == null) throw new IllegalArgumentException("The codigoActuacion argument is required");
        EntityManager em = PapelDeTrabajo.entityManager();
        Query q = em.createQuery("SELECT PapelDeTrabajo FROM PapelDeTrabajo AS papeldetrabajo WHERE papeldetrabajo.codigoActuacion = :codigoActuacion");
        q.setParameter("codigoActuacion", codigoActuacion);
        return q;
    }
    
    @SuppressWarnings("unchecked")
    public static Query PapelDeTrabajo.findPapelDeTrabajoesByActividadActuacion(ActividadActuacion actividadActuacion) {
        if (actividadActuacion == null) throw new IllegalArgumentException("The actividadActuacion argument is required");
        EntityManager em = PapelDeTrabajo.entityManager();
        Query q = em.createQuery("SELECT PapelDeTrabajo FROM PapelDeTrabajo AS papeldetrabajo WHERE papeldetrabajo.actividadActuacion = :actividadActuacion");
        q.setParameter("actividadActuacion", actividadActuacion);
        return q;
    }
    
    @SuppressWarnings("unchecked")
    public static Query PapelDeTrabajo.findPapelDeTrabajoesByCodigoEquals(String codigo) {
        if (codigo == null || codigo.length() == 0) throw new IllegalArgumentException("The codigo argument is required");
        EntityManager em = PapelDeTrabajo.entityManager();
        Query q = em.createQuery("SELECT PapelDeTrabajo FROM PapelDeTrabajo AS papeldetrabajo WHERE papeldetrabajo.codigo = :codigo");
        q.setParameter("codigo", codigo);
        return q;
    }
    
}
