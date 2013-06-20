package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect Documento_Roo_ToString {
    
    public String Documento.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("Nombre: ").append(getNombre()).append(", ");
        sb.append("Descripcion: ").append(getDescripcion()).append(", ");
        sb.append("ActividadActuacion: ").append(getActividadActuacion() == null ? "null" : getActividadActuacion().size());
        return sb.toString();
    }
    
}
