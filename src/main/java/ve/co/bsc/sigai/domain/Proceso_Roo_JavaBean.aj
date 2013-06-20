package ve.co.bsc.sigai.domain;

import java.lang.String;
import java.util.Set;
import ve.co.bsc.sigai.domain.Proceso;
import ve.co.bsc.sigai.domain.Unidad;

privileged aspect Proceso_Roo_JavaBean {
    
    public String Proceso.getDescripcion() {
        return this.descripcion;
    }
    
    public void Proceso.setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public Proceso Proceso.getProceso() {
        return this.proceso;
    }
    
    public void Proceso.setProceso(Proceso proceso) {
        this.proceso = proceso;
    }
    
    public Set<Unidad> Proceso.getUnidades() {
        return this.unidades;
    }
    
    public void Proceso.setUnidades(Set<Unidad> unidades) {
        this.unidades = unidades;
    }
    
}
