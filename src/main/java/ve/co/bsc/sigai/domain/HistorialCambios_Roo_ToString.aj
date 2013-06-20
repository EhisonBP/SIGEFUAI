package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect HistorialCambios_Roo_ToString {
    
    public String HistorialCambios.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("Accion: ").append(getAccion()).append(", ");
        sb.append("Fecha: ").append(getFecha()).append(", ");
        sb.append("Usuario: ").append(getUsuario()).append(", ");
        sb.append("ArchivoAdjunto: ").append(getArchivoAdjunto());
        return sb.toString();
    }
    
}
