package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect TipoEntradaBitacora_Roo_JavaBean {
    
    public String TipoEntradaBitacora.getClase() {
        return this.clase;
    }
    
    public void TipoEntradaBitacora.setClase(String clase) {
        this.clase = clase;
    }
    
}
