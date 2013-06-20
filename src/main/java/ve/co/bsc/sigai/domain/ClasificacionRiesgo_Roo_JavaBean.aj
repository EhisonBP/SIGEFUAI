package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect ClasificacionRiesgo_Roo_JavaBean {
    
    public String ClasificacionRiesgo.getNombre() {
        return this.nombre;
    }
    
    public void ClasificacionRiesgo.setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
