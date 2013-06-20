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
import ve.co.bsc.sigai.domain.TipoOrganismo;

privileged aspect TipoOrganismo_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager TipoOrganismo.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long TipoOrganismo.id;
    
    @Version
    @Column(name = "version")
    private Integer TipoOrganismo.version;
    
    public Long TipoOrganismo.getId() {
        return this.id;
    }
    
    public void TipoOrganismo.setId(Long id) {
        this.id = id;
    }
    
    public Integer TipoOrganismo.getVersion() {
        return this.version;
    }
    
    public void TipoOrganismo.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void TipoOrganismo.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void TipoOrganismo.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            TipoOrganismo attached = this.entityManager.find(TipoOrganismo.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void TipoOrganismo.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void TipoOrganismo.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        TipoOrganismo merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager TipoOrganismo.entityManager() {
        EntityManager em = new TipoOrganismo().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long TipoOrganismo.countTipoOrganismoes() {
        return (Long) entityManager().createQuery("select count(o) from TipoOrganismo o").getSingleResult();
    }
    
    public static List<TipoOrganismo> TipoOrganismo.findAllTipoOrganismoes() {
        return entityManager().createQuery("select o from TipoOrganismo o").getResultList();
    }
    
    public static TipoOrganismo TipoOrganismo.findTipoOrganismo(Long id) {
        if (id == null) return null;
        return entityManager().find(TipoOrganismo.class, id);
    }
    
    public static List<TipoOrganismo> TipoOrganismo.findTipoOrganismoEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from TipoOrganismo o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
