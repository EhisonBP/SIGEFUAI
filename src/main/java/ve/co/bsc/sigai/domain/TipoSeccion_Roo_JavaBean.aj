package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect TipoSeccion_Roo_JavaBean {
    
    public String TipoSeccion.getCodigo() {
        return this.codigo;
    }
    
    public void TipoSeccion.setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public String TipoSeccion.getVista() {
        return this.vista;
    }
    
    public void TipoSeccion.setVista(String vista) {
        this.vista = vista;
    }
    
}
