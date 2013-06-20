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
import ve.co.bsc.sigai.domain.EstatusRequerimiento;

privileged aspect EstatusRequerimiento_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager EstatusRequerimiento.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long EstatusRequerimiento.id;
    
    @Version
    @Column(name = "version")
    private Integer EstatusRequerimiento.version;
    
    public Long EstatusRequerimiento.getId() {
        return this.id;
    }
    
    public void EstatusRequerimiento.setId(Long id) {
        this.id = id;
    }
    
    public Integer EstatusRequerimiento.getVersion() {
        return this.version;
    }
    
    public void EstatusRequerimiento.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void EstatusRequerimiento.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void EstatusRequerimiento.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            EstatusRequerimiento attached = this.entityManager.find(EstatusRequerimiento.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void EstatusRequerimiento.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void EstatusRequerimiento.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        EstatusRequerimiento merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager EstatusRequerimiento.entityManager() {
        EntityManager em = new EstatusRequerimiento().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long EstatusRequerimiento.countEstatusRequerimientoes() {
        return (Long) entityManager().createQuery("select count(o) from EstatusRequerimiento o").getSingleResult();
    }
    
    public static List<EstatusRequerimiento> EstatusRequerimiento.findAllEstatusRequerimientoes() {
        return entityManager().createQuery("select o from EstatusRequerimiento o").getResultList();
    }
    
    public static EstatusRequerimiento EstatusRequerimiento.findEstatusRequerimiento(Long id) {
        if (id == null) return null;
        return entityManager().find(EstatusRequerimiento.class, id);
    }
    
    public static List<EstatusRequerimiento> EstatusRequerimiento.findEstatusRequerimientoEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from EstatusRequerimiento o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
