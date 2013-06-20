package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect EfectividadTecnicaControl_Roo_JavaBean {
    
    public String EfectividadTecnicaControl.getNombre() {
        return this.nombre;
    }
    
    public void EfectividadTecnicaControl.setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
