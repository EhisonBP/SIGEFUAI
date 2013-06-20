package ve.co.bsc.sigai.domain;

import java.lang.String;
import java.util.Date;
import ve.co.bsc.sigai.domain.EstadoAuditor;
import ve.co.bsc.sigai.domain.TipoRol;

privileged aspect RolUsuario_Roo_JavaBean {
    
    public String RolUsuario.getNombre() {
        return this.nombre;
    }
    
    public void RolUsuario.setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String RolUsuario.getDescripcion() {
        return this.descripcion;
    }
    
    public void RolUsuario.setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public EstadoAuditor RolUsuario.getEstatus() {
        return this.estatus;
    }
    
    public void RolUsuario.setEstatus(EstadoAuditor estatus) {
        this.estatus = estatus;
    }
    
    public Date RolUsuario.getFechaCreacion() {
        return this.fechaCreacion;
    }
    
    public void RolUsuario.setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public Date RolUsuario.getFechaModificacion() {
        return this.fechaModificacion;
    }
    
    public void RolUsuario.setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
    
    public String RolUsuario.getUsuario() {
        return this.usuario;
    }
    
    public void RolUsuario.setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    public TipoRol RolUsuario.getTipoRol() {
        return this.tipoRol;
    }
    
    public void RolUsuario.setTipoRol(TipoRol tipoRol) {
        this.tipoRol = tipoRol;
    }
    
}
