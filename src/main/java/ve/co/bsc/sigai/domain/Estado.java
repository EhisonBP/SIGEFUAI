package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;
import javax.validation.constraints.Size;
import ve.co.bsc.sigai.domain.EstadoAuditor;
import javax.validation.constraints.NotNull;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

/**
 * Representa un Estado geográfico
 * @author carlosalvite
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity
public class Estado implements Serializable {

    private String nombre;

    @Size(max = 1000)
    private String Descripcion;

    @NotNull
    @ManyToOne(targetEntity = EstadoAuditor.class)
    @JoinColumn
    private EstadoAuditor Estatus;
}
