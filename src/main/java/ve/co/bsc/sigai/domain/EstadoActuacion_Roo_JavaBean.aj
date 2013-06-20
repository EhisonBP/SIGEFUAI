package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect EstadoActuacion_Roo_JavaBean {
    
    public String EstadoActuacion.getNombre() {
        return this.nombre;
    }
    
    public void EstadoActuacion.setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String EstadoActuacion.getDescripcion() {
        return this.descripcion;
    }
    
    public void EstadoActuacion.setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
