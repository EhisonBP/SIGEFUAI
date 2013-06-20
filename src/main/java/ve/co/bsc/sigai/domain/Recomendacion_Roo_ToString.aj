package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect Recomendacion_Roo_ToString {
    
    public String Recomendacion.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("NombreClase: ").append(getNombreClase()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("Descripcion: ").append(getDescripcion()).append(", ");
        sb.append("Observacion: ").append(getObservacion());
        return sb.toString();
    }
    
}
