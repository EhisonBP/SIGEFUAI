package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect AutoridadMaxima_Roo_ToString {
    
    public String AutoridadMaxima.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("Nombre: ").append(getNombre()).append(", ");
        sb.append("Cedula: ").append(getCedula()).append(", ");
        sb.append("EffectTypes: ").append(getEffectTypes()).append(", ");
        sb.append("CargoAutoridad: ").append(getCargoAutoridad()).append(", ");
        sb.append("Rif: ").append(getRif());
        return sb.toString();
    }
    
}
