package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect TecnicaDeControl_Roo_ToString {
    
    public String TecnicaDeControl.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("NombreClase: ").append(getNombreClase()).append(", ");
        sb.append("DescripcionLimpia: ").append(getDescripcionLimpia()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("Descripcion: ").append(getDescripcion()).append(", ");
        sb.append("Riesgo: ").append(getRiesgo()).append(", ");
        sb.append("Prueba: ").append(getPrueba() == null ? "null" : getPrueba().size()).append(", ");
        sb.append("Referencia: ").append(getReferencia()).append(", ");
        sb.append("EfectividadTecnicaControl: ").append(getEfectividadTecnicaControl());
        return sb.toString();
    }
    
}
