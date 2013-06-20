package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect FrecuenciaOcurrenciaRiesgo_Roo_ToString {
    
    public String FrecuenciaOcurrenciaRiesgo.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("Nombre: ").append(getNombre());
        return sb.toString();
    }
    
}
