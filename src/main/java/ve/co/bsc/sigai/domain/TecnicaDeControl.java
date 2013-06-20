package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;
import javax.persistence.Column;
import javax.persistence.Lob;

/**
 * Representa una técnica de control para mitigar un riesgo
 * @author carlosalvite
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity(finders = { "findTecnicaDeControlsByRiesgo", "findTecnicaDeControlsByPrueba" })
public class TecnicaDeControl implements ObjetoNombreClase, Serializable {

    @Lob
    @Size(min = 1, max = 10000)
    @Column(length = 10000)
    private String descripcion;

    @ManyToOne(targetEntity = Riesgo.class)
    @JoinColumn
    private Riesgo riesgo;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Prueba> prueba = new HashSet<Prueba>();

    @NotNull
    private Integer referencia;

    @ManyToOne(targetEntity = EfectividadTecnicaControl.class)
    @JoinColumn
    private EfectividadTecnicaControl efectividadTecnicaControl;

    @Transient
    @SuppressWarnings("unchecked")
    public static List<TecnicaDeControl> findTecnicasByActuacion(Actuacion actuacion) {
        List<TecnicaDeControl> misTecnicas = new ArrayList<TecnicaDeControl>();
        Set<Proceso> procesos = actuacion.getProcesos();
        Iterator it = procesos.iterator();
        while (it.hasNext()) {
            Proceso proceso = (Proceso) it.next();
            List<Riesgo> riesgos = Riesgo.findRiesgoesByProceso(proceso).getResultList();
            for (int i = 0; i < riesgos.size(); i++) {
                List<TecnicaDeControl> tecnicas = TecnicaDeControl.findTecnicaDeControlsByRiesgo(riesgos.get(i)).getResultList();
                misTecnicas.addAll(tecnicas);
            }
        }
        return misTecnicas;
    }

    public String toStringExtendido() {
        StringBuilder sb = new StringBuilder();
        sb.append("Descripcion: ").append(getDescripcion()).append(", ");
        sb.append("Riesgo: ").append(getRiesgo() == null ? "n/a" : getRiesgo().toStringLimitado());
        return sb.toString();
    }

    public String toStringLimitado() {
        StringBuilder sb = new StringBuilder();
        sb.append(getDescripcion()).append(" ");
        return sb.toString();
    }

    @Override
    public String getNombreClase() {
        return "Tecnica de Control";
    }

    public String getDescripcionLimpia() {
        String desc = getDescripcion();
        desc.replaceAll("\\<.*?\\>", "");
        desc.replaceAll("\\<p\\>", "");
        desc.replaceAll("\\</p\\>", "");

        return desc;
    }

}
