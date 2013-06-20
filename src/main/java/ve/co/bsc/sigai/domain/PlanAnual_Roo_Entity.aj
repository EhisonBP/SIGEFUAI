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
import ve.co.bsc.sigai.domain.PlanAnual;

privileged aspect PlanAnual_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager PlanAnual.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long PlanAnual.id;
    
    @Version
    @Column(name = "version")
    private Integer PlanAnual.version;
    
    public Long PlanAnual.getId() {
        return this.id;
    }
    
    public void PlanAnual.setId(Long id) {
        this.id = id;
    }
    
    public Integer PlanAnual.getVersion() {
        return this.version;
    }
    
    public void PlanAnual.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void PlanAnual.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void PlanAnual.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            PlanAnual attached = this.entityManager.find(PlanAnual.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void PlanAnual.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void PlanAnual.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        PlanAnual merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager PlanAnual.entityManager() {
        EntityManager em = new PlanAnual().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long PlanAnual.countPlanAnuals() {
        return (Long) entityManager().createQuery("select count(o) from PlanAnual o").getSingleResult();
    }
    
    public static List<PlanAnual> PlanAnual.findAllPlanAnuals() {
        return entityManager().createQuery("select o from PlanAnual o").getResultList();
    }
    
    public static PlanAnual PlanAnual.findPlanAnual(Long id) {
        if (id == null) return null;
        return entityManager().find(PlanAnual.class, id);
    }
    
    public static List<PlanAnual> PlanAnual.findPlanAnualEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from PlanAnual o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
