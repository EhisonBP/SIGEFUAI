package ve.co.bsc.sigai.domain;

import java.lang.String;
import java.util.Date;
import ve.co.bsc.sigai.domain.EstadoAuditor;
import ve.co.bsc.sigai.domain.EstructuraOrganizativa;
import ve.co.bsc.sigai.domain.OrganismoEnte;

privileged aspect EstructuraCargos_Roo_JavaBean {
    
    public String EstructuraCargos.getNombreCargo() {
        return this.nombreCargo;
    }
    
    public void EstructuraCargos.setNombreCargo(String nombreCargo) {
        this.nombreCargo = nombreCargo;
    }
    
    public String EstructuraCargos.getDescripcionCargo() {
        return this.descripcionCargo;
    }
    
    public void EstructuraCargos.setDescripcionCargo(String descripcionCargo) {
        this.descripcionCargo = descripcionCargo;
    }
    
    public String EstructuraCargos.getFuncionesCargo() {
        return this.funcionesCargo;
    }
    
    public void EstructuraCargos.setFuncionesCargo(String funcionesCargo) {
        this.funcionesCargo = funcionesCargo;
    }
    
    public Date EstructuraCargos.getFechaCreacion() {
        return this.fechaCreacion;
    }
    
    public void EstructuraCargos.setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public Date EstructuraCargos.getFechaModificacion() {
        return this.fechaModificacion;
    }
    
    public void EstructuraCargos.setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
    
    public EstadoAuditor EstructuraCargos.getEstado() {
        return this.estado;
    }
    
    public void EstructuraCargos.setEstado(EstadoAuditor estado) {
        this.estado = estado;
    }
    
    public EstructuraOrganizativa EstructuraCargos.getIdEstructura() {
        return this.idEstructura;
    }
    
    public void EstructuraCargos.setIdEstructura(EstructuraOrganizativa idEstructura) {
        this.idEstructura = idEstructura;
    }
    
    public OrganismoEnte EstructuraCargos.getRif() {
        return this.rif;
    }
    
    public void EstructuraCargos.setRif(OrganismoEnte rif) {
        this.rif = rif;
    }
    
}
