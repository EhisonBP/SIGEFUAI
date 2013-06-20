package ve.co.bsc.sigai.domain;

import java.lang.String;
import ve.co.bsc.sigai.domain.EstructuraCargos;
import ve.co.bsc.sigai.domain.OrganismoEnte;

privileged aspect AutoridadMaxima_Roo_JavaBean {
    
    public String AutoridadMaxima.getNombre() {
        return this.nombre;
    }
    
    public void AutoridadMaxima.setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String AutoridadMaxima.getCedula() {
        return this.cedula;
    }
    
    public void AutoridadMaxima.setCedula(String cedula) {
        this.cedula = cedula;
    }
    
    public int AutoridadMaxima.getEffectTypes() {
        return this.effectTypes;
    }
    
    public void AutoridadMaxima.setEffectTypes(int effectTypes) {
        this.effectTypes = effectTypes;
    }
    
    public EstructuraCargos AutoridadMaxima.getCargoAutoridad() {
        return this.cargoAutoridad;
    }
    
    public void AutoridadMaxima.setCargoAutoridad(EstructuraCargos cargoAutoridad) {
        this.cargoAutoridad = cargoAutoridad;
    }
    
    public OrganismoEnte AutoridadMaxima.getRif() {
        return this.rif;
    }
    
    public void AutoridadMaxima.setRif(OrganismoEnte rif) {
        this.rif = rif;
    }
    
}
