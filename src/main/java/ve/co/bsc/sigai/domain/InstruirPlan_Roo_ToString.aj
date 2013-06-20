package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect InstruirPlan_Roo_ToString {
    
    public String InstruirPlan.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Url: ").append(getUrl()).append(", ");
        sb.append("EstadoInstruccion: ").append(getEstadoInstruccion()).append(", ");
        sb.append("NombreClase: ").append(getNombreClase()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("Ano: ").append(getAno()).append(", ");
        sb.append("FechaInicio: ").append(getFechaInicio()).append(", ");
        sb.append("FechaFin: ").append(getFechaFin()).append(", ");
        sb.append("Instruccion: ").append(getInstruccion()).append(", ");
        sb.append("Estatus: ").append(getEstatus());
        return sb.toString();
    }
    
}
