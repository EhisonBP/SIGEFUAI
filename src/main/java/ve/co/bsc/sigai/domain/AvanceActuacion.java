package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;
import javax.validation.constraints.Size;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import java.util.Date;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Representa un avance realizado en el marco de una actividad
 * de una actuacion. Incluye el auditor que la ha reportado,
 * la fecha inicial y final y el porcentaje de avance que
 * este reporte representa a la actividad, a juicio del auditor.
 *
 * @author jdeoliveira
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity(finders = { "findAvanceActuacionsByCodigoActividad" })
public class AvanceActuacion implements ObjetoComentable , ObjetoNombreClase, Serializable {

    @Lob
    @Size(max = 10000)
    private String justificacion;

    @ManyToOne(targetEntity = ActividadActuacion.class)
    @JoinColumn
    private ActividadActuacion codigoActividad;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date fechaInicioEstimada;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date fechaFinEstimada;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date fechaInicioReal;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date fechaFinReal;

    public String toStringExtendido() {
        StringBuilder sb = new StringBuilder();
        sb.append("Justificacion: ").append(getJustificacion()).append(", ");
        sb.append("Actividad: ").append(getCodigoActividad()==null ? "n/a" : getCodigoActividad().toStringLimitado()).append(", ");
        sb.append("FechaInicioEstimada: ").append(getFechaInicioEstimada()).append(", ");
        sb.append("FechaFinEstimada: ").append(getFechaFinEstimada()).append(", ");
        sb.append("FechaInicioReal: ").append(getFechaInicioReal()).append(", ");
        sb.append("FechaFinReal: ").append(getFechaFinReal());
        return sb.toString();
    }

    public String toStringLimitado() {
        StringBuilder sb = new StringBuilder();
        sb.append(getJustificacion()).append(", ");
        return sb.toString();
    }

    @Override
    public String getNombreClase()
    {
        return "Avance";
    }

}

