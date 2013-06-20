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
import ve.co.bsc.sigai.domain.DroolsRule;

privileged aspect DroolsRule_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager DroolsRule.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long DroolsRule.id;
    
    @Version
    @Column(name = "version")
    private Integer DroolsRule.version;
    
    public Long DroolsRule.getId() {
        return this.id;
    }
    
    public void DroolsRule.setId(Long id) {
        this.id = id;
    }
    
    public Integer DroolsRule.getVersion() {
        return this.version;
    }
    
    public void DroolsRule.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void DroolsRule.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void DroolsRule.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            DroolsRule attached = this.entityManager.find(DroolsRule.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void DroolsRule.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void DroolsRule.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        DroolsRule merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager DroolsRule.entityManager() {
        EntityManager em = new DroolsRule().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long DroolsRule.countDroolsRules() {
        return (Long) entityManager().createQuery("select count(o) from DroolsRule o").getSingleResult();
    }
    
    public static List<DroolsRule> DroolsRule.findAllDroolsRules() {
        return entityManager().createQuery("select o from DroolsRule o").getResultList();
    }
    
    public static DroolsRule DroolsRule.findDroolsRule(Long id) {
        if (id == null) return null;
        return entityManager().find(DroolsRule.class, id);
    }
    
    public static List<DroolsRule> DroolsRule.findDroolsRuleEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from DroolsRule o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
