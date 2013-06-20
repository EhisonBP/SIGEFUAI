package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect TipoOrganismo_Roo_JavaBean {
    
    public String TipoOrganismo.getNombre() {
        return this.nombre;
    }
    
    public void TipoOrganismo.setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
