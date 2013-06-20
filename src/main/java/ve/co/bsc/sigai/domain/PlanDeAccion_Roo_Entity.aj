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
import ve.co.bsc.sigai.domain.PlanDeAccion;

privileged aspect PlanDeAccion_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager PlanDeAccion.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long PlanDeAccion.id;
    
    @Version
    @Column(name = "version")
    private Integer PlanDeAccion.version;
    
    public Long PlanDeAccion.getId() {
        return this.id;
    }
    
    public void PlanDeAccion.setId(Long id) {
        this.id = id;
    }
    
    public Integer PlanDeAccion.getVersion() {
        return this.version;
    }
    
    public void PlanDeAccion.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void PlanDeAccion.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void PlanDeAccion.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            PlanDeAccion attached = this.entityManager.find(PlanDeAccion.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void PlanDeAccion.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void PlanDeAccion.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        PlanDeAccion merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager PlanDeAccion.entityManager() {
        EntityManager em = new PlanDeAccion().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long PlanDeAccion.countPlanDeAccions() {
        return (Long) entityManager().createQuery("select count(o) from PlanDeAccion o").getSingleResult();
    }
    
    public static List<PlanDeAccion> PlanDeAccion.findAllPlanDeAccions() {
        return entityManager().createQuery("select o from PlanDeAccion o").getResultList();
    }
    
    public static PlanDeAccion PlanDeAccion.findPlanDeAccion(Long id) {
        if (id == null) return null;
        return entityManager().find(PlanDeAccion.class, id);
    }
    
    public static List<PlanDeAccion> PlanDeAccion.findPlanDeAccionEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from PlanDeAccion o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
