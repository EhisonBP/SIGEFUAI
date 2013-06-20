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
import ve.co.bsc.sigai.domain.Respuesta;

privileged aspect Respuesta_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager Respuesta.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Respuesta.id;
    
    @Version
    @Column(name = "version")
    private Integer Respuesta.version;
    
    public Long Respuesta.getId() {
        return this.id;
    }
    
    public void Respuesta.setId(Long id) {
        this.id = id;
    }
    
    public Integer Respuesta.getVersion() {
        return this.version;
    }
    
    public void Respuesta.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void Respuesta.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Respuesta.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Respuesta attached = this.entityManager.find(Respuesta.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Respuesta.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Respuesta.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Respuesta merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager Respuesta.entityManager() {
        EntityManager em = new Respuesta().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Respuesta.countRespuestas() {
        return (Long) entityManager().createQuery("select count(o) from Respuesta o").getSingleResult();
    }
    
    public static List<Respuesta> Respuesta.findAllRespuestas() {
        return entityManager().createQuery("select o from Respuesta o").getResultList();
    }
    
    public static Respuesta Respuesta.findRespuesta(Long id) {
        if (id == null) return null;
        return entityManager().find(Respuesta.class, id);
    }
    
    public static List<Respuesta> Respuesta.findRespuestaEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from Respuesta o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
