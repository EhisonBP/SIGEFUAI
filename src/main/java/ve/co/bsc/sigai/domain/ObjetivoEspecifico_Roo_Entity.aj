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
import ve.co.bsc.sigai.domain.ObjetivoEspecifico;

privileged aspect ObjetivoEspecifico_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager ObjetivoEspecifico.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long ObjetivoEspecifico.id;
    
    @Version
    @Column(name = "version")
    private Integer ObjetivoEspecifico.version;
    
    public Long ObjetivoEspecifico.getId() {
        return this.id;
    }
    
    public void ObjetivoEspecifico.setId(Long id) {
        this.id = id;
    }
    
    public Integer ObjetivoEspecifico.getVersion() {
        return this.version;
    }
    
    public void ObjetivoEspecifico.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void ObjetivoEspecifico.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void ObjetivoEspecifico.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            ObjetivoEspecifico attached = this.entityManager.find(ObjetivoEspecifico.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void ObjetivoEspecifico.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void ObjetivoEspecifico.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        ObjetivoEspecifico merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager ObjetivoEspecifico.entityManager() {
        EntityManager em = new ObjetivoEspecifico().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long ObjetivoEspecifico.countObjetivoEspecificoes() {
        return (Long) entityManager().createQuery("select count(o) from ObjetivoEspecifico o").getSingleResult();
    }
    
    public static List<ObjetivoEspecifico> ObjetivoEspecifico.findAllObjetivoEspecificoes() {
        return entityManager().createQuery("select o from ObjetivoEspecifico o").getResultList();
    }
    
    public static ObjetivoEspecifico ObjetivoEspecifico.findObjetivoEspecifico(Long id) {
        if (id == null) return null;
        return entityManager().find(ObjetivoEspecifico.class, id);
    }
    
    public static List<ObjetivoEspecifico> ObjetivoEspecifico.findObjetivoEspecificoEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from ObjetivoEspecifico o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
