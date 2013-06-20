/*
 * Licencia GPL v3
 * 
 * Copyright (C) 2013 Centro Nacional de Tecnologías de Información.
 * SIGEFUAI Sistema de Informacion Gerencial de Fortalecimiento de las Unidades de Auditoria Interna
 * 
 * Copyright (C) 2013 Ehison Perez, Gerardo Perez, Alexis Veliz. All Rights Reserved.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or any
 * later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see http://www.gnu.org/licenses
 */

package ve.co.bsc.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import ve.co.bsc.sigai.domain.ActividadGeneral;
import ve.co.bsc.sigai.domain.ActividadGenerica;
import ve.co.bsc.sigai.domain.Actuacion;
import ve.co.bsc.sigai.domain.Alegato;
import ve.co.bsc.sigai.domain.Auditado;
import ve.co.bsc.sigai.domain.Auditor;
import ve.co.bsc.sigai.domain.AvanceActuacion;
import ve.co.bsc.sigai.domain.Biblioteca;
import ve.co.bsc.sigai.domain.CargoAuditor;
import ve.co.bsc.sigai.domain.ClaseActuacion;
import ve.co.bsc.sigai.domain.Cuestionario;
import ve.co.bsc.sigai.domain.EntradaBitacora;
import ve.co.bsc.sigai.domain.EstadoActividadActuacion;
import ve.co.bsc.sigai.domain.EstadoActuacion;
import ve.co.bsc.sigai.domain.EstadoAuditor;
import ve.co.bsc.sigai.domain.EstadoObservacion;
import ve.co.bsc.sigai.domain.EstadoPlan;
import ve.co.bsc.sigai.domain.EstadoPlanDeAccion;
import ve.co.bsc.sigai.domain.EstatusRequerimiento;
import ve.co.bsc.sigai.domain.ItemCuestionario;
import ve.co.bsc.sigai.domain.ItemPlanificacionActuacion;
import ve.co.bsc.sigai.domain.ObjetivoEspecifico;
import ve.co.bsc.sigai.domain.ObjetoNombreClase;
import ve.co.bsc.sigai.domain.Observacion;
import ve.co.bsc.sigai.domain.OcupacionAuditor;
import ve.co.bsc.sigai.domain.OrganismoEnte;
import ve.co.bsc.sigai.domain.PapelDeTrabajo;
import ve.co.bsc.sigai.domain.Personalizacion;
import ve.co.bsc.sigai.domain.PlanAnual;
import ve.co.bsc.sigai.domain.PlanDeAccion;
import ve.co.bsc.sigai.domain.Proceso;
import ve.co.bsc.sigai.domain.Prueba;
import ve.co.bsc.sigai.domain.Recomendacion;
import ve.co.bsc.sigai.domain.Requerimiento;
import ve.co.bsc.sigai.domain.Respuesta;
import ve.co.bsc.sigai.domain.Riesgo;
import ve.co.bsc.sigai.domain.SeccionDefault;
import ve.co.bsc.sigai.domain.TecnicaDeControl;
import ve.co.bsc.sigai.domain.TipoEntradaBitacora;
import ve.co.bsc.sigai.domain.TipoSeccion;
import ve.co.bsc.sigai.domain.Unidad;
import ve.co.bsc.sigai.domain.Usuario;

/**
 * 
 * @author Arsen
 */
public class Util {

	private static Logger logger = Logger.getLogger(Util.class);

	// Funcion que recibe un entero y retorna un String con esa cantidad de
	// espacios
	public static String obtenerEspacios(int nivel) {
		String cadena = "";
		for (int i = 0; i < nivel; i++) {
			cadena = cadena + "  ";
		}
		return cadena;
	}

	// Funcion que recibe un entero entre 1 y 12 y retorna un String indicando a
	// cuál mes corresponde el entero
	public static String obtenerMes(int mes) {
		String mesString = "";
		if (mes == 1)
			mesString = "Enero";
		else if (mes == 2)
			mesString = "Febrero";
		else if (mes == 3)
			mesString = "Marzo";
		else if (mes == 4)
			mesString = "Abril";
		else if (mes == 5)
			mesString = "Mayo";
		else if (mes == 6)
			mesString = "Junio";
		else if (mes == 7)
			mesString = "Julio";
		else if (mes == 8)
			mesString = "Agosto";
		else if (mes == 9)
			mesString = "Septiembre";
		else if (mes == 10)
			mesString = "Octubre";
		else if (mes == 11)
			mesString = "Noviembre";
		else if (mes == 12)
			mesString = "Diciembre";
		return mesString;
	}

