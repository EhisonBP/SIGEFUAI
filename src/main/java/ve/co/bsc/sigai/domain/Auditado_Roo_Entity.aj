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
import ve.co.bsc.sigai.domain.Auditado;

privileged aspect Auditado_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager Auditado.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Auditado.id;
    
    @Version
    @Column(name = "version")
    private Integer Auditado.version;
    
    public Long Auditado.getId() {
        return this.id;
    }
    
    public void Auditado.setId(Long id) {
        this.id = id;
    }
    
    public Integer Auditado.getVersion() {
        return this.version;
    }
    
    public void Auditado.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void Auditado.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Auditado.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Auditado attached = this.entityManager.find(Auditado.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Auditado.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Auditado.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Auditado merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager Auditado.entityManager() {
        EntityManager em = new Auditado().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Auditado.countAuditadoes() {
        return (Long) entityManager().createQuery("select count(o) from Auditado o").getSingleResult();
    }
    
    public static List<Auditado> Auditado.findAllAuditadoes() {
        return entityManager().createQuery("select o from Auditado o").getResultList();
    }
    
    public static Auditado Auditado.findAuditado(Long id) {
        if (id == null) return null;
        return entityManager().find(Auditado.class, id);
    }
    
    public static List<Auditado> Auditado.findAuditadoEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from Auditado o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
