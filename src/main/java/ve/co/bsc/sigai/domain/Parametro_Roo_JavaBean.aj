package ve.co.bsc.sigai.domain;

import java.lang.String;
import ve.co.bsc.sigai.domain.Reporte;

privileged aspect Parametro_Roo_JavaBean {
    
    public String Parametro.getParametro() {
        return this.parametro;
    }
    
    public void Parametro.setParametro(String parametro) {
        this.parametro = parametro;
    }
    
    public String Parametro.getTipoParametro() {
        return this.tipoParametro;
    }
    
    public void Parametro.setTipoParametro(String tipoParametro) {
        this.tipoParametro = tipoParametro;
    }
    
    public Reporte Parametro.getReporte() {
        return this.reporte;
    }
    
    public void Parametro.setReporte(Reporte reporte) {
        this.reporte = reporte;
    }
    
}
