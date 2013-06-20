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
import ve.co.bsc.sigai.domain.ClasificacionRiesgo;

privileged aspect ClasificacionRiesgo_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager ClasificacionRiesgo.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long ClasificacionRiesgo.id;
    
    @Version
    @Column(name = "version")
    private Integer ClasificacionRiesgo.version;
    
    public Long ClasificacionRiesgo.getId() {
        return this.id;
    }
    
    public void ClasificacionRiesgo.setId(Long id) {
        this.id = id;
    }
    
    public Integer ClasificacionRiesgo.getVersion() {
        return this.version;
    }
    
    public void ClasificacionRiesgo.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void ClasificacionRiesgo.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void ClasificacionRiesgo.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            ClasificacionRiesgo attached = this.entityManager.find(ClasificacionRiesgo.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void ClasificacionRiesgo.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void ClasificacionRiesgo.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        ClasificacionRiesgo merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager ClasificacionRiesgo.entityManager() {
        EntityManager em = new ClasificacionRiesgo().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long ClasificacionRiesgo.countClasificacionRiesgoes() {
        return (Long) entityManager().createQuery("select count(o) from ClasificacionRiesgo o").getSingleResult();
    }
    
    public static List<ClasificacionRiesgo> ClasificacionRiesgo.findAllClasificacionRiesgoes() {
        return entityManager().createQuery("select o from ClasificacionRiesgo o").getResultList();
    }
    
    public static ClasificacionRiesgo ClasificacionRiesgo.findClasificacionRiesgo(Long id) {
        if (id == null) return null;
        return entityManager().find(ClasificacionRiesgo.class, id);
    }
    
    public static List<ClasificacionRiesgo> ClasificacionRiesgo.findClasificacionRiesgoEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from ClasificacionRiesgo o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
