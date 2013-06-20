package ve.co.bsc.sigai.domain;

import java.lang.Integer;
import java.util.Date;
import ve.co.bsc.sigai.domain.Actuacion;
import ve.co.bsc.sigai.domain.OtraActividad;
import ve.co.bsc.sigai.domain.PlanAnual;

privileged aspect ItemPlanificacionActuacion_Roo_JavaBean {
    
    public Integer ItemPlanificacionActuacion.getInicioEstimado() {
        return this.inicioEstimado;
    }
    
    public void ItemPlanificacionActuacion.setInicioEstimado(Integer inicioEstimado) {
        this.inicioEstimado = inicioEstimado;
    }
    
    public Integer ItemPlanificacionActuacion.getFinEstimado() {
        return this.finEstimado;
    }
    
    public void ItemPlanificacionActuacion.setFinEstimado(Integer finEstimado) {
        this.finEstimado = finEstimado;
    }
    
    public Date ItemPlanificacionActuacion.getFechaInicioEstimada() {
        return this.fechaInicioEstimada;
    }
    
    public void ItemPlanificacionActuacion.setFechaInicioEstimada(Date fechaInicioEstimada) {
        this.fechaInicioEstimada = fechaInicioEstimada;
    }
    
    public Date ItemPlanificacionActuacion.getFechaFinEstimada() {
        return this.fechaFinEstimada;
    }
    
    public void ItemPlanificacionActuacion.setFechaFinEstimada(Date fechaFinEstimada) {
        this.fechaFinEstimada = fechaFinEstimada;
    }
    
    public Date ItemPlanificacionActuacion.getInicioReal() {
        return this.inicioReal;
    }
    
    public void ItemPlanificacionActuacion.setInicioReal(Date inicioReal) {
        this.inicioReal = inicioReal;
    }
    
    public Date ItemPlanificacionActuacion.getFinReal() {
        return this.finReal;
    }
    
    public void ItemPlanificacionActuacion.setFinReal(Date finReal) {
        this.finReal = finReal;
    }
    
    public PlanAnual ItemPlanificacionActuacion.getPlanAnual() {
        return this.planAnual;
    }
    
    public void ItemPlanificacionActuacion.setPlanAnual(PlanAnual planAnual) {
        this.planAnual = planAnual;
    }
    
    public Actuacion ItemPlanificacionActuacion.getActuacion() {
        return this.actuacion;
    }
    
    public void ItemPlanificacionActuacion.setActuacion(Actuacion actuacion) {
        this.actuacion = actuacion;
    }
    
    public OtraActividad ItemPlanificacionActuacion.getOtraActividad() {
        return this.otraActividad;
    }
    
    public void ItemPlanificacionActuacion.setOtraActividad(OtraActividad otraActividad) {
        this.otraActividad = otraActividad;
    }
    
}
