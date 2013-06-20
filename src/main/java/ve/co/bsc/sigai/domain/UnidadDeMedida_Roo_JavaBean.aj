package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect UnidadDeMedida_Roo_JavaBean {
    
    public String UnidadDeMedida.getNombre() {
        return this.nombre;
    }
    
    public void UnidadDeMedida.setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String UnidadDeMedida.getDescripcion() {
        return this.descripcion;
    }
    
    public void UnidadDeMedida.setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
