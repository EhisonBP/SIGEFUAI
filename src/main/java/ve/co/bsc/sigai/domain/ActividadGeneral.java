package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.Lob;

/**
 * Representa una actividad generica parte de una actuacion (papel de trabajo)
 * Todo lo que NO ES una prueba, se representa como una actividad general.
 * @author jdeoliveira
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity(finders = { "findActividadGeneralsByCodigoActuacion" })
public class ActividadGeneral extends ActividadActuacion implements ObjetoNombreClase, Serializable {

    @NotNull
    @Lob
    @Size(min = 0, max = 10000)
    @Column(length = 10000)
    private String resultadosEsperados;

    @NotNull
    @Lob
    @Size(min = 0, max = 10000)
    @Column(length = 10000)
    private String hitosDeControl;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date fechaEstimadaDeInicio;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date fechaEstimadaDeFin;

    public String getType() {
        return "a";
    }

    public String toStringExtendido() {
        StringBuilder sb = new StringBuilder();

        sb.append("Codigo: ").append(getCodigoCompleto()).append(", ");
        sb.append("Actuacion: ").append(getCodigoActuacion()==null ? "n/a" : getCodigoActuacion().toStringLimitado()).append(", ");
        sb.append("Descripcion: ").append(getDescripcion()==null ? "n/a" : getDescripcion()).append(", ");
        sb.append("EstadoActividadActuacion: ").append(getEstadoActividadActuacion() == null ? "n/a" : getEstadoActividadActuacion().toStringLimitado()).append(", ");
        sb.append("Padre: ").append(getActividadActuacion()==null ? "n/a" : getActividadActuacion().toStringLimitado()).append(", ");
        //TODO: Resolver N:N
        //sb.append("Responsables: ").append(getResponsables() == null ? "n/a" : getResponsables().size()).append(", ");
        sb.append("ObjetivoAMitigar: ").append(getObjetivoAMitigar()==null ? "n/a" : getObjetivoAMitigar().toStringLimitado()).append(", ");
        //sb.append("MesEstimadoInicio: ").append(getMesEstimadoInicio()==null ? "n/a" : getMesEstimadoInicio()).append(", ");
        //sb.append("MesEstimadoFin: ").append(getMesEstimadoFin()==null ? "n/a" : getMesEstimadoFin()).append(", ");
        sb.append("ResultadosEsperados: ").append(getResultadosEsperados()==null ? "n/a" :getResultadosEsperados()).append(", ");
        sb.append("HitosDeControl: ").append(getHitosDeControl()==null ? "n/a" : getHitosDeControl());

        return sb.toString();
    }

    @Override
    public String getNombreClase()
    {
        return "Actividad";
    }

}