	public static void registrarEntradaEnBitacora(Object elObjeto,
			String descripcion, String ip) {
		EntradaBitacora bitacora = new EntradaBitacora();

		Calendar cal = Calendar.getInstance();
		bitacora.setTimeStamp(cal.getTime());
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		bitacora.setUsuario(((User) principal).getUsername().toString());
		bitacora.setIp(ip);

		{
			String nombreClase = ((ObjetoNombreClase) elObjeto)
					.getNombreClase();
			Query queryTipos = TipoEntradaBitacora
					.findTipoEntradaBitacorasByClase(nombreClase);
			List<TipoEntradaBitacora> tipoEntradaBitacoras = queryTipos
					.getResultList();
			// Verificamos si el tipo de entrada de bitacora existe, sino existe
			// lo creamos
			if (tipoEntradaBitacoras.isEmpty()) {
				TipoEntradaBitacora tipoEntradaBitacora = new TipoEntradaBitacora();
				tipoEntradaBitacora.setClase(nombreClase);
				tipoEntradaBitacora.persist();
				bitacora.setTipo(tipoEntradaBitacora);
			}
			// Si existe, tomamos el primer elemento del list, nunca nos vamos a
			// encontrar con mas de uno
			else {
				bitacora.setTipo(tipoEntradaBitacoras.get(0));
			}
		}

		if (elObjeto instanceof ActividadGeneral) {
			bitacora.setDescripcion(descripcion + " : "
					+ ((ActividadGeneral) elObjeto).toStringExtendido());
		}
		if (elObjeto instanceof ActividadGenerica) {
			bitacora.setDescripcion(descripcion + " : "
					+ ((ActividadGenerica) elObjeto).toStringExtendido());
		}
		if (elObjeto instanceof Actuacion) {
			bitacora.setDescripcion(descripcion + " : "
					+ ((Actuacion) elObjeto).toStringExtendido());
		}
		if (elObjeto instanceof Alegato) {
			bitacora.setDescripcion(descripcion + " : "
					+ ((Alegato) elObjeto).toStringExtendido());
		}
		if (elObjeto instanceof Auditado) {
			// bitacora.setDescripcion(descripcion+" : "
			// +((Auditado)elObjeto).toStringExtendido());
			// bitacora.setTipo("Auditado");
		}
		if (elObjeto instanceof Auditor) {
			bitacora.setDescripcion(descripcion + " : "
					+ ((Auditor) elObjeto).toStringExtendido());
		}
		if (elObjeto instanceof AvanceActuacion) {
			bitacora.setDescripcion(descripcion + " : "
					+ ((AvanceActuacion) elObjeto).toStringExtendido());
		}
		if (elObjeto instanceof Biblioteca) {
			bitacora.setDescripcion(descripcion + " : "
					+ ((Biblioteca) elObjeto).toStringExtendido());
		}
		if (elObjeto instanceof CargoAuditor) {
			bitacora.setDescripcion(descripcion + " : "
					+ ((CargoAuditor) elObjeto).toStringExtendido());
		}
		if (elObjeto instanceof ClaseActuacion) {
			bitacora.setDescripcion(descripcion + " : "
					+ ((ClaseActuacion) elObjeto).toStringExtendido());
		}
		if (elObjeto instanceof Cuestionario) {
			bitacora.setDescripcion(descripcion + " : "
					+ ((Cuestionario) elObjeto).toStringExtendido());
		}
		if (elObjeto instanceof EstadoActividadActuacion) {
			bitacora.setDescripcion(descripcion + " : "
					+ ((EstadoActividadActuacion) elObjeto).toStringExtendido());
		}
		if (elObjeto instanceof EstadoActuacion) {
			bitacora.setDescripcion(descripcion + " : "
					+ ((EstadoActuacion) elObjeto).toStringExtendido());
		}
		if (elObjeto instanceof EstadoAuditor) {
			bitacora.setDescripcion(descripcion + " : "
					+ ((EstadoAuditor) elObjeto).toStringExtendido());
		}
		if (elObjeto instanceof EstadoObservacion) {
			bitacora.setDescripcion(descripcion + " : "
					+ ((EstadoObservacion) elObjeto).toStringExtendido());
		}
		if (elObjeto instanceof EstadoPlan) {
			bitacora.setDescripcion(descripcion + " : "
					+ ((EstadoPlan) elObjeto).toStringExtendido());
		}
		if (elObjeto instanceof EstadoPlanDeAccion) {
			bitacora.setDescripcion(descripcion + " : "
					+ ((EstadoPlanDeAccion) elObjeto).toStringExtendido());
		}
		if (elObjeto instanceof EstatusRequerimiento) {
			bitacora.setDescripcion(descripcion + " : "
					+ ((EstatusRequerimiento) elObjeto).toStringExtendido());
		}
		if (elObjeto instanceof ItemCuestionario) {
			bitacora.setDescripcion(descripcion + " : "
					+ ((ItemCuestionario) elObjeto).toStringExtendido());
		}
		if (elObjeto instanceof ItemPlanificacionActuacion) {
			bitacora.setDescripcion(descripcion
					+ " : "
					+ ((ItemPlanificacionActuacion) elObjeto)
							.toStringExtendido());
		}
		if (elObjeto instanceof ObjetivoEspecifico) {
			bitacora.setDescripcion(descripcion + " : "
					+ ((ObjetivoEspecifico) elObjeto).toStringExtendido());
		}
		if (elObjeto instanceof Observacion) {
			bitacora.setDescripcion(descripcion + " : "
					+ ((Observacion) elObjeto).toStringExtendido());
		}
		if (elObjeto instanceof OcupacionAuditor) {
			bitacora.setDescripcion(descripcion + " : "
					+ ((OcupacionAuditor) elObjeto).toStringExtendido());
		}
		if (elObjeto instanceof PapelDeTrabajo) {
			bitacora.setDescripcion(descripcion + " : "
					+ ((PapelDeTrabajo) elObjeto).toStringExtendido());
		}
		if (elObjeto instanceof Personalizacion) {
			bitacora.setDescripcion(descripcion + " : "
					+ ((Personalizacion) elObjeto).toStringExtendido());
		}
		if (elObjeto instanceof PlanAnual) {
			bitacora.setDescripcion(descripcion + " : "
					+ ((PlanAnual) elObjeto).toStringExtendido());
		}
		if (elObjeto instanceof PlanDeAccion) {
			bitacora.setDescripcion(descripcion + " : "
					+ ((PlanDeAccion) elObjeto).toStringExtendido());
		}
		if (elObjeto instanceof Proceso) {
			bitacora.setDescripcion(descripcion + " : "
					+ ((Proceso) elObjeto).toStringExtendido());
		}
		if (elObjeto instanceof Prueba) {
			bitacora.setDescripcion(descripcion + " : "
					+ ((Prueba) elObjeto).toStringExtendido());
		}
		if (elObjeto instanceof Recomendacion) {
			bitacora.setDescripcion(descripcion + " : "
					+ ((Recomendacion) elObjeto).toStringExtendido());
		}
		if (elObjeto instanceof Requerimiento) {
			bitacora.setDescripcion(descripcion + " : "
					+ ((Requerimiento) elObjeto).toStringExtendido());
		}
		if (elObjeto instanceof Respuesta) {
			bitacora.setDescripcion(descripcion + " : "
					+ ((Respuesta) elObjeto).toStringExtendido());
		}
		if (elObjeto instanceof Riesgo) {
			bitacora.setDescripcion(descripcion + " : "
					+ ((Riesgo) elObjeto).toStringExtendido());
		}
		if (elObjeto instanceof SeccionDefault) {
			bitacora.setDescripcion(descripcion + " : "
					+ ((SeccionDefault) elObjeto).toStringExtendido());
		}
		if (elObjeto instanceof TecnicaDeControl) {
			bitacora.setDescripcion(descripcion + " : "
					+ ((TecnicaDeControl) elObjeto).toStringExtendido());
		}
		if (elObjeto instanceof TipoSeccion) {
			bitacora.setDescripcion(descripcion + " : "
					+ ((TipoSeccion) elObjeto).toStringExtendido());
		}
		if (elObjeto instanceof Unidad) {
			bitacora.setDescripcion(descripcion + " : "
					+ ((Unidad) elObjeto).toStringExtendido());
		}
		bitacora.persist();
	}

