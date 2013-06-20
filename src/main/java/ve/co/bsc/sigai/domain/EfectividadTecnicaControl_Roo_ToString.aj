package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect EfectividadTecnicaControl_Roo_ToString {
    
    public String EfectividadTecnicaControl.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("Nombre: ").append(getNombre());
        return sb.toString();
    }
    
}
