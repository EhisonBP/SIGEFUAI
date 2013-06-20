package ve.co.bsc.sigai.domain;

import java.lang.Integer;
import java.lang.String;
import java.util.Date;
import ve.co.bsc.sigai.domain.OrganismoEnte;
import ve.co.bsc.sigai.domain.Unidad;
import ve.co.bsc.sigai.domain.UnidadDeMedida;

privileged aspect OtraActividad_Roo_JavaBean {
    
    public Integer OtraActividad.getNumero() {
        return this.numero;
    }
    
    public void OtraActividad.setNumero(Integer numero) {
        this.numero = numero;
    }
    
    public String OtraActividad.getObjetivo() {
        return this.objetivo;
    }
    
    public void OtraActividad.setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }
    
    public Date OtraActividad.getFechaInicio() {
        return this.fechaInicio;
    }
    
    public void OtraActividad.setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    
    public Date OtraActividad.getFechaFin() {
        return this.fechaFin;
    }
    
    public void OtraActividad.setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
    
    public Unidad OtraActividad.getUnidad() {
        return this.unidad;
    }
    
    public void OtraActividad.setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }
    
    public UnidadDeMedida OtraActividad.getUnidadDeMedida() {
        return this.unidadDeMedida;
    }
    
    public void OtraActividad.setUnidadDeMedida(UnidadDeMedida unidadDeMedida) {
        this.unidadDeMedida = unidadDeMedida;
    }
    
    public String OtraActividad.getDescripcionCorta() {
        return this.descripcionCorta;
    }
    
    public void OtraActividad.setDescripcionCorta(String descripcionCorta) {
        this.descripcionCorta = descripcionCorta;
    }
    
    public String[] OtraActividad.getAccionesPermitidas() {
        return this.accionesPermitidas;
    }
    
    public void OtraActividad.setAccionesPermitidas(String[] accionesPermitidas) {
        this.accionesPermitidas = accionesPermitidas;
    }
    
    public OrganismoEnte OtraActividad.getRif() {
        return this.rif;
    }
    
    public void OtraActividad.setRif(OrganismoEnte rif) {
        this.rif = rif;
    }
    
}
