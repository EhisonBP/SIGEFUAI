package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;

/**
 * Representa un objetivo específico asociado a una actuación
 * @author carlosalvite
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity(finders = { "findObjetivoEspecificoesByActuacion" })
public class ObjetivoEspecifico implements ObjetoNombreClase, Serializable {

    @Lob
    @Size(min = 0, max = 10000)
    @Column(length = 10000)
    private String descripcion;

    @ManyToOne(targetEntity = Actuacion.class)
    @JoinColumn
    private Actuacion actuacion;

    @NotNull
    private Integer referencia;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String descripcion = getDescripcion();
        String subDescripcion = descripcion;
        if (descripcion.length() > 90)
            subDescripcion = descripcion.substring(0, 90);
        sb.append(getReferencia()).append("- " + subDescripcion);
        return sb.toString();
    }

    public String getObjetivoCompleto() {
        String desc = ((ObjetivoEspecifico)this).getDescripcion();
        String[] descArray = desc.split("<p>");
        String cadena = descArray[0] + ((ObjetivoEspecifico)this).getReferencia() + " - " + descArray[1];
        return cadena;
    }

    public String toStringExtendido() {
        StringBuilder sb = new StringBuilder();
        String descripcion = getDescripcion();
        String subDescripcion = descripcion;
        if (descripcion.length() > 90)
            subDescripcion = descripcion.substring(0, 89);
        sb.append("Descripcion: ").append(subDescripcion);
        sb.append("Referencia: ").append(getReferencia());
        sb.append("Actuacion: ").append(getActuacion()==null ? "n/a" : getActuacion().toStringLimitado());
        return sb.toString();
    }

    public String toStringLimitado() {
        StringBuilder sb = new StringBuilder();
        sb.append(getReferencia());
        return sb.toString();
    }

    @Override
    public String getNombreClase()
    {
        return "Objetivo Especifico";
    }
}
