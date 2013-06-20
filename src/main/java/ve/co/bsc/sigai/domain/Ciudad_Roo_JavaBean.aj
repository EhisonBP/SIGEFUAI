package ve.co.bsc.sigai.domain;

import java.lang.String;
import ve.co.bsc.sigai.domain.Estado;
import ve.co.bsc.sigai.domain.EstadoAuditor;

privileged aspect Ciudad_Roo_JavaBean {
    
    public String Ciudad.getNombre() {
        return this.Nombre;
    }
    
    public void Ciudad.setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
    
    public Estado Ciudad.getId_estado() {
        return this.id_estado;
    }
    
    public void Ciudad.setId_estado(Estado id_estado) {
        this.id_estado = id_estado;
    }
    
    public String Ciudad.getDescripcion() {
        return this.Descripcion;
    }
    
    public void Ciudad.setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }
    
    public EstadoAuditor Ciudad.getEstatus() {
        return this.Estatus;
    }
    
    public void Ciudad.setEstatus(EstadoAuditor Estatus) {
        this.Estatus = Estatus;
    }
    
}
