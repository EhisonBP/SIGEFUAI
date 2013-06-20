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
import ve.co.bsc.sigai.domain.EstadoPlan;

privileged aspect EstadoPlan_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager EstadoPlan.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long EstadoPlan.id;
    
    @Version
    @Column(name = "version")
    private Integer EstadoPlan.version;
    
    public Long EstadoPlan.getId() {
        return this.id;
    }
    
    public void EstadoPlan.setId(Long id) {
        this.id = id;
    }
    
    public Integer EstadoPlan.getVersion() {
        return this.version;
    }
    
    public void EstadoPlan.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void EstadoPlan.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void EstadoPlan.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            EstadoPlan attached = this.entityManager.find(EstadoPlan.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void EstadoPlan.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void EstadoPlan.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        EstadoPlan merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager EstadoPlan.entityManager() {
        EntityManager em = new EstadoPlan().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long EstadoPlan.countEstadoPlans() {
        return (Long) entityManager().createQuery("select count(o) from EstadoPlan o").getSingleResult();
    }
    
    public static List<EstadoPlan> EstadoPlan.findAllEstadoPlans() {
        return entityManager().createQuery("select o from EstadoPlan o").getResultList();
    }
    
    public static EstadoPlan EstadoPlan.findEstadoPlan(Long id) {
        if (id == null) return null;
        return entityManager().find(EstadoPlan.class, id);
    }
    
    public static List<EstadoPlan> EstadoPlan.findEstadoPlanEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from EstadoPlan o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
