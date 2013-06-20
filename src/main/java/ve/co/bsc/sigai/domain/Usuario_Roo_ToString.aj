package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect Usuario_Roo_ToString {
    
    public String Usuario.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CurrentUserAuthorities: ").append(getCurrentUserAuthorities() == null ? "null" : getCurrentUserAuthorities().size()).append(", ");
        sb.append("NombreClase: ").append(getNombreClase()).append(", ");
        sb.append("RolesAsString: ").append(getRolesAsString() == null ? "null" : getRolesAsString().size()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("Login: ").append(getLogin()).append(", ");
        sb.append("Password: ").append(getPassword()).append(", ");
        sb.append("Roles: ").append(getRoles() == null ? "null" : getRoles().size()).append(", ");
        sb.append("Cargos: ").append(getCargos() == null ? "null" : getCargos().size()).append(", ");
        sb.append("Activo: ").append(getActivo());
        return sb.toString();
    }
    
}
