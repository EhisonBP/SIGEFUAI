package ve.co.bsc.sigai.domain;

import java.lang.String;
import java.util.Date;
import java.util.Set;
import ve.co.bsc.sigai.domain.EstadoPlanDeAccion;
import ve.co.bsc.sigai.domain.Observacion;
import ve.co.bsc.sigai.domain.Unidad;

privileged aspect PlanDeAccion_Roo_JavaBean {
    
    public String PlanDeAccion.getDescripcion() {
        return this.descripcion;
    }
    
    public void PlanDeAccion.setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public Date PlanDeAccion.getFechaCierre() {
        return this.fechaCierre;
    }
    
    public void PlanDeAccion.setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }
    
    public Set<Unidad> PlanDeAccion.getUnidad() {
        return this.unidad;
    }
    
    public void PlanDeAccion.setUnidad(Set<Unidad> unidad) {
        this.unidad = unidad;
    }
    
    public EstadoPlanDeAccion PlanDeAccion.getEstadoPlanDeAccion() {
        return this.estadoPlanDeAccion;
    }
    
    public void PlanDeAccion.setEstadoPlanDeAccion(EstadoPlanDeAccion estadoPlanDeAccion) {
        this.estadoPlanDeAccion = estadoPlanDeAccion;
    }
    
    public Observacion PlanDeAccion.getObservacion() {
        return this.observacion;
    }
    
    public void PlanDeAccion.setObservacion(Observacion observacion) {
        this.observacion = observacion;
    }
    
    public Date PlanDeAccion.getFechaCierre2() {
        return this.fechaCierre2;
    }
    
    public void PlanDeAccion.setFechaCierre2(Date fechaCierre2) {
        this.fechaCierre2 = fechaCierre2;
    }
    
    public Date PlanDeAccion.getFechaSeguimiento() {
        return this.fechaSeguimiento;
    }
    
    public void PlanDeAccion.setFechaSeguimiento(Date fechaSeguimiento) {
        this.fechaSeguimiento = fechaSeguimiento;
    }
    
}
