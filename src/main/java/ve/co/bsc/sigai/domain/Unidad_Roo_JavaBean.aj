package ve.co.bsc.sigai.domain;

import java.lang.String;
import java.util.Set;
import ve.co.bsc.sigai.domain.Auditado;
import ve.co.bsc.sigai.domain.OrganismoEnte;
import ve.co.bsc.sigai.domain.PlanDeAccion;
import ve.co.bsc.sigai.domain.TipoUnidad;
import ve.co.bsc.sigai.domain.Unidad;

privileged aspect Unidad_Roo_JavaBean {
    
    public String Unidad.getNombre() {
        return this.nombre;
    }
    
    public void Unidad.setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public OrganismoEnte Unidad.getRif() {
        return this.rif;
    }
    
    public void Unidad.setRif(OrganismoEnte rif) {
        this.rif = rif;
    }
    
    public String Unidad.getDescripcion() {
        return this.descripcion;
    }
    
    public void Unidad.setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public Unidad Unidad.getAuditado() {
        return this.auditado;
    }
    
    public void Unidad.setAuditado(Unidad auditado) {
        this.auditado = auditado;
    }
    
    public TipoUnidad Unidad.getTipoUnidad() {
        return this.tipoUnidad;
    }
    
    public void Unidad.setTipoUnidad(TipoUnidad tipoUnidad) {
        this.tipoUnidad = tipoUnidad;
    }
    
    public Set<PlanDeAccion> Unidad.getPlanesDeAccion() {
        return this.planesDeAccion;
    }
    
    public void Unidad.setPlanesDeAccion(Set<PlanDeAccion> planesDeAccion) {
        this.planesDeAccion = planesDeAccion;
    }
    
    public String Unidad.getMision() {
        return this.mision;
    }
    
    public void Unidad.setMision(String mision) {
        this.mision = mision;
    }
    
    public String Unidad.getVision() {
        return this.vision;
    }
    
    public void Unidad.setVision(String vision) {
        this.vision = vision;
    }
    
    public String Unidad.getFunciones() {
        return this.funciones;
    }
    
    public void Unidad.setFunciones(String funciones) {
        this.funciones = funciones;
    }
    
    public Auditado Unidad.getResponsable() {
        return this.responsable;
    }
    
    public void Unidad.setResponsable(Auditado responsable) {
        this.responsable = responsable;
    }
    
}
