package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect TipoUnidad_Roo_JavaBean {
    
    public String TipoUnidad.getNombre() {
        return this.nombre;
    }
    
    public void TipoUnidad.setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String TipoUnidad.getDescripcion() {
        return this.descripcion;
    }
    
    public void TipoUnidad.setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
