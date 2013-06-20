package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect ActividadAuditor_Roo_ToString {
    
    public String ActividadAuditor.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DescripcionGeneral: ").append(getDescripcionGeneral()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Version: ").append(getVersion());
        return sb.toString();
    }
    
}
