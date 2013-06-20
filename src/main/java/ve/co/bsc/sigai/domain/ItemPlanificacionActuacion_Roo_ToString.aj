package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect ItemPlanificacionActuacion_Roo_ToString {
    
    public String ItemPlanificacionActuacion.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("NombreClase: ").append(getNombreClase()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("InicioEstimado: ").append(getInicioEstimado()).append(", ");
        sb.append("FinEstimado: ").append(getFinEstimado()).append(", ");
        sb.append("FechaInicioEstimada: ").append(getFechaInicioEstimada()).append(", ");
        sb.append("FechaFinEstimada: ").append(getFechaFinEstimada()).append(", ");
        sb.append("InicioReal: ").append(getInicioReal()).append(", ");
        sb.append("FinReal: ").append(getFinReal()).append(", ");
        sb.append("PlanAnual: ").append(getPlanAnual()).append(", ");
        sb.append("Actuacion: ").append(getActuacion()).append(", ");
        sb.append("OtraActividad: ").append(getOtraActividad());
        return sb.toString();
    }
    
}
