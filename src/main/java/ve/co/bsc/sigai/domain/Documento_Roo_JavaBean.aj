package ve.co.bsc.sigai.domain;

import java.lang.String;
import java.util.Set;
import ve.co.bsc.sigai.domain.ActividadActuacion;

privileged aspect Documento_Roo_JavaBean {
    
    public String Documento.getNombre() {
        return this.nombre;
    }
    
    public void Documento.setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String Documento.getDescripcion() {
        return this.descripcion;
    }
    
    public void Documento.setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public Set<ActividadActuacion> Documento.getActividadActuacion() {
        return this.actividadActuacion;
    }
    
    public void Documento.setActividadActuacion(Set<ActividadActuacion> actividadActuacion) {
        this.actividadActuacion = actividadActuacion;
    }
    
}
