package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect EstadoAuditor_Roo_JavaBean {
    
    public String EstadoAuditor.getNombre() {
        return this.nombre;
    }
    
    public void EstadoAuditor.setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String EstadoAuditor.getDescripcion() {
        return this.descripcion;
    }
    
    public void EstadoAuditor.setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
