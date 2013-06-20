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
import ve.co.bsc.sigai.domain.AutoridadMaxima;

privileged aspect AutoridadMaxima_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager AutoridadMaxima.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long AutoridadMaxima.id;
    
    @Version
    @Column(name = "version")
    private Integer AutoridadMaxima.version;
    
    public Long AutoridadMaxima.getId() {
        return this.id;
    }
    
    public void AutoridadMaxima.setId(Long id) {
        this.id = id;
    }
    
    public Integer AutoridadMaxima.getVersion() {
        return this.version;
    }
    
    public void AutoridadMaxima.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void AutoridadMaxima.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void AutoridadMaxima.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            AutoridadMaxima attached = this.entityManager.find(AutoridadMaxima.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void AutoridadMaxima.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void AutoridadMaxima.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        AutoridadMaxima merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager AutoridadMaxima.entityManager() {
        EntityManager em = new AutoridadMaxima().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long AutoridadMaxima.countAutoridadMaximas() {
        return (Long) entityManager().createQuery("select count(o) from AutoridadMaxima o").getSingleResult();
    }
    
    public static List<AutoridadMaxima> AutoridadMaxima.findAllAutoridadMaximas() {
        return entityManager().createQuery("select o from AutoridadMaxima o").getResultList();
    }
    
    public static AutoridadMaxima AutoridadMaxima.findAutoridadMaxima(Long id) {
        if (id == null) return null;
        return entityManager().find(AutoridadMaxima.class, id);
    }
    
    public static List<AutoridadMaxima> AutoridadMaxima.findAutoridadMaximaEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from AutoridadMaxima o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
