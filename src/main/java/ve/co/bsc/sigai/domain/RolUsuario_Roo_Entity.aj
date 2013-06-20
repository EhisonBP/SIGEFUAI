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
import ve.co.bsc.sigai.domain.RolUsuario;

privileged aspect RolUsuario_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager RolUsuario.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long RolUsuario.id;
    
    @Version
    @Column(name = "version")
    private Integer RolUsuario.version;
    
    public Long RolUsuario.getId() {
        return this.id;
    }
    
    public void RolUsuario.setId(Long id) {
        this.id = id;
    }
    
    public Integer RolUsuario.getVersion() {
        return this.version;
    }
    
    public void RolUsuario.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void RolUsuario.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void RolUsuario.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            RolUsuario attached = this.entityManager.find(RolUsuario.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void RolUsuario.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void RolUsuario.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        RolUsuario merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager RolUsuario.entityManager() {
        EntityManager em = new RolUsuario().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long RolUsuario.countRolUsuarios() {
        return (Long) entityManager().createQuery("select count(o) from RolUsuario o").getSingleResult();
    }
    
    public static List<RolUsuario> RolUsuario.findAllRolUsuarios() {
        return entityManager().createQuery("select o from RolUsuario o").getResultList();
    }
    
    public static RolUsuario RolUsuario.findRolUsuario(Long id) {
        if (id == null) return null;
        return entityManager().find(RolUsuario.class, id);
    }
    
    public static List<RolUsuario> RolUsuario.findRolUsuarioEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from RolUsuario o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
