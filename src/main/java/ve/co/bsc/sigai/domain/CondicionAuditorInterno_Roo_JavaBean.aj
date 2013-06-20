package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect CondicionAuditorInterno_Roo_JavaBean {
    
    public String CondicionAuditorInterno.getNombre() {
        return this.nombre;
    }
    
    public void CondicionAuditorInterno.setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
