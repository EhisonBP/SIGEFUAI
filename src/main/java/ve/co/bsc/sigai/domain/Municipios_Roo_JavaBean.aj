package ve.co.bsc.sigai.domain;

import java.lang.String;
import ve.co.bsc.sigai.domain.Ciudad;
import ve.co.bsc.sigai.domain.EstadoAuditor;

privileged aspect Municipios_Roo_JavaBean {
    
    public String Municipios.getNombre() {
        return this.Nombre;
    }
    
    public void Municipios.setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
    
    public Ciudad Municipios.getId_ciudad() {
        return this.id_ciudad;
    }
    
    public void Municipios.setId_ciudad(Ciudad id_ciudad) {
        this.id_ciudad = id_ciudad;
    }
    
    public String Municipios.getDescripcion() {
        return this.Descripcion;
    }
    
    public void Municipios.setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }
    
    public EstadoAuditor Municipios.getEstatus() {
        return this.Estatus;
    }
    
    public void Municipios.setEstatus(EstadoAuditor Estatus) {
        this.Estatus = Estatus;
    }
    
}
