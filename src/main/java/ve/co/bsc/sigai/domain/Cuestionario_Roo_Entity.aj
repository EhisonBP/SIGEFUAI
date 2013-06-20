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
import ve.co.bsc.sigai.domain.Cuestionario;

privileged aspect Cuestionario_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager Cuestionario.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Cuestionario.id;
    
    @Version
    @Column(name = "version")
    private Integer Cuestionario.version;
    
    public Long Cuestionario.getId() {
        return this.id;
    }
    
    public void Cuestionario.setId(Long id) {
        this.id = id;
    }
    
    public Integer Cuestionario.getVersion() {
        return this.version;
    }
    
    public void Cuestionario.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void Cuestionario.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Cuestionario.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Cuestionario attached = this.entityManager.find(Cuestionario.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Cuestionario.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Cuestionario.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Cuestionario merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager Cuestionario.entityManager() {
        EntityManager em = new Cuestionario().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Cuestionario.countCuestionarios() {
        return (Long) entityManager().createQuery("select count(o) from Cuestionario o").getSingleResult();
    }
    
    public static List<Cuestionario> Cuestionario.findAllCuestionarios() {
        return entityManager().createQuery("select o from Cuestionario o").getResultList();
    }
    
    public static Cuestionario Cuestionario.findCuestionario(Long id) {
        if (id == null) return null;
        return entityManager().find(Cuestionario.class, id);
    }
    
    public static List<Cuestionario> Cuestionario.findCuestionarioEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from Cuestionario o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
