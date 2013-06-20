package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import java.util.Comparator;
import javax.persistence.Query;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;
import javax.validation.constraints.NotNull;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

/**
 * Representa un papel de trabajo el cual conforma una biblioteca de programa
 * @author carlosalvite
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity(finders = { "findSeccionDefaultsBySeccionDefault", "findSeccionDefaultsByBiblioteca" })
public class SeccionDefault implements ObjetoNombreClase, Serializable {

    @NotNull
    private String codigo;

    private String descripcion;

    @ManyToOne(targetEntity = ve.co.bsc.sigai.domain.SeccionDefault.class)
    @JoinColumn
    private ve.co.bsc.sigai.domain.SeccionDefault seccionDefault;

    public int getNivelIndentacion() {
        if (this.seccionDefault != null) {
            return this.seccionDefault.getNivelIndentacion() + 1;
        } else {
            return 0;
        }
    }

    public static final Comparator compararSeccionesPorId = new Comparator() {

        @Override
        public int compare(Object arg0, Object arg1) {
            SeccionDefault seccion1 = (SeccionDefault) arg0;
            SeccionDefault seccion2 = (SeccionDefault) arg1;
            return seccion1.getId().compareTo(seccion2.getId());
        }
    };

    //private Integer tipo;

    @ManyToOne(targetEntity = Biblioteca.class)
    @JoinColumn
    private Biblioteca biblioteca;

    @ManyToOne(targetEntity = TipoSeccion.class)
    @JoinColumn
    private TipoSeccion tipoSeccion;

    @SuppressWarnings("unchecked")
    public static Query findSeccionDefaultsPadresByBiblioteca(Biblioteca biblioteca) {
        if (biblioteca == null) throw new IllegalArgumentException("The biblioteca argument is required");
        EntityManager em = SeccionDefault.entityManager();
        Query q = (Query) em.createQuery("SELECT SeccionDefault FROM SeccionDefault AS secciondefault WHERE ( secciondefault.biblioteca = :biblioteca AND secciondefault.seccionDefault = NULL)");
        q.setParameter("biblioteca", biblioteca);
        return q;
    }

    public String toStringExtendido() {
        StringBuilder sb = new StringBuilder();
        sb.append("Codigo: ").append(getCodigo()).append(", ");
        sb.append("Descripcion: ").append(getDescripcion()).append(", ");
        sb.append("SeccionDefaultPadre: ").append(getSeccionDefault()==null ? "n/a" : getSeccionDefault().toStringLimitado()).append(", ");
        sb.append("Biblioteca: ").append(getBiblioteca()==null ? "n/a" : getBiblioteca().toStringLimitado()).append(", ");
        sb.append("TipoSeccion: ").append(getTipoSeccion()==null ? "n/a" : getTipoSeccion().toStringLimitado());
        return sb.toString();
    }

    public String toStringLimitado() {
        StringBuilder sb = new StringBuilder();
        sb.append(getCodigo()).append(" ");
        return sb.toString();
    }

    @Override
    public String getNombreClase()
    {
        return "Seccion Predefinida";
    }
}
