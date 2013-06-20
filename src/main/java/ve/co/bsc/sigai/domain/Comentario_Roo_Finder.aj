package ve.co.bsc.sigai.domain;

import java.lang.Long;
import java.lang.String;
import java.lang.SuppressWarnings;
import javax.persistence.EntityManager;
import javax.persistence.Query;

privileged aspect Comentario_Roo_Finder {
    
    @SuppressWarnings("unchecked")
    public static Query Comentario.findComentariosByTipoAndId(String tipo, Long id) {
        if (tipo == null || tipo.length() == 0) throw new IllegalArgumentException("The tipo argument is required");
        if (id == null) throw new IllegalArgumentException("The id argument is required");
        EntityManager em = Comentario.entityManager();
        Query q = em.createQuery("SELECT Comentario FROM Comentario AS comentario WHERE comentario.tipo = :tipo AND comentario.id = :id");
        q.setParameter("tipo", tipo);
        q.setParameter("id", id);
        return q;
    }
    
}
