package ve.co.bsc.sigai.domain;

import java.lang.String;
import ve.co.bsc.sigai.domain.Reporte;

privileged aspect Reporte_Roo_JavaBean {
    
    public String Reporte.getNombre() {
        return this.nombre;
    }
    
    public void Reporte.setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String Reporte.getDescripcion() {
        return this.descripcion;
    }
    
    public void Reporte.setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public byte[] Reporte.getFiledata() {
        return this.filedata;
    }
    
    public void Reporte.setFiledata(byte[] filedata) {
        this.filedata = filedata;
    }
    
    public String Reporte.getExtension() {
        return this.extension;
    }
    
    public void Reporte.setExtension(String extension) {
        this.extension = extension;
    }
    
    public Reporte Reporte.getReporte() {
        return this.reporte;
    }
    
    public void Reporte.setReporte(Reporte reporte) {
        this.reporte = reporte;
    }
    
    public String Reporte.getClave() {
        return this.clave;
    }
    
    public void Reporte.setClave(String clave) {
        this.clave = clave;
    }
    
}
