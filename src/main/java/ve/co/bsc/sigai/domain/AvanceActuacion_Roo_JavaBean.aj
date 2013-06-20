package ve.co.bsc.sigai.domain;

import java.lang.String;
import java.util.Date;
import ve.co.bsc.sigai.domain.ActividadActuacion;

privileged aspect AvanceActuacion_Roo_JavaBean {
    
    public String AvanceActuacion.getJustificacion() {
        return this.justificacion;
    }
    
    public void AvanceActuacion.setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }
    
    public ActividadActuacion AvanceActuacion.getCodigoActividad() {
        return this.codigoActividad;
    }
    
    public void AvanceActuacion.setCodigoActividad(ActividadActuacion codigoActividad) {
        this.codigoActividad = codigoActividad;
    }
    
    public Date AvanceActuacion.getFechaInicioEstimada() {
        return this.fechaInicioEstimada;
    }
    
    public void AvanceActuacion.setFechaInicioEstimada(Date fechaInicioEstimada) {
        this.fechaInicioEstimada = fechaInicioEstimada;
    }
    
    public Date AvanceActuacion.getFechaFinEstimada() {
        return this.fechaFinEstimada;
    }
    
    public void AvanceActuacion.setFechaFinEstimada(Date fechaFinEstimada) {
        this.fechaFinEstimada = fechaFinEstimada;
    }
    
    public Date AvanceActuacion.getFechaInicioReal() {
        return this.fechaInicioReal;
    }
    
    public void AvanceActuacion.setFechaInicioReal(Date fechaInicioReal) {
        this.fechaInicioReal = fechaInicioReal;
    }
    
    public Date AvanceActuacion.getFechaFinReal() {
        return this.fechaFinReal;
    }
    
    public void AvanceActuacion.setFechaFinReal(Date fechaFinReal) {
        this.fechaFinReal = fechaFinReal;
    }
    
}
