package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect EstadoPlanDeAccion_Roo_JavaBean {
    
    public String EstadoPlanDeAccion.getNombre() {
        return this.nombre;
    }
    
    public void EstadoPlanDeAccion.setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String EstadoPlanDeAccion.getDescripcion() {
        return this.descripcion;
    }
    
    public void EstadoPlanDeAccion.setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
