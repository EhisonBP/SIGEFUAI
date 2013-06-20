package ve.co.bsc.sigai.domain;

import java.lang.String;
import ve.co.bsc.sigai.domain.EstadoObservacion;
import ve.co.bsc.sigai.domain.Prueba;

privileged aspect Observacion_Roo_JavaBean {
    
    public String Observacion.getCriterio() {
        return this.criterio;
    }
    
    public void Observacion.setCriterio(String criterio) {
        this.criterio = criterio;
    }
    
    public String Observacion.getCondicion() {
        return this.condicion;
    }
    
    public void Observacion.setCondicion(String condicion) {
        this.condicion = condicion;
    }
    
    public String Observacion.getCausa() {
        return this.causa;
    }
    
    public void Observacion.setCausa(String causa) {
        this.causa = causa;
    }
    
    public String Observacion.getEfecto() {
        return this.efecto;
    }
    
    public void Observacion.setEfecto(String efecto) {
        this.efecto = efecto;
    }
    
    public Prueba Observacion.getPrueba() {
        return this.prueba;
    }
    
    public void Observacion.setPrueba(Prueba prueba) {
        this.prueba = prueba;
    }
    
    public EstadoObservacion Observacion.getEstadoObservacion() {
        return this.estadoObservacion;
    }
    
    public void Observacion.setEstadoObservacion(EstadoObservacion estadoObservacion) {
        this.estadoObservacion = estadoObservacion;
    }
    
    public String Observacion.getReferencia() {
        return this.referencia;
    }
    
    public void Observacion.setReferencia(String referencia) {
        this.referencia = referencia;
    }
    
    public String Observacion.getDescripcionCorta() {
        return this.descripcionCorta;
    }
    
    public void Observacion.setDescripcionCorta(String descripcionCorta) {
        this.descripcionCorta = descripcionCorta;
    }
    
    public String Observacion.getRedaccion() {
        return this.redaccion;
    }
    
    public void Observacion.setRedaccion(String redaccion) {
        this.redaccion = redaccion;
    }
    
    public String Observacion.getComentarios() {
        return this.comentarios;
    }
    
    public void Observacion.setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
    
    public String Observacion.getConclusion() {
        return this.conclusion;
    }
    
    public void Observacion.setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }
    
    public String[] Observacion.getAccionesPermitidas() {
        return this.accionesPermitidas;
    }
    
    public void Observacion.setAccionesPermitidas(String[] accionesPermitidas) {
        this.accionesPermitidas = accionesPermitidas;
    }
    
}
