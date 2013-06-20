package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;

/**
 * Representa una condición de un auditor
 * @author carlosalvite
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity
public class CondicionAuditorInterno implements Serializable {

    private String nombre;
}
