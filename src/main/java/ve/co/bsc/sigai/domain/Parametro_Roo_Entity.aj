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
import ve.co.bsc.sigai.domain.Parametro;

privileged aspect Parametro_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager Parametro.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Parametro.id;
    
    @Version
    @Column(name = "version")
    private Integer Parametro.version;
    
    public Long Parametro.getId() {
        return this.id;
    }
    
    public void Parametro.setId(Long id) {
        this.id = id;
    }
    
    public Integer Parametro.getVersion() {
        return this.version;
    }
    
    public void Parametro.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void Parametro.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Parametro.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Parametro attached = this.entityManager.find(Parametro.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Parametro.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Parametro.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Parametro merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager Parametro.entityManager() {
        EntityManager em = new Parametro().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Parametro.countParametroes() {
        return (Long) entityManager().createQuery("select count(o) from Parametro o").getSingleResult();
    }
    
    public static List<Parametro> Parametro.findAllParametroes() {
        return entityManager().createQuery("select o from Parametro o").getResultList();
    }
    
    public static Parametro Parametro.findParametro(Long id) {
        if (id == null) return null;
        return entityManager().find(Parametro.class, id);
    }
    
    public static List<Parametro> Parametro.findParametroEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from Parametro o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
