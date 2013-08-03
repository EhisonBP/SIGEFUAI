package ve.co.bsc.sigai.domain;

import java.lang.String;
import java.util.Set;
import ve.co.bsc.sigai.domain.ActividadActuacion;
import ve.co.bsc.sigai.domain.Actuacion;
import ve.co.bsc.sigai.domain.Auditor;
import ve.co.bsc.sigai.domain.Documento;
import ve.co.bsc.sigai.domain.EstadoActividadActuacion;
import ve.co.bsc.sigai.domain.ObjetivoEspecifico;

privileged aspect ActividadActuacion_Roo_JavaBean {
    
    public Actuacion ActividadActuacion.getCodigoActuacion() {
        return this.codigoActuacion;
    }
    
    public void ActividadActuacion.setCodigoActuacion(Actuacion codigoActuacion) {
        this.codigoActuacion = codigoActuacion;
    }
    
    public String ActividadActuacion.getCodigo() {
        return this.codigo;
    }
    
    public void ActividadActuacion.setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public String ActividadActuacion.getDescripcion() {
        return this.descripcion;
    }
    
    public void ActividadActuacion.setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public EstadoActividadActuacion ActividadActuacion.getEstadoActividadActuacion() {
        return this.estadoActividadActuacion;
    }
    
    public void ActividadActuacion.setEstadoActividadActuacion(EstadoActividadActuacion estadoActividadActuacion) {
        this.estadoActividadActuacion = estadoActividadActuacion;
    }
    
    public Set<Documento> ActividadActuacion.getDocumento() {
        return this.documento;
    }
    
    public void ActividadActuacion.setDocumento(Set<Documento> documento) {
        this.documento = documento;
    }
    
    public ActividadActuacion ActividadActuacion.getActividadActuacion() {
        return this.actividadActuacion;
    }
    
    public void ActividadActuacion.setActividadActuacion(ActividadActuacion actividadActuacion) {
        this.actividadActuacion = actividadActuacion;
    }
    
    public String[] ActividadActuacion.getAccionesPermitidas() {
        return this.accionesPermitidas;
    }
    
    public void ActividadActuacion.setAccionesPermitidas(String[] accionesPermitidas) {
        this.accionesPermitidas = accionesPermitidas;
    }
    
    public Auditor ActividadActuacion.getCreador() {
        return this.creador;
    }
    
    public void ActividadActuacion.setCreador(Auditor creador) {
        this.creador = creador;
    }
    
    public Set<Auditor> ActividadActuacion.getResponsables() {
        return this.responsables;
    }
    
    public void ActividadActuacion.setResponsables(Set<Auditor> responsables) {
        this.responsables = responsables;
    }
    
    public ObjetivoEspecifico ActividadActuacion.getObjetivoAMitigar() {
        return this.objetivoAMitigar;
    }
    
    public void ActividadActuacion.setObjetivoAMitigar(ObjetivoEspecifico objetivoAMitigar) {
        this.objetivoAMitigar = objetivoAMitigar;
    }
    
}
