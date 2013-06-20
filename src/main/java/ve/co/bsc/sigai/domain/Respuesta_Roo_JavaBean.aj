package ve.co.bsc.sigai.domain;

import java.lang.String;
import java.util.Date;
import ve.co.bsc.sigai.domain.Requerimiento;

privileged aspect Respuesta_Roo_JavaBean {
    
    public String Respuesta.getNumero() {
        return this.numero;
    }
    
    public void Respuesta.setNumero(String numero) {
        this.numero = numero;
    }
    
    public Date Respuesta.getFecha() {
        return this.fecha;
    }
    
    public void Respuesta.setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public Requerimiento Respuesta.getRequerimiento() {
        return this.requerimiento;
    }
    
    public void Respuesta.setRequerimiento(Requerimiento requerimiento) {
        this.requerimiento = requerimiento;
    }
    
}
