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
import ve.co.bsc.sigai.domain.Documento;

privileged aspect Documento_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager Documento.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Documento.id;
    
    @Version
    @Column(name = "version")
    private Integer Documento.version;
    
    public Long Documento.getId() {
        return this.id;
    }
    
    public void Documento.setId(Long id) {
        this.id = id;
    }
    
    public Integer Documento.getVersion() {
        return this.version;
    }
    
    public void Documento.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void Documento.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Documento.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Documento attached = this.entityManager.find(Documento.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Documento.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Documento.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Documento merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager Documento.entityManager() {
        EntityManager em = new Documento().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Documento.countDocumentoes() {
        return (Long) entityManager().createQuery("select count(o) from Documento o").getSingleResult();
    }
    
    public static List<Documento> Documento.findAllDocumentoes() {
        return entityManager().createQuery("select o from Documento o").getResultList();
    }
    
    public static Documento Documento.findDocumento(Long id) {
        if (id == null) return null;
        return entityManager().find(Documento.class, id);
    }
    
    public static List<Documento> Documento.findDocumentoEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from Documento o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
