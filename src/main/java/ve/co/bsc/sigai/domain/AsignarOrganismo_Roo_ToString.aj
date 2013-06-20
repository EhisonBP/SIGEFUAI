package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect AsignarOrganismo_Roo_ToString {
    
    public String AsignarOrganismo.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("Analista: ").append(getAnalista()).append(", ");
        sb.append("Organismo: ").append(getOrganismo() == null ? "null" : getOrganismo().size());
        return sb.toString();
    }
    
}
