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
import ve.co.bsc.sigai.domain.Observacion;

privileged aspect Observacion_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager Observacion.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Observacion.id;
    
    @Version
    @Column(name = "version")
    private Integer Observacion.version;
    
    public Long Observacion.getId() {
        return this.id;
    }
    
    public void Observacion.setId(Long id) {
        this.id = id;
    }
    
    public Integer Observacion.getVersion() {
        return this.version;
    }
    
    public void Observacion.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void Observacion.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Observacion.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Observacion attached = this.entityManager.find(Observacion.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Observacion.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Observacion.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Observacion merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager Observacion.entityManager() {
        EntityManager em = new Observacion().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Observacion.countObservacions() {
        return (Long) entityManager().createQuery("select count(o) from Observacion o").getSingleResult();
    }
    
    public static List<Observacion> Observacion.findAllObservacions() {
        return entityManager().createQuery("select o from Observacion o").getResultList();
    }
    
    public static Observacion Observacion.findObservacion(Long id) {
        if (id == null) return null;
        return entityManager().find(Observacion.class, id);
    }
    
    public static List<Observacion> Observacion.findObservacionEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from Observacion o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
