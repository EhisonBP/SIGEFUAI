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
import ve.co.bsc.sigai.domain.ActividadActuacion;

privileged aspect ActividadActuacion_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager ActividadActuacion.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long ActividadActuacion.id;
    
    @Version
    @Column(name = "version")
    private Integer ActividadActuacion.version;
    
    public Long ActividadActuacion.getId() {
        return this.id;
    }
    
    public void ActividadActuacion.setId(Long id) {
        this.id = id;
    }
    
    public Integer ActividadActuacion.getVersion() {
        return this.version;
    }
    
    public void ActividadActuacion.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void ActividadActuacion.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void ActividadActuacion.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            ActividadActuacion attached = this.entityManager.find(ActividadActuacion.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void ActividadActuacion.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void ActividadActuacion.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        ActividadActuacion merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager ActividadActuacion.entityManager() {
        EntityManager em = new ActividadActuacion().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long ActividadActuacion.countActividadActuacions() {
        return (Long) entityManager().createQuery("select count(o) from ActividadActuacion o").getSingleResult();
    }
    
    public static List<ActividadActuacion> ActividadActuacion.findAllActividadActuacions() {
        return entityManager().createQuery("select o from ActividadActuacion o").getResultList();
    }
    
    public static ActividadActuacion ActividadActuacion.findActividadActuacion(Long id) {
        if (id == null) return null;
        return entityManager().find(ActividadActuacion.class, id);
    }
    
    public static List<ActividadActuacion> ActividadActuacion.findActividadActuacionEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from ActividadActuacion o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
