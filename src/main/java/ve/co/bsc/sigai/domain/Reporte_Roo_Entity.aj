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
import ve.co.bsc.sigai.domain.Reporte;

privileged aspect Reporte_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager Reporte.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Reporte.id;
    
    @Version
    @Column(name = "version")
    private Integer Reporte.version;
    
    public Long Reporte.getId() {
        return this.id;
    }
    
    public void Reporte.setId(Long id) {
        this.id = id;
    }
    
    public Integer Reporte.getVersion() {
        return this.version;
    }
    
    public void Reporte.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void Reporte.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Reporte.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Reporte attached = this.entityManager.find(Reporte.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Reporte.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Reporte.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Reporte merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager Reporte.entityManager() {
        EntityManager em = new Reporte().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Reporte.countReportes() {
        return (Long) entityManager().createQuery("select count(o) from Reporte o").getSingleResult();
    }
    
    public static List<Reporte> Reporte.findAllReportes() {
        return entityManager().createQuery("select o from Reporte o").getResultList();
    }
    
    public static Reporte Reporte.findReporte(Long id) {
        if (id == null) return null;
        return entityManager().find(Reporte.class, id);
    }
    
    public static List<Reporte> Reporte.findReporteEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from Reporte o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
