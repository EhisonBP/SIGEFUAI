package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@Entity
@RooJavaBean
@RooToString
@RooEntity(finders = { "findAutoridadMaximasByRif" })
public class AutoridadMaxima implements ObjetoComentable, Serializable {

    @NotNull
    private String nombre;

    private String cedula;

    private int effectTypes;

    @NotNull
    @ManyToOne(targetEntity = EstructuraCargos.class)
    @JoinColumn
    private EstructuraCargos cargoAutoridad;

    @ManyToOne(targetEntity = OrganismoEnte.class)
    @JoinColumn
    private OrganismoEnte rif;
}
