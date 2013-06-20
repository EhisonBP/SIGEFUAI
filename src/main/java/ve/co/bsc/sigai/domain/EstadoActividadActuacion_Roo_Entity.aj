package ve.co.bsc.sigai.domain;

import java.lang.Integer;
import java.lang.Long;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import org.springframework.transaction.annotation.Transactional;
import ve.co.bsc.sigai.domain.EstadoActividadActuacion;

privileged aspect EstadoActividadActuacion_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager EstadoActividadActuacion.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long EstadoActividadActuacion.id;
    
    @Version
    @Column(name = "version")
    private Integer EstadoActividadActuacion.version;
    
    public Long EstadoActividadActuacion.getId() {
        return this.id;
    }
    
    public void EstadoActividadActuacion.setId(Long id) {
        this.id = id;
    }
    
    public Integer EstadoActividadActuacion.getVersion() {
        return this.version;
    }
    
    public void EstadoActividadActuacion.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void EstadoActividadActuacion.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void EstadoActividadActuacion.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            EstadoActividadActuacion attached = this.entityManager.find(EstadoActividadActuacion.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void EstadoActividadActuacion.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void EstadoActividadActuacion.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        EstadoActividadActuacion merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager EstadoActividadActuacion.entityManager() {
        EntityManager em = new EstadoActividadActuacion().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long EstadoActividadActuacion.countEstadoActividadActuacions() {
        return (Long) entityManager().createQuery("select count(o) from EstadoActividadActuacion o").getSingleResult();
    }
    
    public static List<EstadoActividadActuacion> EstadoActividadActuacion.findAllEstadoActividadActuacions() {
        return entityManager().createQuery("select o from EstadoActividadActuacion o").getResultList();
    }
    
    public static EstadoActividadActuacion EstadoActividadActuacion.findEstadoActividadActuacion(Long id) {
        if (id == null) return null;
        return entityManager().find(EstadoActividadActuacion.class, id);
    }
    
    public static List<EstadoActividadActuacion> EstadoActividadActuacion.findEstadoActividadActuacionEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from EstadoActividadActuacion o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
