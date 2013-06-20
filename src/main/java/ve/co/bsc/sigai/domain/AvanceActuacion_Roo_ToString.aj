package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect AvanceActuacion_Roo_ToString {
    
    public String AvanceActuacion.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("NombreClase: ").append(getNombreClase()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("Justificacion: ").append(getJustificacion()).append(", ");
        sb.append("CodigoActividad: ").append(getCodigoActividad()).append(", ");
        sb.append("FechaInicioEstimada: ").append(getFechaInicioEstimada()).append(", ");
        sb.append("FechaFinEstimada: ").append(getFechaFinEstimada()).append(", ");
        sb.append("FechaInicioReal: ").append(getFechaInicioReal()).append(", ");
        sb.append("FechaFinReal: ").append(getFechaFinReal());
        return sb.toString();
    }
    
}
