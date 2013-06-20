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
import ve.co.bsc.sigai.domain.ItemPlanificacionActuacion;

privileged aspect ItemPlanificacionActuacion_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager ItemPlanificacionActuacion.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long ItemPlanificacionActuacion.id;
    
    @Version
    @Column(name = "version")
    private Integer ItemPlanificacionActuacion.version;
    
    public Long ItemPlanificacionActuacion.getId() {
        return this.id;
    }
    
    public void ItemPlanificacionActuacion.setId(Long id) {
        this.id = id;
    }
    
    public Integer ItemPlanificacionActuacion.getVersion() {
        return this.version;
    }
    
    public void ItemPlanificacionActuacion.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void ItemPlanificacionActuacion.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void ItemPlanificacionActuacion.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            ItemPlanificacionActuacion attached = this.entityManager.find(ItemPlanificacionActuacion.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void ItemPlanificacionActuacion.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void ItemPlanificacionActuacion.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        ItemPlanificacionActuacion merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager ItemPlanificacionActuacion.entityManager() {
        EntityManager em = new ItemPlanificacionActuacion().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long ItemPlanificacionActuacion.countItemPlanificacionActuacions() {
        return (Long) entityManager().createQuery("select count(o) from ItemPlanificacionActuacion o").getSingleResult();
    }
    
    public static List<ItemPlanificacionActuacion> ItemPlanificacionActuacion.findAllItemPlanificacionActuacions() {
        return entityManager().createQuery("select o from ItemPlanificacionActuacion o").getResultList();
    }
    
    public static ItemPlanificacionActuacion ItemPlanificacionActuacion.findItemPlanificacionActuacion(Long id) {
        if (id == null) return null;
        return entityManager().find(ItemPlanificacionActuacion.class, id);
    }
    
    public static List<ItemPlanificacionActuacion> ItemPlanificacionActuacion.findItemPlanificacionActuacionEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from ItemPlanificacionActuacion o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
