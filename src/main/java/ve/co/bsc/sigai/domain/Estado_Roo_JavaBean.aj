package ve.co.bsc.sigai.domain;

import java.lang.String;
import ve.co.bsc.sigai.domain.EstadoAuditor;

privileged aspect Estado_Roo_JavaBean {
    
    public String Estado.getNombre() {
        return this.nombre;
    }
    
    public void Estado.setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String Estado.getDescripcion() {
        return this.Descripcion;
    }
    
    public void Estado.setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }
    
    public EstadoAuditor Estado.getEstatus() {
        return this.Estatus;
    }
    
    public void Estado.setEstatus(EstadoAuditor Estatus) {
        this.Estatus = Estatus;
    }
    
}
