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
import ve.co.bsc.sigai.domain.DatosOrganismoEnte;

privileged aspect DatosOrganismoEnte_Roo_Entity {
    
    @PersistenceContext
    transient EntityManager DatosOrganismoEnte.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long DatosOrganismoEnte.id;
    
    @Version
    @Column(name = "version")
    private Integer DatosOrganismoEnte.version;
    
    public Long DatosOrganismoEnte.getId() {
        return this.id;
    }
    
    public void DatosOrganismoEnte.setId(Long id) {
        this.id = id;
    }
    
    public Integer DatosOrganismoEnte.getVersion() {
        return this.version;
    }
    
    public void DatosOrganismoEnte.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void DatosOrganismoEnte.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void DatosOrganismoEnte.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            DatosOrganismoEnte attached = this.entityManager.find(DatosOrganismoEnte.class, this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void DatosOrganismoEnte.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void DatosOrganismoEnte.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        DatosOrganismoEnte merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    public static final EntityManager DatosOrganismoEnte.entityManager() {
        EntityManager em = new DatosOrganismoEnte().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long DatosOrganismoEnte.countDatosOrganismoEntes() {
        return (Long) entityManager().createQuery("select count(o) from DatosOrganismoEnte o").getSingleResult();
    }
    
    public static List<DatosOrganismoEnte> DatosOrganismoEnte.findAllDatosOrganismoEntes() {
        return entityManager().createQuery("select o from DatosOrganismoEnte o").getResultList();
    }
    
    public static DatosOrganismoEnte DatosOrganismoEnte.findDatosOrganismoEnte(Long id) {
        if (id == null) return null;
        return entityManager().find(DatosOrganismoEnte.class, id);
    }
    
    public static List<DatosOrganismoEnte> DatosOrganismoEnte.findDatosOrganismoEnteEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from DatosOrganismoEnte o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
