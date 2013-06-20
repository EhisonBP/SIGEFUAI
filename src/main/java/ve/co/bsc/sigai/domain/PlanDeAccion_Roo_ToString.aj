package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect PlanDeAccion_Roo_ToString {
    
    public String PlanDeAccion.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("NombreClase: ").append(getNombreClase()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("Descripcion: ").append(getDescripcion()).append(", ");
        sb.append("FechaCierre: ").append(getFechaCierre()).append(", ");
        sb.append("Unidad: ").append(getUnidad() == null ? "null" : getUnidad().size()).append(", ");
        sb.append("EstadoPlanDeAccion: ").append(getEstadoPlanDeAccion()).append(", ");
        sb.append("Observacion: ").append(getObservacion()).append(", ");
        sb.append("FechaCierre2: ").append(getFechaCierre2()).append(", ");
        sb.append("FechaSeguimiento: ").append(getFechaSeguimiento());
        return sb.toString();
    }
    
}
