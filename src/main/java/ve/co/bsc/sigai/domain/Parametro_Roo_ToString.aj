package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect Parametro_Roo_ToString {
    
    public String Parametro.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("Parametro: ").append(getParametro()).append(", ");
        sb.append("TipoParametro: ").append(getTipoParametro()).append(", ");
        sb.append("Reporte: ").append(getReporte());
        return sb.toString();
    }
    
}
