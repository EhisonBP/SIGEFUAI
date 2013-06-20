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
import ve.co.bsc.sigai.domain.TipoPersonaRif;

privileged aspect TipoPersonaRif_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager TipoPersonaRif.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long TipoPersonaRif.id;
    
    @Version
    @Column(name = "version")
    private Integer TipoPersonaRif.version;
    
    public Long TipoPersonaRif.getId() {
        return this.id;
    }
    
    public void TipoPersonaRif.setId(Long id) {
        this.id = id;
    }
    
    public Integer TipoPersonaRif.getVersion() {
        return this.version;
    }
    
    public void TipoPersonaRif.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void TipoPersonaRif.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void TipoPersonaRif.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            TipoPersonaRif attached = this.entityManager.find(TipoPersonaRif.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void TipoPersonaRif.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void TipoPersonaRif.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        TipoPersonaRif merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager TipoPersonaRif.entityManager() {
        EntityManager em = new TipoPersonaRif().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long TipoPersonaRif.countTipoPersonaRifs() {
        return (Long) entityManager().createQuery("select count(o) from TipoPersonaRif o").getSingleResult();
    }
    
    public static List<TipoPersonaRif> TipoPersonaRif.findAllTipoPersonaRifs() {
        return entityManager().createQuery("select o from TipoPersonaRif o").getResultList();
    }
    
    public static TipoPersonaRif TipoPersonaRif.findTipoPersonaRif(Long id) {
        if (id == null) return null;
        return entityManager().find(TipoPersonaRif.class, id);
    }
    
    public static List<TipoPersonaRif> TipoPersonaRif.findTipoPersonaRifEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from TipoPersonaRif o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
