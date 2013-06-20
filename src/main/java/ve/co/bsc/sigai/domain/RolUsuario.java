package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

/**
 * Representa un perfil de un usuario registrado
 * 
 * @author carlosalvite
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity(finders = { "findRolUsuariosByNombreEquals",
		"findRolUsuariosByNombreNotEquals", "findRolUsuariosByTipoRol" })
public class RolUsuario implements Cloneable, Serializable {

	private String nombre;

	private String descripcion;

	@ManyToOne(targetEntity = EstadoAuditor.class)
	@JoinColumn
	private EstadoAuditor estatus;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "S-")
	private Date fechaCreacion;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "S-")
	private Date fechaModificacion;

	private String usuario;

	@ManyToOne(targetEntity = TipoRol.class)
	@JoinColumn
	private TipoRol tipoRol;

	@Override
	public String toString() {
		return nombre.substring(5);
	}

	// Futuro metodo para el vcersionamiento del sistema
	public void registrarRolUsuario(int version, String nombre,
			String descripcion, EstadoAuditor estatus, TipoRol tipoRol,
			Date fechaCreacion) {
		RolUsuario obj = new RolUsuario();
		obj.setVersion(version);
		obj.setNombre(nombre);
		obj.setDescripcion(descripcion);
		obj.setEstatus(estatus);
		Object usuario = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		obj.setUsuario(((User) usuario).getUsername().toString());
		// obj.setLife(true);
		obj.setTipoRol(tipoRol);
		java.util.Date fechaAct = new Date();
		obj.setFechaModificacion(fechaAct);
		obj.persist();
	}
}