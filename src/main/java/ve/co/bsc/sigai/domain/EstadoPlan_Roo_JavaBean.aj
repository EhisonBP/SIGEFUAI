package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect EstadoPlan_Roo_JavaBean {
    
    public String EstadoPlan.getNombre() {
        return this.nombre;
    }
    
    public void EstadoPlan.setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String EstadoPlan.getDescripcion() {
        return this.descripcion;
    }
    
    public void EstadoPlan.setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
