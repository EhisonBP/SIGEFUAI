package ve.co.bsc.sigai.domain;

import java.lang.String;
import java.util.Date;

privileged aspect ActividadGeneral_Roo_JavaBean {
    
    public String ActividadGeneral.getResultadosEsperados() {
        return this.resultadosEsperados;
    }
    
    public void ActividadGeneral.setResultadosEsperados(String resultadosEsperados) {
        this.resultadosEsperados = resultadosEsperados;
    }
    
    public String ActividadGeneral.getHitosDeControl() {
        return this.hitosDeControl;
    }
    
    public void ActividadGeneral.setHitosDeControl(String hitosDeControl) {
        this.hitosDeControl = hitosDeControl;
    }
    
    public Date ActividadGeneral.getFechaEstimadaDeInicio() {
        return this.fechaEstimadaDeInicio;
    }
    
    public void ActividadGeneral.setFechaEstimadaDeInicio(Date fechaEstimadaDeInicio) {
        this.fechaEstimadaDeInicio = fechaEstimadaDeInicio;
    }
    
    public Date ActividadGeneral.getFechaEstimadaDeFin() {
        return this.fechaEstimadaDeFin;
    }
    
    public void ActividadGeneral.setFechaEstimadaDeFin(Date fechaEstimadaDeFin) {
        this.fechaEstimadaDeFin = fechaEstimadaDeFin;
    }
    
}
