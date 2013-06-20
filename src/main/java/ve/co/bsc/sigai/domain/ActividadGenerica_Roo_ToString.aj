package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect ActividadGenerica_Roo_ToString {
    
    public String ActividadGenerica.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DescripcionGeneral: ").append(getDescripcionGeneral()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("NombreClase: ").append(getNombreClase()).append(", ");
        sb.append("Descripcion: ").append(getDescripcion()).append(", ");
        sb.append("DescripcionCorta: ").append(getDescripcionCorta());
        return sb.toString();
    }
    
}
