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
import ve.co.bsc.sigai.domain.TipoRol;

privileged aspect TipoRol_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager TipoRol.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long TipoRol.id;
    
    @Version
    @Column(name = "version")
    private Integer TipoRol.version;
    
    public Long TipoRol.getId() {
        return this.id;
    }
    
    public void TipoRol.setId(Long id) {
        this.id = id;
    }
    
    public Integer TipoRol.getVersion() {
        return this.version;
    }
    
    public void TipoRol.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void TipoRol.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void TipoRol.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            TipoRol attached = this.entityManager.find(TipoRol.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void TipoRol.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void TipoRol.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        TipoRol merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager TipoRol.entityManager() {
        EntityManager em = new TipoRol().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long TipoRol.countTipoRols() {
        return (Long) entityManager().createQuery("select count(o) from TipoRol o").getSingleResult();
    }
    
    public static List<TipoRol> TipoRol.findAllTipoRols() {
        return entityManager().createQuery("select o from TipoRol o").getResultList();
    }
    
    public static TipoRol TipoRol.findTipoRol(Long id) {
        if (id == null) return null;
        return entityManager().find(TipoRol.class, id);
    }
    
    public static List<TipoRol> TipoRol.findTipoRolEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from TipoRol o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
