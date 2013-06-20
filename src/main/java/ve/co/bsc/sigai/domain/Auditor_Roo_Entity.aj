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
import ve.co.bsc.sigai.domain.Auditor;

privileged aspect Auditor_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager Auditor.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Auditor.id;
    
    @Version
    @Column(name = "version")
    private Integer Auditor.version;
    
    public Long Auditor.getId() {
        return this.id;
    }
    
    public void Auditor.setId(Long id) {
        this.id = id;
    }
    
    public Integer Auditor.getVersion() {
        return this.version;
    }
    
    public void Auditor.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void Auditor.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Auditor.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Auditor attached = this.entityManager.find(Auditor.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Auditor.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Auditor.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Auditor merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager Auditor.entityManager() {
        EntityManager em = new Auditor().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Auditor.countAuditors() {
        return (Long) entityManager().createQuery("select count(o) from Auditor o").getSingleResult();
    }
    
    public static List<Auditor> Auditor.findAllAuditors() {
        return entityManager().createQuery("select o from Auditor o").getResultList();
    }
    
    public static Auditor Auditor.findAuditor(Long id) {
        if (id == null) return null;
        return entityManager().find(Auditor.class, id);
    }
    
    public static List<Auditor> Auditor.findAuditorEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from Auditor o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
