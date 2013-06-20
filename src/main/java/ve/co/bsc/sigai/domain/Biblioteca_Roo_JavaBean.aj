package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect Biblioteca_Roo_JavaBean {
    
    public String Biblioteca.getNombre() {
        return this.nombre;
    }
    
    public void Biblioteca.setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String Biblioteca.getDescripcion() {
        return this.descripcion;
    }
    
    public void Biblioteca.setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
