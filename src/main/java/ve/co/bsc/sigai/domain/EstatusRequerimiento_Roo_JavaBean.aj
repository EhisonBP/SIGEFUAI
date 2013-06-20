package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect EstatusRequerimiento_Roo_JavaBean {
    
    public String EstatusRequerimiento.getNombre() {
        return this.nombre;
    }
    
    public void EstatusRequerimiento.setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String EstatusRequerimiento.getDescripcion() {
        return this.descripcion;
    }
    
    public void EstatusRequerimiento.setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
