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
import ve.co.bsc.sigai.domain.AsignarOrganismo;

privileged aspect AsignarOrganismo_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager AsignarOrganismo.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long AsignarOrganismo.id;
    
    @Version
    @Column(name = "version")
    private Integer AsignarOrganismo.version;
    
    public Long AsignarOrganismo.getId() {
        return this.id;
    }
    
    public void AsignarOrganismo.setId(Long id) {
        this.id = id;
    }
    
    public Integer AsignarOrganismo.getVersion() {
        return this.version;
    }
    
    public void AsignarOrganismo.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void AsignarOrganismo.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void AsignarOrganismo.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            AsignarOrganismo attached = this.entityManager.find(AsignarOrganismo.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void AsignarOrganismo.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void AsignarOrganismo.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        AsignarOrganismo merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager AsignarOrganismo.entityManager() {
        EntityManager em = new AsignarOrganismo().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long AsignarOrganismo.countAsignarOrganismoes() {
        return (Long) entityManager().createQuery("select count(o) from AsignarOrganismo o").getSingleResult();
    }
    
    public static List<AsignarOrganismo> AsignarOrganismo.findAllAsignarOrganismoes() {
        return entityManager().createQuery("select o from AsignarOrganismo o").getResultList();
    }
    
    public static AsignarOrganismo AsignarOrganismo.findAsignarOrganismo(Long id) {
        if (id == null) return null;
        return entityManager().find(AsignarOrganismo.class, id);
    }
    
    public static List<AsignarOrganismo> AsignarOrganismo.findAsignarOrganismoEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from AsignarOrganismo o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
