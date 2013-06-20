package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect CargoAuditor_Roo_JavaBean {
    
    public String CargoAuditor.getNombre() {
        return this.nombre;
    }
    
    public void CargoAuditor.setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String CargoAuditor.getDescripcion() {
        return this.descripcion;
    }
    
    public void CargoAuditor.setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
