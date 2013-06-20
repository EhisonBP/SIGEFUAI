package ve.co.bsc.sigai.domain;

import java.lang.String;
import java.util.Date;
import ve.co.bsc.sigai.domain.TipoEntradaBitacora;

privileged aspect EntradaBitacora_Roo_JavaBean {
    
    public String EntradaBitacora.getDescripcion() {
        return this.descripcion;
    }
    
    public void EntradaBitacora.setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public Date EntradaBitacora.getTimeStamp() {
        return this.timeStamp;
    }
    
    public void EntradaBitacora.setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
    
    public String EntradaBitacora.getUsuario() {
        return this.usuario;
    }
    
    public void EntradaBitacora.setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    public String EntradaBitacora.getIp() {
        return this.ip;
    }
    
    public void EntradaBitacora.setIp(String ip) {
        this.ip = ip;
    }
    
    public TipoEntradaBitacora EntradaBitacora.getTipo() {
        return this.tipo;
    }
    
    public void EntradaBitacora.setTipo(TipoEntradaBitacora tipo) {
        this.tipo = tipo;
    }
    
}
