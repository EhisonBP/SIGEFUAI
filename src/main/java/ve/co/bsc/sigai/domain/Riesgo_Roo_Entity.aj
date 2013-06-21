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
import ve.co.bsc.sigai.domain.Riesgo;

privileged aspect Riesgo_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager Riesgo.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Riesgo.id;
    
    @Version
    @Column(name = "version")
    private Integer Riesgo.version;
    
    public Long Riesgo.getId() {
        return this.id;
    }
    
    public void Riesgo.setId(Long id) {
        this.id = id;
    }
    
    public Integer Riesgo.getVersion() {
        return this.version;
    }
    
    public void Riesgo.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void Riesgo.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Riesgo.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Riesgo attached = this.entityManager.find(Riesgo.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Riesgo.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Riesgo.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Riesgo merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager Riesgo.entityManager() {
        EntityManager em = new Riesgo().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Riesgo.countRiesgoes() {
        return (Long) entityManager().createQuery("select count(o) from Riesgo o").getSingleResult();
    }
    
    public static List<Riesgo> Riesgo.findAllRiesgoes() {
        return entityManager().createQuery("select o from Riesgo o").getResultList();
    }
    
    public static Riesgo Riesgo.findRiesgo(Long id) {
        if (id == null) return null;
        return entityManager().find(Riesgo.class, id);
    }
    
    public static List<Riesgo> Riesgo.findRiesgoEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from Riesgo o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}