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
import ve.co.bsc.sigai.domain.Alegato;

privileged aspect Alegato_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager Alegato.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Alegato.id;
    
    @Version
    @Column(name = "version")
    private Integer Alegato.version;
    
    public Long Alegato.getId() {
        return this.id;
    }
    
    public void Alegato.setId(Long id) {
        this.id = id;
    }
    
    public Integer Alegato.getVersion() {
        return this.version;
    }
    
    public void Alegato.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void Alegato.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Alegato.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Alegato attached = this.entityManager.find(Alegato.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Alegato.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Alegato.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Alegato merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager Alegato.entityManager() {
        EntityManager em = new Alegato().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Alegato.countAlegatoes() {
        return (Long) entityManager().createQuery("select count(o) from Alegato o").getSingleResult();
    }
    
    public static List<Alegato> Alegato.findAllAlegatoes() {
        return entityManager().createQuery("select o from Alegato o").getResultList();
    }
    
    public static Alegato Alegato.findAlegato(Long id) {
        if (id == null) return null;
        return entityManager().find(Alegato.class, id);
    }
    
    public static List<Alegato> Alegato.findAlegatoEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from Alegato o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
