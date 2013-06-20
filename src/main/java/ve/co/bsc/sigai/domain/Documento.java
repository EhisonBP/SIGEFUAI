package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;

/**
 * Representa un documento
 * @author carlosalvite
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity(finders = { "findDocumentoesByActividadActuacion" })
public class Documento implements Serializable {

    @NotNull
    private String nombre;

    private String descripcion;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private Set<ActividadActuacion> actividadActuacion = new HashSet<ActividadActuacion>();

    

}
