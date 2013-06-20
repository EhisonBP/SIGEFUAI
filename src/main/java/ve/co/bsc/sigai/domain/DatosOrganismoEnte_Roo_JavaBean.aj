package ve.co.bsc.sigai.domain;

import java.lang.Integer;
import java.lang.String;
import java.util.Date;
import ve.co.bsc.sigai.domain.CodigoArea;
import ve.co.bsc.sigai.domain.EstadoAuditor;

privileged aspect DatosOrganismoEnte_Roo_JavaBean {
    
    public String DatosOrganismoEnte.getRif() {
        return this.Rif;
    }
    
    public void DatosOrganismoEnte.setRif(String Rif) {
        this.Rif = Rif;
    }
    
    public String DatosOrganismoEnte.getPagina_web() {
        return this.pagina_web;
    }
    
    public void DatosOrganismoEnte.setPagina_web(String pagina_web) {
        this.pagina_web = pagina_web;
    }
    
    public String DatosOrganismoEnte.getDirecion() {
        return this.direcion;
    }
    
    public void DatosOrganismoEnte.setDirecion(String direcion) {
        this.direcion = direcion;
    }
    
    public String DatosOrganismoEnte.getEstado() {
        return this.estado;
    }
    
    public void DatosOrganismoEnte.setEstado(String estado) {
        this.estado = estado;
    }
    
    public String DatosOrganismoEnte.getCiudad() {
        return this.ciudad;
    }
    
    public void DatosOrganismoEnte.setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
    
    public String DatosOrganismoEnte.getMunicipios() {
        return this.municipios;
    }
    
    public void DatosOrganismoEnte.setMunicipios(String municipios) {
        this.municipios = municipios;
    }
    
    public Integer DatosOrganismoEnte.getTelefono_master() {
        return this.telefono_master;
    }
    
    public void DatosOrganismoEnte.setTelefono_master(Integer telefono_master) {
        this.telefono_master = telefono_master;
    }
    
    public Date DatosOrganismoEnte.getFechaCreacion() {
        return this.fechaCreacion;
    }
    
    public void DatosOrganismoEnte.setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public Date DatosOrganismoEnte.getFechaModificacion() {
        return this.fechaModificacion;
    }
    
    public void DatosOrganismoEnte.setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
    
    public EstadoAuditor DatosOrganismoEnte.getEstatus() {
        return this.Estatus;
    }
    
    public void DatosOrganismoEnte.setEstatus(EstadoAuditor Estatus) {
        this.Estatus = Estatus;
    }
    
    public CodigoArea DatosOrganismoEnte.getCodigoArea() {
        return this.CodigoArea;
    }
    
    public void DatosOrganismoEnte.setCodigoArea(CodigoArea CodigoArea) {
        this.CodigoArea = CodigoArea;
    }
    
}
