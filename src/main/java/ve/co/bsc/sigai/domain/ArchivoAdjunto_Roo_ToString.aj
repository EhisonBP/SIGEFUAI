package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect ArchivoAdjunto_Roo_ToString {
    
    public String ArchivoAdjunto.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("Nombre: ").append(getNombre()).append(", ");
        sb.append("Descripcion: ").append(getDescripcion()).append(", ");
        sb.append("ActividadActuacion: ").append(getActividadActuacion() == null ? "null" : getActividadActuacion().size()).append(", ");
        sb.append("Type: ").append(getType()).append(", ");
        sb.append("Codigo: ").append(getCodigo()).append(", ");
        sb.append("Filedata: ").append(java.util.Arrays.toString(getFiledata())).append(", ");
        sb.append("Extension: ").append(getExtension()).append(", ");
        sb.append("Filesize: ").append(getFilesize()).append(", ");
        sb.append("LoginUsuario: ").append(getLoginUsuario()).append(", ");
        sb.append("Respuesta: ").append(getRespuesta()).append(", ");
        sb.append("Requerimiento: ").append(getRequerimiento()).append(", ");
        sb.append("Alegato: ").append(getAlegato()).append(", ");
        sb.append("Hallazgo: ").append(getHallazgo()).append(", ");
        sb.append("SeccionDefault: ").append(getSeccionDefault()).append(", ");
        sb.append("AvanceActuacion: ").append(getAvanceActuacion()).append(", ");
        sb.append("Personalizacion: ").append(getPersonalizacion());
        return sb.toString();
    }
    
}
