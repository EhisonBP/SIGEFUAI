package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Query;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import org.apache.log4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;

/**
 * Representa una entrada en bitácora
 * @author carlosalvite
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity
public class EntradaBitacora implements Serializable {

    private static Logger logger = Logger.getLogger(EntradaBitacora.class);


    @Lob
    @Size(min = 0, max = 10000)
    @Column(length = 10000)
    private String descripcion;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "SM")
    private Date timeStamp;

    private String usuario;

    private String ip;

    @ManyToOne(targetEntity = TipoEntradaBitacora.class)
    @JoinColumn
    private TipoEntradaBitacora tipo;

    @Transient
    @SuppressWarnings("unchecked")
    public static List<EntradaBitacora> findEntradasBitacorasByParams(Date _timeStampDesde, boolean _porTimeStampDesde, Date _timeStampHasta, boolean _porTimeStampHasta, String _ip, boolean _porIp, String _usuario, boolean _porUsuario, Long _tipo, boolean _porTipo) {
        StringBuffer condition = null;

        condition = new StringBuffer("SELECT i FROM EntradaBitacora AS i WHERE ((1=1) ");
        
        EntityManager em = EntradaBitacora.entityManager();
        if(_porTimeStampDesde && _porTimeStampHasta){
            condition.append(" AND (i.timeStamp >= :timeStampDesde) AND (i.timeStamp <= :timeStampHasta) ");
        }
        if (_porTimeStampDesde && !_porTimeStampHasta)
        {
            condition.append(" AND (i.timeStamp >= :timeStampDesde) ");
        }
        if (!_porTimeStampDesde && _porTimeStampHasta)
        {
            condition.append(" AND (i.timeStamp <= :timeStampHasta) ");
        }

        if (_porIp) {
            condition.append(" AND (i.ip = :ip) ");
        }
        if (_porUsuario) {
            condition.append(" AND (i.usuario LIKE :usuario) ");
        }
        if (_porTipo) {
            condition.append(" AND (i.tipo.id = :tipo) ");
        }

        condition.append(")");
        
        Query query = em.createQuery(condition.toString());
        if(_porTimeStampDesde && _porTimeStampHasta){
            query.setParameter("timeStampDesde", _timeStampDesde);
            query.setParameter("timeStampHasta", _timeStampHasta);
        }
        if (_porTimeStampDesde && !_porTimeStampHasta)
        {
            query.setParameter("timeStampDesde", _timeStampDesde);
        }
        if (!_porTimeStampDesde && _porTimeStampHasta)
        {
            query.setParameter("timeStampHasta", _timeStampHasta);
        }
        if (_porIp) {
            query.setParameter("ip", _ip);
        }
        if (_porUsuario) {
            query.setParameter("usuario", "%" + _usuario + "%");
        }
        if (_porTipo) {
            query.setParameter("tipo", _tipo);
        }

        return query.getResultList();
    }
}
