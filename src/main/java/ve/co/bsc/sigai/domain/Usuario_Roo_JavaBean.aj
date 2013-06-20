package ve.co.bsc.sigai.domain;

import java.lang.Boolean;
import java.lang.String;
import java.util.List;
import ve.co.bsc.sigai.domain.EstructuraCargos;
import ve.co.bsc.sigai.domain.RolUsuario;

privileged aspect Usuario_Roo_JavaBean {
    
    public String Usuario.getLogin() {
        return this.login;
    }
    
    public void Usuario.setLogin(String login) {
        this.login = login;
    }
    
    public String Usuario.getPassword() {
        return this.password;
    }
    
    public void Usuario.setPassword(String password) {
        this.password = password;
    }
    
    public List<RolUsuario> Usuario.getRoles() {
        return this.roles;
    }
    
    public void Usuario.setRoles(List<RolUsuario> roles) {
        this.roles = roles;
    }
    
    public List<EstructuraCargos> Usuario.getCargos() {
        return this.cargos;
    }
    
    public void Usuario.setCargos(List<EstructuraCargos> cargos) {
        this.cargos = cargos;
    }
    
    public Boolean Usuario.getActivo() {
        return this.activo;
    }
    
    public void Usuario.setActivo(Boolean activo) {
        this.activo = activo;
    }
    
}
