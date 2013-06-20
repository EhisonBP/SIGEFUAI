package ve.co.bsc.sigai.domain;

import java.lang.Integer;
import java.lang.String;
import ve.co.bsc.sigai.domain.EstadoPlan;
import ve.co.bsc.sigai.domain.OrganismoEnte;

privileged aspect PlanAnual_Roo_JavaBean {
    
    public EstadoPlan PlanAnual.getEstadoPlan() {
        return this.estadoPlan;
    }
    
    public void PlanAnual.setEstadoPlan(EstadoPlan estadoPlan) {
        this.estadoPlan = estadoPlan;
    }
    
    public String PlanAnual.getDescripcion() {
        return this.descripcion;
    }
    
    public void PlanAnual.setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public Integer PlanAnual.getAnoFiscal() {
        return this.anoFiscal;
    }
    
    public void PlanAnual.setAnoFiscal(Integer anoFiscal) {
        this.anoFiscal = anoFiscal;
    }
    
    public String[] PlanAnual.getAccionesPermitidas() {
        return this.accionesPermitidas;
    }
    
    public void PlanAnual.setAccionesPermitidas(String[] accionesPermitidas) {
        this.accionesPermitidas = accionesPermitidas;
    }
    
    public OrganismoEnte PlanAnual.getRif() {
        return this.rif;
    }
    
    public void PlanAnual.setRif(OrganismoEnte rif) {
        this.rif = rif;
    }
    
}
