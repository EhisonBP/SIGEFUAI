package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect SeccionDefault_Roo_ToString {
    
    public String SeccionDefault.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("NivelIndentacion: ").append(getNivelIndentacion()).append(", ");
        sb.append("NombreClase: ").append(getNombreClase()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("Codigo: ").append(getCodigo()).append(", ");
        sb.append("Descripcion: ").append(getDescripcion()).append(", ");
        sb.append("SeccionDefault: ").append(getSeccionDefault()).append(", ");
        sb.append("Biblioteca: ").append(getBiblioteca()).append(", ");
        sb.append("TipoSeccion: ").append(getTipoSeccion());
        return sb.toString();
    }
    
}
