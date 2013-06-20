package ve.co.bsc.sigai.domain;

import javax.persistence.Entity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;
import javax.validation.constraints.NotNull;
import ve.co.bsc.sigai.domain.Ciudad;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.validation.constraints.Size;
import ve.co.bsc.sigai.domain.EstadoAuditor;

@Entity
@RooJavaBean
@RooToString
@RooEntity
public class Municipios {

    @NotNull
    private String Nombre;

    @NotNull
    @ManyToOne(targetEntity = Ciudad.class)
    @JoinColumn
    private Ciudad id_ciudad;

    @Size(max = 1000)
    private String Descripcion;

    @NotNull
    @ManyToOne(targetEntity = EstadoAuditor.class)
    @JoinColumn
    private EstadoAuditor Estatus;
}
