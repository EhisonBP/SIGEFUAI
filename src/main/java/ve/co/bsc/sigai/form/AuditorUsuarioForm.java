/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ve.co.bsc.sigai.form;

import ve.co.bsc.sigai.domain.Auditor;
import ve.co.bsc.sigai.domain.Personalizacion;
import ve.co.bsc.sigai.domain.Usuario;

public class AuditorUsuarioForm {

	private Usuario usuario;
	private Auditor auditor;
	private Personalizacion personalizacion;
	private String password;
	private String password2;
	private int effectTypes;
	private int effectTypes2;

	public int getEffectTypes2() {
		return effectTypes2;
	}

	public void setEffectTypes2(int effectTypes2) {
		this.effectTypes2 = effectTypes2;
	}

	public AuditorUsuarioForm() {
		usuario = new Usuario();
		auditor = new Auditor();
		personalizacion = new Personalizacion();
	}

	public int getEffectTypes() {
		return effectTypes;
	}

	public void setEffectTypes(int effectTypes) {
		this.effectTypes = effectTypes;
	}

	public Personalizacion getPersonalizacion() {
		return personalizacion;
	}

	public void setPersonalizacion(Personalizacion personalizacion) {
		this.personalizacion = personalizacion;
	}

	public Auditor getAuditor() {
		return auditor;
	}

	public void setAuditor(Auditor auditor) {
		this.auditor = auditor;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

}
