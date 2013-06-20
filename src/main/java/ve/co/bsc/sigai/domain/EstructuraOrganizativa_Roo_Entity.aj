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
import ve.co.bsc.sigai.domain.EstructuraOrganizativa;

privileged aspect EstructuraOrganizativa_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager EstructuraOrganizativa.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long EstructuraOrganizativa.id;
    
    @Version
    @Column(name = "version")
    private Integer EstructuraOrganizativa.version;
    
    public Long EstructuraOrganizativa.getId() {
        return this.id;
    }
    
    public void EstructuraOrganizativa.setId(Long id) {
        this.id = id;
    }
    
    public Integer EstructuraOrganizativa.getVersion() {
        return this.version;
    }
    
    public void EstructuraOrganizativa.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void EstructuraOrganizativa.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void EstructuraOrganizativa.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            EstructuraOrganizativa attached = this.entityManager.find(EstructuraOrganizativa.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void EstructuraOrganizativa.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void EstructuraOrganizativa.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        EstructuraOrganizativa merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager EstructuraOrganizativa.entityManager() {
        EntityManager em = new EstructuraOrganizativa().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long EstructuraOrganizativa.countEstructuraOrganizativas() {
        return (Long) entityManager().createQuery("select count(o) from EstructuraOrganizativa o").getSingleResult();
    }
    
    public static List<EstructuraOrganizativa> EstructuraOrganizativa.findAllEstructuraOrganizativas() {
        return entityManager().createQuery("select o from EstructuraOrganizativa o").getResultList();
    }
    
    public static EstructuraOrganizativa EstructuraOrganizativa.findEstructuraOrganizativa(Long id) {
        if (id == null) return null;
        return entityManager().find(EstructuraOrganizativa.class, id);
    }
    
    public static List<EstructuraOrganizativa> EstructuraOrganizativa.findEstructuraOrganizativaEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from EstructuraOrganizativa o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
