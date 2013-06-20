package ve.co.bsc.sigai.domain;

import java.lang.Long;
import java.lang.String;
import ve.co.bsc.sigai.domain.Alegato;
import ve.co.bsc.sigai.domain.AvanceActuacion;
import ve.co.bsc.sigai.domain.Observacion;
import ve.co.bsc.sigai.domain.Personalizacion;
import ve.co.bsc.sigai.domain.Requerimiento;
import ve.co.bsc.sigai.domain.Respuesta;
import ve.co.bsc.sigai.domain.SeccionDefault;

privileged aspect ArchivoAdjunto_Roo_JavaBean {
    
    public String ArchivoAdjunto.getCodigo() {
        return this.codigo;
    }
    
    public void ArchivoAdjunto.setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public byte[] ArchivoAdjunto.getFiledata() {
        return this.filedata;
    }
    
    public void ArchivoAdjunto.setFiledata(byte[] filedata) {
        this.filedata = filedata;
    }
    
    public String ArchivoAdjunto.getExtension() {
        return this.extension;
    }
    
    public void ArchivoAdjunto.setExtension(String extension) {
        this.extension = extension;
    }
    
    public Long ArchivoAdjunto.getFilesize() {
        return this.filesize;
    }
    
    public void ArchivoAdjunto.setFilesize(Long filesize) {
        this.filesize = filesize;
    }
    
    public String ArchivoAdjunto.getLoginUsuario() {
        return this.loginUsuario;
    }
    
    public void ArchivoAdjunto.setLoginUsuario(String loginUsuario) {
        this.loginUsuario = loginUsuario;
    }
    
    public Respuesta ArchivoAdjunto.getRespuesta() {
        return this.respuesta;
    }
    
    public void ArchivoAdjunto.setRespuesta(Respuesta respuesta) {
        this.respuesta = respuesta;
    }
    
    public Requerimiento ArchivoAdjunto.getRequerimiento() {
        return this.requerimiento;
    }
    
    public void ArchivoAdjunto.setRequerimiento(Requerimiento requerimiento) {
        this.requerimiento = requerimiento;
    }
    
    public Alegato ArchivoAdjunto.getAlegato() {
        return this.alegato;
    }
    
    public void ArchivoAdjunto.setAlegato(Alegato alegato) {
        this.alegato = alegato;
    }
    
    public Observacion ArchivoAdjunto.getHallazgo() {
        return this.hallazgo;
    }
    
    public void ArchivoAdjunto.setHallazgo(Observacion hallazgo) {
        this.hallazgo = hallazgo;
    }
    
    public SeccionDefault ArchivoAdjunto.getSeccionDefault() {
        return this.seccionDefault;
    }
    
    public void ArchivoAdjunto.setSeccionDefault(SeccionDefault seccionDefault) {
        this.seccionDefault = seccionDefault;
    }
    
    public AvanceActuacion ArchivoAdjunto.getAvanceActuacion() {
        return this.avanceActuacion;
    }
    
    public void ArchivoAdjunto.setAvanceActuacion(AvanceActuacion avanceActuacion) {
        this.avanceActuacion = avanceActuacion;
    }
    
    public Personalizacion ArchivoAdjunto.getPersonalizacion() {
        return this.personalizacion;
    }
    
    public void ArchivoAdjunto.setPersonalizacion(Personalizacion personalizacion) {
        this.personalizacion = personalizacion;
    }
    
}
