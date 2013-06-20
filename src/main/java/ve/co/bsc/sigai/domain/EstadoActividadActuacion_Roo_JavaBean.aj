package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect EstadoActividadActuacion_Roo_JavaBean {
    
    public String EstadoActividadActuacion.getNombre() {
        return this.nombre;
    }
    
    public void EstadoActividadActuacion.setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String EstadoActividadActuacion.getDescripcion() {
        return this.descripcion;
    }
    
    public void EstadoActividadActuacion.setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
