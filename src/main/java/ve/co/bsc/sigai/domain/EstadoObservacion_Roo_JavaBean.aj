package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect EstadoObservacion_Roo_JavaBean {
    
    public String EstadoObservacion.getNombre() {
        return this.nombre;
    }
    
    public void EstadoObservacion.setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String EstadoObservacion.getDescripcion() {
        return this.descripcion;
    }
    
    public void EstadoObservacion.setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String EstadoObservacion.getDescripcionCorta() {
        return this.descripcionCorta;
    }
    
    public void EstadoObservacion.setDescripcionCorta(String descripcionCorta) {
        this.descripcionCorta = descripcionCorta;
    }
    
}
