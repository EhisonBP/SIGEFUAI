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
import ve.co.bsc.sigai.domain.Recomendacion;

privileged aspect Recomendacion_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager Recomendacion.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Recomendacion.id;
    
    @Version
    @Column(name = "version")
    private Integer Recomendacion.version;
    
    public Long Recomendacion.getId() {
        return this.id;
    }
    
    public void Recomendacion.setId(Long id) {
        this.id = id;
    }
    
    public Integer Recomendacion.getVersion() {
        return this.version;
    }
    
    public void Recomendacion.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void Recomendacion.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Recomendacion.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Recomendacion attached = this.entityManager.find(Recomendacion.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Recomendacion.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Recomendacion.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Recomendacion merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager Recomendacion.entityManager() {
        EntityManager em = new Recomendacion().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Recomendacion.countRecomendacions() {
        return (Long) entityManager().createQuery("select count(o) from Recomendacion o").getSingleResult();
    }
    
    public static List<Recomendacion> Recomendacion.findAllRecomendacions() {
        return entityManager().createQuery("select o from Recomendacion o").getResultList();
    }
    
    public static Recomendacion Recomendacion.findRecomendacion(Long id) {
        if (id == null) return null;
        return entityManager().find(Recomendacion.class, id);
    }
    
    public static List<Recomendacion> Recomendacion.findRecomendacionEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from Recomendacion o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
