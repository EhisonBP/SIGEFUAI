package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect Comentario_Roo_ToString {
    
    public String Comentario.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("_id: ").append(get_id()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Tipo: ").append(getTipo()).append(", ");
        sb.append("RedaccionComentario: ").append(getRedaccionComentario()).append(", ");
        sb.append("Usuario: ").append(getUsuario());
        return sb.toString();
    }
    
}
