package ve.co.bsc.sigai.domain;

import java.lang.String;
import java.util.Date;
import ve.co.bsc.sigai.domain.ArchivoAdjunto;

privileged aspect HistorialCambios_Roo_JavaBean {
    
    public String HistorialCambios.getAccion() {
        return this.accion;
    }
    
    public void HistorialCambios.setAccion(String accion) {
        this.accion = accion;
    }
    
    public Date HistorialCambios.getFecha() {
        return this.fecha;
    }
    
    public void HistorialCambios.setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public String HistorialCambios.getUsuario() {
        return this.usuario;
    }
    
    public void HistorialCambios.setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    public ArchivoAdjunto HistorialCambios.getArchivoAdjunto() {
        return this.archivoAdjunto;
    }
    
    public void HistorialCambios.setArchivoAdjunto(ArchivoAdjunto archivoAdjunto) {
        this.archivoAdjunto = archivoAdjunto;
    }
    
}
