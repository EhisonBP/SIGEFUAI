package ve.co.bsc.sigai.domain;

import java.lang.String;

privileged aspect Personalizacion_Roo_ToString {
    
    public String Personalizacion.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CorreoInstitucionalSimple: ").append(getCorreoInstitucionalSimple()).append(", ");
        sb.append("CorreouaiSimple: ").append(getCorreouaiSimple()).append(", ");
        sb.append("CorreoSunaiSimple: ").append(getCorreoSunaiSimple()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("NombreInstitucion: ").append(getNombreInstitucion()).append(", ");
        sb.append("CorreoSunai: ").append(getCorreoSunai()).append(", ");
        sb.append("ArchivoImagen: ").append(java.util.Arrays.toString(getArchivoImagen())).append(", ");
        sb.append("NombreTutelar: ").append(getNombreTutelar()).append(", ");
        sb.append("TipoOrganismo: ").append(getTipoOrganismo()).append(", ");
        sb.append("Web: ").append(getWeb()).append(", ");
        sb.append("Direccion: ").append(getDireccion()).append(", ");
        sb.append("Municipio: ").append(getMunicipio()).append(", ");
        sb.append("Ciudad: ").append(getCiudad()).append(", ");
        sb.append("Estado: ").append(getEstado()).append(", ");
        sb.append("NombreMaximaAutoridad: ").append(getNombreMaximaAutoridad()).append(", ");
        sb.append("CargoMaximaAutoridad: ").append(getCargoMaximaAutoridad()).append(", ");
        sb.append("TelefonoMaster: ").append(getTelefonoMaster()).append(", ");
        sb.append("TelefonoFax: ").append(getTelefonoFax()).append(", ");
        sb.append("NombreCompletoAuditorInterno: ").append(getNombreCompletoAuditorInterno()).append(", ");
        sb.append("CondicionAuditorInterno: ").append(getCondicionAuditorInterno()).append(", ");
        sb.append("Cedula: ").append(getCedula()).append(", ");
        sb.append("Profesion: ").append(getProfesion()).append(", ");
        sb.append("TiempoEnElCargo: ").append(getTiempoEnElCargo()).append(", ");
        sb.append("DesignadoSegun: ").append(getDesignadoSegun()).append(", ");
        sb.append("ExperienciaAnos: ").append(getExperienciaAnos()).append(", ");
        sb.append("ExperienciaMeses: ").append(getExperienciaMeses()).append(", ");
        sb.append("TelefonoOficina: ").append(getTelefonoOficina()).append(", ");
        sb.append("TelefonoMovil: ").append(getTelefonoMovil()).append(", ");
        sb.append("Correouai: ").append(getCorreouai()).append(", ");
        sb.append("CorreoInstitucional: ").append(getCorreoInstitucional()).append(", ");
        sb.append("CorreoPersonal: ").append(getCorreoPersonal()).append(", ");
        sb.append("CantAuditorInterno: ").append(getCantAuditorInterno()).append(", ");
        sb.append("CantDirectivos: ").append(getCantDirectivos()).append(", ");
        sb.append("CantCoordinadores: ").append(getCantCoordinadores()).append(", ");
        sb.append("CantProfesionales: ").append(getCantProfesionales()).append(", ");
        sb.append("CantTecnicos: ").append(getCantTecnicos()).append(", ");
        sb.append("CantNoProfesionales: ").append(getCantNoProfesionales()).append(", ");
        sb.append("Rif: ").append(getRif());
        return sb.toString();
    }
    
}
