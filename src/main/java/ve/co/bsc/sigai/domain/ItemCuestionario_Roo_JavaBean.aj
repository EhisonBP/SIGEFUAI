package ve.co.bsc.sigai.domain;

import java.lang.String;
import ve.co.bsc.sigai.domain.Cuestionario;

privileged aspect ItemCuestionario_Roo_JavaBean {
    
    public String ItemCuestionario.getPregunta() {
        return this.pregunta;
    }
    
    public void ItemCuestionario.setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }
    
    public String ItemCuestionario.getRespuesta() {
        return this.respuesta;
    }
    
    public void ItemCuestionario.setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
    
    public byte[] ItemCuestionario.getFiledata() {
        return this.filedata;
    }
    
    public void ItemCuestionario.setFiledata(byte[] filedata) {
        this.filedata = filedata;
    }
    
    public String ItemCuestionario.getFileExtension() {
        return this.fileExtension;
    }
    
    public void ItemCuestionario.setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }
    
    public String ItemCuestionario.getFileName() {
        return this.fileName;
    }
    
    public void ItemCuestionario.setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public Cuestionario ItemCuestionario.getCuestionario() {
        return this.cuestionario;
    }
    
    public void ItemCuestionario.setCuestionario(Cuestionario cuestionario) {
        this.cuestionario = cuestionario;
    }
    
}
