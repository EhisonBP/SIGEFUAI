package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect TipoSeccion_Roo_ToString {
    
    public String TipoSeccion.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("Codigo: ").append(getCodigo()).append(", ");
        sb.append("Vista: ").append(getVista());
        return sb.toString();
    }
    
}
