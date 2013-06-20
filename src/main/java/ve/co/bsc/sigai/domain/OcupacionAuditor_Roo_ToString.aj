package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect OcupacionAuditor_Roo_ToString {
    
    public String OcupacionAuditor.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("NombreClase: ").append(getNombreClase()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("Horas: ").append(getHoras()).append(", ");
        sb.append("Inicio: ").append(getInicio()).append(", ");
        sb.append("Fin: ").append(getFin()).append(", ");
        sb.append("CargoAuditor: ").append(getCargoAuditor()).append(", ");
        sb.append("Auditor: ").append(getAuditor()).append(", ");
        sb.append("PlanAnual: ").append(getPlanAnual()).append(", ");
        sb.append("ActividadAuditor: ").append(getActividadAuditor()).append(", ");
        sb.append("Observaciones: ").append(getObservaciones()).append(", ");
        sb.append("PorcentajeCompletado: ").append(getPorcentajeCompletado()).append(", ");
        sb.append("UnidadDeMedida: ").append(getUnidadDeMedida());
        return sb.toString();
    }
    
}
