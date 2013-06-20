package ve.co.bsc.sigai.domain;

import java.lang.Long;
import java.lang.String;

privileged aspect Comentario_Roo_JavaBean {
    
    public Long Comentario.getId() {
        return this.id;
    }
    
    public void Comentario.setId(Long id) {
        this.id = id;
    }
    
    public String Comentario.getTipo() {
        return this.tipo;
    }
    
    public void Comentario.setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public String Comentario.getRedaccionComentario() {
        return this.redaccionComentario;
    }
    
    public void Comentario.setRedaccionComentario(String redaccionComentario) {
        this.redaccionComentario = redaccionComentario;
    }
    
    public String Comentario.getUsuario() {
        return this.usuario;
    }
    
    public void Comentario.setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
}
