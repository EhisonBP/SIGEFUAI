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
import ve.co.bsc.sigai.domain.EstadoPlanDeAccion;

privileged aspect EstadoPlanDeAccion_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager EstadoPlanDeAccion.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long EstadoPlanDeAccion.id;
    
    @Version
    @Column(name = "version")
    private Integer EstadoPlanDeAccion.version;
    
    public Long EstadoPlanDeAccion.getId() {
        return this.id;
    }
    
    public void EstadoPlanDeAccion.setId(Long id) {
        this.id = id;
    }
    
    public Integer EstadoPlanDeAccion.getVersion() {
        return this.version;
    }
    
    public void EstadoPlanDeAccion.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void EstadoPlanDeAccion.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void EstadoPlanDeAccion.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            EstadoPlanDeAccion attached = this.entityManager.find(EstadoPlanDeAccion.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void EstadoPlanDeAccion.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void EstadoPlanDeAccion.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        EstadoPlanDeAccion merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager EstadoPlanDeAccion.entityManager() {
        EntityManager em = new EstadoPlanDeAccion().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long EstadoPlanDeAccion.countEstadoPlanDeAccions() {
        return (Long) entityManager().createQuery("select count(o) from EstadoPlanDeAccion o").getSingleResult();
    }
    
    public static List<EstadoPlanDeAccion> EstadoPlanDeAccion.findAllEstadoPlanDeAccions() {
        return entityManager().createQuery("select o from EstadoPlanDeAccion o").getResultList();
    }
    
    public static EstadoPlanDeAccion EstadoPlanDeAccion.findEstadoPlanDeAccion(Long id) {
        if (id == null) return null;
        return entityManager().find(EstadoPlanDeAccion.class, id);
    }
    
    public static List<EstadoPlanDeAccion> EstadoPlanDeAccion.findEstadoPlanDeAccionEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from EstadoPlanDeAccion o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
