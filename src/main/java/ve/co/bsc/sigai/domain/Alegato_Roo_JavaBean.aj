package ve.co.bsc.sigai.domain;

import java.lang.String;
import ve.co.bsc.sigai.domain.Observacion;

privileged aspect Alegato_Roo_JavaBean {
    
    public String Alegato.getDescripcion() {
        return this.descripcion;
    }
    
    public void Alegato.setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public Observacion Alegato.getObservacion() {
        return this.observacion;
    }
    
    public void Alegato.setObservacion(Observacion observacion) {
        this.observacion = observacion;
    }
    
}
