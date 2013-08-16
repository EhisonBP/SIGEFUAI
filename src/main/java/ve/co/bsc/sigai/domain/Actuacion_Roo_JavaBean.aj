package ve.co.bsc.sigai.domain;

import java.lang.Boolean;
import java.lang.Integer;
import java.lang.String;
import java.util.Date;
import java.util.Set;
import ve.co.bsc.sigai.domain.Actuacion;
import ve.co.bsc.sigai.domain.Auditor;
import ve.co.bsc.sigai.domain.Biblioteca;
import ve.co.bsc.sigai.domain.ClaseActuacion;
import ve.co.bsc.sigai.domain.EstadoActuacion;
import ve.co.bsc.sigai.domain.OrganismoEnte;
import ve.co.bsc.sigai.domain.Proceso;
import ve.co.bsc.sigai.domain.Unidad;
import ve.co.bsc.sigai.domain.UnidadDeMedida;

privileged aspect Actuacion_Roo_JavaBean {
    
    public String Actuacion.getCodigo() {
        return this.codigo;
    }
    
    public void Actuacion.setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public String Actuacion.getNombre() {
        return this.nombre;
    }
    
    public void Actuacion.setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String Actuacion.getAlcance() {
        return this.alcance;
    }
    
    public void Actuacion.setAlcance(String alcance) {
        this.alcance = alcance;
    }
    
    public String Actuacion.getObjetivo() {
        return this.objetivo;
    }
    
    public void Actuacion.setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }
    
    public String Actuacion.getEnfoque() {
        return this.enfoque;
    }
    
    public void Actuacion.setEnfoque(String enfoque) {
        this.enfoque = enfoque;
    }
    
    public String Actuacion.getMetodo() {
        return this.metodo;
    }
    
    public void Actuacion.setMetodo(String metodo) {
        this.metodo = metodo;
    }
    
    public Auditor Actuacion.getResponsable() {
        return this.responsable;
    }
    
    public void Actuacion.setResponsable(Auditor responsable) {
        this.responsable = responsable;
    }
    
    public Set<Auditor> Actuacion.getResponsableAuditor() {
        return this.responsableAuditor;
    }
    
    public void Actuacion.setResponsableAuditor(Set<Auditor> responsableAuditor) {
        this.responsableAuditor = responsableAuditor;
    }
    
    public EstadoActuacion Actuacion.getEstadoActuacion() {
        return this.estadoActuacion;
    }
    
    public void Actuacion.setEstadoActuacion(EstadoActuacion estadoActuacion) {
        this.estadoActuacion = estadoActuacion;
    }
    
    public Actuacion Actuacion.getActuacion() {
        return this.actuacion;
    }
    
    public void Actuacion.setActuacion(Actuacion actuacion) {
        this.actuacion = actuacion;
    }
    
    public ClaseActuacion Actuacion.getClaseActuacion() {
        return this.claseActuacion;
    }
    
    public void Actuacion.setClaseActuacion(ClaseActuacion claseActuacion) {
        this.claseActuacion = claseActuacion;
    }
    
    public String[] Actuacion.getSeccionesVisibles() {
        return this.seccionesVisibles;
    }
    
    public void Actuacion.setSeccionesVisibles(String[] seccionesVisibles) {
        this.seccionesVisibles = seccionesVisibles;
    }
    
    public Set<Unidad> Actuacion.getUnidades() {
        return this.unidades;
    }
    
    public void Actuacion.setUnidades(Set<Unidad> unidades) {
        this.unidades = unidades;
    }
    
    public Integer Actuacion.getMesDesde() {
        return this.mesDesde;
    }
    
    public void Actuacion.setMesDesde(Integer mesDesde) {
        this.mesDesde = mesDesde;
    }
    
    public Integer Actuacion.getMesHasta() {
        return this.mesHasta;
    }
    
    public void Actuacion.setMesHasta(Integer mesHasta) {
        this.mesHasta = mesHasta;
    }
    
    public Integer Actuacion.getAnoFiscal() {
        return this.anoFiscal;
    }
    
    public void Actuacion.setAnoFiscal(Integer anoFiscal) {
        this.anoFiscal = anoFiscal;
    }
    
    public Integer Actuacion.getMesDesdeTentativo() {
        return this.mesDesdeTentativo;
    }
    
    public void Actuacion.setMesDesdeTentativo(Integer mesDesdeTentativo) {
        this.mesDesdeTentativo = mesDesdeTentativo;
    }
    
    public Integer Actuacion.getMesHastaTentativo() {
        return this.mesHastaTentativo;
    }
    
    public void Actuacion.setMesHastaTentativo(Integer mesHastaTentativo) {
        this.mesHastaTentativo = mesHastaTentativo;
    }
    
    public Integer Actuacion.getMesDesdeReal() {
        return this.mesDesdeReal;
    }
    
    public void Actuacion.setMesDesdeReal(Integer mesDesdeReal) {
        this.mesDesdeReal = mesDesdeReal;
    }
    
    public Integer Actuacion.getMesHastaReal() {
        return this.mesHastaReal;
    }
    
    public void Actuacion.setMesHastaReal(Integer mesHastaReal) {
        this.mesHastaReal = mesHastaReal;
    }
    
    public String Actuacion.getConclusionGeneral() {
        return this.conclusionGeneral;
    }
    
    public void Actuacion.setConclusionGeneral(String conclusionGeneral) {
        this.conclusionGeneral = conclusionGeneral;
    }
    
    public Biblioteca Actuacion.getBiblioteca() {
        return this.biblioteca;
    }
    
    public void Actuacion.setBiblioteca(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }
    
    public String Actuacion.getComentarios() {
        return this.comentarios;
    }
    
    public void Actuacion.setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
    
    public UnidadDeMedida Actuacion.getUnidadDeMedida() {
        return this.unidadDeMedida;
    }
    
    public void Actuacion.setUnidadDeMedida(UnidadDeMedida unidadDeMedida) {
        this.unidadDeMedida = unidadDeMedida;
    }
    
    public Integer Actuacion.getCorrelativo() {
        return this.correlativo;
    }
    
    public void Actuacion.setCorrelativo(Integer correlativo) {
        this.correlativo = correlativo;
    }
    
    public String Actuacion.getCodigoPlanificacion() {
        return this.codigoPlanificacion;
    }
    
    public void Actuacion.setCodigoPlanificacion(String codigoPlanificacion) {
        this.codigoPlanificacion = codigoPlanificacion;
    }
    
    public String Actuacion.getCodigoTotal() {
        return this.codigoTotal;
    }
    
    public void Actuacion.setCodigoTotal(String codigoTotal) {
        this.codigoTotal = codigoTotal;
    }
    
    public Boolean Actuacion.getPorProcesos() {
        return this.porProcesos;
    }
    
    public void Actuacion.setPorProcesos(Boolean porProcesos) {
        this.porProcesos = porProcesos;
    }
    
    public Set<Proceso> Actuacion.getProcesos() {
        return this.procesos;
    }
    
    public void Actuacion.setProcesos(Set<Proceso> procesos) {
        this.procesos = procesos;
    }
    
    public Date Actuacion.getFechaRegistro() {
        return this.fechaRegistro;
    }
    
    public void Actuacion.setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    public String Actuacion.getNombreProyecto() {
        return this.nombreProyecto;
    }
    
    public void Actuacion.setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }
    
    public String[] Actuacion.getAccionesPermitidas() {
        return this.accionesPermitidas;
    }
    
    public void Actuacion.setAccionesPermitidas(String[] accionesPermitidas) {
        this.accionesPermitidas = accionesPermitidas;
    }
    
    public OrganismoEnte Actuacion.getRif() {
        return this.rif;
    }
    
    public void Actuacion.setRif(OrganismoEnte rif) {
        this.rif = rif;
    }
    
}
