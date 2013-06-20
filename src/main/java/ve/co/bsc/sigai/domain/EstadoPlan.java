package ve.co.bsc.sigai.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * Representa un estado posible de un planAnual (pendiente, aprobado, pospuesto
 * etc). El estado de un plan anual altera y es alterado por las reglas del
 * flujo de trabajo.
 * 
 * @author adernersissian
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity
public class EstadoPlan implements ObjetoNombreClase, Serializable {

	@NotNull
	@Size(min = 2)
	private String nombre;

	@NotNull
	@Size(min = 2)
	private String descripcion;

	public static EstadoPlan findByNombre(String nombre) {
		Query q = EstadoPlan.entityManager().createQuery(
				"select o from EstadoPlan o where o.nombre = :nombre");
		q.setParameter("nombre", nombre);
		return (EstadoPlan) q.getSingleResult();
	}

	public String toStringExtendido() {
		StringBuilder sb = new StringBuilder();
		sb.append("Nombre: ").append(getNombre()).append(", ");
		sb.append("Descripcion: ").append(getDescripcion());
		return sb.toString();
	}

	public String toStringLimitado() {
		StringBuilder sb = new StringBuilder();
		sb.append(getNombre()).append(" ");
		return sb.toString();
	}

	@Override
	public String getNombreClase() {
		return "Estado Plan";
	}
}
