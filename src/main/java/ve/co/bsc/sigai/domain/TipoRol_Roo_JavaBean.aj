package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect TipoRol_Roo_JavaBean {
    
    public String TipoRol.getNombre() {
        return this.nombre;
    }
    
    public void TipoRol.setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String TipoRol.getDescripcion() {
        return this.descripcion;
    }
    
    public void TipoRol.setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
