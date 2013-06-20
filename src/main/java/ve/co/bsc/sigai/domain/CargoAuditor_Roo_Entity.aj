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
import ve.co.bsc.sigai.domain.CargoAuditor;

privileged aspect CargoAuditor_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager CargoAuditor.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long CargoAuditor.id;
    
    @Version
    @Column(name = "version")
    private Integer CargoAuditor.version;
    
    public Long CargoAuditor.getId() {
        return this.id;
    }
    
    public void CargoAuditor.setId(Long id) {
        this.id = id;
    }
    
    public Integer CargoAuditor.getVersion() {
        return this.version;
    }
    
    public void CargoAuditor.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void CargoAuditor.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void CargoAuditor.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            CargoAuditor attached = this.entityManager.find(CargoAuditor.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void CargoAuditor.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void CargoAuditor.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        CargoAuditor merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager CargoAuditor.entityManager() {
        EntityManager em = new CargoAuditor().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long CargoAuditor.countCargoAuditors() {
        return (Long) entityManager().createQuery("select count(o) from CargoAuditor o").getSingleResult();
    }
    
    public static List<CargoAuditor> CargoAuditor.findAllCargoAuditors() {
        return entityManager().createQuery("select o from CargoAuditor o").getResultList();
    }
    
    public static CargoAuditor CargoAuditor.findCargoAuditor(Long id) {
        if (id == null) return null;
        return entityManager().find(CargoAuditor.class, id);
    }
    
    public static List<CargoAuditor> CargoAuditor.findCargoAuditorEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from CargoAuditor o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
