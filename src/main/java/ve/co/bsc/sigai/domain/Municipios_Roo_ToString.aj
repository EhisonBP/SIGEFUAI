package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect Municipios_Roo_ToString {
    
    public String Municipios.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("Nombre: ").append(getNombre()).append(", ");
        sb.append("Id_ciudad: ").append(getId_ciudad()).append(", ");
        sb.append("Descripcion: ").append(getDescripcion()).append(", ");
        sb.append("Estatus: ").append(getEstatus());
        return sb.toString();
    }
    
}
