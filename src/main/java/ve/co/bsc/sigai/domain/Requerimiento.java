package ve.co.bsc.sigai.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.persistence.Entity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Locale;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.validation.constraints.Size;
import java.text.ParseException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Representa un requerimiento solicitado a un auditado
 * @author carlosalvite
 */
@Entity
@RooJavaBean
@RooToString
@RooEntity(finders = { "findRequerimientoesByActuacion", "findRequerimientoesByAuditado", "findRequerimientoesByEstadoRequerimientoAndAuditado" })
public class Requerimiento implements ObjetoNombreClase, Serializable {

    @NotNull
    private String numeroSolicitud;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date fechaSolicitud;

    @Lob
    @Size(min = 0, max = 10000)
    @Column(length = 10000)
    private String observaciones;

    @Lob
    @Size(min = 0, max = 10000)
    @Column(length = 10000)
    private String asunto;

    @ManyToOne(targetEntity = Actuacion.class)
    @JoinColumn
    private Actuacion actuacion;

    @ManyToOne(targetEntity = Unidad.class)
    @JoinColumn
    private Unidad unidad;

    @ManyToOne(targetEntity = EstatusRequerimiento.class)
    @JoinColumn
    private EstatusRequerimiento estadoRequerimiento;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date fechaRecibidoTotalmente;

    @ManyToOne(targetEntity = Auditado.class)
    @JoinColumn
    private Auditado auditado;

    public String toString() {
        StringBuilder sb = new StringBuilder();
        String asunto = getAsunto();
        String subAsunto = asunto;
        if (asunto.length() > 39) subAsunto = asunto.substring(0, 39);
        sb.append(subAsunto);
        return sb.toString();
    }

    public static int getDiasHabiles(String sFechaIni, String sFechaFin) {
        Locale _currentLocale = new Locale("es", "VE");
        Calendar _calIni;
        Calendar _calFin = null;
        int iDiasAsueto = 0;
        int iDiasHabiles = 0;
        int iCountSabDom = 0;
        int iYear = Integer.parseInt(sFechaIni.substring(6, 10));
        int iMonth = Integer.parseInt(sFechaIni.substring(3, 5));
        int iDay = Integer.parseInt(sFechaIni.substring(0, 2));
        Calendar cldInicio = Calendar.getInstance(_currentLocale);
        _calIni = Calendar.getInstance(_currentLocale);
        _calIni.set(iYear, iMonth - 1, iDay);
        int viMesInicio = iMonth;
        cldInicio.setFirstDayOfWeek(cldInicio.MONDAY);
        cldInicio.set(iYear, iMonth - 1, iDay);
        int iDiaSemana = cldInicio.get(cldInicio.DAY_OF_WEEK);
        int iDInicio = cldInicio.get(cldInicio.DAY_OF_YEAR);
        iYear = Integer.parseInt(sFechaFin.substring(6, 10));
        iMonth = Integer.parseInt(sFechaFin.substring(3, 5));
        iDay = Integer.parseInt(sFechaFin.substring(0, 2));
        Calendar cldFin = Calendar.getInstance(_currentLocale);
        _calFin = Calendar.getInstance(_currentLocale);
        _calFin.set(iYear, iMonth - 1, iDay);
        cldFin.setFirstDayOfWeek(cldFin.MONDAY);
        cldFin.set(iYear, iMonth - 1, iDay);
        int viMesFin = iMonth;
        int iDiaSemanaFin = cldFin.get(cldFin.DAY_OF_WEEK);
        int iDFin = cldFin.get(cldFin.DAY_OF_YEAR);
        if ((cldInicio == null) || (cldFin == null)) return 0;
        int iNumeroDias = 0;
        if (viMesInicio == viMesFin) {
            iNumeroDias = iDFin - iDInicio;
        } else {
            iNumeroDias = (iDFin - iDInicio);
        }
        if (iDFin == iDInicio) iNumeroDias = 0;
        String sFechaIniAux = sFechaIni;
        if (sFechaIni.indexOf(" ") != -1) sFechaIniAux = sFechaIni.substring(0, sFechaIni.indexOf(" "));
        iCountSabDom = getSatSunDays2(sFechaIniAux, sFechaFin);
        iDiasHabiles = (iNumeroDias - iCountSabDom) - iDiasAsueto;
        if (iDiasHabiles < 0) iDiasHabiles = 0;
        return iDiasHabiles;
    }

    static int getSatSunDays2(String sFechaIni, String sFechaFin) {
        int iCountSatSun = 0;
        try {
            Calendar calIni = Calendar.getInstance();
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            Date dateIni = formato.parse(sFechaIni);
            Date dateFin = formato.parse(sFechaFin);
            calIni.setTime(dateIni);
            while (dateIni.compareTo(dateFin) <= 0) {
                if (calIni.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calIni.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    iCountSatSun++;
                }
                calIni.add(Calendar.DATE, 1);
                dateIni = calIni.getTime();
            }
        } catch (ParseException pe) {
            System.out.println(pe);
            iCountSatSun = 0;
        }
        return iCountSatSun;
    }

    public String toStringExtendido() {
        StringBuilder sb = new StringBuilder();
        sb.append("NumeroSolicitud: ").append(getNumeroSolicitud()).append(", ");
        sb.append("FechaSolicitud: ").append(getFechaSolicitud()).append(", ");
        sb.append("Observaciones: ").append(getObservaciones()).append(", ");
        sb.append("Asunto: ").append(getAsunto()).append(", ");
        sb.append("Actuacion: ").append(getActuacion() == null ? "n/a" : getActuacion().toStringLimitado()).append(", ");
        sb.append("Unidad: ").append(getUnidad() == null ? "n/a" : getUnidad().toStringLimitado()).append(", ");
        sb.append("FechaSolicitud: ").append(getFechaSolicitud()).append(", ");
        sb.append("EstatusRequerimiento: ").append(getEstadoRequerimiento() == null ? "n/a" : getEstadoRequerimiento().toStringLimitado()).append(", ");
        sb.append("FechaRecibidoTotalmente: ").append(getFechaRecibidoTotalmente()).append(", ");
        return sb.toString();
    }

    @SuppressWarnings("unchecked")
    public static Query findRequerimientoesByNotEstadoRequerimientoAndAuditado(EstatusRequerimiento estadoRequerimiento, Auditado auditado) {
        if (estadoRequerimiento == null) throw new IllegalArgumentException("The estadoRequerimiento argument is required");
        if (auditado == null) throw new IllegalArgumentException("The auditado argument is required");
        EntityManager em = Requerimiento.entityManager();
        Query q = em.createQuery("SELECT Requerimiento FROM Requerimiento AS requerimiento WHERE requerimiento.estadoRequerimiento != :estadoRequerimiento AND requerimiento.auditado = :auditado");
        q.setParameter("estadoRequerimiento", estadoRequerimiento);
        q.setParameter("auditado", auditado);
        return q;
    }

    public String toStringLimitado() {
        StringBuilder sb = new StringBuilder();
        sb.append(getNumeroSolicitud()).append(", ");
        return sb.toString();
    }

    @Override
    public String getNombreClase() {
        return "Requerimiento";
    }
}
