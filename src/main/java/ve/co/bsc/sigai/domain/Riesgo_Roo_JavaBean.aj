package ve.co.bsc.sigai.domain;

import java.lang.Boolean;
import java.lang.Integer;
import java.lang.String;
import ve.co.bsc.sigai.domain.ClasificacionRiesgo;
import ve.co.bsc.sigai.domain.FrecuenciaOcurrenciaRiesgo;
import ve.co.bsc.sigai.domain.Proceso;

privileged aspect Riesgo_Roo_JavaBean {
    
    public String Riesgo.getDescripcion() {
        return this.descripcion;
    }
    
    public void Riesgo.setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public Proceso Riesgo.getProceso() {
        return this.proceso;
    }
    
    public void Riesgo.setProceso(Proceso proceso) {
        this.proceso = proceso;
    }
    
    public Boolean Riesgo.getInactivo() {
        return this.inactivo;
    }
    
    public void Riesgo.setInactivo(Boolean inactivo) {
        this.inactivo = inactivo;
    }
    
    public Integer Riesgo.getReferencia() {
        return this.referencia;
    }
    
    public void Riesgo.setReferencia(Integer referencia) {
        this.referencia = referencia;
    }
    
    public ClasificacionRiesgo Riesgo.getClasificacionRiesgo() {
        return this.clasificacionRiesgo;
    }
    
    public void Riesgo.setClasificacionRiesgo(ClasificacionRiesgo clasificacionRiesgo) {
        this.clasificacionRiesgo = clasificacionRiesgo;
    }
    
    public FrecuenciaOcurrenciaRiesgo Riesgo.getFrecuenciaOcurrenciaRiesgo() {
        return this.frecuenciaOcurrenciaRiesgo;
    }
    
    public void Riesgo.setFrecuenciaOcurrenciaRiesgo(FrecuenciaOcurrenciaRiesgo frecuenciaOcurrenciaRiesgo) {
        this.frecuenciaOcurrenciaRiesgo = frecuenciaOcurrenciaRiesgo;
    }
    
}
