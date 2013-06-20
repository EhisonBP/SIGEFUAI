package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect Ciudad_Roo_ToString {
    
    public String Ciudad.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("Nombre: ").append(getNombre()).append(", ");
        sb.append("Id_estado: ").append(getId_estado()).append(", ");
        sb.append("Descripcion: ").append(getDescripcion()).append(", ");
        sb.append("Estatus: ").append(getEstatus());
        return sb.toString();
    }
    
}
