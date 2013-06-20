package ve.co.bsc.sigai.domain;

import java.lang.String;
import java.util.Date;
import ve.co.bsc.sigai.domain.Actuacion;
import ve.co.bsc.sigai.domain.Auditado;
import ve.co.bsc.sigai.domain.EstatusRequerimiento;
import ve.co.bsc.sigai.domain.Unidad;

privileged aspect Requerimiento_Roo_JavaBean {
    
    public String Requerimiento.getNumeroSolicitud() {
        return this.numeroSolicitud;
    }
    
    public void Requerimiento.setNumeroSolicitud(String numeroSolicitud) {
        this.numeroSolicitud = numeroSolicitud;
    }
    
    public Date Requerimiento.getFechaSolicitud() {
        return this.fechaSolicitud;
    }
    
    public void Requerimiento.setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }
    
    public String Requerimiento.getObservaciones() {
        return this.observaciones;
    }
    
    public void Requerimiento.setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    public String Requerimiento.getAsunto() {
        return this.asunto;
    }
    
    public void Requerimiento.setAsunto(String asunto) {
        this.asunto = asunto;
    }
    
    public Actuacion Requerimiento.getActuacion() {
        return this.actuacion;
    }
    
    public void Requerimiento.setActuacion(Actuacion actuacion) {
        this.actuacion = actuacion;
    }
    
    public Unidad Requerimiento.getUnidad() {
        return this.unidad;
    }
    
    public void Requerimiento.setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }
    
    public EstatusRequerimiento Requerimiento.getEstadoRequerimiento() {
        return this.estadoRequerimiento;
    }
    
    public void Requerimiento.setEstadoRequerimiento(EstatusRequerimiento estadoRequerimiento) {
        this.estadoRequerimiento = estadoRequerimiento;
    }
    
    public Date Requerimiento.getFechaRecibidoTotalmente() {
        return this.fechaRecibidoTotalmente;
    }
    
    public void Requerimiento.setFechaRecibidoTotalmente(Date fechaRecibidoTotalmente) {
        this.fechaRecibidoTotalmente = fechaRecibidoTotalmente;
    }
    
    public Auditado Requerimiento.getAuditado() {
        return this.auditado;
    }
    
    public void Requerimiento.setAuditado(Auditado auditado) {
        this.auditado = auditado;
    }
    
}
