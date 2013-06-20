package ve.co.bsc.sigai.domain;

import java.io.Serializable;

import javax.persistence.Entity;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * Representa un tipo de organismo al cual es asociado una unidad de auditoría
 * interna
 * 
 * @author carlosalvite
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity
public class TipoOrganismo implements Cloneable, Serializable {

	private String nombre;
}
