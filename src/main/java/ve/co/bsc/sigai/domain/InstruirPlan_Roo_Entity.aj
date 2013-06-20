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
import ve.co.bsc.sigai.domain.InstruirPlan;

privileged aspect InstruirPlan_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager InstruirPlan.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long InstruirPlan.id;
    
    @Version
    @Column(name = "version")
    private Integer InstruirPlan.version;
    
    public Long InstruirPlan.getId() {
        return this.id;
    }
    
    public void InstruirPlan.setId(Long id) {
        this.id = id;
    }
    
    public Integer InstruirPlan.getVersion() {
        return this.version;
    }
    
    public void InstruirPlan.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void InstruirPlan.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void InstruirPlan.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            InstruirPlan attached = this.entityManager.find(InstruirPlan.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void InstruirPlan.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void InstruirPlan.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        InstruirPlan merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager InstruirPlan.entityManager() {
        EntityManager em = new InstruirPlan().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long InstruirPlan.countInstruirPlans() {
        return (Long) entityManager().createQuery("select count(o) from InstruirPlan o").getSingleResult();
    }
    
    public static List<InstruirPlan> InstruirPlan.findAllInstruirPlans() {
        return entityManager().createQuery("select o from InstruirPlan o").getResultList();
    }
    
    public static InstruirPlan InstruirPlan.findInstruirPlan(Long id) {
        if (id == null) return null;
        return entityManager().find(InstruirPlan.class, id);
    }
    
    public static List<InstruirPlan> InstruirPlan.findInstruirPlanEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from InstruirPlan o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
