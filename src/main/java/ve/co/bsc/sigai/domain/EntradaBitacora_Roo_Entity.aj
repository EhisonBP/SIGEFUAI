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
import ve.co.bsc.sigai.domain.EntradaBitacora;

privileged aspect EntradaBitacora_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager EntradaBitacora.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long EntradaBitacora.id;
    
    @Version
    @Column(name = "version")
    private Integer EntradaBitacora.version;
    
    public Long EntradaBitacora.getId() {
        return this.id;
    }
    
    public void EntradaBitacora.setId(Long id) {
        this.id = id;
    }
    
    public Integer EntradaBitacora.getVersion() {
        return this.version;
    }
    
    public void EntradaBitacora.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void EntradaBitacora.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void EntradaBitacora.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            EntradaBitacora attached = this.entityManager.find(EntradaBitacora.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void EntradaBitacora.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void EntradaBitacora.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        EntradaBitacora merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager EntradaBitacora.entityManager() {
        EntityManager em = new EntradaBitacora().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long EntradaBitacora.countEntradaBitacoras() {
        return (Long) entityManager().createQuery("select count(o) from EntradaBitacora o").getSingleResult();
    }
    
    public static List<EntradaBitacora> EntradaBitacora.findAllEntradaBitacoras() {
        return entityManager().createQuery("select o from EntradaBitacora o").getResultList();
    }
    
    public static EntradaBitacora EntradaBitacora.findEntradaBitacora(Long id) {
        if (id == null) return null;
        return entityManager().find(EntradaBitacora.class, id);
    }
    
    public static List<EntradaBitacora> EntradaBitacora.findEntradaBitacoraEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from EntradaBitacora o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
