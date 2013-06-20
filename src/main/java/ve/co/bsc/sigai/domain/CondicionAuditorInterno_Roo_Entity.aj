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
import ve.co.bsc.sigai.domain.CondicionAuditorInterno;

privileged aspect CondicionAuditorInterno_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager CondicionAuditorInterno.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long CondicionAuditorInterno.id;
    
    @Version
    @Column(name = "version")
    private Integer CondicionAuditorInterno.version;
    
    public Long CondicionAuditorInterno.getId() {
        return this.id;
    }
    
    public void CondicionAuditorInterno.setId(Long id) {
        this.id = id;
    }
    
    public Integer CondicionAuditorInterno.getVersion() {
        return this.version;
    }
    
    public void CondicionAuditorInterno.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void CondicionAuditorInterno.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void CondicionAuditorInterno.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            CondicionAuditorInterno attached = this.entityManager.find(CondicionAuditorInterno.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void CondicionAuditorInterno.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void CondicionAuditorInterno.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        CondicionAuditorInterno merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager CondicionAuditorInterno.entityManager() {
        EntityManager em = new CondicionAuditorInterno().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long CondicionAuditorInterno.countCondicionAuditorInternoes() {
        return (Long) entityManager().createQuery("select count(o) from CondicionAuditorInterno o").getSingleResult();
    }
    
    public static List<CondicionAuditorInterno> CondicionAuditorInterno.findAllCondicionAuditorInternoes() {
        return entityManager().createQuery("select o from CondicionAuditorInterno o").getResultList();
    }
    
    public static CondicionAuditorInterno CondicionAuditorInterno.findCondicionAuditorInterno(Long id) {
        if (id == null) return null;
        return entityManager().find(CondicionAuditorInterno.class, id);
    }
    
    public static List<CondicionAuditorInterno> CondicionAuditorInterno.findCondicionAuditorInternoEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from CondicionAuditorInterno o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
