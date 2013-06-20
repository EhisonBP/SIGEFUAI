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
import ve.co.bsc.sigai.domain.TecnicaDeControl;

privileged aspect TecnicaDeControl_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager TecnicaDeControl.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long TecnicaDeControl.id;
    
    @Version
    @Column(name = "version")
    private Integer TecnicaDeControl.version;
    
    public Long TecnicaDeControl.getId() {
        return this.id;
    }
    
    public void TecnicaDeControl.setId(Long id) {
        this.id = id;
    }
    
    public Integer TecnicaDeControl.getVersion() {
        return this.version;
    }
    
    public void TecnicaDeControl.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void TecnicaDeControl.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void TecnicaDeControl.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            TecnicaDeControl attached = this.entityManager.find(TecnicaDeControl.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void TecnicaDeControl.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void TecnicaDeControl.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        TecnicaDeControl merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager TecnicaDeControl.entityManager() {
        EntityManager em = new TecnicaDeControl().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long TecnicaDeControl.countTecnicaDeControls() {
        return (Long) entityManager().createQuery("select count(o) from TecnicaDeControl o").getSingleResult();
    }
    
    public static List<TecnicaDeControl> TecnicaDeControl.findAllTecnicaDeControls() {
        return entityManager().createQuery("select o from TecnicaDeControl o").getResultList();
    }
    
    public static TecnicaDeControl TecnicaDeControl.findTecnicaDeControl(Long id) {
        if (id == null) return null;
        return entityManager().find(TecnicaDeControl.class, id);
    }
    
    public static List<TecnicaDeControl> TecnicaDeControl.findTecnicaDeControlEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from TecnicaDeControl o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
