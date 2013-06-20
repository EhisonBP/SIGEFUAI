package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect OtraActividad_Roo_ToString {
    
    public String OtraActividad.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DescripcionGeneral: ").append(getDescripcionGeneral()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("NombreClase: ").append(getNombreClase()).append(", ");
        sb.append("Numero: ").append(getNumero()).append(", ");
        sb.append("Objetivo: ").append(getObjetivo()).append(", ");
        sb.append("FechaInicio: ").append(getFechaInicio()).append(", ");
        sb.append("FechaFin: ").append(getFechaFin()).append(", ");
        sb.append("Unidad: ").append(getUnidad()).append(", ");
        sb.append("UnidadDeMedida: ").append(getUnidadDeMedida()).append(", ");
        sb.append("DescripcionCorta: ").append(getDescripcionCorta()).append(", ");
        sb.append("AccionesPermitidas: ").append(java.util.Arrays.toString(getAccionesPermitidas())).append(", ");
        sb.append("Rif: ").append(getRif());
        return sb.toString();
    }
    
}
