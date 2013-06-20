package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect EntradaBitacora_Roo_ToString {
    
    public String EntradaBitacora.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("Descripcion: ").append(getDescripcion()).append(", ");
        sb.append("TimeStamp: ").append(getTimeStamp()).append(", ");
        sb.append("Usuario: ").append(getUsuario()).append(", ");
        sb.append("Ip: ").append(getIp()).append(", ");
        sb.append("Tipo: ").append(getTipo());
        return sb.toString();
    }
    
}
