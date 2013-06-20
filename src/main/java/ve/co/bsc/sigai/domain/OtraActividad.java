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
import javax.persistence.Transient;
import org.apache.log4j.Logger;

/**
 * Representa otra actividad genérica que puede ser asociada a un auditor
 * @author carlosalvite
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity(finders = { "findOtraActividadsByRif" })
public class OtraActividad extends ActividadAuditor implements Cloneable, ObjetoComentable, ObjetoNombreClase, Serializable {

    private static final Logger logger = Logger.getLogger(OtraActividad.class);

    private Integer numero;

    @NotNull
    private String objetivo;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date fechaInicio;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date fechaFin;

    @ManyToOne(targetEntity = Unidad.class)
    @JoinColumn
    private Unidad unidad;

    @ManyToOne(targetEntity = UnidadDeMedida.class)
    @JoinColumn
    private UnidadDeMedida unidadDeMedida;

    private String descripcionCorta;

    @Transient
    private String[] accionesPermitidas;

    //@NotNull
    @ManyToOne(targetEntity = ve.co.bsc.sigai.domain.OrganismoEnte.class)
    @JoinColumn
    private ve.co.bsc.sigai.domain.OrganismoEnte rif;

    public void setAccionesPermitidasFromString(String commaSeparadtedStringWithActionsList) {
        logger.debug("Setting acciones permitidas desde string con: " + commaSeparadtedStringWithActionsList);
        this.setAccionesPermitidas(commaSeparadtedStringWithActionsList.split(","));
    }

    @Override
    public Object clone() {
        OtraActividad obj = new OtraActividad();
        obj.setNumero(this.numero);
        obj.setObjetivo(this.objetivo);
        obj.setFechaInicio(this.fechaInicio);
        obj.setFechaFin(this.fechaFin);
        obj.setUnidad(this.unidad);
        obj.setUnidadDeMedida(this.unidadDeMedida);
        return obj;
    }

    public String toStringExtendido() {
        StringBuilder sb = new StringBuilder();
        sb.append("DescripcionGeneral: ").append(getDescripcionGeneral()).append(", ");
        sb.append("Numero: ").append(getNumero()).append(", ");
        sb.append("Objetivo: ").append(getObjetivo()).append(", ");
        sb.append("FechaInicio: ").append(getFechaInicio()).append(", ");
        sb.append("FechaFin: ").append(getFechaFin()).append(", ");
        sb.append("Unidad: ").append(getUnidad() == null ? "n/a" : getUnidad().toStringLimitado()).append(", ");
        sb.append("UnidadDeMedida: ").append(getUnidadDeMedida() == null ? "n/a" : getUnidadDeMedida().toStringLimitado());
        return sb.toString();
    }

    @Override
    public String getNombreClase() {
        return "Otra Actividad";
    }
}
