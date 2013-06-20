package ve.co.bsc.sigai.domain;

import java.lang.Integer;
import java.lang.String;
import java.util.Date;
import ve.co.bsc.sigai.domain.Ciudad;
import ve.co.bsc.sigai.domain.Estado;

privileged aspect CodigoArea_Roo_JavaBean {
    
    public Integer CodigoArea.getCodigo() {
        return this.Codigo;
    }
    
    public void CodigoArea.setCodigo(Integer Codigo) {
        this.Codigo = Codigo;
    }
    
    public String CodigoArea.getNombre() {
        return this.Nombre;
    }
    
    public void CodigoArea.setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
    
    public Estado CodigoArea.getEstado() {
        return this.estado;
    }
    
    public void CodigoArea.setEstado(Estado estado) {
        this.estado = estado;
    }
    
    public Ciudad CodigoArea.getCiudad() {
        return this.ciudad;
    }
    
    public void CodigoArea.setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }
    
    public String CodigoArea.getTipoTelefono() {
        return this.TipoTelefono;
    }
    
    public void CodigoArea.setTipoTelefono(String TipoTelefono) {
        this.TipoTelefono = TipoTelefono;
    }
    
    public Date CodigoArea.getFechaCreacion() {
        return this.fechaCreacion;
    }
    
    public void CodigoArea.setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public Date CodigoArea.getFechaModificacion() {
        return this.fechaModificacion;
    }
    
    public void CodigoArea.setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
    
}
