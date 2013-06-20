package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect FrecuenciaOcurrenciaRiesgo_Roo_JavaBean {
    
    public String FrecuenciaOcurrenciaRiesgo.getNombre() {
        return this.nombre;
    }
    
    public void FrecuenciaOcurrenciaRiesgo.setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
