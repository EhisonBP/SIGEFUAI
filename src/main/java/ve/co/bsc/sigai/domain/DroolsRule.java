package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.persistence.Enumerated;
import org.springframework.roo.addon.entity.RooEntity;
import org.hibernate.annotations.Type;

/**
 * Representa una regla de negocio para proceso (bpm)
 * @author carlosalvite
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity
public class DroolsRule implements Serializable {

    private String nombre;

    @Enumerated
    private DroolsRuleTypeEnum tipo;

    @Type(type="binary")
    private byte[] ruleDefinition;

}
