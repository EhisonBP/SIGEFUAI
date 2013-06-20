package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect ItemCuestionario_Roo_ToString {
    
    public String ItemCuestionario.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("NombreClase: ").append(getNombreClase()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("Pregunta: ").append(getPregunta()).append(", ");
        sb.append("Respuesta: ").append(getRespuesta()).append(", ");
        sb.append("Filedata: ").append(java.util.Arrays.toString(getFiledata())).append(", ");
        sb.append("FileExtension: ").append(getFileExtension()).append(", ");
        sb.append("FileName: ").append(getFileName()).append(", ");
        sb.append("Cuestionario: ").append(getCuestionario());
        return sb.toString();
    }
    
}
