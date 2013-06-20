package ve.co.bsc.sigai.domain;

import java.lang.Boolean;
import java.lang.String;
import java.util.Date;
import ve.co.bsc.sigai.domain.EstadoAuditor;
import ve.co.bsc.sigai.domain.EstructuraOrganizativa;
import ve.co.bsc.sigai.domain.OrganismoEnte;
import ve.co.bsc.sigai.domain.TipoUnidad;

privileged aspect EstructuraOrganizativa_Roo_JavaBean {
    
    public OrganismoEnte EstructuraOrganizativa.getRif() {
        return this.rif;
    }
    
    public void EstructuraOrganizativa.setRif(OrganismoEnte rif) {
        this.rif = rif;
    }
    
    public String EstructuraOrganizativa.getNombreUnidad() {
        return this.nombreUnidad;
    }
    
    public void EstructuraOrganizativa.setNombreUnidad(String nombreUnidad) {
        this.nombreUnidad = nombreUnidad;
    }
    
    public String EstructuraOrganizativa.getDescripcionUnidad() {
        return this.descripcionUnidad;
    }
    
    public void EstructuraOrganizativa.setDescripcionUnidad(String descripcionUnidad) {
        this.descripcionUnidad = descripcionUnidad;
    }
    
    public TipoUnidad EstructuraOrganizativa.getTipoUnidad() {
        return this.tipoUnidad;
    }
    
    public void EstructuraOrganizativa.setTipoUnidad(TipoUnidad tipoUnidad) {
        this.tipoUnidad = tipoUnidad;
    }
    
    public Date EstructuraOrganizativa.getFechaCreacion() {
        return this.fechaCreacion;
    }
    
    public void EstructuraOrganizativa.setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public Date EstructuraOrganizativa.getFechaModificacion() {
        return this.fechaModificacion;
    }
    
    public void EstructuraOrganizativa.setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
    
    public EstadoAuditor EstructuraOrganizativa.getEstatus() {
        return this.Estatus;
    }
    
    public void EstructuraOrganizativa.setEstatus(EstadoAuditor Estatus) {
        this.Estatus = Estatus;
    }
    
    public EstructuraOrganizativa EstructuraOrganizativa.getIdEstructuraPadre() {
        return this.idEstructuraPadre;
    }
    
    public void EstructuraOrganizativa.setIdEstructuraPadre(EstructuraOrganizativa idEstructuraPadre) {
        this.idEstructuraPadre = idEstructuraPadre;
    }
    
    public Boolean EstructuraOrganizativa.getVerificarUnidad() {
        return this.verificarUnidad;
    }
    
    public void EstructuraOrganizativa.setVerificarUnidad(Boolean verificarUnidad) {
        this.verificarUnidad = verificarUnidad;
    }
    
}
