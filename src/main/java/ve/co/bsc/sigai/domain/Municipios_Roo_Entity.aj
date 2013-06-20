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
import ve.co.bsc.sigai.domain.Municipios;

privileged aspect Municipios_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager Municipios.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Municipios.id;
    
    @Version
    @Column(name = "version")
    private Integer Municipios.version;
    
    public Long Municipios.getId() {
        return this.id;
    }
    
    public void Municipios.setId(Long id) {
        this.id = id;
    }
    
    public Integer Municipios.getVersion() {
        return this.version;
    }
    
    public void Municipios.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void Municipios.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Municipios.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Municipios attached = this.entityManager.find(Municipios.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Municipios.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Municipios.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Municipios merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager Municipios.entityManager() {
        EntityManager em = new Municipios().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Municipios.countMunicipioses() {
        return (Long) entityManager().createQuery("select count(o) from Municipios o").getSingleResult();
    }
    
    public static List<Municipios> Municipios.findAllMunicipioses() {
        return entityManager().createQuery("select o from Municipios o").getResultList();
    }
    
    public static Municipios Municipios.findMunicipios(Long id) {
        if (id == null) return null;
        return entityManager().find(Municipios.class, id);
    }
    
    public static List<Municipios> Municipios.findMunicipiosEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from Municipios o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
