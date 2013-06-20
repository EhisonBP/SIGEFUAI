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
import ve.co.bsc.sigai.domain.Unidad;

privileged aspect Unidad_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager Unidad.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Unidad.id;
    
    @Version
    @Column(name = "version")
    private Integer Unidad.version;
    
    public Long Unidad.getId() {
        return this.id;
    }
    
    public void Unidad.setId(Long id) {
        this.id = id;
    }
    
    public Integer Unidad.getVersion() {
        return this.version;
    }
    
    public void Unidad.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void Unidad.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Unidad.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Unidad attached = this.entityManager.find(Unidad.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Unidad.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Unidad.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Unidad merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager Unidad.entityManager() {
        EntityManager em = new Unidad().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Unidad.countUnidads() {
        return (Long) entityManager().createQuery("select count(o) from Unidad o").getSingleResult();
    }
    
    public static List<Unidad> Unidad.findAllUnidads() {
        return entityManager().createQuery("select o from Unidad o").getResultList();
    }
    
    public static Unidad Unidad.findUnidad(Long id) {
        if (id == null) return null;
        return entityManager().find(Unidad.class, id);
    }
    
    public static List<Unidad> Unidad.findUnidadEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from Unidad o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