	// Funcion que recibe una colección y un objeto retorna un boolean diciendo
	// si el objeto existe en esa colección
	public static boolean contains(String[] coll, String o) {
		if (coll != null) {
			for (int i = 0; i < coll.length; i++) {
				if (coll[i].trim().equals(o.trim())) {
					return true;
				}
			}
		}
		return false;
	}

	// Funcion que recibe una colección y un objeto retorna un boolean diciendo
	// si el objeto existe en esa colección
	public static boolean usuarioEstaAsignado(Set<Auditor> auditores) {
		String login = null;

		if (auditores.size() > 0) {
			Object o = Usuario.findUsuarioCurrentlyLoggedIn();
			if ((o != null) && (o instanceof Usuario)) {
				Usuario u = (Usuario) o;
				login = u.getLogin();
			}

			Iterator it = auditores.iterator();
			while (it.hasNext()) {
				Auditor auditor = (Auditor) it.next();
				if (auditor.getLogin().equals(login)) {

					return true;
				}
			}
		}
		return false;
	}

	/* Se Modifico esta funcion para que mostrara el Nombre y no el login */
	public static String usuarioLoggeado() {
		String rolUsuario = null;
		Object usuario = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		String login = ((User) usuario).getUsername().toString();
		String Nombre = null;
		String Apellido = null;
		boolean n = false;
		List<Auditor> a = Auditor.findAllAuditors();
		for (Auditor p : a) {
			if (p.getLogin().equals(login)) {
				Nombre = p.getNombre();
				Apellido = p.getApellido();
				n = true;
			}
			if (Apellido == null) {
				Apellido = " ";
			}
		}
		if (n == false) {
			List<Auditado> au = Auditado.findAllAuditadoes();
			for (Auditado auditado : au) {
				if (auditado.getLogin().equals(login)) {
					Nombre = auditado.getNombre();
					Apellido = auditado.getApellido();
				}

			}
		}
		return Nombre + " " + Apellido;
	}

