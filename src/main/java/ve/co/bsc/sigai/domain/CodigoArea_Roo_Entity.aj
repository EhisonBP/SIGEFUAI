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
import ve.co.bsc.sigai.domain.CodigoArea;

privileged aspect CodigoArea_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager CodigoArea.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long CodigoArea.id;
    
    @Version
    @Column(name = "version")
    private Integer CodigoArea.version;
    
    public Long CodigoArea.getId() {
        return this.id;
    }
    
    public void CodigoArea.setId(Long id) {
        this.id = id;
    }
    
    public Integer CodigoArea.getVersion() {
        return this.version;
    }
    
    public void CodigoArea.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void CodigoArea.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void CodigoArea.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            CodigoArea attached = this.entityManager.find(CodigoArea.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void CodigoArea.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void CodigoArea.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        CodigoArea merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager CodigoArea.entityManager() {
        EntityManager em = new CodigoArea().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long CodigoArea.countCodigoAreas() {
        return (Long) entityManager().createQuery("select count(o) from CodigoArea o").getSingleResult();
    }
    
    public static List<CodigoArea> CodigoArea.findAllCodigoAreas() {
        return entityManager().createQuery("select o from CodigoArea o").getResultList();
    }
    
    public static CodigoArea CodigoArea.findCodigoArea(Long id) {
        if (id == null) return null;
        return entityManager().find(CodigoArea.class, id);
    }
    
    public static List<CodigoArea> CodigoArea.findCodigoAreaEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from CodigoArea o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
