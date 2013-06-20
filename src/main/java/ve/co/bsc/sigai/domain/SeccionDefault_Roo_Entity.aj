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
import ve.co.bsc.sigai.domain.SeccionDefault;

privileged aspect SeccionDefault_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager SeccionDefault.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long SeccionDefault.id;
    
    @Version
    @Column(name = "version")
    private Integer SeccionDefault.version;
    
    public Long SeccionDefault.getId() {
        return this.id;
    }
    
    public void SeccionDefault.setId(Long id) {
        this.id = id;
    }
    
    public Integer SeccionDefault.getVersion() {
        return this.version;
    }
    
    public void SeccionDefault.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void SeccionDefault.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void SeccionDefault.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            SeccionDefault attached = this.entityManager.find(SeccionDefault.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void SeccionDefault.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void SeccionDefault.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        SeccionDefault merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager SeccionDefault.entityManager() {
        EntityManager em = new SeccionDefault().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long SeccionDefault.countSeccionDefaults() {
        return (Long) entityManager().createQuery("select count(o) from SeccionDefault o").getSingleResult();
    }
    
    public static List<SeccionDefault> SeccionDefault.findAllSeccionDefaults() {
        return entityManager().createQuery("select o from SeccionDefault o").getResultList();
    }
    
    public static SeccionDefault SeccionDefault.findSeccionDefault(Long id) {
        if (id == null) return null;
        return entityManager().find(SeccionDefault.class, id);
    }
    
    public static List<SeccionDefault> SeccionDefault.findSeccionDefaultEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from SeccionDefault o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
