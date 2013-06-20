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
import ve.co.bsc.sigai.domain.EstructuraCargos;

privileged aspect EstructuraCargos_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager EstructuraCargos.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long EstructuraCargos.id;
    
    @Version
    @Column(name = "version")
    private Integer EstructuraCargos.version;
    
    public Long EstructuraCargos.getId() {
        return this.id;
    }
    
    public void EstructuraCargos.setId(Long id) {
        this.id = id;
    }
    
    public Integer EstructuraCargos.getVersion() {
        return this.version;
    }
    
    public void EstructuraCargos.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void EstructuraCargos.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void EstructuraCargos.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            EstructuraCargos attached = this.entityManager.find(EstructuraCargos.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void EstructuraCargos.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void EstructuraCargos.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        EstructuraCargos merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager EstructuraCargos.entityManager() {
        EntityManager em = new EstructuraCargos().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long EstructuraCargos.countEstructuraCargoses() {
        return (Long) entityManager().createQuery("select count(o) from EstructuraCargos o").getSingleResult();
    }
    
    public static List<EstructuraCargos> EstructuraCargos.findAllEstructuraCargoses() {
        return entityManager().createQuery("select o from EstructuraCargos o").getResultList();
    }
    
    public static EstructuraCargos EstructuraCargos.findEstructuraCargos(Long id) {
        if (id == null) return null;
        return entityManager().find(EstructuraCargos.class, id);
    }
    
    public static List<EstructuraCargos> EstructuraCargos.findEstructuraCargosEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from EstructuraCargos o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
