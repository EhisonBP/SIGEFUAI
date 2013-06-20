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
import ve.co.bsc.sigai.domain.JbpmSession;

privileged aspect JbpmSession_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager JbpmSession.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long JbpmSession.id;
    
    @Version
    @Column(name = "version")
    private Integer JbpmSession.version;
    
    public Long JbpmSession.getId() {
        return this.id;
    }
    
    public void JbpmSession.setId(Long id) {
        this.id = id;
    }
    
    public Integer JbpmSession.getVersion() {
        return this.version;
    }
    
    public void JbpmSession.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void JbpmSession.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void JbpmSession.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            JbpmSession attached = this.entityManager.find(JbpmSession.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void JbpmSession.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void JbpmSession.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        JbpmSession merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager JbpmSession.entityManager() {
        EntityManager em = new JbpmSession().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long JbpmSession.countJbpmSessions() {
        return (Long) entityManager().createQuery("select count(o) from JbpmSession o").getSingleResult();
    }
    
    public static List<JbpmSession> JbpmSession.findAllJbpmSessions() {
        return entityManager().createQuery("select o from JbpmSession o").getResultList();
    }
    
    public static JbpmSession JbpmSession.findJbpmSession(Long id) {
        if (id == null) return null;
        return entityManager().find(JbpmSession.class, id);
    }
    
    public static List<JbpmSession> JbpmSession.findJbpmSessionEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from JbpmSession o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
