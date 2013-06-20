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
import ve.co.bsc.sigai.domain.HistorialCambios;

privileged aspect HistorialCambios_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager HistorialCambios.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long HistorialCambios.id;
    
    @Version
    @Column(name = "version")
    private Integer HistorialCambios.version;
    
    public Long HistorialCambios.getId() {
        return this.id;
    }
    
    public void HistorialCambios.setId(Long id) {
        this.id = id;
    }
    
    public Integer HistorialCambios.getVersion() {
        return this.version;
    }
    
    public void HistorialCambios.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void HistorialCambios.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void HistorialCambios.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            HistorialCambios attached = this.entityManager.find(HistorialCambios.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void HistorialCambios.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void HistorialCambios.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        HistorialCambios merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager HistorialCambios.entityManager() {
        EntityManager em = new HistorialCambios().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long HistorialCambios.countHistorialCambioses() {
        return (Long) entityManager().createQuery("select count(o) from HistorialCambios o").getSingleResult();
    }
    
    public static List<HistorialCambios> HistorialCambios.findAllHistorialCambioses() {
        return entityManager().createQuery("select o from HistorialCambios o").getResultList();
    }
    
    public static HistorialCambios HistorialCambios.findHistorialCambios(Long id) {
        if (id == null) return null;
        return entityManager().find(HistorialCambios.class, id);
    }
    
    public static List<HistorialCambios> HistorialCambios.findHistorialCambiosEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from HistorialCambios o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
