package ve.co.bsc.util;

import ve.co.bsc.sigai.domain.Actuacion;


public class ConstantesEmail {

	Actuacion actuacion = new Actuacion();
	
	public final String ASUNTO_COORDINADOR_1 = "SIGEFUAI: Aprobación y Asignación de la Actuación Fiscal"
			+ codigoActuacion;
	public final String CONTENIDO_COORDINADOR_3 = "Estimado"
			+ nombre.responsable
			+ " \n\nTengo a bien dirigirme a usted, con la finalidad de notificarle que la actuación fiscal "
			+ actuacion.getCodigoActuacionSinCod()
			+ ",se encuentra a la espera de ser asignada al Auditor a quien corresponda ejecutarla."
			+ "\n\n La cual Puede Ingresar Mediante la siguiente URL: "+ actuacion.getUrl();

	public static final String ASUNTO_AUDITOR_2 = "SIGEFUAI: ";
}
