package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

/**
 * Representa un usuario registrado del sistema
 * @author carlosalvite
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity(finders = { "findUsuariosByLogin", "findUsuariosByRoles" })
public class Usuario implements ObjetoNombreClase, Serializable {

    private static Logger logger = Logger.getLogger(Usuario.class);

    @Column(unique=true)
    private String login;

    private String password;

    @ManyToMany(targetEntity = RolUsuario.class)
    @JoinColumn
    private List<RolUsuario> roles;

    @ManyToMany(targetEntity = EstructuraCargos.class)
    @JoinColumn
    private List<EstructuraCargos> cargos;
    
    private Boolean activo;

    public static Usuario findUsuarioCurrentlyLoggedIn() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logger.debug("[principal] es: " + principal);
        try {
            if ((principal != null) && (principal instanceof User)) {
                return (Usuario) Usuario.findUsuariosByLogin(((User) principal).getUsername()).getSingleResult();
            } else {
                logger.debug("No hay principal valido (principal no es User)");
                return null;
            }
        } catch (NoResultException e) {
            logger.debug("No se encontro usuario con login " + ((User) principal).getUsername());
            return null;
        } catch (Exception e) {
            logger.error("Error buscando usuario actualmente loggeado", e);
            return null;
        } 
    }

    public static Collection<GrantedAuthority> getCurrentUserAuthorities() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null) {
            return ((User) principal).getAuthorities();
        } else {
            return new HashSet<GrantedAuthority>();
        }
    }

    public static boolean getCurrentUserHasAuthority(String authority) {
        for (GrantedAuthority auth : getCurrentUserAuthorities()) {
            if (auth.getAuthority().equals(authority)) return true;
        }
        return false;
    }
    
    @Override
	public String getNombreClase() {
		return "Usuario";
	}

    public List<String> getRolesAsString() {
        List<String> result = new LinkedList<String>();
        for (RolUsuario r : getRoles()) {
            result.add(r.getNombre());
        }
        return result;
    }
}
