package ve.co.bsc.sigai.domain;

import java.lang.String;
import ve.co.bsc.sigai.domain.Observacion;

privileged aspect Recomendacion_Roo_JavaBean {
    
    public String Recomendacion.getDescripcion() {
        return this.descripcion;
    }
    
    public void Recomendacion.setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public Observacion Recomendacion.getObservacion() {
        return this.observacion;
    }
    
    public void Recomendacion.setObservacion(Observacion observacion) {
        this.observacion = observacion;
    }
    
}
