package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * Representa junto con DatosOrganismoEnte todos datos de los organismos
 * 
 * @author gerardoperez
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity(finders = { "findOrganismoEntesByRifEquals" })
public class OrganismoEnte implements Cloneable, Serializable {

    private String Nombre;

    private String Acronimo;

    @NotNull
    @ManyToOne(targetEntity = TipoPersonaRif.class)
    @JoinColumn
    private TipoPersonaRif tipo_rif;

    private String Rif;

    @ManyToOne(targetEntity = ve.co.bsc.sigai.domain.OrganismoEnte.class)
    @JoinColumn
    private ve.co.bsc.sigai.domain.OrganismoEnte id_organismo_padre;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date fechaCreacion;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date fechaModificacion;

    @NotNull
    @ManyToOne(targetEntity = EstadoAuditor.class)
    @JoinColumn
    private EstadoAuditor Estatus;

    @NotNull
    @ManyToOne(targetEntity = TipoOrganismo.class)
    @JoinColumn
    private TipoOrganismo id_tipo_organismo;

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("Nombre: ").append(getNombre()).append(", ");
        sb.append("Acronimo: ").append(getAcronimo()).append(", ");
        sb.append("Tipo_rif: ").append(getTipo_rif()).append(", ");
        sb.append("Rif: ").append(getRif()).append(", ");
        sb.append("FechaCreacion: ").append(getFechaCreacion()).append(", ");
        sb.append("FechaModificacion: ").append(getFechaModificacion()).append(", ");
        sb.append("Estatus: ").append(getEstatus()).append(", ");
        sb.append("Id_tipo_organismo: ").append(getId_tipo_organismo());
        return sb.toString();
    }
}