	public String rolUsuarioLoggeado() {
		String login = null;
		String rolUsuario = null;
		Object usuario = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		login = ((User) usuario).getUsername().toString();
		Query query = Usuario.findUsuariosByLogin(login);
		List<Usuario> list = query.getResultList();
		for (Usuario u : list) {
			rolUsuario = u.getRoles().toString();
		}
		return rolUsuario;
	}

	public static String rolUsuarioLoggeado2() {
		String login = null;
		String rolUsuario = null;
		Object usuario = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		login = ((User) usuario).getUsername().toString();
		Query query = Usuario.findUsuariosByLogin(login);
		List<Usuario> list = query.getResultList();
		for (Usuario u : list) {
			rolUsuario = u.getRoles().toString();
		}
		return rolUsuario;
	}

	public OrganismoEnte traerIdRif() {
		// Metodo Para ingresar al Estructura organizativa el organismo del
		// usurio q la crea
		Object usuario = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		OrganismoEnte ente = new OrganismoEnte();
		String l = ((User) usuario).getUsername().toString();
		Query query = Auditor.findAuditorsByLoginEquals(l);
		List<Auditor> au = query.getResultList();
		if (au.size() >= 1) {
			for (Auditor p : au) {
				ente = p.getId_OrganismoEnte();
			}
			return ente;
		} else {
			return null;
		}
	}

	public long traerIdPersonalizacion() {
		// Metodo Para ingresar al Estructura organizativa el organismo del
		// usurio q la crea
		Object usuario = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		String rifEnte = null;
		long idP = 1;
		String l = ((User) usuario).getUsername().toString();
		Query query = Auditor.findAuditorsByLoginEquals(l);
		List<Auditor> au = query.getResultList();
		if (au.size() >= 1) {
			for (Auditor p : au) {
				rifEnte = p.getId_OrganismoEnte().getRif().toString();
			}

			query = null;
			query = Personalizacion.findPersonalizacionsByRifEquals(rifEnte);
			List<Personalizacion> personalizacion = query.getResultList();
			for (Personalizacion p : personalizacion) {
				idP = p.getId();
			}
			return idP;
		} else {
			return 0;
		}
	}

	public static long traerIdPersonalizacion2() {
		// Metodo Para ingresar al Estructura organizativa el organismo del
		// usurio q la crea
		Object usuario = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		String rifEnte = null;
		long idP = 1;
		String l = ((User) usuario).getUsername().toString();
		Query query = Auditor.findAuditorsByLoginEquals(l);
		List<Auditor> au = query.getResultList();
		if (au.size() >= 1) {
			for (Auditor p : au) {
				rifEnte = p.getId_OrganismoEnte().getRif().toString();
			}

			query = null;
			query = Personalizacion.findPersonalizacionsByRifEquals(rifEnte);
			List<Personalizacion> personalizacion = query.getResultList();
			for (Personalizacion p : personalizacion) {
				idP = p.getId();
			}
			return idP;
		} else {
			return 0;
		}
	}

	public String obtenerRif() {
		// Metodo para Obtener el rif de la institucion a que pertecene el
		// usuario logueado
		Object usuario = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		String rifEnte = null;
		String l = ((User) usuario).getUsername().toString();
		Query query = Auditor.findAuditorsByLoginEquals(l);
		List<Auditor> au = query.getResultList();
		if (au.size() >= 1) {
			for (Auditor p : au) {
				rifEnte = p.getId_OrganismoEnte().getRif().toString();
			}
			return rifEnte;
		} else {
			return "TU USUARIO NO ESTA ASOCIADO A NINGUN ENTE";
		}
	}

