package ve.co.bsc.sigai.domain;

import javax.persistence.Entity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;
import ve.co.bsc.sigai.domain.Auditor;
import javax.validation.constraints.NotNull;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import java.util.Set;
import ve.co.bsc.sigai.domain.OrganismoEnte;
import java.util.HashSet;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;

@Entity
@RooJavaBean
@RooToString
@RooEntity
public class AsignarOrganismo {

    @NotNull
    @ManyToOne(targetEntity = Auditor.class)
    @JoinColumn
    private Auditor analista;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<OrganismoEnte> organismo = new HashSet<OrganismoEnte>();
}
