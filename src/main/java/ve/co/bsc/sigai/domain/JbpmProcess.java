package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;
import org.hibernate.annotations.Type;

/**
 * Representa un proceso de negocio bpm
 * @author carlosalvite
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity
public class JbpmProcess implements Serializable {

    private String nombre;

    @Type(type="binary")
    private byte[] processDefinition;
}
