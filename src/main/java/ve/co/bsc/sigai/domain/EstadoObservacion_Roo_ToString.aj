package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect EstadoObservacion_Roo_ToString {
    
    public String EstadoObservacion.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("NombreClase: ").append(getNombreClase()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("Nombre: ").append(getNombre()).append(", ");
        sb.append("Descripcion: ").append(getDescripcion()).append(", ");
        sb.append("DescripcionCorta: ").append(getDescripcionCorta());
        return sb.toString();
    }
    
}
