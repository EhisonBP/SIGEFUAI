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
import ve.co.bsc.sigai.domain.ActividadAuditor;

privileged aspect ActividadAuditor_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager ActividadAuditor.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long ActividadAuditor.id;
    
    @Version
    @Column(name = "version")
    private Integer ActividadAuditor.version;
    
    public Long ActividadAuditor.getId() {
        return this.id;
    }
    
    public void ActividadAuditor.setId(Long id) {
        this.id = id;
    }
    
    public Integer ActividadAuditor.getVersion() {
        return this.version;
    }
    
    public void ActividadAuditor.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void ActividadAuditor.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void ActividadAuditor.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            ActividadAuditor attached = this.entityManager.find(ActividadAuditor.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void ActividadAuditor.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void ActividadAuditor.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        ActividadAuditor merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager ActividadAuditor.entityManager() {
        EntityManager em = new ActividadAuditor().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long ActividadAuditor.countActividadAuditors() {
        return (Long) entityManager().createQuery("select count(o) from ActividadAuditor o").getSingleResult();
    }
    
    public static List<ActividadAuditor> ActividadAuditor.findAllActividadAuditors() {
        return entityManager().createQuery("select o from ActividadAuditor o").getResultList();
    }
    
    public static ActividadAuditor ActividadAuditor.findActividadAuditor(Long id) {
        if (id == null) return null;
        return entityManager().find(ActividadAuditor.class, id);
    }
    
    public static List<ActividadAuditor> ActividadAuditor.findActividadAuditorEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from ActividadAuditor o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
