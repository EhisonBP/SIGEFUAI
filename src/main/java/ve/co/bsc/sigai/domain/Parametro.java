package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

/**
 * Representa un parametro asociado  a un reporte
 * @author carlosalvite
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity(finders = { "findParametroesByReporte" })
public class Parametro implements Serializable {

    @NotNull
    @Size(max = 25)
    private String parametro;

    @NotNull
    @Size(max = 25)
    private String tipoParametro;

    @ManyToOne(targetEntity = Reporte.class)
    @JoinColumn
    private Reporte reporte;
}
