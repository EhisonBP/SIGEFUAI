package ve.co.bsc.sigai.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@Entity
@RooJavaBean
@RooToString
@RooEntity
public class TipoRol implements Cloneable, Serializable {

	@NotNull
	private String nombre;

	@NotNull
	private String descripcion;
}
