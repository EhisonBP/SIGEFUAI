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
import ve.co.bsc.sigai.domain.EstadoAuditor;

privileged aspect EstadoAuditor_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager EstadoAuditor.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long EstadoAuditor.id;
    
    @Version
    @Column(name = "version")
    private Integer EstadoAuditor.version;
    
    public Long EstadoAuditor.getId() {
        return this.id;
    }
    
    public void EstadoAuditor.setId(Long id) {
        this.id = id;
    }
    
    public Integer EstadoAuditor.getVersion() {
        return this.version;
    }
    
    public void EstadoAuditor.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void EstadoAuditor.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void EstadoAuditor.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            EstadoAuditor attached = this.entityManager.find(EstadoAuditor.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void EstadoAuditor.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void EstadoAuditor.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        EstadoAuditor merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager EstadoAuditor.entityManager() {
        EntityManager em = new EstadoAuditor().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long EstadoAuditor.countEstadoAuditors() {
        return (Long) entityManager().createQuery("select count(o) from EstadoAuditor o").getSingleResult();
    }
    
    public static List<EstadoAuditor> EstadoAuditor.findAllEstadoAuditors() {
        return entityManager().createQuery("select o from EstadoAuditor o").getResultList();
    }
    
    public static EstadoAuditor EstadoAuditor.findEstadoAuditor(Long id) {
        if (id == null) return null;
        return entityManager().find(EstadoAuditor.class, id);
    }
    
    public static List<EstadoAuditor> EstadoAuditor.findEstadoAuditorEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from EstadoAuditor o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
