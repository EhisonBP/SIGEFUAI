package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;
import javax.validation.constraints.NotNull;

/**
 * Representa el nombre de una clase que haya ejecutado un registro en bitácora
 * @author carlosalvite
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity(finders = { "findTipoEntradaBitacorasByClase" })
public class TipoEntradaBitacora implements Serializable {

    @NotNull
    private String clase;

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClase());

        return sb.toString();
    }
}
