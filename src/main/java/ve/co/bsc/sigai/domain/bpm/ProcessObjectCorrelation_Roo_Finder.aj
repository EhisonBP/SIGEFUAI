package ve.co.bsc.sigai.domain.bpm;

import java.lang.Long;
import java.lang.SuppressWarnings;
import javax.persistence.EntityManager;
import javax.persistence.Query;

privileged aspect ProcessObjectCorrelation_Roo_Finder {
    
    @SuppressWarnings("unchecked")
    public static Query ProcessObjectCorrelation.findProcessObjectCorrelationsByItemId(Long itemId) {
        if (itemId == null) throw new IllegalArgumentException("The itemId argument is required");
        EntityManager em = ProcessObjectCorrelation.entityManager();
        Query q = em.createQuery("SELECT ProcessObjectCorrelation FROM ProcessObjectCorrelation AS processobjectcorrelation WHERE processobjectcorrelation.itemId = :itemId");
        q.setParameter("itemId", itemId);
        return q;
    }
    
}
