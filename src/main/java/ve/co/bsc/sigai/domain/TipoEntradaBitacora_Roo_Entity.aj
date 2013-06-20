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
import ve.co.bsc.sigai.domain.TipoEntradaBitacora;

privileged aspect TipoEntradaBitacora_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager TipoEntradaBitacora.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long TipoEntradaBitacora.id;
    
    @Version
    @Column(name = "version")
    private Integer TipoEntradaBitacora.version;
    
    public Long TipoEntradaBitacora.getId() {
        return this.id;
    }
    
    public void TipoEntradaBitacora.setId(Long id) {
        this.id = id;
    }
    
    public Integer TipoEntradaBitacora.getVersion() {
        return this.version;
    }
    
    public void TipoEntradaBitacora.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void TipoEntradaBitacora.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void TipoEntradaBitacora.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            TipoEntradaBitacora attached = this.entityManager.find(TipoEntradaBitacora.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void TipoEntradaBitacora.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void TipoEntradaBitacora.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        TipoEntradaBitacora merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager TipoEntradaBitacora.entityManager() {
        EntityManager em = new TipoEntradaBitacora().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long TipoEntradaBitacora.countTipoEntradaBitacoras() {
        return (Long) entityManager().createQuery("select count(o) from TipoEntradaBitacora o").getSingleResult();
    }
    
    public static List<TipoEntradaBitacora> TipoEntradaBitacora.findAllTipoEntradaBitacoras() {
        return entityManager().createQuery("select o from TipoEntradaBitacora o").getResultList();
    }
    
    public static TipoEntradaBitacora TipoEntradaBitacora.findTipoEntradaBitacora(Long id) {
        if (id == null) return null;
        return entityManager().find(TipoEntradaBitacora.class, id);
    }
    
    public static List<TipoEntradaBitacora> TipoEntradaBitacora.findTipoEntradaBitacoraEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from TipoEntradaBitacora o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
