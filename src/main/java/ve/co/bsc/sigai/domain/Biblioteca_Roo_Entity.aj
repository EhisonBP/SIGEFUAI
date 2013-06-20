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
import ve.co.bsc.sigai.domain.Biblioteca;

privileged aspect Biblioteca_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager Biblioteca.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Biblioteca.id;
    
    @Version
    @Column(name = "version")
    private Integer Biblioteca.version;
    
    public Long Biblioteca.getId() {
        return this.id;
    }
    
    public void Biblioteca.setId(Long id) {
        this.id = id;
    }
    
    public Integer Biblioteca.getVersion() {
        return this.version;
    }
    
    public void Biblioteca.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void Biblioteca.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Biblioteca.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Biblioteca attached = this.entityManager.find(Biblioteca.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Biblioteca.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Biblioteca.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Biblioteca merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager Biblioteca.entityManager() {
        EntityManager em = new Biblioteca().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Biblioteca.countBibliotecas() {
        return (Long) entityManager().createQuery("select count(o) from Biblioteca o").getSingleResult();
    }
    
    public static List<Biblioteca> Biblioteca.findAllBibliotecas() {
        return entityManager().createQuery("select o from Biblioteca o").getResultList();
    }
    
    public static Biblioteca Biblioteca.findBiblioteca(Long id) {
        if (id == null) return null;
        return entityManager().find(Biblioteca.class, id);
    }
    
    public static List<Biblioteca> Biblioteca.findBibliotecaEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from Biblioteca o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
