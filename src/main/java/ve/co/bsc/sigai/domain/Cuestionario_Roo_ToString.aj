package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect Cuestionario_Roo_ToString {
    
    public String Cuestionario.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("NombreClase: ").append(getNombreClase()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("Nombre: ").append(getNombre()).append(", ");
        sb.append("Descripcion: ").append(getDescripcion()).append(", ");
        sb.append("Actuacion: ").append(getActuacion()).append(", ");
        sb.append("Correlativo: ").append(getCorrelativo()).append(", ");
        sb.append("Respondido: ").append(getRespondido()).append(", ");
        sb.append("Auditado: ").append(getAuditado()).append(", ");
        sb.append("Unidad: ").append(getUnidad());
        return sb.toString();
    }
    
}
