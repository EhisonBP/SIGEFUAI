package ve.co.bsc.sigai.form;

import ve.co.bsc.sigai.domain.Auditado;
import ve.co.bsc.sigai.domain.Auditor;
import ve.co.bsc.sigai.domain.Unidad;
import ve.co.bsc.sigai.domain.Usuario;

public class RegUsuarioSunaiForm {
	private String password2;
	private int effectTypes2;
	private Usuario usuarios;
	private Auditor auditor;
	private Unidad unidad;
	private Auditado auditado;

	public RegUsuarioSunaiForm() {
		usuarios = new Usuario();
		auditor = new Auditor();
		unidad = new Unidad();
		setAuditado(new Auditado());
	}

	public Usuario getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Usuario usuarios) {
		this.usuarios = usuarios;
	}

	public Auditor getAuditor() {
		return auditor;
	}

	public void setAuditor(Auditor auditor) {
		this.auditor = auditor;
	}

	public Unidad getUnidad() {
		return unidad;
	}

	public void setUnidad(Unidad unidad) {
		this.unidad = unidad;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public int getEffectTypes2() {
		return effectTypes2;
	}

	public void setEffectTypes2(int effectTypes2) {
		this.effectTypes2 = effectTypes2;
	}

	public void setAuditado(Auditado auditado) {
		this.auditado = auditado;
	}

	public Auditado getAuditado() {
		return auditado;
	}
}