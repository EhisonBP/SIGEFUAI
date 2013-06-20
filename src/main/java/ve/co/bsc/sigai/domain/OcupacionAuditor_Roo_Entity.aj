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
import ve.co.bsc.sigai.domain.OcupacionAuditor;

privileged aspect OcupacionAuditor_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager OcupacionAuditor.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long OcupacionAuditor.id;
    
    @Version
    @Column(name = "version")
    private Integer OcupacionAuditor.version;
    
    public Long OcupacionAuditor.getId() {
        return this.id;
    }
    
    public void OcupacionAuditor.setId(Long id) {
        this.id = id;
    }
    
    public Integer OcupacionAuditor.getVersion() {
        return this.version;
    }
    
    public void OcupacionAuditor.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void OcupacionAuditor.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void OcupacionAuditor.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            OcupacionAuditor attached = this.entityManager.find(OcupacionAuditor.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void OcupacionAuditor.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void OcupacionAuditor.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        OcupacionAuditor merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager OcupacionAuditor.entityManager() {
        EntityManager em = new OcupacionAuditor().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long OcupacionAuditor.countOcupacionAuditors() {
        return (Long) entityManager().createQuery("select count(o) from OcupacionAuditor o").getSingleResult();
    }
    
    public static List<OcupacionAuditor> OcupacionAuditor.findAllOcupacionAuditors() {
        return entityManager().createQuery("select o from OcupacionAuditor o").getResultList();
    }
    
    public static OcupacionAuditor OcupacionAuditor.findOcupacionAuditor(Long id) {
        if (id == null) return null;
        return entityManager().find(OcupacionAuditor.class, id);
    }
    
    public static List<OcupacionAuditor> OcupacionAuditor.findOcupacionAuditorEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from OcupacionAuditor o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
