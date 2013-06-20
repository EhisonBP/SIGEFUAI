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
import ve.co.bsc.sigai.domain.FrecuenciaOcurrenciaRiesgo;

privileged aspect FrecuenciaOcurrenciaRiesgo_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager FrecuenciaOcurrenciaRiesgo.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long FrecuenciaOcurrenciaRiesgo.id;
    
    @Version
    @Column(name = "version")
    private Integer FrecuenciaOcurrenciaRiesgo.version;
    
    public Long FrecuenciaOcurrenciaRiesgo.getId() {
        return this.id;
    }
    
    public void FrecuenciaOcurrenciaRiesgo.setId(Long id) {
        this.id = id;
    }
    
    public Integer FrecuenciaOcurrenciaRiesgo.getVersion() {
        return this.version;
    }
    
    public void FrecuenciaOcurrenciaRiesgo.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void FrecuenciaOcurrenciaRiesgo.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void FrecuenciaOcurrenciaRiesgo.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            FrecuenciaOcurrenciaRiesgo attached = this.entityManager.find(FrecuenciaOcurrenciaRiesgo.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void FrecuenciaOcurrenciaRiesgo.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void FrecuenciaOcurrenciaRiesgo.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        FrecuenciaOcurrenciaRiesgo merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager FrecuenciaOcurrenciaRiesgo.entityManager() {
        EntityManager em = new FrecuenciaOcurrenciaRiesgo().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long FrecuenciaOcurrenciaRiesgo.countFrecuenciaOcurrenciaRiesgoes() {
        return (Long) entityManager().createQuery("select count(o) from FrecuenciaOcurrenciaRiesgo o").getSingleResult();
    }
    
    public static List<FrecuenciaOcurrenciaRiesgo> FrecuenciaOcurrenciaRiesgo.findAllFrecuenciaOcurrenciaRiesgoes() {
        return entityManager().createQuery("select o from FrecuenciaOcurrenciaRiesgo o").getResultList();
    }
    
    public static FrecuenciaOcurrenciaRiesgo FrecuenciaOcurrenciaRiesgo.findFrecuenciaOcurrenciaRiesgo(Long id) {
        if (id == null) return null;
        return entityManager().find(FrecuenciaOcurrenciaRiesgo.class, id);
    }
    
    public static List<FrecuenciaOcurrenciaRiesgo> FrecuenciaOcurrenciaRiesgo.findFrecuenciaOcurrenciaRiesgoEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from FrecuenciaOcurrenciaRiesgo o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
