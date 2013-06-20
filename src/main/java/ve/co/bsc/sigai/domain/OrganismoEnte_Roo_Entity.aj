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
import ve.co.bsc.sigai.domain.OrganismoEnte;

privileged aspect OrganismoEnte_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager OrganismoEnte.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long OrganismoEnte.id;
    
    @Version
    @Column(name = "version")
    private Integer OrganismoEnte.version;
    
    public Long OrganismoEnte.getId() {
        return this.id;
    }
    
    public void OrganismoEnte.setId(Long id) {
        this.id = id;
    }
    
    public Integer OrganismoEnte.getVersion() {
        return this.version;
    }
    
    public void OrganismoEnte.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void OrganismoEnte.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void OrganismoEnte.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            OrganismoEnte attached = this.entityManager.find(OrganismoEnte.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void OrganismoEnte.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void OrganismoEnte.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        OrganismoEnte merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager OrganismoEnte.entityManager() {
        EntityManager em = new OrganismoEnte().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long OrganismoEnte.countOrganismoEntes() {
        return (Long) entityManager().createQuery("select count(o) from OrganismoEnte o").getSingleResult();
    }
    
    public static List<OrganismoEnte> OrganismoEnte.findAllOrganismoEntes() {
        return entityManager().createQuery("select o from OrganismoEnte o").getResultList();
    }
    
    public static OrganismoEnte OrganismoEnte.findOrganismoEnte(Long id) {
        if (id == null) return null;
        return entityManager().find(OrganismoEnte.class, id);
    }
    
    public static List<OrganismoEnte> OrganismoEnte.findOrganismoEnteEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from OrganismoEnte o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
