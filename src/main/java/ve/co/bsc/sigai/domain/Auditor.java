package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import java.util.Set;
import ve.co.bsc.sigai.domain.PlanAnual;
import java.util.HashSet;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;

/**
 * Representa a un auditor registrado en el sistema
 * 
 * @author jdeoliveira
 */
@Entity
@RooJavaBean
@RooEntity(finders = { "findAuditorsByCedulaEquals", "findAuditorsByLoginEquals", "findAuditorsById_OrganismoEnte" })
public class Auditor implements ObjetoNombreClase, Serializable {

    @NotNull
    @Size(min = 2)
    private String nombre;

    @NotNull
    @Size(min = 2)
    private String apellido;

    @Size(min = 2)
    private String cedula;

    @Size(min = 2)
    private String correo;

    @Size(min = 2)
    private String login;

    @Size(min = 2)
    private String telefono;

    @Size(min = 2)
    private String celular;

    @Lob
    @Size(min = 0, max = 10000)
    @Column(length = 10000)
    private String comentario;

    @ManyToOne(targetEntity = EstadoAuditor.class)
    @JoinColumn
    private EstadoAuditor estadoAuditor;

    @ManyToOne(targetEntity = EstructuraOrganizativa.class)
    @JoinColumn
    private EstructuraOrganizativa id_estructura;

    @ManyToOne(targetEntity = EstructuraCargos.class)
    @JoinColumn
    private EstructuraCargos cargo;

    @ManyToOne(targetEntity = OrganismoEnte.class)
    @JoinColumn
    private OrganismoEnte id_OrganismoEnte;

    @ManyToOne(targetEntity = CodigoArea.class)
    @JoinColumn
    private CodigoArea telfOficina;

    @ManyToOne(targetEntity = CodigoArea.class)
    @JoinColumn
    private CodigoArea telfCelular;

    @ManyToOne(targetEntity = CondicionAuditorInterno.class)
    @JoinColumn
    private CondicionAuditorInterno condicionAuditorInterno;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<PlanAnual> responsable = new HashSet<PlanAnual>();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getNombre()).append(" ");
        sb.append(getApellido()).append(" ");
        return sb.toString();
    }

    public String toStringExtendido() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nombre: ").append(getNombre()).append(", ");
        sb.append("Apellido: ").append(getApellido()).append(", ");
        sb.append("login: ").append(getLogin()).append(", ");
        sb.append("Cedula: ").append(getCedula()).append(", ");
        sb.append("Correo electronico: ").append(getCorreo()).append(", ");
        sb.append("Telefono: ").append(getTelefono()).append(", ");
        sb.append("Celular: ").append(getCelular()).append(", ");
        sb.append("Estado: ").append(getEstadoAuditor() == null ? "n/a" : getEstadoAuditor().toStringLimitado()).append(" ");
        return sb.toString();
    }

    public String toStringLimitado() {
        StringBuilder sb = new StringBuilder();
        sb.append(getLogin()).append(" ");
        return sb.toString();
    }

    @Override
    public String getNombreClase() {
        return "Auditor";
    }
}
