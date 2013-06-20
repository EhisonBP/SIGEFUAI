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
import ve.co.bsc.sigai.domain.UnidadDeMedida;

privileged aspect UnidadDeMedida_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager UnidadDeMedida.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long UnidadDeMedida.id;
    
    @Version
    @Column(name = "version")
    private Integer UnidadDeMedida.version;
    
    public Long UnidadDeMedida.getId() {
        return this.id;
    }
    
    public void UnidadDeMedida.setId(Long id) {
        this.id = id;
    }
    
    public Integer UnidadDeMedida.getVersion() {
        return this.version;
    }
    
    public void UnidadDeMedida.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void UnidadDeMedida.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void UnidadDeMedida.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            UnidadDeMedida attached = this.entityManager.find(UnidadDeMedida.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void UnidadDeMedida.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void UnidadDeMedida.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        UnidadDeMedida merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager UnidadDeMedida.entityManager() {
        EntityManager em = new UnidadDeMedida().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long UnidadDeMedida.countUnidadDeMedidas() {
        return (Long) entityManager().createQuery("select count(o) from UnidadDeMedida o").getSingleResult();
    }
    
    public static List<UnidadDeMedida> UnidadDeMedida.findAllUnidadDeMedidas() {
        return entityManager().createQuery("select o from UnidadDeMedida o").getResultList();
    }
    
    public static UnidadDeMedida UnidadDeMedida.findUnidadDeMedida(Long id) {
        if (id == null) return null;
        return entityManager().find(UnidadDeMedida.class, id);
    }
    
    public static List<UnidadDeMedida> UnidadDeMedida.findUnidadDeMedidaEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from UnidadDeMedida o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
