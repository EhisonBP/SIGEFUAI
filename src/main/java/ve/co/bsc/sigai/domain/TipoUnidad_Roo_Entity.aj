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
import ve.co.bsc.sigai.domain.TipoUnidad;

privileged aspect TipoUnidad_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager TipoUnidad.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long TipoUnidad.id;
    
    @Version
    @Column(name = "version")
    private Integer TipoUnidad.version;
    
    public Long TipoUnidad.getId() {
        return this.id;
    }
    
    public void TipoUnidad.setId(Long id) {
        this.id = id;
    }
    
    public Integer TipoUnidad.getVersion() {
        return this.version;
    }
    
    public void TipoUnidad.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void TipoUnidad.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void TipoUnidad.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            TipoUnidad attached = this.entityManager.find(TipoUnidad.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void TipoUnidad.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void TipoUnidad.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        TipoUnidad merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager TipoUnidad.entityManager() {
        EntityManager em = new TipoUnidad().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long TipoUnidad.countTipoUnidads() {
        return (Long) entityManager().createQuery("select count(o) from TipoUnidad o").getSingleResult();
    }
    
    public static List<TipoUnidad> TipoUnidad.findAllTipoUnidads() {
        return entityManager().createQuery("select o from TipoUnidad o").getResultList();
    }
    
    public static TipoUnidad TipoUnidad.findTipoUnidad(Long id) {
        if (id == null) return null;
        return entityManager().find(TipoUnidad.class, id);
    }
    
    public static List<TipoUnidad> TipoUnidad.findTipoUnidadEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from TipoUnidad o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
