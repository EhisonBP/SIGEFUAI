package ve.co.bsc.sigai.domain;

import java.lang.Boolean;
import java.lang.Integer;
import java.lang.String;
import ve.co.bsc.sigai.domain.Actuacion;
import ve.co.bsc.sigai.domain.Auditado;
import ve.co.bsc.sigai.domain.Unidad;

privileged aspect Cuestionario_Roo_JavaBean {
    
    public String Cuestionario.getNombre() {
        return this.nombre;
    }
    
    public void Cuestionario.setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String Cuestionario.getDescripcion() {
        return this.descripcion;
    }
    
    public void Cuestionario.setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public Actuacion Cuestionario.getActuacion() {
        return this.actuacion;
    }
    
    public void Cuestionario.setActuacion(Actuacion actuacion) {
        this.actuacion = actuacion;
    }
    
    public Integer Cuestionario.getCorrelativo() {
        return this.correlativo;
    }
    
    public void Cuestionario.setCorrelativo(Integer correlativo) {
        this.correlativo = correlativo;
    }
    
    public Boolean Cuestionario.getRespondido() {
        return this.respondido;
    }
    
    public void Cuestionario.setRespondido(Boolean respondido) {
        this.respondido = respondido;
    }
    
    public Auditado Cuestionario.getAuditado() {
        return this.auditado;
    }
    
    public void Cuestionario.setAuditado(Auditado auditado) {
        this.auditado = auditado;
    }
    
    public Unidad Cuestionario.getUnidad() {
        return this.unidad;
    }
    
    public void Cuestionario.setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }
    
}
