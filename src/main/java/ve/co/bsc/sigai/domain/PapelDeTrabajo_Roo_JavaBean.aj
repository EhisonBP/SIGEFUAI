package ve.co.bsc.sigai.domain;

import java.lang.String;
import ve.co.bsc.sigai.domain.TipoSeccion;

privileged aspect PapelDeTrabajo_Roo_JavaBean {
    
    public TipoSeccion PapelDeTrabajo.getTipoSeccion() {
        return this.tipoSeccion;
    }
    
    public void PapelDeTrabajo.setTipoSeccion(TipoSeccion tipoSeccion) {
        this.tipoSeccion = tipoSeccion;
    }
    
    public String PapelDeTrabajo.getContenido() {
        return this.contenido;
    }
    
    public void PapelDeTrabajo.setContenido(String contenido) {
        this.contenido = contenido;
    }
    
    public String[] PapelDeTrabajo.getAccionesPermitidas() {
        return this.accionesPermitidas;
    }
    
    public void PapelDeTrabajo.setAccionesPermitidas(String[] accionesPermitidas) {
        this.accionesPermitidas = accionesPermitidas;
    }
    
}
