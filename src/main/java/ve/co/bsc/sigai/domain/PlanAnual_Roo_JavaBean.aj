package ve.co.bsc.sigai.domain;

import java.lang.Integer;
import java.lang.String;
import ve.co.bsc.sigai.domain.Auditor;
import ve.co.bsc.sigai.domain.EstadoPlan;
import ve.co.bsc.sigai.domain.InstruirPlan;
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
    
    public InstruirPlan PlanAnual.getAnoPlan() {
        return this.anoPlan;
    }
    
    public void PlanAnual.setAnoPlan(InstruirPlan anoPlan) {
        this.anoPlan = anoPlan;
    }
    
    public OrganismoEnte PlanAnual.getRif() {
        return this.rif;
    }
    
    public void PlanAnual.setRif(OrganismoEnte rif) {
        this.rif = rif;
    }
    
    public Auditor PlanAnual.getResponsable() {
        return this.responsable;
    }
    
    public void PlanAnual.setResponsable(Auditor responsable) {
        this.responsable = responsable;
    }
    
    public Auditor PlanAnual.getResponsable2() {
        return this.responsable2;
    }
    
    public void PlanAnual.setResponsable2(Auditor responsable2) {
        this.responsable2 = responsable2;
    }
    
}
