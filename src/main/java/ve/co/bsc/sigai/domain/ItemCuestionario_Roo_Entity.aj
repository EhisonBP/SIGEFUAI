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
import ve.co.bsc.sigai.domain.ItemCuestionario;

privileged aspect ItemCuestionario_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager ItemCuestionario.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long ItemCuestionario.id;
    
    @Version
    @Column(name = "version")
    private Integer ItemCuestionario.version;
    
    public Long ItemCuestionario.getId() {
        return this.id;
    }
    
    public void ItemCuestionario.setId(Long id) {
        this.id = id;
    }
    
    public Integer ItemCuestionario.getVersion() {
        return this.version;
    }
    
    public void ItemCuestionario.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void ItemCuestionario.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void ItemCuestionario.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            ItemCuestionario attached = this.entityManager.find(ItemCuestionario.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void ItemCuestionario.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void ItemCuestionario.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        ItemCuestionario merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager ItemCuestionario.entityManager() {
        EntityManager em = new ItemCuestionario().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long ItemCuestionario.countItemCuestionarios() {
        return (Long) entityManager().createQuery("select count(o) from ItemCuestionario o").getSingleResult();
    }
    
    public static List<ItemCuestionario> ItemCuestionario.findAllItemCuestionarios() {
        return entityManager().createQuery("select o from ItemCuestionario o").getResultList();
    }
    
    public static ItemCuestionario ItemCuestionario.findItemCuestionario(Long id) {
        if (id == null) return null;
        return entityManager().find(ItemCuestionario.class, id);
    }
    
    public static List<ItemCuestionario> ItemCuestionario.findItemCuestionarioEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from ItemCuestionario o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
