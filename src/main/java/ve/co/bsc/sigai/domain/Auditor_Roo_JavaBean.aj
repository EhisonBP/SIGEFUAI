package ve.co.bsc.sigai.domain;

import java.lang.String;
import java.util.Set;
import ve.co.bsc.sigai.domain.CodigoArea;
import ve.co.bsc.sigai.domain.CondicionAuditorInterno;
import ve.co.bsc.sigai.domain.EstadoAuditor;
import ve.co.bsc.sigai.domain.EstructuraCargos;
import ve.co.bsc.sigai.domain.EstructuraOrganizativa;
import ve.co.bsc.sigai.domain.OrganismoEnte;
import ve.co.bsc.sigai.domain.PlanAnual;

privileged aspect Auditor_Roo_JavaBean {
    
    public String Auditor.getNombre() {
        return this.nombre;
    }
    
    public void Auditor.setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String Auditor.getApellido() {
        return this.apellido;
    }
    
    public void Auditor.setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    public String Auditor.getCedula() {
        return this.cedula;
    }
    
    public void Auditor.setCedula(String cedula) {
        this.cedula = cedula;
    }
    
    public String Auditor.getCorreo() {
        return this.correo;
    }
    
    public void Auditor.setCorreo(String correo) {
        this.correo = correo;
    }
    
    public String Auditor.getLogin() {
        return this.login;
    }
    
    public void Auditor.setLogin(String login) {
        this.login = login;
    }
    
    public String Auditor.getTelefono() {
        return this.telefono;
    }
    
    public void Auditor.setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String Auditor.getCelular() {
        return this.celular;
    }
    
    public void Auditor.setCelular(String celular) {
        this.celular = celular;
    }
    
    public String Auditor.getComentario() {
        return this.comentario;
    }
    
    public void Auditor.setComentario(String comentario) {
        this.comentario = comentario;
    }
    
    public EstadoAuditor Auditor.getEstadoAuditor() {
        return this.estadoAuditor;
    }
    
    public void Auditor.setEstadoAuditor(EstadoAuditor estadoAuditor) {
        this.estadoAuditor = estadoAuditor;
    }
    
    public EstructuraOrganizativa Auditor.getId_estructura() {
        return this.id_estructura;
    }
    
    public void Auditor.setId_estructura(EstructuraOrganizativa id_estructura) {
        this.id_estructura = id_estructura;
    }
    
    public EstructuraCargos Auditor.getCargo() {
        return this.cargo;
    }
    
    public void Auditor.setCargo(EstructuraCargos cargo) {
        this.cargo = cargo;
    }
    
    public OrganismoEnte Auditor.getId_OrganismoEnte() {
        return this.id_OrganismoEnte;
    }
    
    public void Auditor.setId_OrganismoEnte(OrganismoEnte id_OrganismoEnte) {
        this.id_OrganismoEnte = id_OrganismoEnte;
    }
    
    public CodigoArea Auditor.getTelfOficina() {
        return this.telfOficina;
    }
    
    public void Auditor.setTelfOficina(CodigoArea telfOficina) {
        this.telfOficina = telfOficina;
    }
    
    public CodigoArea Auditor.getTelfCelular() {
        return this.telfCelular;
    }
    
    public void Auditor.setTelfCelular(CodigoArea telfCelular) {
        this.telfCelular = telfCelular;
    }
    
    public CondicionAuditorInterno Auditor.getCondicionAuditorInterno() {
        return this.condicionAuditorInterno;
    }
    
    public void Auditor.setCondicionAuditorInterno(CondicionAuditorInterno condicionAuditorInterno) {
        this.condicionAuditorInterno = condicionAuditorInterno;
    }
    
    public Set<PlanAnual> Auditor.getResponsable() {
        return this.responsable;
    }
    
    public void Auditor.setResponsable(Set<PlanAnual> responsable) {
        this.responsable = responsable;
    }
    
}
