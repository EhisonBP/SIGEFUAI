package ve.co.bsc.sigai.domain;

import java.lang.String;
import java.util.Date;
import ve.co.bsc.sigai.domain.EstadoAuditor;
import ve.co.bsc.sigai.domain.TipoOrganismo;
import ve.co.bsc.sigai.domain.TipoPersonaRif;

privileged aspect OrganismoEnte_Roo_JavaBean {
    
    public String OrganismoEnte.getNombre() {
        return this.Nombre;
    }
    
    public void OrganismoEnte.setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
    
    public String OrganismoEnte.getAcronimo() {
        return this.Acronimo;
    }
    
    public void OrganismoEnte.setAcronimo(String Acronimo) {
        this.Acronimo = Acronimo;
    }
    
    public TipoPersonaRif OrganismoEnte.getTipo_rif() {
        return this.tipo_rif;
    }
    
    public void OrganismoEnte.setTipo_rif(TipoPersonaRif tipo_rif) {
        this.tipo_rif = tipo_rif;
    }
    
    public String OrganismoEnte.getRif() {
        return this.Rif;
    }
    
    public void OrganismoEnte.setRif(String Rif) {
        this.Rif = Rif;
    }
    
    public String OrganismoEnte.getOrganismo_padre() {
        return this.organismo_padre;
    }
    
    public void OrganismoEnte.setOrganismo_padre(String organismo_padre) {
        this.organismo_padre = organismo_padre;
    }
    
    public Date OrganismoEnte.getFechaCreacion() {
        return this.fechaCreacion;
    }
    
    public void OrganismoEnte.setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public Date OrganismoEnte.getFechaModificacion() {
        return this.fechaModificacion;
    }
    
    public void OrganismoEnte.setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
    
    public EstadoAuditor OrganismoEnte.getEstatus() {
        return this.Estatus;
    }
    
    public void OrganismoEnte.setEstatus(EstadoAuditor Estatus) {
        this.Estatus = Estatus;
    }
    
    public TipoOrganismo OrganismoEnte.getId_tipo_organismo() {
        return this.id_tipo_organismo;
    }
    
    public void OrganismoEnte.setId_tipo_organismo(TipoOrganismo id_tipo_organismo) {
        this.id_tipo_organismo = id_tipo_organismo;
    }
    
}
