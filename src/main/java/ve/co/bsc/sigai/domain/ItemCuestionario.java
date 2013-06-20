package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;
import javax.validation.constraints.Size;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Type;

/**
 * Representa un item cuestionario que incluye una pregunta con respectiva respuesta y archivo adjunto si lo requiere
 * @author carlosalvite
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity(finders = { "findItemCuestionariosByCuestionario" })
public class ItemCuestionario implements ObjetoNombreClase, Serializable {

    @NotNull
    private String pregunta;

    @Lob
    @Size(min = 0, max = 10000)
    @Column(length = 10000)
    private String respuesta;

    @Type(type="binary")
    @Column(length = 104857600)
    @Basic(fetch = FetchType.LAZY)
    private byte[] filedata;

    private String fileExtension;

    private String fileName;

    @ManyToOne(targetEntity = Cuestionario.class)
    @JoinColumn
    private Cuestionario cuestionario;

    public String toStringExtendido() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pregunta: ").append(getPregunta()).append(", ");
        sb.append("Respuesta: ").append(getRespuesta()).append(", ");
        sb.append("Cuestionario: ").append(getCuestionario().toStringLimitado());
        return sb.toString();
    }

    public String toStringLimitado() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pregunta: ").append(getPregunta()).append(", ");
        sb.append("Respuesta: ").append(getRespuesta()).append(", ");
        return sb.toString();
    }

    @Override
    public String getNombreClase()
    {
        return "";
    }
}
