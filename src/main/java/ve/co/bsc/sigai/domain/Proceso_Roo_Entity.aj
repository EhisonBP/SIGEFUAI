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
import ve.co.bsc.sigai.domain.Proceso;

privileged aspect Proceso_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager Proceso.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Proceso.id;
    
    @Version
    @Column(name = "version")
    private Integer Proceso.version;
    
    public Long Proceso.getId() {
        return this.id;
    }
    
    public void Proceso.setId(Long id) {
        this.id = id;
    }
    
    public Integer Proceso.getVersion() {
        return this.version;
    }
    
    public void Proceso.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void Proceso.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Proceso.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Proceso attached = this.entityManager.find(Proceso.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Proceso.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Proceso.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Proceso merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager Proceso.entityManager() {
        EntityManager em = new Proceso().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Proceso.countProcesoes() {
        return (Long) entityManager().createQuery("select count(o) from Proceso o").getSingleResult();
    }
    
    public static List<Proceso> Proceso.findAllProcesoes() {
        return entityManager().createQuery("select o from Proceso o").getResultList();
    }
    
    public static Proceso Proceso.findProceso(Long id) {
        if (id == null) return null;
        return entityManager().find(Proceso.class, id);
    }
    
    public static List<Proceso> Proceso.findProcesoEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from Proceso o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