	public static String obtenerCorreoSunai() {
		Personalizacion personalizacion = Personalizacion
				.findPersonalizacion(new Long(1));
		return personalizacion.getCorreoSunai();
	}

	public static String getNombreInstitucion() {
		String nombre = "";
		if (rolUsuarioLoggeado2().equals("[ADMIN]")
				|| rolUsuarioLoggeado2().equals("[SUNAI_SUPERINTENDENTE]")
				|| rolUsuarioLoggeado2().equals("[SUNAI_ANALISTA]")
				|| rolUsuarioLoggeado2().equals("[SUNAI_COORDINADOR]")
				|| rolUsuarioLoggeado2().equals("[SUNAI_SUPERINTENDENTE]")) {
			nombre = "SUNAI";
		} else {
			Personalizacion personalizacion = Personalizacion
					.findPersonalizacion(traerIdPersonalizacion2());
			nombre = personalizacion.getNombreInstitucion();
		}
		return nombre;
	}

	public static String getCurrentDate() {
		return (new SimpleDateFormat("dd/MM/yyyy hh:mm aa")).format(new Date());
	}

	public void desabilitarUsuariosAuditores(OrganismoEnte organismoEnte) {
		Query query = Auditor.findAuditorsById_OrganismoEnte(organismoEnte);
		List<Auditor> auditors = query.getResultList();
		List<Usuario> usuarios = Usuario.findAllUsuarios();
		if (auditors.size() >= 1) {
			for (Auditor auditor : auditors) {
				for (Usuario usuario : usuarios) {
					if (auditor.getLogin().equals(usuario.getLogin())) {
						usuario.setActivo(false);
						usuario.merge();
					}
				}
			}
		}
	}

	public void desabilitarUsuariosAuditados(OrganismoEnte organismoEnte) {
		Query query = Auditado.findAuditadoesByIdOrganismoEnte(organismoEnte);
		List<Auditado> auditados = query.getResultList();
		List<Usuario> usuarios = Usuario.findAllUsuarios();
		if (auditados.size() >= 1) {
			for (Auditado auditado : auditados) {
				for (Usuario usuario : usuarios) {
					if (auditado.getLogin().equals(usuario.getLogin())) {
						usuario.setActivo(false);
						usuario.merge();
					}
				}
			}
		}
	}

	public void habilitarUsuariosAuditados(OrganismoEnte organismoEnte) {
		Query query = Auditado.findAuditadoesByIdOrganismoEnte(organismoEnte);
		List<Auditado> auditados = query.getResultList();
		List<Usuario> usuarios = Usuario.findAllUsuarios();
		if (auditados.size() >= 1) {
			for (Auditado auditado : auditados) {
				for (Usuario usuario : usuarios) {
					if (auditado.getLogin().equals(usuario.getLogin())) {
						usuario.setActivo(true);
						usuario.merge();
					}
				}
			}
		}
	}

	public void habilitarUsuariosAuditores(OrganismoEnte organismoEnte) {
		Query query = Auditor.findAuditorsById_OrganismoEnte(organismoEnte);
		List<Auditor> auditors = query.getResultList();
		List<Usuario> usuarios = Usuario.findAllUsuarios();
		if (auditors.size() >= 1) {
			for (Auditor auditor : auditors) {
				for (Usuario usuario : usuarios) {
					if (auditor.getLogin().equals(usuario.getLogin())) {
						usuario.setActivo(true);
						usuario.merge();
					}
				}
			}
		}
	}

	public Long traerIdUsuario() {
		long id = 0;
		Object usuario = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		String login = ((User) usuario).getUsername().toString();
		Query query = Usuario.findUsuariosByLogin(login);
		List<Usuario> usuarios = query.getResultList();
		for (Usuario u : usuarios) {
			id = u.getId();
		}
		return id;
	}

	public String loginGerente() {
		Query query = Auditor.findAuditorsById_OrganismoEnte(traerIdRif());
		List<Auditor> list = query.getResultList();
		String gerente = "";
		for (Auditor auditor : list) {
			if (auditor.getCondicionAuditorInterno() != null
					&& auditor.getEstadoAuditor().getNombre().equals("Activo")){
				if (gerente.equals("")) {
					gerente = auditor.getLogin();
				} else {
					gerente = gerente + ", " + auditor.getLogin();
				}
			}
		}
		return gerente;
	}
}