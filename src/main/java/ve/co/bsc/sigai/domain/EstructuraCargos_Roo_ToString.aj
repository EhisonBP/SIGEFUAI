package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect EstructuraCargos_Roo_ToString {
    
    public String EstructuraCargos.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("NombreClase: ").append(getNombreClase()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("NombreCargo: ").append(getNombreCargo()).append(", ");
        sb.append("DescripcionCargo: ").append(getDescripcionCargo()).append(", ");
        sb.append("FuncionesCargo: ").append(getFuncionesCargo()).append(", ");
        sb.append("FechaCreacion: ").append(getFechaCreacion()).append(", ");
        sb.append("FechaModificacion: ").append(getFechaModificacion()).append(", ");
        sb.append("Estado: ").append(getEstado()).append(", ");
        sb.append("IdEstructura: ").append(getIdEstructura()).append(", ");
        sb.append("Rif: ").append(getRif());
        return sb.toString();
    }
    
}
