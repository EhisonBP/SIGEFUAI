package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect PapelDeTrabajo_Roo_ToString {
    
    public String PapelDeTrabajo.toString() {
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
        sb.append("Creador: ").append(getCreador()).append(", ");
        sb.append("Responsables: ").append(getResponsables() == null ? "null" : getResponsables().size()).append(", ");
        sb.append("ObjetivoAMitigar: ").append(getObjetivoAMitigar()).append(", ");
        sb.append("Type: ").append(getType()).append(", ");
        sb.append("Nivel: ").append(getNivel()).append(", ");
        sb.append("NombreClase: ").append(getNombreClase()).append(", ");
        sb.append("TipoSeccion: ").append(getTipoSeccion()).append(", ");
        sb.append("Contenido: ").append(getContenido());
        sb.append("AccionesPermitidas: ").append(java.util.Arrays.toString(getAccionesPermitidas()));
        return sb.toString();
    }
    
}
