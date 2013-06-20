/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ve.co.bsc.sigai.form;

import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
import ve.co.bsc.sigai.domain.TipoEntradaBitacora;

/**
 *
 * @author User
 */
public class TipoEntradaBitacoraForm {

    private TipoEntradaBitacora tipo;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "SM")
    private Date timeStampDesde;

    public Date getTimeStampDesde() {
        return timeStampDesde;
    }

    public void setTimeStampDesde(Date timeStampDesde) {
        this.timeStampDesde = timeStampDesde;
    }

    public Date getTimeStampHasta() {
        return timeStampHasta;
    }

    public void setTimeStampHasta(Date timeStampHasta) {
        this.timeStampHasta = timeStampHasta;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "SM")
    private Date timeStampHasta;


    private String usuario;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    
    private String ip;

    public TipoEntradaBitacoraForm(){

    }

    public TipoEntradaBitacora getTipo() {
        return tipo;
    }

    public void setTipo(TipoEntradaBitacora tipo) {
        this.tipo = tipo;
    }

}
