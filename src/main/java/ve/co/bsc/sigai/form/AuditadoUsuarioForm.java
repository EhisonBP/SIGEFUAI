/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ve.co.bsc.sigai.form;

import ve.co.bsc.sigai.domain.Auditado;
import ve.co.bsc.sigai.domain.Usuario;

public class AuditadoUsuarioForm {

    private Usuario usuario;
    private Auditado auditado;
    private String password;
    private String password2;

    public AuditadoUsuarioForm() {
        usuario = new Usuario();
        auditado = new Auditado();
    }

    public Auditado getAuditado() {
        return auditado;
    }

    public void setAuditado(Auditado auditado) {
        this.auditado = auditado;
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
