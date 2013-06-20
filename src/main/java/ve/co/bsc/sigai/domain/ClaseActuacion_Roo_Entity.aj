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
import ve.co.bsc.sigai.domain.ClaseActuacion;

privileged aspect ClaseActuacion_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager ClaseActuacion.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long ClaseActuacion.id;
    
    @Version
    @Column(name = "version")
    private Integer ClaseActuacion.version;
    
    public Long ClaseActuacion.getId() {
        return this.id;
    }
    
    public void ClaseActuacion.setId(Long id) {
        this.id = id;
    }
    
    public Integer ClaseActuacion.getVersion() {
        return this.version;
    }
    
    public void ClaseActuacion.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void ClaseActuacion.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void ClaseActuacion.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            ClaseActuacion attached = this.entityManager.find(ClaseActuacion.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void ClaseActuacion.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void ClaseActuacion.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        ClaseActuacion merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager ClaseActuacion.entityManager() {
        EntityManager em = new ClaseActuacion().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long ClaseActuacion.countClaseActuacions() {
        return (Long) entityManager().createQuery("select count(o) from ClaseActuacion o").getSingleResult();
    }
    
    public static List<ClaseActuacion> ClaseActuacion.findAllClaseActuacions() {
        return entityManager().createQuery("select o from ClaseActuacion o").getResultList();
    }
    
    public static ClaseActuacion ClaseActuacion.findClaseActuacion(Long id) {
        if (id == null) return null;
        return entityManager().find(ClaseActuacion.class, id);
    }
    
    public static List<ClaseActuacion> ClaseActuacion.findClaseActuacionEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from ClaseActuacion o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
