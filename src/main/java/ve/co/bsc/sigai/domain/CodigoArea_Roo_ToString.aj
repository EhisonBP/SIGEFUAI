package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect CodigoArea_Roo_ToString {
    
    public String CodigoArea.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("Codigo: ").append(getCodigo()).append(", ");
        sb.append("Nombre: ").append(getNombre()).append(", ");
        sb.append("Estado: ").append(getEstado()).append(", ");
        sb.append("Ciudad: ").append(getCiudad()).append(", ");
        sb.append("TipoTelefono: ").append(getTipoTelefono()).append(", ");
        sb.append("FechaCreacion: ").append(getFechaCreacion()).append(", ");
        sb.append("FechaModificacion: ").append(getFechaModificacion());
        return sb.toString();
    }
    
}
