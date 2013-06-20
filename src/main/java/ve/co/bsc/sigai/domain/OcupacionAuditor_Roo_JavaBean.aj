package ve.co.bsc.sigai.domain;

import java.lang.Double;
import java.lang.Integer;
import java.lang.String;
import java.util.Date;
import ve.co.bsc.sigai.domain.ActividadAuditor;
import ve.co.bsc.sigai.domain.Auditor;
import ve.co.bsc.sigai.domain.CargoAuditor;
import ve.co.bsc.sigai.domain.PlanAnual;

privileged aspect OcupacionAuditor_Roo_JavaBean {
    
    public Double OcupacionAuditor.getHoras() {
        return this.horas;
    }
    
    public void OcupacionAuditor.setHoras(Double horas) {
        this.horas = horas;
    }
    
    public Date OcupacionAuditor.getInicio() {
        return this.inicio;
    }
    
    public void OcupacionAuditor.setInicio(Date inicio) {
        this.inicio = inicio;
    }
    
    public Date OcupacionAuditor.getFin() {
        return this.fin;
    }
    
    public void OcupacionAuditor.setFin(Date fin) {
        this.fin = fin;
    }
    
    public CargoAuditor OcupacionAuditor.getCargoAuditor() {
        return this.cargoAuditor;
    }
    
    public void OcupacionAuditor.setCargoAuditor(CargoAuditor cargoAuditor) {
        this.cargoAuditor = cargoAuditor;
    }
    
    public Auditor OcupacionAuditor.getAuditor() {
        return this.auditor;
    }
    
    public void OcupacionAuditor.setAuditor(Auditor auditor) {
        this.auditor = auditor;
    }
    
    public PlanAnual OcupacionAuditor.getPlanAnual() {
        return this.planAnual;
    }
    
    public void OcupacionAuditor.setPlanAnual(PlanAnual planAnual) {
        this.planAnual = planAnual;
    }
    
    public ActividadAuditor OcupacionAuditor.getActividadAuditor() {
        return this.actividadAuditor;
    }
    
    public void OcupacionAuditor.setActividadAuditor(ActividadAuditor actividadAuditor) {
        this.actividadAuditor = actividadAuditor;
    }
    
    public String OcupacionAuditor.getObservaciones() {
        return this.observaciones;
    }
    
    public void OcupacionAuditor.setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    public Integer OcupacionAuditor.getPorcentajeCompletado() {
        return this.porcentajeCompletado;
    }
    
    public void OcupacionAuditor.setPorcentajeCompletado(Integer porcentajeCompletado) {
        this.porcentajeCompletado = porcentajeCompletado;
    }
    
    public String OcupacionAuditor.getUnidadDeMedida() {
        return this.unidadDeMedida;
    }
    
    public void OcupacionAuditor.setUnidadDeMedida(String unidadDeMedida) {
        this.unidadDeMedida = unidadDeMedida;
    }
    
}
