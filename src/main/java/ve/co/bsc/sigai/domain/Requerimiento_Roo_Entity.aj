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
import ve.co.bsc.sigai.domain.Requerimiento;

privileged aspect Requerimiento_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager Requerimiento.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Requerimiento.id;
    
    @Version
    @Column(name = "version")
    private Integer Requerimiento.version;
    
    public Long Requerimiento.getId() {
        return this.id;
    }
    
    public void Requerimiento.setId(Long id) {
        this.id = id;
    }
    
    public Integer Requerimiento.getVersion() {
        return this.version;
    }
    
    public void Requerimiento.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void Requerimiento.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Requerimiento.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Requerimiento attached = this.entityManager.find(Requerimiento.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Requerimiento.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Requerimiento.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Requerimiento merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager Requerimiento.entityManager() {
        EntityManager em = new Requerimiento().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Requerimiento.countRequerimientoes() {
        return (Long) entityManager().createQuery("select count(o) from Requerimiento o").getSingleResult();
    }
    
    public static List<Requerimiento> Requerimiento.findAllRequerimientoes() {
        return entityManager().createQuery("select o from Requerimiento o").getResultList();
    }
    
    public static Requerimiento Requerimiento.findRequerimiento(Long id) {
        if (id == null) return null;
        return entityManager().find(Requerimiento.class, id);
    }
    
    public static List<Requerimiento> Requerimiento.findRequerimientoEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from Requerimiento o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
