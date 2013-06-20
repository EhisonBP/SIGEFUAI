package ve.co.bsc.sigai.domain;

import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import ve.co.bsc.sigai.domain.RolUsuario;

privileged aspect Usuario_Roo_Finder {
    
    @SuppressWarnings("unchecked")
    public static Query Usuario.findUsuariosByLogin(String login) {
        if (login == null || login.length() == 0) throw new IllegalArgumentException("The login argument is required");
        EntityManager em = Usuario.entityManager();
        Query q = em.createQuery("SELECT Usuario FROM Usuario AS usuario WHERE usuario.login = :login");
        q.setParameter("login", login);
        return q;
    }
    
    @SuppressWarnings("unchecked")
    public static Query Usuario.findUsuariosByRoles(List<RolUsuario> roles) {
        if (roles == null) throw new IllegalArgumentException("The roles argument is required");
        EntityManager em = Usuario.entityManager();
        StringBuilder queryBuilder = new StringBuilder("SELECT Usuario FROM Usuario AS usuario WHERE");
        for (int i = 0; i < roles.size(); i++) {
            if (i > 0) queryBuilder.append(" AND");
            queryBuilder.append(" :roles_item").append(i).append(" MEMBER OF usuario.roles");
        }
        Query q = em.createQuery(queryBuilder.toString());
        int rolesIndex = 0;
        for (RolUsuario _rolusuario: roles) {
            q.setParameter("roles_item" + rolesIndex++, _rolusuario);
        }
        return q;
    }
    
}
