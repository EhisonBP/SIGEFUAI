package ve.co.bsc.sigai.domain;

import java.lang.Integer;
import java.lang.String;
import java.util.Set;
import ve.co.bsc.sigai.domain.Requerimiento;

privileged aspect Prueba_Roo_JavaBean {
    
    public String Prueba.getObjetivo() {
        return this.objetivo;
    }
    
    public void Prueba.setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }
    
    public String Prueba.getProcedimiento() {
        return this.procedimiento;
    }
    
    public void Prueba.setProcedimiento(String procedimiento) {
        this.procedimiento = procedimiento;
    }
    
    public String Prueba.getResultado() {
        return this.resultado;
    }
    
    public void Prueba.setResultado(String resultado) {
        this.resultado = resultado;
    }
    
    public Integer Prueba.getPorcentajeCompletitud() {
        return this.porcentajeCompletitud;
    }
    
    public void Prueba.setPorcentajeCompletitud(Integer porcentajeCompletitud) {
        this.porcentajeCompletitud = porcentajeCompletitud;
    }
    
    public Set<Requerimiento> Prueba.getRequerimientos() {
        return this.requerimientos;
    }
    
    public void Prueba.setRequerimientos(Set<Requerimiento> requerimientos) {
        this.requerimientos = requerimientos;
    }
    
}
