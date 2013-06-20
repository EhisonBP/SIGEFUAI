package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Query;

/**
 * Representa la planificación de un plan anual.
 * En un plan anual se van a ejecutar varias actuaciones,
 * es aquí donde se colocan las fechas estimadas y reales (inicio y fin)
 * del periodo de tiempo en el que se ejecutarán esas actuaciones.
 * @author adernersissian
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity(finders = { "findItemPlanificacionActuacionsByPlanAnual", "findItemPlanificacionActuacionsByActuacion", "findItemPlanificacionActuacionsByOtraActividad" })
public class ItemPlanificacionActuacion implements Cloneable, ObjetoNombreClase, Serializable {

    //Se usan cuando es un itemPlanificacionActuacion relacionado con una Actuacion
    private Integer inicioEstimado;
    private Integer finEstimado;
    //*****************************************************************************

    //Se usan cuando es un itemPlanificacionActuacion relacionado con OtraActividad
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date fechaInicioEstimada;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date fechaFinEstimada;
    //*****************************************************************************
    
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date inicioReal;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date finReal;

    @ManyToOne(targetEntity = PlanAnual.class)
    @JoinColumn
    private PlanAnual planAnual;

    @ManyToOne(targetEntity = Actuacion.class)
    @JoinColumn
    private Actuacion actuacion;

    @ManyToOne(targetEntity = OtraActividad.class)
    @JoinColumn
    private OtraActividad otraActividad;

    @Override
    public Object clone() {
        ItemPlanificacionActuacion obj = new ItemPlanificacionActuacion();
        obj.setInicioEstimado(this.inicioEstimado);
        obj.setFinEstimado(this.finEstimado);
        obj.setFechaInicioEstimada(this.fechaInicioEstimada);
        obj.setFechaFinEstimada(this.fechaFinEstimada);
        obj.setInicioReal(this.inicioReal);
        obj.setFinReal(this.finReal);
        obj.setPlanAnual(this.planAnual);
        obj.setActuacion(this.actuacion);
        obj.setOtraActividad(this.otraActividad);
        return obj;
    }

    @SuppressWarnings("unchecked")
    public static Query findItemPlanificacionActuacionsByPlanAnualAndActuacionNotNull(PlanAnual planAnual) {
        if (planAnual == null) throw new IllegalArgumentException("The planAnual argument is required");
        EntityManager em = ItemPlanificacionActuacion.entityManager();
        Query q = em.createQuery("SELECT ItemPlanificacionActuacion FROM ItemPlanificacionActuacion AS itemplanificacionactuacion WHERE itemplanificacionactuacion.planAnual = :planAnual AND itemplanificacionactuacion.actuacion != NULL");
        q.setParameter("planAnual", planAnual);
        return q;
    }

    @SuppressWarnings("unchecked")
    public static Query findItemPlanificacionActuacionsByPlanAnualAndOtraActividadNotNull(PlanAnual planAnual) {
        if (planAnual == null) throw new IllegalArgumentException("The planAnual argument is required");
        EntityManager em = ItemPlanificacionActuacion.entityManager();
        Query q = em.createQuery("SELECT ItemPlanificacionActuacion FROM ItemPlanificacionActuacion AS itemplanificacionactuacion WHERE itemplanificacionactuacion.planAnual = :planAnual AND itemplanificacionactuacion.otraActividad != NULL");
        q.setParameter("planAnual", planAnual);
        return q;
    }

    public String toStringExtendido() {
        StringBuilder sb = new StringBuilder();
        sb.append("InicioEstimado: ").append(getInicioEstimado()).append(", ");
        sb.append("FinEstimado: ").append(getFinEstimado()).append(", ");
        sb.append("FechaInicioEstimada: ").append(getFechaInicioEstimada()).append(", ");
        sb.append("FechaFinEstimada: ").append(getFechaFinEstimada()).append(", ");
        sb.append("InicioReal: ").append(getInicioReal()).append(", ");
        sb.append("FinReal: ").append(getFinReal()).append(", ");
        sb.append("PlanAnual: ").append(getPlanAnual()==null ? "n/a" : getPlanAnual().toStringLimitado()).append(", ");
        sb.append("Actuacion: ").append(getActuacion()==null ? "n/a" : getActuacion().toStringLimitado()).append(", ");
        sb.append("OtraActividad: ").append(getOtraActividad()==null ? "n/a" : getOtraActividad().toStringLimitado());
        return sb.toString();
    }

    //TODO: Definir cuales son los más importantes
    public String toStringLimitado() {
        StringBuilder sb = new StringBuilder();
        sb.append("InicioEstimado: ").append(getInicioEstimado()).append(", ");
        sb.append("FinEstimado: ").append(getFinEstimado()).append(", ");
        sb.append("FechaInicioEstimada: ").append(getFechaInicioEstimada()).append(", ");
        sb.append("FechaFinEstimada: ").append(getFechaFinEstimada()).append(", ");
        sb.append("InicioReal: ").append(getInicioReal()).append(", ");
        sb.append("FinReal: ").append(getFinReal()).append(", ");
        return sb.toString();
    }

    @Override
    public String getNombreClase()
    {
        return "Item Planificación Actuación";
    }
}
