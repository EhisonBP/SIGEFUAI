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
import ve.co.bsc.sigai.domain.JbpmProcess;

privileged aspect JbpmProcess_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager JbpmProcess.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long JbpmProcess.id;
    
    @Version
    @Column(name = "version")
    private Integer JbpmProcess.version;
    
    public Long JbpmProcess.getId() {
        return this.id;
    }
    
    public void JbpmProcess.setId(Long id) {
        this.id = id;
    }
    
    public Integer JbpmProcess.getVersion() {
        return this.version;
    }
    
    public void JbpmProcess.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void JbpmProcess.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void JbpmProcess.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            JbpmProcess attached = this.entityManager.find(JbpmProcess.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void JbpmProcess.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void JbpmProcess.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        JbpmProcess merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager JbpmProcess.entityManager() {
        EntityManager em = new JbpmProcess().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long JbpmProcess.countJbpmProcesses() {
        return (Long) entityManager().createQuery("select count(o) from JbpmProcess o").getSingleResult();
    }
    
    public static List<JbpmProcess> JbpmProcess.findAllJbpmProcesses() {
        return entityManager().createQuery("select o from JbpmProcess o").getResultList();
    }
    
    public static JbpmProcess JbpmProcess.findJbpmProcess(Long id) {
        if (id == null) return null;
        return entityManager().find(JbpmProcess.class, id);
    }
    
    public static List<JbpmProcess> JbpmProcess.findJbpmProcessEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from JbpmProcess o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
