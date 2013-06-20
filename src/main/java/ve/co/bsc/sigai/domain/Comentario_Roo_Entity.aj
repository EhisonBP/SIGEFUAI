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
import ve.co.bsc.sigai.domain.Comentario;

privileged aspect Comentario_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager Comentario.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "_id")
    private Long Comentario._id;
    
    @Version
    @Column(name = "version")
    private Integer Comentario.version;
    
    public Long Comentario.get_id() {
        return this._id;
    }
    
    public void Comentario.set_id(Long id) {
        this._id = id;
    }
    
    public Integer Comentario.getVersion() {
        return this.version;
    }
    
    public void Comentario.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void Comentario.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Comentario.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Comentario attached = this.entityManager.find(Comentario.class, this._id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Comentario.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Comentario.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Comentario merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this._id = merged.get_id();
    }
    
    public static final EntityManager Comentario.entityManager() {
        EntityManager em = new Comentario().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Comentario.countComentarios() {
        return (Long) entityManager().createQuery("select count(o) from Comentario o").getSingleResult();
    }
    
    public static List<Comentario> Comentario.findAllComentarios() {
        return entityManager().createQuery("select o from Comentario o").getResultList();
    }
    
    public static Comentario Comentario.findComentario(Long id) {
        if (id == null) return null;
        return entityManager().find(Comentario.class, id);
    }
    
    public static List<Comentario> Comentario.findComentarioEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from Comentario o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
