package ve.co.bsc.sigai.domain.bpm;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;

@Entity
@RooJavaBean
@RooToString
@RooEntity(finders = { "findProcessObjectCorrelationsByItemId" })
public class ProcessObjectCorrelation {

    private Long itemId;

    private Long processInstanceId;

    private String classname;

    public static Query findProcessObjectCorrelationsByItem(Long itemId, String classname) {
        if (itemId == null) throw new IllegalArgumentException("The itemId argument is required");
        if (classname == null) throw new IllegalArgumentException("The itemId argument is required");
        EntityManager em = ProcessObjectCorrelation.entityManager();
        Query q = em.createQuery("SELECT ProcessObjectCorrelation FROM ProcessObjectCorrelation AS processobjectcorrelation WHERE processobjectcorrelation.itemId = :itemId AND processobjectcorrelation.classname = :classname");
        q.setParameter("itemId", itemId);
        q.setParameter("classname", classname);
        return q;
    }
}
