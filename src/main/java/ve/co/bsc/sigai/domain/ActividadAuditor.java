package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import org.apache.log4j.Logger;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;

/**
 * Representa una actividad (actividad generica o actuacion)
 * que será asignada a un auditor.
 * @author adernersissian
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity
public class ActividadAuditor implements Serializable {

    private static final Logger logger = Logger.getLogger(ActividadAuditor.class);

    public String getDescripcionGeneral() {
        String descripcion = "";
        if (this instanceof Actuacion) {
            descripcion = "A - " + ((Actuacion)this).getNombre();
        }else if (this instanceof ActividadGenerica) {
            String desc = ((ActividadGenerica)this).getDescripcionCorta();
//            if (desc.length() > 30){
//                desc = desc.substring(0, 29);
//            }
            descripcion = "O - " + desc;
        }else if (this instanceof OtraActividad){            
            String desc = ((OtraActividad)this).getDescripcionCorta();
//            if (desc.length() > 30){
//                desc = desc.substring(0, 29);
//            }
            descripcion = "OA - " + desc;
        }
        
        return descripcion;
    }

    public String toStringLimitado() {
        StringBuilder sb = new StringBuilder();
        sb.append("DescripcionGeneral: ").append(getDescripcionGeneral()).append(", ");
        return sb.toString();
    }
}
