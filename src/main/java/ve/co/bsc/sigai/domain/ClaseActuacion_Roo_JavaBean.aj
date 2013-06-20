package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect ClaseActuacion_Roo_JavaBean {
    
    public String ClaseActuacion.getNombre() {
        return this.nombre;
    }
    
    public void ClaseActuacion.setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String ClaseActuacion.getDescripcion() {
        return this.descripcion;
    }
    
    public void ClaseActuacion.setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
