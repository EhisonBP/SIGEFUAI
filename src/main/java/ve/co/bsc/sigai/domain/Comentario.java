package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;

/**
 * Representa un comentario
 * @author carlosalvite
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity(finders = { "findComentariosByTipoAndId" })
public class Comentario implements Serializable {

    private Long id;

    private String tipo;

    private String redaccionComentario;

    private String usuario;
}
