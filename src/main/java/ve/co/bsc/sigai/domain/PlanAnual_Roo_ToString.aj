package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect PlanAnual_Roo_ToString {
    
    public String PlanAnual.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Url: ").append(getUrl()).append(", ");
        sb.append("DescripcionSimple: ").append(getDescripcionSimple()).append(", ");
        sb.append("NombreClase: ").append(getNombreClase()).append(", ");
        sb.append("EstadoSimple: ").append(getEstadoSimple()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("EstadoPlan: ").append(getEstadoPlan()).append(", ");
        sb.append("Descripcion: ").append(getDescripcion()).append(", ");
        sb.append("AnoFiscal: ").append(getAnoFiscal()).append(", ");
        sb.append("AccionesPermitidas: ").append(java.util.Arrays.toString(getAccionesPermitidas())).append(", ");
        sb.append("Rif: ").append(getRif());
        return sb.toString();
    }
    
}
