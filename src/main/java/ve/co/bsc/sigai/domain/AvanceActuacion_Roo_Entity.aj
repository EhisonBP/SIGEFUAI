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
import ve.co.bsc.sigai.domain.AvanceActuacion;

privileged aspect AvanceActuacion_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager AvanceActuacion.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long AvanceActuacion.id;
    
    @Version
    @Column(name = "version")
    private Integer AvanceActuacion.version;
    
    public Long AvanceActuacion.getId() {
        return this.id;
    }
    
    public void AvanceActuacion.setId(Long id) {
        this.id = id;
    }
    
    public Integer AvanceActuacion.getVersion() {
        return this.version;
    }
    
    public void AvanceActuacion.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void AvanceActuacion.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void AvanceActuacion.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            AvanceActuacion attached = this.entityManager.find(AvanceActuacion.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void AvanceActuacion.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void AvanceActuacion.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        AvanceActuacion merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager AvanceActuacion.entityManager() {
        EntityManager em = new AvanceActuacion().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long AvanceActuacion.countAvanceActuacions() {
        return (Long) entityManager().createQuery("select count(o) from AvanceActuacion o").getSingleResult();
    }
    
    public static List<AvanceActuacion> AvanceActuacion.findAllAvanceActuacions() {
        return entityManager().createQuery("select o from AvanceActuacion o").getResultList();
    }
    
    public static AvanceActuacion AvanceActuacion.findAvanceActuacion(Long id) {
        if (id == null) return null;
        return entityManager().find(AvanceActuacion.class, id);
    }
    
    public static List<AvanceActuacion> AvanceActuacion.findAvanceActuacionEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from AvanceActuacion o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
