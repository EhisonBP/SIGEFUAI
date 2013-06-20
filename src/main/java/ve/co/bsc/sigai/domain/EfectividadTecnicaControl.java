package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;
import javax.validation.constraints.NotNull;

/**
 * Representa una efectividad de una técnica de control
 * @author carlosalvite
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity
public class EfectividadTecnicaControl implements Serializable {

    @NotNull
    private String nombre;
}
