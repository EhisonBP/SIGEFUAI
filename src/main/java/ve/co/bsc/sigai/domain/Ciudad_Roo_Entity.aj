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
import ve.co.bsc.sigai.domain.Ciudad;

privileged aspect Ciudad_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager Ciudad.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Ciudad.id;
    
    @Version
    @Column(name = "version")
    private Integer Ciudad.version;
    
    public Long Ciudad.getId() {
        return this.id;
    }
    
    public void Ciudad.setId(Long id) {
        this.id = id;
    }
    
    public Integer Ciudad.getVersion() {
        return this.version;
    }
    
    public void Ciudad.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void Ciudad.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Ciudad.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Ciudad attached = this.entityManager.find(Ciudad.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Ciudad.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Ciudad.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Ciudad merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager Ciudad.entityManager() {
        EntityManager em = new Ciudad().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Ciudad.countCiudads() {
        return (Long) entityManager().createQuery("select count(o) from Ciudad o").getSingleResult();
    }
    
    public static List<Ciudad> Ciudad.findAllCiudads() {
        return entityManager().createQuery("select o from Ciudad o").getResultList();
    }
    
    public static Ciudad Ciudad.findCiudad(Long id) {
        if (id == null) return null;
        return entityManager().find(Ciudad.class, id);
    }
    
    public static List<Ciudad> Ciudad.findCiudadEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from Ciudad o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
