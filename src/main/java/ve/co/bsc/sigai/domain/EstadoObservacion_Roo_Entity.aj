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
import ve.co.bsc.sigai.domain.EstadoObservacion;

privileged aspect EstadoObservacion_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager EstadoObservacion.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long EstadoObservacion.id;
    
    @Version
    @Column(name = "version")
    private Integer EstadoObservacion.version;
    
    public Long EstadoObservacion.getId() {
        return this.id;
    }
    
    public void EstadoObservacion.setId(Long id) {
        this.id = id;
    }
    
    public Integer EstadoObservacion.getVersion() {
        return this.version;
    }
    
    public void EstadoObservacion.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void EstadoObservacion.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void EstadoObservacion.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            EstadoObservacion attached = this.entityManager.find(EstadoObservacion.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void EstadoObservacion.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void EstadoObservacion.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        EstadoObservacion merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager EstadoObservacion.entityManager() {
        EntityManager em = new EstadoObservacion().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long EstadoObservacion.countEstadoObservacions() {
        return (Long) entityManager().createQuery("select count(o) from EstadoObservacion o").getSingleResult();
    }
    
    public static List<EstadoObservacion> EstadoObservacion.findAllEstadoObservacions() {
        return entityManager().createQuery("select o from EstadoObservacion o").getResultList();
    }
    
    public static EstadoObservacion EstadoObservacion.findEstadoObservacion(Long id) {
        if (id == null) return null;
        return entityManager().find(EstadoObservacion.class, id);
    }
    
    public static List<EstadoObservacion> EstadoObservacion.findEstadoObservacionEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from EstadoObservacion o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
