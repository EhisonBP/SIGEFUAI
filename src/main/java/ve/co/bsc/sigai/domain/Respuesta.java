package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;
import javax.validation.constraints.NotNull;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

/**
 * Representa una respuesta de un requerimiento solicitado a un auditado
 * @author carlosalvite
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity(finders = { "findRespuestasByRequerimiento" })
public class Respuesta implements ObjetoNombreClase, Serializable {

    @NotNull
    private String numero;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date fecha;

    @ManyToOne(targetEntity = Requerimiento.class)
    @JoinColumn
    private Requerimiento requerimiento;

    public String toStringExtendido() {
        StringBuilder sb = new StringBuilder();
        sb.append("Numero: ").append(getNumero()).append(", ");
        sb.append("Fecha: ").append(getFecha()).append(", ");
        sb.append("Requerimiento: ").append(getRequerimiento()==null ? "n/a" : getRequerimiento().toStringLimitado());
        return sb.toString();
    }
    public String toStringLimitado() {
        StringBuilder sb = new StringBuilder();
        sb.append(getNumero()).append(" ");
        return sb.toString();
    }
    @Override
    public String getNombreClase()
    {
        return "Respuesta";
    }
}
