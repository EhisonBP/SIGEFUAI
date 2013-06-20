package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect Prueba_Roo_ToString {
    
    public String Prueba.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CodigoCompleto: ").append(getCodigoCompleto()).append(", ");
        sb.append("CodigoPadres: ").append(getCodigoPadres()).append(", ");
        sb.append("NivelIndentacion: ").append(getNivelIndentacion()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("CodigoActuacion: ").append(getCodigoActuacion()).append(", ");
        sb.append("Codigo: ").append(getCodigo()).append(", ");
        sb.append("Descripcion: ").append(getDescripcion()).append(", ");
        sb.append("EstadoActividadActuacion: ").append(getEstadoActividadActuacion()).append(", ");
        sb.append("Documento: ").append(getDocumento() == null ? "null" : getDocumento().size()).append(", ");
        sb.append("ActividadActuacion: ").append(getActividadActuacion()).append(", ");
        sb.append("AccionesPermitidas: ").append(java.util.Arrays.toString(getAccionesPermitidas())).append(", ");
        sb.append("Responsables: ").append(getResponsables() == null ? "null" : getResponsables().size()).append(", ");
        sb.append("ObjetivoAMitigar: ").append(getObjetivoAMitigar()).append(", ");
        sb.append("Type: ").append(getType()).append(", ");
        sb.append("NombreClase: ").append(getNombreClase()).append(", ");
        sb.append("Objetivo: ").append(getObjetivo()).append(", ");
        sb.append("Procedimiento: ").append(getProcedimiento()).append(", ");
        sb.append("Resultado: ").append(getResultado()).append(", ");
        sb.append("PorcentajeCompletitud: ").append(getPorcentajeCompletitud()).append(", ");
        sb.append("Requerimientos: ").append(getRequerimientos() == null ? "null" : getRequerimientos().size());
        return sb.toString();
    }
    
}
