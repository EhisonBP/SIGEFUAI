package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect Riesgo_Roo_ToString {
    
    public String Riesgo.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("NombreClase: ").append(getNombreClase()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("Descripcion: ").append(getDescripcion()).append(", ");
        sb.append("Proceso: ").append(getProceso()).append(", ");
        sb.append("Inactivo: ").append(getInactivo()).append(", ");
        sb.append("Referencia: ").append(getReferencia()).append(", ");
        sb.append("ClasificacionRiesgo: ").append(getClasificacionRiesgo()).append(", ");
        sb.append("FrecuenciaOcurrenciaRiesgo: ").append(getFrecuenciaOcurrenciaRiesgo());
        return sb.toString();
    }
    
}
