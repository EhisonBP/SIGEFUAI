package ve.co.bsc.sigai.domain;

import java.lang.String;
import java.util.Date;
import ve.co.bsc.sigai.domain.EstadoAuditor;

privileged aspect TipoPersonaRif_Roo_JavaBean {
    
    public String TipoPersonaRif.getNombre() {
        return this.Nombre;
    }
    
    public void TipoPersonaRif.setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
    
    public String TipoPersonaRif.getDescripcion() {
        return this.Descripcion;
    }
    
    public void TipoPersonaRif.setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }
    
    public Date TipoPersonaRif.getFechaCreacion() {
        return this.fechaCreacion;
    }
    
    public void TipoPersonaRif.setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public Date TipoPersonaRif.getFechaModificacion() {
        return this.fechaModificacion;
    }
    
    public void TipoPersonaRif.setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
    
    public EstadoAuditor TipoPersonaRif.getEstatus() {
        return this.Estatus;
    }
    
    public void TipoPersonaRif.setEstatus(EstadoAuditor Estatus) {
        this.Estatus = Estatus;
    }
    
}
