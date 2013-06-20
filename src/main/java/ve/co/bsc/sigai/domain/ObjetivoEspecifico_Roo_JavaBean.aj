package ve.co.bsc.sigai.domain;

import java.lang.Integer;
import java.lang.String;
import ve.co.bsc.sigai.domain.Actuacion;

privileged aspect ObjetivoEspecifico_Roo_JavaBean {
    
    public String ObjetivoEspecifico.getDescripcion() {
        return this.descripcion;
    }
    
    public void ObjetivoEspecifico.setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public Actuacion ObjetivoEspecifico.getActuacion() {
        return this.actuacion;
    }
    
    public void ObjetivoEspecifico.setActuacion(Actuacion actuacion) {
        this.actuacion = actuacion;
    }
    
    public Integer ObjetivoEspecifico.getReferencia() {
        return this.referencia;
    }
    
    public void ObjetivoEspecifico.setReferencia(Integer referencia) {
        this.referencia = referencia;
    }
    
}
