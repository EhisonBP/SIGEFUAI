package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect Respuesta_Roo_ToString {
    
    public String Respuesta.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("NombreClase: ").append(getNombreClase()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("Numero: ").append(getNumero()).append(", ");
        sb.append("Fecha: ").append(getFecha()).append(", ");
        sb.append("Requerimiento: ").append(getRequerimiento());
        return sb.toString();
    }
    
}
