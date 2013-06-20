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
import ve.co.bsc.sigai.domain.EfectividadTecnicaControl;

privileged aspect EfectividadTecnicaControl_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager EfectividadTecnicaControl.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long EfectividadTecnicaControl.id;
    
    @Version
    @Column(name = "version")
    private Integer EfectividadTecnicaControl.version;
    
    public Long EfectividadTecnicaControl.getId() {
        return this.id;
    }
    
    public void EfectividadTecnicaControl.setId(Long id) {
        this.id = id;
    }
    
    public Integer EfectividadTecnicaControl.getVersion() {
        return this.version;
    }
    
    public void EfectividadTecnicaControl.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void EfectividadTecnicaControl.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void EfectividadTecnicaControl.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            EfectividadTecnicaControl attached = this.entityManager.find(EfectividadTecnicaControl.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void EfectividadTecnicaControl.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void EfectividadTecnicaControl.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        EfectividadTecnicaControl merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager EfectividadTecnicaControl.entityManager() {
        EntityManager em = new EfectividadTecnicaControl().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long EfectividadTecnicaControl.countEfectividadTecnicaControls() {
        return (Long) entityManager().createQuery("select count(o) from EfectividadTecnicaControl o").getSingleResult();
    }
    
    public static List<EfectividadTecnicaControl> EfectividadTecnicaControl.findAllEfectividadTecnicaControls() {
        return entityManager().createQuery("select o from EfectividadTecnicaControl o").getResultList();
    }
    
    public static EfectividadTecnicaControl EfectividadTecnicaControl.findEfectividadTecnicaControl(Long id) {
        if (id == null) return null;
        return entityManager().find(EfectividadTecnicaControl.class, id);
    }
    
    public static List<EfectividadTecnicaControl> EfectividadTecnicaControl.findEfectividadTecnicaControlEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from EfectividadTecnicaControl o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
