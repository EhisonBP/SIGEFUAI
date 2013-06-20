package ve.co.bsc.sigai.domain;

import java.lang.String;
import java.lang.SuppressWarnings;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import ve.co.bsc.sigai.domain.TipoRol;

privileged aspect RolUsuario_Roo_Finder {
    
    @SuppressWarnings("unchecked")
    public static Query RolUsuario.findRolUsuariosByNombreEquals(String nombre) {
        if (nombre == null || nombre.length() == 0) throw new IllegalArgumentException("The nombre argument is required");
        EntityManager em = RolUsuario.entityManager();
        Query q = em.createQuery("SELECT RolUsuario FROM RolUsuario AS rolusuario WHERE rolusuario.nombre = :nombre");
        q.setParameter("nombre", nombre);
        return q;
    }
    
    @SuppressWarnings("unchecked")
    public static Query RolUsuario.findRolUsuariosByNombreNotEquals(String nombre) {
        if (nombre == null || nombre.length() == 0) throw new IllegalArgumentException("The nombre argument is required");
        EntityManager em = RolUsuario.entityManager();
        Query q = em.createQuery("SELECT RolUsuario FROM RolUsuario AS rolusuario WHERE rolusuario.nombre != :nombre");
        q.setParameter("nombre", nombre);
        return q;
    }
    
    @SuppressWarnings("unchecked")
    public static Query RolUsuario.findRolUsuariosByTipoRol(TipoRol tipoRol) {
        if (tipoRol == null) throw new IllegalArgumentException("The tipoRol argument is required");
        EntityManager em = RolUsuario.entityManager();
        Query q = em.createQuery("SELECT RolUsuario FROM RolUsuario AS rolusuario WHERE rolusuario.tipoRol = :tipoRol");
        q.setParameter("tipoRol", tipoRol);
        return q;
    }
    
}
