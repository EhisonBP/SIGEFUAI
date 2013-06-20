package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect DatosOrganismoEnte_Roo_ToString {
    
    public String DatosOrganismoEnte.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("Rif: ").append(getRif()).append(", ");
        sb.append("Pagina_web: ").append(getPagina_web()).append(", ");
        sb.append("Direcion: ").append(getDirecion()).append(", ");
        sb.append("Estado: ").append(getEstado()).append(", ");
        sb.append("Ciudad: ").append(getCiudad()).append(", ");
        sb.append("Municipios: ").append(getMunicipios()).append(", ");
        sb.append("Telefono_master: ").append(getTelefono_master()).append(", ");
        sb.append("FechaCreacion: ").append(getFechaCreacion()).append(", ");
        sb.append("FechaModificacion: ").append(getFechaModificacion()).append(", ");
        sb.append("Estatus: ").append(getEstatus()).append(", ");
        sb.append("CodigoArea: ").append(getCodigoArea());
        return sb.toString();
    }
    
}
