package ve.co.bsc.sigai.form;

import ve.co.bsc.sigai.domain.Auditor;

public class OcupacionAuditorBusquedaForm{

    private Auditor auditor;
    private int idPlan;

    public OcupacionAuditorBusquedaForm(){
    
    }
    
    public int getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(int idPlan) {
        this.idPlan = idPlan;
    }

    public Auditor getAuditor() {
        return auditor;
    }

    public void setAuditor(Auditor auditor) {
        this.auditor = auditor;
    }


}