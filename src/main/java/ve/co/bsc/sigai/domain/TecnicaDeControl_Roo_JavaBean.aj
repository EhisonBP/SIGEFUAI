package ve.co.bsc.sigai.domain;

import java.lang.Integer;
import java.lang.String;
import java.util.Set;
import ve.co.bsc.sigai.domain.EfectividadTecnicaControl;
import ve.co.bsc.sigai.domain.Prueba;
import ve.co.bsc.sigai.domain.Riesgo;

privileged aspect TecnicaDeControl_Roo_JavaBean {
    
    public String TecnicaDeControl.getDescripcion() {
        return this.descripcion;
    }
    
    public void TecnicaDeControl.setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public Riesgo TecnicaDeControl.getRiesgo() {
        return this.riesgo;
    }
    
    public void TecnicaDeControl.setRiesgo(Riesgo riesgo) {
        this.riesgo = riesgo;
    }
    
    public Set<Prueba> TecnicaDeControl.getPrueba() {
        return this.prueba;
    }
    
    public void TecnicaDeControl.setPrueba(Set<Prueba> prueba) {
        this.prueba = prueba;
    }
    
    public Integer TecnicaDeControl.getReferencia() {
        return this.referencia;
    }
    
    public void TecnicaDeControl.setReferencia(Integer referencia) {
        this.referencia = referencia;
    }
    
    public EfectividadTecnicaControl TecnicaDeControl.getEfectividadTecnicaControl() {
        return this.efectividadTecnicaControl;
    }
    
    public void TecnicaDeControl.setEfectividadTecnicaControl(EfectividadTecnicaControl efectividadTecnicaControl) {
        this.efectividadTecnicaControl = efectividadTecnicaControl;
    }
    
}
