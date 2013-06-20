package ve.co.bsc.sigai.domain;

import java.lang.String;
import ve.co.bsc.sigai.domain.Biblioteca;
import ve.co.bsc.sigai.domain.SeccionDefault;
import ve.co.bsc.sigai.domain.TipoSeccion;

privileged aspect SeccionDefault_Roo_JavaBean {
    
    public String SeccionDefault.getCodigo() {
        return this.codigo;
    }
    
    public void SeccionDefault.setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public String SeccionDefault.getDescripcion() {
        return this.descripcion;
    }
    
    public void SeccionDefault.setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public SeccionDefault SeccionDefault.getSeccionDefault() {
        return this.seccionDefault;
    }
    
    public void SeccionDefault.setSeccionDefault(SeccionDefault seccionDefault) {
        this.seccionDefault = seccionDefault;
    }
    
    public Biblioteca SeccionDefault.getBiblioteca() {
        return this.biblioteca;
    }
    
    public void SeccionDefault.setBiblioteca(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }
    
    public TipoSeccion SeccionDefault.getTipoSeccion() {
        return this.tipoSeccion;
    }
    
    public void SeccionDefault.setTipoSeccion(TipoSeccion tipoSeccion) {
        this.tipoSeccion = tipoSeccion;
    }
    
}
