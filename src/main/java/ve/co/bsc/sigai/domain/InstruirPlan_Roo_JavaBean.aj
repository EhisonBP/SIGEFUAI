package ve.co.bsc.sigai.domain;

import java.lang.Integer;
import java.lang.String;
import java.util.Date;
import ve.co.bsc.sigai.domain.EstadoAuditor;

privileged aspect InstruirPlan_Roo_JavaBean {
    
    public Integer InstruirPlan.getAno() {
        return this.ano;
    }
    
    public void InstruirPlan.setAno(Integer ano) {
        this.ano = ano;
    }
    
    public Date InstruirPlan.getFechaInicio() {
        return this.fechaInicio;
    }
    
    public void InstruirPlan.setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    
    public Date InstruirPlan.getFechaFin() {
        return this.fechaFin;
    }
    
    public void InstruirPlan.setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
    
    public String InstruirPlan.getInstruccion() {
        return this.instruccion;
    }
    
    public void InstruirPlan.setInstruccion(String instruccion) {
        this.instruccion = instruccion;
    }
    
    public EstadoAuditor InstruirPlan.getEstatus() {
        return this.estatus;
    }
    
    public void InstruirPlan.setEstatus(EstadoAuditor estatus) {
        this.estatus = estatus;
    }
    
}
