package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect Biblioteca_Roo_ToString {
    
    public String Biblioteca.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("Nombre: ").append(getNombre()).append(", ");
        sb.append("Descripcion: ").append(getDescripcion());
        return sb.toString();
    }
    
}
