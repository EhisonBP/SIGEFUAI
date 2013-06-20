package ve.co.bsc.sigai.domain.bpm;

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
import ve.co.bsc.sigai.domain.bpm.ProcessObjectCorrelation;

privileged aspect ProcessObjectCorrelation_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager ProcessObjectCorrelation.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long ProcessObjectCorrelation.id;
    
    @Version
    @Column(name = "version")
    private Integer ProcessObjectCorrelation.version;
    
    public Long ProcessObjectCorrelation.getId() {
        return this.id;
    }
    
    public void ProcessObjectCorrelation.setId(Long id) {
        this.id = id;
    }
    
    public Integer ProcessObjectCorrelation.getVersion() {
        return this.version;
    }
    
    public void ProcessObjectCorrelation.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void ProcessObjectCorrelation.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void ProcessObjectCorrelation.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            ProcessObjectCorrelation attached = this.entityManager.find(ProcessObjectCorrelation.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void ProcessObjectCorrelation.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void ProcessObjectCorrelation.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        ProcessObjectCorrelation merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager ProcessObjectCorrelation.entityManager() {
        EntityManager em = new ProcessObjectCorrelation().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long ProcessObjectCorrelation.countProcessObjectCorrelations() {
        return (Long) entityManager().createQuery("select count(o) from ProcessObjectCorrelation o").getSingleResult();
    }
    
    public static List<ProcessObjectCorrelation> ProcessObjectCorrelation.findAllProcessObjectCorrelations() {
        return entityManager().createQuery("select o from ProcessObjectCorrelation o").getResultList();
    }
    
    public static ProcessObjectCorrelation ProcessObjectCorrelation.findProcessObjectCorrelation(Long id) {
        if (id == null) return null;
        return entityManager().find(ProcessObjectCorrelation.class, id);
    }
    
    public static List<ProcessObjectCorrelation> ProcessObjectCorrelation.findProcessObjectCorrelationEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from ProcessObjectCorrelation o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
