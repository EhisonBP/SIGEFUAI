package ve.co.bsc.sigai.domain;

import java.lang.String;
import ve.co.bsc.sigai.domain.CodigoArea;
import ve.co.bsc.sigai.domain.EstadoAuditor;
import ve.co.bsc.sigai.domain.EstructuraCargos;
import ve.co.bsc.sigai.domain.EstructuraOrganizativa;
import ve.co.bsc.sigai.domain.OrganismoEnte;

privileged aspect Auditado_Roo_JavaBean {
    
    public String Auditado.getNombre() {
        return this.nombre;
    }
    
    public void Auditado.setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String Auditado.getApellido() {
        return this.apellido;
    }
    
    public void Auditado.setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    public String Auditado.getCedula() {
        return this.cedula;
    }
    
    public void Auditado.setCedula(String cedula) {
        this.cedula = cedula;
    }
    
    public String Auditado.getLogin() {
        return this.login;
    }
    
    public void Auditado.setLogin(String login) {
        this.login = login;
    }
    
    public String Auditado.getTelefono() {
        return this.telefono;
    }
    
    public void Auditado.setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String Auditado.getCelular() {
        return this.celular;
    }
    
    public void Auditado.setCelular(String celular) {
        this.celular = celular;
    }
    
    public String Auditado.getCorreo() {
        return this.correo;
    }
    
    public void Auditado.setCorreo(String correo) {
        this.correo = correo;
    }
    
    public CodigoArea Auditado.getTelfOficina() {
        return this.telfOficina;
    }
    
    public void Auditado.setTelfOficina(CodigoArea telfOficina) {
        this.telfOficina = telfOficina;
    }
    
    public CodigoArea Auditado.getTelfCelular() {
        return this.telfCelular;
    }
    
    public void Auditado.setTelfCelular(CodigoArea telfCelular) {
        this.telfCelular = telfCelular;
    }
    
    public OrganismoEnte Auditado.getIdOrganismoEnte() {
        return this.idOrganismoEnte;
    }
    
    public void Auditado.setIdOrganismoEnte(OrganismoEnte idOrganismoEnte) {
        this.idOrganismoEnte = idOrganismoEnte;
    }
    
    public EstructuraOrganizativa Auditado.getIdEstructura() {
        return this.idEstructura;
    }
    
    public void Auditado.setIdEstructura(EstructuraOrganizativa idEstructura) {
        this.idEstructura = idEstructura;
    }
    
    public EstructuraCargos Auditado.getCargo() {
        return this.cargo;
    }
    
    public void Auditado.setCargo(EstructuraCargos cargo) {
        this.cargo = cargo;
    }
    
    public EstadoAuditor Auditado.getEstadoAuditado() {
        return this.estadoAuditado;
    }
    
    public void Auditado.setEstadoAuditado(EstadoAuditor estadoAuditado) {
        this.estadoAuditado = estadoAuditado;
    }
    
}
