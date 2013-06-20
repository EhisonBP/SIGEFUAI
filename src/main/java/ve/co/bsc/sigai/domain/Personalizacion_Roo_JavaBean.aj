package ve.co.bsc.sigai.domain;

import java.lang.Integer;
import java.lang.String;
import ve.co.bsc.sigai.domain.CondicionAuditorInterno;
import ve.co.bsc.sigai.domain.TipoOrganismo;

privileged aspect Personalizacion_Roo_JavaBean {
    
    public String Personalizacion.getNombreInstitucion() {
        return this.nombreInstitucion;
    }
    
    public void Personalizacion.setNombreInstitucion(String nombreInstitucion) {
        this.nombreInstitucion = nombreInstitucion;
    }
    
    public String Personalizacion.getCorreoSunai() {
        return this.correoSunai;
    }
    
    public void Personalizacion.setCorreoSunai(String correoSunai) {
        this.correoSunai = correoSunai;
    }
    
    public byte[] Personalizacion.getArchivoImagen() {
        return this.archivoImagen;
    }
    
    public void Personalizacion.setArchivoImagen(byte[] archivoImagen) {
        this.archivoImagen = archivoImagen;
    }
    
    public String Personalizacion.getNombreTutelar() {
        return this.nombreTutelar;
    }
    
    public void Personalizacion.setNombreTutelar(String nombreTutelar) {
        this.nombreTutelar = nombreTutelar;
    }
    
    public TipoOrganismo Personalizacion.getTipoOrganismo() {
        return this.tipoOrganismo;
    }
    
    public void Personalizacion.setTipoOrganismo(TipoOrganismo tipoOrganismo) {
        this.tipoOrganismo = tipoOrganismo;
    }
    
    public String Personalizacion.getWeb() {
        return this.web;
    }
    
    public void Personalizacion.setWeb(String web) {
        this.web = web;
    }
    
    public String Personalizacion.getDireccion() {
        return this.direccion;
    }
    
    public void Personalizacion.setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public String Personalizacion.getMunicipio() {
        return this.Municipio;
    }
    
    public void Personalizacion.setMunicipio(String Municipio) {
        this.Municipio = Municipio;
    }
    
    public String Personalizacion.getCiudad() {
        return this.ciudad;
    }
    
    public void Personalizacion.setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
    
    public String Personalizacion.getEstado() {
        return this.estado;
    }
    
    public void Personalizacion.setEstado(String estado) {
        this.estado = estado;
    }
    
    public String Personalizacion.getNombreMaximaAutoridad() {
        return this.nombreMaximaAutoridad;
    }
    
    public void Personalizacion.setNombreMaximaAutoridad(String nombreMaximaAutoridad) {
        this.nombreMaximaAutoridad = nombreMaximaAutoridad;
    }
    
    public String Personalizacion.getCargoMaximaAutoridad() {
        return this.cargoMaximaAutoridad;
    }
    
    public void Personalizacion.setCargoMaximaAutoridad(String cargoMaximaAutoridad) {
        this.cargoMaximaAutoridad = cargoMaximaAutoridad;
    }
    
    public String Personalizacion.getTelefonoMaster() {
        return this.telefonoMaster;
    }
    
    public void Personalizacion.setTelefonoMaster(String telefonoMaster) {
        this.telefonoMaster = telefonoMaster;
    }
    
    public String Personalizacion.getTelefonoFax() {
        return this.telefonoFax;
    }
    
    public void Personalizacion.setTelefonoFax(String telefonoFax) {
        this.telefonoFax = telefonoFax;
    }
    
    public String Personalizacion.getNombreCompletoAuditorInterno() {
        return this.nombreCompletoAuditorInterno;
    }
    
    public void Personalizacion.setNombreCompletoAuditorInterno(String nombreCompletoAuditorInterno) {
        this.nombreCompletoAuditorInterno = nombreCompletoAuditorInterno;
    }
    
    public CondicionAuditorInterno Personalizacion.getCondicionAuditorInterno() {
        return this.condicionAuditorInterno;
    }
    
    public void Personalizacion.setCondicionAuditorInterno(CondicionAuditorInterno condicionAuditorInterno) {
        this.condicionAuditorInterno = condicionAuditorInterno;
    }
    
    public String Personalizacion.getCedula() {
        return this.cedula;
    }
    
    public void Personalizacion.setCedula(String cedula) {
        this.cedula = cedula;
    }
    
    public String Personalizacion.getProfesion() {
        return this.profesion;
    }
    
    public void Personalizacion.setProfesion(String profesion) {
        this.profesion = profesion;
    }
    
    public String Personalizacion.getTiempoEnElCargo() {
        return this.tiempoEnElCargo;
    }
    
    public void Personalizacion.setTiempoEnElCargo(String tiempoEnElCargo) {
        this.tiempoEnElCargo = tiempoEnElCargo;
    }
    
    public String Personalizacion.getDesignadoSegun() {
        return this.designadoSegun;
    }
    
    public void Personalizacion.setDesignadoSegun(String designadoSegun) {
        this.designadoSegun = designadoSegun;
    }
    
    public String Personalizacion.getExperienciaAnos() {
        return this.experienciaAnos;
    }
    
    public void Personalizacion.setExperienciaAnos(String experienciaAnos) {
        this.experienciaAnos = experienciaAnos;
    }
    
    public String Personalizacion.getExperienciaMeses() {
        return this.experienciaMeses;
    }
    
    public void Personalizacion.setExperienciaMeses(String experienciaMeses) {
        this.experienciaMeses = experienciaMeses;
    }
    
    public String Personalizacion.getTelefonoOficina() {
        return this.telefonoOficina;
    }
    
    public void Personalizacion.setTelefonoOficina(String telefonoOficina) {
        this.telefonoOficina = telefonoOficina;
    }
    
    public String Personalizacion.getTelefonoMovil() {
        return this.telefonoMovil;
    }
    
    public void Personalizacion.setTelefonoMovil(String telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }
    
    public String Personalizacion.getCorreouai() {
        return this.correouai;
    }
    
    public void Personalizacion.setCorreouai(String correouai) {
        this.correouai = correouai;
    }
    
    public String Personalizacion.getCorreoInstitucional() {
        return this.correoInstitucional;
    }
    
    public void Personalizacion.setCorreoInstitucional(String correoInstitucional) {
        this.correoInstitucional = correoInstitucional;
    }
    
    public String Personalizacion.getCorreoPersonal() {
        return this.correoPersonal;
    }
    
    public void Personalizacion.setCorreoPersonal(String correoPersonal) {
        this.correoPersonal = correoPersonal;
    }
    
    public Integer Personalizacion.getCantAuditorInterno() {
        return this.cantAuditorInterno;
    }
    
    public void Personalizacion.setCantAuditorInterno(Integer cantAuditorInterno) {
        this.cantAuditorInterno = cantAuditorInterno;
    }
    
    public Integer Personalizacion.getCantDirectivos() {
        return this.cantDirectivos;
    }
    
    public void Personalizacion.setCantDirectivos(Integer cantDirectivos) {
        this.cantDirectivos = cantDirectivos;
    }
    
    public Integer Personalizacion.getCantCoordinadores() {
        return this.cantCoordinadores;
    }
    
    public void Personalizacion.setCantCoordinadores(Integer cantCoordinadores) {
        this.cantCoordinadores = cantCoordinadores;
    }
    
    public Integer Personalizacion.getCantProfesionales() {
        return this.cantProfesionales;
    }
    
    public void Personalizacion.setCantProfesionales(Integer cantProfesionales) {
        this.cantProfesionales = cantProfesionales;
    }
    
    public Integer Personalizacion.getCantTecnicos() {
        return this.cantTecnicos;
    }
    
    public void Personalizacion.setCantTecnicos(Integer cantTecnicos) {
        this.cantTecnicos = cantTecnicos;
    }
    
    public Integer Personalizacion.getCantNoProfesionales() {
        return this.cantNoProfesionales;
    }
    
    public void Personalizacion.setCantNoProfesionales(Integer cantNoProfesionales) {
        this.cantNoProfesionales = cantNoProfesionales;
    }
    
    public String Personalizacion.getRif() {
        return this.Rif;
    }
    
    public void Personalizacion.setRif(String Rif) {
        this.Rif = Rif;
    }
    
}
