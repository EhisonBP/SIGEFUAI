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
import ve.co.bsc.sigai.domain.Estado;

privileged aspect Estado_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager Estado.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Estado.id;
    
    @Version
    @Column(name = "version")
    private Integer Estado.version;
    
    public Long Estado.getId() {
        return this.id;
    }
    
    public void Estado.setId(Long id) {
        this.id = id;
    }
    
    public Integer Estado.getVersion() {
        return this.version;
    }
    
    public void Estado.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void Estado.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Estado.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Estado attached = this.entityManager.find(Estado.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Estado.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Estado.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Estado merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager Estado.entityManager() {
        EntityManager em = new Estado().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Estado.countEstadoes() {
        return (Long) entityManager().createQuery("select count(o) from Estado o").getSingleResult();
    }
    
    public static List<Estado> Estado.findAllEstadoes() {
        return entityManager().createQuery("select o from Estado o").getResultList();
    }
    
    public static Estado Estado.findEstado(Long id) {
        if (id == null) return null;
        return entityManager().find(Estado.class, id);
    }
    
    public static List<Estado> Estado.findEstadoEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from Estado o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
