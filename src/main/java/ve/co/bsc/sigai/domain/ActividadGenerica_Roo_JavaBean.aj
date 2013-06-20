package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect ActividadGenerica_Roo_JavaBean {
    
    public String ActividadGenerica.getDescripcion() {
        return this.descripcion;
    }
    
    public void ActividadGenerica.setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String ActividadGenerica.getDescripcionCorta() {
        return this.descripcionCorta;
    }
    
    public void ActividadGenerica.setDescripcionCorta(String descripcionCorta) {
        this.descripcionCorta = descripcionCorta;
    }
    
}
