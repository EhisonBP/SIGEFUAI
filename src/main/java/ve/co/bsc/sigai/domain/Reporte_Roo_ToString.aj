package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect Reporte_Roo_ToString {
    
    public String Reporte.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("Nombre: ").append(getNombre()).append(", ");
        sb.append("Descripcion: ").append(getDescripcion()).append(", ");
        sb.append("Filedata: ").append(java.util.Arrays.toString(getFiledata())).append(", ");
        sb.append("Extension: ").append(getExtension()).append(", ");
        sb.append("Reporte: ").append(getReporte()).append(", ");
        sb.append("Clave: ").append(getClave());
        return sb.toString();
    }
    
}
